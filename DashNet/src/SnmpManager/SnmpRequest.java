package SnmpManager;
 
 import java.io.IOException ;
 import java.util.Vector ;
 import org.snmp4j.*;
 import org.snmp4j.mp.*;
 import org.snmp4j.security.*;
 import org.snmp4j.smi.*;
 import org.snmp4j.transport.*;
 import org.snmp4j.util.*;
 import org.snmp4j.event.ResponseEvent;
 import java.util.StringTokenizer ;
 import org.snmp4j.log.LogFactory;
 import org.snmp4j.log.LogLevel;
 

public class SnmpRequest implements CommandResponder, PDUFactory{

    public static final int DEFAULT = 0;
    public static final int WALK = 1;
    public static final int LISTEN = 2;
    public static final int TABLE = 3;
    public static final int CVS_TABLE = 4;
    public static final int TIME_BASED_CVS_TABLE = 5;
 
    Target target;
    Address address;
    OID authProtocol;
    OID privProtocol;
    OctetString privPassphrase;
    OctetString authPassphrase;
    OctetString community = new OctetString("public");
    OctetString authoritativeEngineID;
    OctetString contextEngineID;
    OctetString contextName = new OctetString();
    OctetString securityName = new OctetString();
    OctetString localEngineID = new OctetString(MPv3.createLocalEngineID());
    TimeTicks sysUpTime = new TimeTicks(0);
    OID trapOID = SnmpConstants.coldStart;
    PDUv1 v1TrapPDU = new PDUv1();
    int version = SnmpConstants.version3;
    int engineBootCount = 0;
    int retries = 1;
    int timeout = 1000;
    int pduType = PDU.GETNEXT;
    int maxRepetitions = 10;
    int nonRepeaters = 0;
    int maxSizeResponsePDU = 65535;
    Vector vbs = new Vector ();
    public int operation = DEFAULT;
    int numDispatcherThreads = 2;
    boolean useDenseTableOperation = false;
    OID lowerBoundIndex, upperBoundIndex;
 
    public SnmpRequest(String [] args){
    
        CounterSupport.getInstance().addCounterListener(new DefaultCounterListener());
 
        vbs.add(new VariableBinding(new OID("1.3.6")));
        int paramStart = parseArgs(args);
        if (paramStart >= args.length){
            printUsage();
            System.exit(1);
        }else{
            checkOptions();
            address = getAddress(args[paramStart++]);
            Vector vbs = getVariableBindings(args, paramStart);
            checkTrapVariables(vbs);
            if (vbs.size() > 0){
                this.vbs = vbs;
            }
        }
    }
 
    public int getPduType(){ return pduType; }
    
    private void checkOptions(){
     
       if ((operation == WALK) && ((pduType != PDU.GETBULK) && (pduType != PDU.GETNEXT))){
            throw new IllegalArgumentException ("Walk operation is not supported for PDU type: " + PDU.getTypeString(pduType));
        }else if ((operation == WALK) && (vbs.size() != 1)){
            throw new IllegalArgumentException ("There must be exactly one OID supplied for walk operations");
        }
        if ((pduType == PDU.V1TRAP) && (version != SnmpConstants.version1)){
            throw new IllegalArgumentException ("V1TRAP PDU type is only available for SNMP version 1");
        }
    }
 
    private void checkTrapVariables(Vector vbs){
        
        if ((pduType == PDU.INFORM) || (pduType == PDU.TRAP)){
            if ((vbs.isEmpty()) || ((vbs.size() > 1) && (!((VariableBinding) vbs.get(0)).getOid().equals(SnmpConstants.sysUpTime)))){
                vbs.add(0, new VariableBinding(SnmpConstants.sysUpTime, sysUpTime));
            }
            if ((vbs.size() == 1) || ((vbs.size() > 2) && (!((VariableBinding) vbs.get(1)).getOid().equals(SnmpConstants.snmpTrapOID)))){
                vbs.add(1, new VariableBinding(SnmpConstants.snmpTrapOID, trapOID));
            }
        }
    }
 
    public synchronized void listen() throws IOException{
        
        AbstractTransportMapping transport;
        
        if (address instanceof TcpAddress){
            transport = new DefaultTcpTransportMapping((TcpAddress) address);
        }else{
            transport = new DefaultUdpTransportMapping((UdpAddress) address);
        }
        ThreadPool threadPool = ThreadPool.create("DispatcherPool", numDispatcherThreads);
        MessageDispatcher mtDispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());
 
        mtDispatcher.addMessageProcessingModel(new MPv1());
        mtDispatcher.addMessageProcessingModel(new MPv2c());
        mtDispatcher.addMessageProcessingModel(new MPv3());
 
        SecurityProtocols.getInstance().addDefaultProtocols();
 
        Snmp snmp = new Snmp(mtDispatcher, transport);
        
        if (version == SnmpConstants.version3){
            USM usm = new USM(SecurityProtocols.getInstance(), localEngineID, 0);
            SecurityModels.getInstance().addSecurityModel(usm);
            if (authoritativeEngineID != null){
                snmp.setLocalEngine(authoritativeEngineID.getValue(), 0, 0);
            }
            addUsmUser(snmp);
        }else{
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(community);
            this.target = target;
        }
        snmp.addCommandResponder(this); 
        transport.listen();
        System.out.println("Listening on "+address);
 
        try{
            this.wait();
        }catch (InterruptedException ex){ }
    }
 
    private void addUsmUser(Snmp snmp){

        snmp.getUSM().addUser(securityName, new UsmUser(securityName, authProtocol, authPassphrase, privProtocol, privPassphrase));
   }
 
   private Snmp createSnmpSession() throws IOException{
        
        AbstractTransportMapping transport;
        
        if (address instanceof TcpAddress){
            transport = new DefaultTcpTransportMapping();
        }else{
            transport = new DefaultUdpTransportMapping();
        }
     
        Snmp snmp = new Snmp(transport);
 
        if (version == SnmpConstants.version3){
            USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), engineBootCount);
            SecurityModels.getInstance().addSecurityModel(usm);
            addUsmUser(snmp);
        }
        return snmp;
    }
 
    private Target createTarget(){
        
        if (version == SnmpConstants.version3){
            UserTarget target = new UserTarget();        
            if (authPassphrase != null){
                if (privPassphrase != null){
                    target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
                }else{
                    target.setSecurityLevel(SecurityLevel.AUTH_NOPRIV);
                }
            }else{
                target.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
            }
            target.setSecurityName(securityName);
            return target;
        }else{
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(community);
            return target;
        }
    }
 
    public PDU send() throws IOException{
        
        Snmp snmp = createSnmpSession();
        this.target = createTarget();
        target.setVersion(version);
        target.setAddress(address);
        target.setRetries(retries);
        target.setTimeout(timeout);
        target.setMaxSizeRequestPDU(maxSizeResponsePDU);
        snmp.listen();
        PDU request = createPDU(target);
     
        if (request.getType() == PDU.GETBULK){
            request.setMaxRepetitions(maxRepetitions);
            request.setNonRepeaters(nonRepeaters);
        }
        for (Object vb : vbs) {
            request.add((VariableBinding) vb);
        }
 
        PDU response = null;
        
        if (operation == WALK){
            walk(snmp, request, target);
            return null;
        }else{
            ResponseEvent responseEvent;
            long startTime = System.currentTimeMillis();
            responseEvent = snmp.send(request, target);
            if (responseEvent != null){
                response = responseEvent.getResponse();
                System.out.println("Received response after " + (System.currentTimeMillis()-startTime)+" millis");
            }
        }
        snmp.close();
        return response;
    }
 
    private PDU walk(Snmp snmp, PDU request, Target target) throws IOException{
    
        request.setNonRepeaters(0);
        OID rootOID = request.get(0).getOid();
        PDU response = null;
        final long startTime = System.currentTimeMillis();
        TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
        treeUtils.getSubtree(target, rootOID, null, new TreeListener(){
            @Override
            public boolean next(TreeEvent e){
                    
                if (e.getVariableBindings() != null){
                    for (VariableBinding variableBinding : e.getVariableBindings()) {
                        System.out.println(variableBinding.toString());
                    }
                }
                return true;
            }
 
            @Override
            public void finished(TreeEvent e){
                
                System.out.println("\nTotal walk time: " + (System.currentTimeMillis()-startTime) + " milliseconds");
                
                if (e.isError()){
                    System.err.println("The following error occurred during walk:");
                    System.err.println(e.getErrorMessage());
                }
            }

            @Override
            public boolean isFinished() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        return response;
    }
 
    private static Vector getVariableBindings(String [] args, int position){
        
        Vector v = new Vector (args.length-position+1);
        
        for (int i=position; i<args.length; i++){
            String oid = args[i];
            char type = 'i';
            String value = null;
            int equal = oid.indexOf("={");
            
            if (equal > 0){
                oid = args[i].substring(0, equal);
                type = args[i].charAt(equal+2);
                value = args[i].substring(args[i].indexOf('}')+1);
            }else if (oid.indexOf('-') > 0){
                StringTokenizer st = new StringTokenizer (oid, "-");
                if (st.countTokens() != 2){
                    throw new IllegalArgumentException ("Illegal OID range specified: '" + oid);
                }
                oid = st.nextToken();
                VariableBinding vbLower = new VariableBinding(new OID(oid));
                v.add(vbLower);
                long last = Long.parseLong(st.nextToken());
                long first = vbLower.getOid().lastUnsigned();
                
                for (long k=first+1; k<=last; k++){
                    OID nextOID = new OID(vbLower.getOid().getValue(), 0, vbLower.getOid().size()-1);
                    nextOID.appendUnsigned(k);
                    VariableBinding next = new VariableBinding(nextOID);
                    v.add(next);
                }
                continue;
            }
            VariableBinding vb = new VariableBinding(new OID(oid));
            if (value != null){
                Variable variable;
                switch (type){
                    case 'i':
                        variable = new Integer32(Integer.parseInt(value));
                        break;
                    case 'u':
                        variable = new UnsignedInteger32(Long.parseLong(value));
                        break;
                    case 's':
                        variable = new OctetString(value);
                        break;
                    case 'x':
                        variable = OctetString.fromString(value, ':', 16);
                        break;
                    case 'd':
                        variable = OctetString.fromString(value, '.', 10);
                        break;
                    case 'b':
                        variable = OctetString.fromString(value, ' ', 2);
                        break;
                    case 'n':
                        variable = new Null();
                        break;
                    case 'o':
                        variable = new OID(value);
                        break;
                    case 't':
                        variable = new TimeTicks(Long.parseLong(value));
                        break;
                    case 'a':
                        variable = new IpAddress(value);
                        break;
                    default:
                        throw new IllegalArgumentException ("Variable type " + type + " not supported");
                }
                vb.setVariable(variable);
            }
            v.add(vb);
        }
        return v;
    }
 
    private static Address getAddress(String transportAddress){
        
        String transport = "udp";
        int colon = transportAddress.indexOf(':');
        
        if (colon > 0){
            transport = transportAddress.substring(0, colon);
            transportAddress = transportAddress.substring(colon+1);
        }
        if (transportAddress.indexOf('/') < 0){
            transportAddress += "/161";
        }
        if (transport.equalsIgnoreCase("udp")){
            return new UdpAddress(transportAddress);
        }else if (transport.equalsIgnoreCase("tcp")){
            return new TcpAddress(transportAddress);
        }
        throw new IllegalArgumentException ("Unknown transport "+transport);
    }
 
    private static String nextOption(String [] args, int position){
        
        if (position+1 >= args.length){
            throw new IllegalArgumentException ("Missing option value for " + args[position]);
        }
        return args[position+1];
    }
 
    private static OctetString createOctetString(String s){
        
        OctetString octetString;
        
        if (s.startsWith("0x")){
            octetString = OctetString.fromHexString(s.substring(2), ':');
        }else{
            octetString = new OctetString(s);
        }
        return octetString;
    }
 
    private int parseArgs(String [] args){
        
        for (int i=0; i<args.length; i++){
            if (args[i].equals("-a")){
                String s = nextOption(args, i++);
                switch (s) {
                    case "MD5":
                        authProtocol = AuthMD5.ID;
                        break;
                    case "SHA":
                        authProtocol = AuthSHA.ID;
                        break;
                    default:
                        throw new IllegalArgumentException ("Authentication protocol unsupported: "+s);
                }
            }else if (args[i].equals("-A")){
                authPassphrase = createOctetString(nextOption(args, i++));
            }else if (args[i].equals("-X") || args[i].equals("-P")){
                privPassphrase = createOctetString(nextOption(args, i++));
            }else if (args[i].equals("-c")){
                community = createOctetString(nextOption(args, i++));
            }else if (args[i].equals("-b")){
                engineBootCount = Math.max(Integer.parseInt(nextOption(args, i++)), 0);
            }else if (args[i].equals("-d")){
                String debugOption = nextOption(args, i++);
                LogFactory.getLogFactory().getRootLogger().setLogLevel(
                LogLevel.toLevel(debugOption));
            }else if (args[i].equals("-l")){
                localEngineID = createOctetString(nextOption(args, i++));
            }else if (args[i].equals("-e")){
                authoritativeEngineID = createOctetString(nextOption(args, i++));
            }else if (args[i].equals("-E")){
                contextEngineID = createOctetString(nextOption(args, i++));
            }else if (args[i].equals("-h")){
                printUsage();
                System.exit(0);
            }else if (args[i].equals("-n")){
                contextName = createOctetString(nextOption(args, i++));
            }else if (args[i].equals("-m")){
                maxSizeResponsePDU = Integer.parseInt(nextOption(args, i++));
            }else if (args[i].equals("-r")){
                retries = Integer.parseInt(nextOption(args, i++));
            }else if (args[i].equals("-t")){
                timeout = Integer.parseInt(nextOption(args, i++));
            }else if (args[i].equals("-u")){
                securityName = createOctetString(nextOption(args, i++));
            }else if (args[i].equals("-V")){
                printVersion();
                System.exit(0);
            }else if (args[i].equals("-Cr")){
                maxRepetitions = Integer.parseInt(nextOption(args, i++));
            }else if (args[i].equals("-Cn")){
                nonRepeaters = Integer.parseInt(nextOption(args, i++));
            }else if (args[i].equals("-Ce")){
                v1TrapPDU.setEnterprise(new OID(nextOption(args, i++)));
            }else if (args[i].equals("-Ct")){
                trapOID = new OID(nextOption(args, i++));
            }else if (args[i].equals("-Cg")){
                v1TrapPDU.setGenericTrap(Integer.parseInt(nextOption(args, i++)));
            }else if (args[i].equals("-Cs")){
                v1TrapPDU.setSpecificTrap(Integer.parseInt(nextOption(args, i++)));
            }else if (args[i].equals("-Ca")){
                v1TrapPDU.setAgentAddress(new IpAddress(nextOption(args, i++)));
            }else if (args[i].equals("-Cu")){
                String upTime = nextOption(args, i++);
                v1TrapPDU.setTimestamp(Long.parseLong(upTime));
                sysUpTime.setValue(Long.parseLong(upTime));
            }else if (args[i].equals("-Ow")){
                operation = WALK;
            }else if (args[i].equals("-Ol")){
                operation = LISTEN;
            }else if (args[i].equals("-OtCSV")){
                operation = CVS_TABLE;
            }else if (args[i].equals("-OttCSV")){
                operation = TIME_BASED_CVS_TABLE;
            }else if (args[i].equals("-Ot")){
                operation = TABLE;
            }else if (args[i].equals("-Otd")){
                operation = TABLE;
                useDenseTableOperation = true;
            }else if (args[i].equals("-Cil")){
                lowerBoundIndex = new OID(nextOption(args, i++));
            }else if (args[i].equals("-Ciu")){
                upperBoundIndex = new OID(nextOption(args, i++));
            }else if (args[i].equals("-v")){
                String v = nextOption(args, i++);
                switch (v) {
                    case "1":
                        version = SnmpConstants.version1;
                        break;
                    case "2c":
                        version = SnmpConstants.version2c;
                        break;
                    case "3":
                        version = SnmpConstants.version3;
                        break;
                    default:
                        throw new IllegalArgumentException ("Version "+v+" not supported");
                }
            }else if (args[i].equals("-x")){
                String s = nextOption(args, i++);
                switch (s) {
                    case "DES":
                        privProtocol = PrivDES.ID;
                        break;
                    case "AES128":
                    case "AES":
                        privProtocol = PrivAES128.ID;
                        break;
                    case "AES192":
                        privProtocol = PrivAES192.ID;
                        break;
                    case "AES256":
                        privProtocol = PrivAES256.ID;
                        break;
                    default:
                        throw new IllegalArgumentException ("Privacy protocol " + s + " not supported");
                }
            }else if (args[i].equals("-p")){
                String s = nextOption(args, i++);
                pduType = PDU.getTypeFromString(s);
                if (pduType == Integer.MIN_VALUE){
                    throw new IllegalArgumentException ("Unknown PDU type " + s);
                }
            }else if (!args[i].startsWith("-")){
                return i;
            }else{
                throw new IllegalArgumentException ("Unknown option "+args[i]);
            }
        }
        return 0;
    }
 
    protected static void printVersion(){
        
        System.out.println();
        System.out.println("SNMP4J Command Line Tool v1.8.2 Copyright " + (char)0xA9 + " 2004-2007, Frank Fock and Jochen Katz");
        System.out.println("http://www.snmp4j.org");
        System.out.println();
        System.out.println("SNMP4J is licensed under the Apache License 2.0:");
        System.out.println("http://www.apache.org/licenses/LICENSE-2.0.txt");
        System.out.println();
    }
 
    protected static void printUsage(){
        
        String[] usage = new String[]{
            "",
            "Usage: SNMP4J [options] [transport:]address [OID[={type}value] ...]\n",
            " -a authProtocol Sets the authentication protocol used to authenticate SNMPv3 messages. Valid values are MD5 and SHA.",
            " -A authPassphrase Sets the authentication pass phrase for authenticated SNMPv3 messages.",
            " -b engineBootCount Sets the engine boot count to the specified value greater or equal to zero. Default is zero.",
            " -c community Sets the community for SNMPv1/v2c messages.",
            " -Ca agentAddress Sets the agent address field of a V1TRAP PDU (The default value is '0.0.0.0').",
            " -Cg genericID Sets the generic ID for SNMPv1 TRAPs (V1TRAP). The default is 1 (coldStart).",
            " -Ce enterpriseOID Sets the enterprise OID field of a V1TRAP PDU.",
            " -Cil lowerBoundIndex Sets the lower bound index for TABLE operations.",
            " -Ciu upperBoundIndex Sets the upper bound index for TABLE operations.",
            " -Cn non-repeaters Sets the non-repeaters field for GETBULK PDUs. It specifies the number of supplied variables that should not be iterated over. The default is 0.",
            " -Cr max-repetitions Sets the max-repetitions field for GETBULK PDUs. This specifies the maximum number of iterations over the repeating variables. The default is 10.",
            " -Cs specificID Sets the specific ID for V1TRAP PDU. The default is 0.",
            " -Ct trapOID Sets the trapOID (1.3.6.1.6.3.1.1.4.1.0) of an INFORM or TRAP PDU. The default is 1.3.6.1.6.3.1.1.5.1.",
            " -Cu upTime Sets the sysUpTime field of an INFORM, TRAP, or V1TRAP PDU.",
            " -d debugLevel Sets the global debug level for Log4J logging output. Valid values are OFF, ERROR, WARN, INFO, and DEBUG.",
            " -e engineID Sets the authoritative engine ID of the command responder used for SNMPv3 request messages. If not supplied, the engine ID will be discovered.",
            " -E contextEngineID Sets the context engine ID used for the SNMPv3 scoped PDU. The authoritative engine ID will be used for the context engine ID, if the latter is not  specified.",
            " -h Displays this message and then exits the application.",
            " -l localEngineID Sets the local engine ID of the command generator and the notification receiver (thus this SNMP4J-Tool) used for SNMPv3 request messages. This option  can be used to avoid engine ID clashes through duplicate \n    IDs leading to usmStatsNotInTimeWindows reports.",
            " -n contextName Sets the target context name for SNMPv3 messages. Default is the empty string.",
            " -m maxSizeRespPDU The maximum size of the response PDU in bytes.",
            " -Ol Activates listen operation mode. In this mode, the application will listen for incoming TRAPs and INFORMs on the supplied address. Received request will be dumped  to the console until the application is stopped.",
            " -Ot Activates table operation mode. In this mode, the application receives tabular data from the column OIDs specified as parameters. The retrieved rows will be dumped  to the console ordered by their index values.",
            " -Otd Activates dense table operation mode. In this mode, the application receives tabular data from the column OIDs specified as parameters. The retrieved rows will be  dumped to the console ordered by their index values. \n    In contrast to -Ot this option must not be used with sparse tables. ",
            " -OtCSV Same as -Ot except that for each SNMP row received exactly one row of comma separated values will printed to the console where the first column contains the row  index.",
            " -OttCSV Same as -OtCSV except that each row's first column will report the current time (millis after 1.1.1970) when the request has been sent.",
            " -Ow Activates walk operation mode for GETNEXT and GETBULK PDUs. If activated, the GETNEXT and GETBULK operations will be repeated until all instances within the OID  subtree of the supplied OID have been retrieved successfully \n    or until an error occurred.",
            " -p pduType Specifies the PDU type to be used for the message. Valid types are GET, GETNEXT, GETBULK (SNMPv2c/v3), SET, INFORM, TRAP, and V1TRAP (SNMPv1).",
            " -P privacyPassphrase Sets the privacy pass phrase for encrypted SNMPv3 messages (same as -X).",
            " -r retries Sets the number of retries used for requests. A zero value will send out a request exactly once. Default is 1.",
            " -t timeout Sets the timeout in milliseconds between retries. Default is 1000 milliseconds.",
            " -u securityName Sets the security name for authenticated v3 messages.",
            " -v 1|2c|3 Sets the SNMP protocol version to be used. Default is 3.",
            " -x privacyProtocol Sets the privacy protocol to be used to encrypt SNMPv3 messages. Valid values are DES, AES (AES128), AES192, and AES256.",
            " -X privacyPassphrase Sets the privacy pass phrase for encrypted SNMPv3 messages (same as -P).\n",
            "The address of the target SNMP engine is parsed according to the specified <transport> selector (Ex. udp | tcp hostname[/port], ipv4Address[/port], or ipv6Address[/port]) Default=udp).",
            "The OIDs have to be specified in numerical form (Ex. \"1.3.6.1.2.1.1.5.0\" will return the sysName.0 instance with a GET). To request multiple instances, add additional OIDs with a space asseparator.",
            "For the last sub-identifier of a plain OID (without an assigned value) a range can be specified, for example '1.3.6.1.2.1.2.2.1-10' will/has the same effect as enumerating  all OIDs from '1.3.6.1.2.1.2.2.1' to'1.3.6.1.2.1.2.2.10'.",
            "For SET and INFORM request, you can specify a value for each OID by using the following form: OID={type}value where <type> is one of the following single characters  enclosed by '{' and '}':\n",
            "\ti Integer32",
            "\tu UnsingedInteger32, Gauge32",
            "\ts OCTET STRING",
            "\tx OCTET STRING specified as hex string where bytes separated by colons (':').",
            "\td OCTET STRING specified as decimal string where bytes are separated by dots ('.').",
            "\tn Null",
            "\to OBJECT IDENTIFIER",
            "\tt TimeTicks",
            "\ta IpAddress",
            "\tb OCTET STRING specified as binary string where bytes are separated by spaces.\n",
            "An example for a complete SNMPv2c SET request to set sysName (Ex. SNMP4J -c private -v 2c -p SET udp:localhost/161 \"1.3.6.1.2.1.1.5.0={s}SNMP4J\").",
            "To walk the whole MIB tree with GETBULK and using SNMPv3 MD5 authentication (Ex. SNMP4J -a MD5 -A MD5UserAuthPassword -u MD5User -p GETBULK -Ow 127.0.0.1/161).",
            "Listen for unauthenticated SNMPv3 INFORMs and TRAPs and all v1/v2c TRAPs (Ex. SNMP4J -u aSecurityName -Ol 0.0.0.0/162).",
            "Send an unauthenticated SNMPv3 notification (trap) (Ex. SNMP4J -p TRAP -v 3 -u aSecurityName 127.0.0.1/162 \"1.3.6.1.2.1.1.3.0={t}0\", \"1.3.6.1.6.3.1.1.4.1.0={o}1.3.6.1.6.3.1.1.5.1\", or \"1.3.6.1.2.1.1.1.0={s}System XYZ, Version N.M\").",
            "Retrieve rows of the columnar objects ifDescr to ifInOctets and ifOutOctets (Ex. SNMP4J -c public -v 2c -Ot localhost 1.3.6.1.2.1.2.2.1.2-10\\ 1.3.6.1.2.1.2.2.1.16)."
        };

        for (String usage1 : usage) {
            System.out.println(usage1);
        }
    }
 
 
    public void printVariableBindings(PDU response){
        
        for (int i=0; i<response.size(); i++){
            VariableBinding vb = response.get(i);
            System.out.println(vb.toString());
        }
    }
 
    public void printReport(PDU response){
        
        if (response.size() < 1){
            System.out.println("REPORT PDU does not contain a variable binding.");
            return;
        }
 
        VariableBinding vb = response.get(0);
        OID oid =vb.getOid();
        
        if (SnmpConstants.usmStatsUnsupportedSecLevels.equals(oid)){
            System.out.print("REPORT: Unsupported Security Level.");
        }else if (SnmpConstants.usmStatsNotInTimeWindows.equals(oid)){
            System.out.print("REPORT: Message not within time window.");
        }else if (SnmpConstants.usmStatsUnknownUserNames.equals(oid)){
            System.out.print("REPORT: Unknown user name.");
        }else if (SnmpConstants.usmStatsUnknownEngineIDs.equals(oid)){
            System.out.print("REPORT: Unknown engine id.");
        }else if (SnmpConstants.usmStatsWrongDigests.equals(oid)){
            System.out.print("REPORT: Wrong digest.");
        }else if (SnmpConstants.usmStatsDecryptionErrors.equals(oid)){
            System.out.print("REPORT: Decryption error.");
        }else if (SnmpConstants.snmpUnknownSecurityModels.equals(oid)){
            System.out.print("REPORT: Unknown security model.");
        }else if (SnmpConstants.snmpInvalidMsgs.equals(oid)){
            System.out.print("REPORT: Invalid message.");
        }else if (SnmpConstants.snmpUnknownPDUHandlers.equals(oid)){
            System.out.print("REPORT: Unknown PDU handler.");
        }else if (SnmpConstants.snmpUnavailableContexts.equals(oid)){
            System.out.print("REPORT: Unavailable context.");
        }else if (SnmpConstants.snmpUnknownContexts.equals(oid)){
            System.out.print("REPORT: Unknown context.");
        }else{
            System.out.print("REPORT contains unknown OID (" + oid.toString() + ").");
        }
        System.out.println(" Current counter value is " + vb.getVariable().toString() + ".");
    }
 
    @Override
    public synchronized void processPdu(CommandResponderEvent e){
        
        PDU command = e.getPDU();
        if (command != null){
            System.out.println(command.toString());
            if ((command.getType() != PDU.TRAP) && (command.getType() != PDU.V1TRAP) && (command.getType() != PDU.REPORT) && (command.getType() != PDU.RESPONSE)){
                command.setErrorIndex(0);
                command.setErrorStatus(0);
                command.setType(PDU.RESPONSE);
                StatusInformation statusInformation = new StatusInformation();
                StateReference ref = e.getStateReference();
                try{
                    e.getMessageDispatcher().returnResponsePdu(e.getMessageProcessingModel(), e.getSecurityModel(), e.getSecurityName(), e.getSecurityLevel(), command, e.getMaxSizeResponsePDU(), ref, statusInformation);
                }catch (MessageException ex){
                    System.err.println("Error while sending response: "+ex.getMessage());
                    LogFactory.getLogger(SnmpRequest.class).error(ex);
                }
            }
        }
    }
 
    @Override
    public PDU createPDU(Target target){
        
        PDU request;
     
        if (target.getVersion() == SnmpConstants.version3){
            request = new ScopedPDU();
            ScopedPDU scopedPDU = (ScopedPDU)request;
            if (contextEngineID != null){
                scopedPDU.setContextEngineID(contextEngineID);
            }
            if (contextName != null){
                scopedPDU.setContextName(contextName);
            }
        }else{
            if (pduType == PDU.V1TRAP){
                request = v1TrapPDU;
            }else{
                request = new PDU();
            }
        }
        request.setType(pduType);
        return request;
    }
 
    public void table() throws IOException{
       
        Snmp snmp = createSnmpSession();
        this.target = createTarget();
        target.setVersion(version);
        target.setAddress(address);
        target.setRetries(retries);
        target.setTimeout(timeout);
        snmp.listen();
 
        TableUtils tableUtils = new TableUtils(snmp, this);
        tableUtils.setMaxNumRowsPerPDU(maxRepetitions);
        Counter32 counter = new Counter32();

        OID[] columns = new OID[vbs.size()];
        
        for (int i=0; i<columns.length; i++){
            columns[i] = ((VariableBinding)vbs.get(i)).getOid();
        }
        long startTime = System.currentTimeMillis();
        synchronized (counter){

            TableListener listener;
            if (operation == TABLE){
                listener = new TextTableListener();
            }else{
                listener = new CVSTableListener(System.currentTimeMillis());
            }
            if (useDenseTableOperation){
                tableUtils.getDenseTable(target, columns, listener, counter, lowerBoundIndex, upperBoundIndex);
            }else{
                tableUtils.getTable(target, columns, listener, counter, lowerBoundIndex, upperBoundIndex);
            }
            
            try{
                counter.wait(timeout);
            }catch (InterruptedException ex){ }
        }
        System.out.println("Table received in " + (System.currentTimeMillis()-startTime)+" milliseconds.");
        snmp.close();
    }

    @Override
    public PDU createPDU(MessageProcessingModel mpm) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class CVSTableListener implements TableListener{

        private final long requestTime;

        public CVSTableListener(long time){
            this.requestTime = time;
        }
        
        @Override
        public boolean next(TableEvent event){
        
            if (operation == TIME_BASED_CVS_TABLE){
                System.out.print(requestTime);
                System.out.print(",");
            }
            System.out.print("\""+event.getIndex()+"\",");
            for (int i=0; i<event.getColumns().length; i++){
                Variable v = event.getColumns()[i].getVariable();
                String value = v.toString();
            
                switch (v.getSyntax()){
                    case SMIConstants.SYNTAX_OCTET_STRING:{
                        StringBuilder escapedString = new StringBuilder (value.length());
                        StringTokenizer st = new StringTokenizer (value, "\"", true);
                        while (st.hasMoreTokens()){
                            String token = st.nextToken();
                            escapedString.append(token);
                            if (token.equals("\"")){
                                escapedString.append("\"");
                            }
                        }
                    }
                    case SMIConstants.SYNTAX_IPADDRESS:
                    case SMIConstants.SYNTAX_OBJECT_IDENTIFIER:
                    case SMIConstants.SYNTAX_OPAQUE:{
                        System.out.print("\"");
                        System.out.print(value);
                        System.out.print("\"");
                        break;
                    }
                    default:{
                        System.out.print(value);
                    }
                }
                if (i+1 < event.getColumns().length){
                    System.out.print(",");
                }
            }
            System.out.println();
            return true;
        }

        @Override
        public void finished(TableEvent event){
            
            synchronized (event.getUserObject()){
                event.getUserObject().notify();
            }
        }

        @Override
        public boolean isFinished() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    class TextTableListener implements TableListener{

        @Override
        public void finished(TableEvent event){
            System.out.println();
            System.out.println("Table walk completed with status "+event.getStatus() + ". Received " + event.getUserObject()+" rows.");
            synchronized (event.getUserObject()){
                event.getUserObject().notify();
            }
        }
        
        @Override
        public boolean next(TableEvent event){
            
            System.out.println("Index = "+event.getIndex()+":");
            
            for (VariableBinding column : event.getColumns()) {
                System.out.println(column);
            }
            System.out.println();
            ((Counter32)event.getUserObject()).increment();
            return true;
        }

        @Override
        public boolean isFinished() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}