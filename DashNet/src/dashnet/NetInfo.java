package dashnet;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;


public class NetInfo {
    
    private static class InterfaceInfo {
        String dname;
        String name;
        String ip4;
        String ip6;
    }
    
    int ipcnt = 0;
    InetAddress addr;
    InterfaceInfo[] netArr = new InterfaceInfo[25];
    
    public NetInfo() throws UnknownHostException, SocketException{
       
        printNetInfo();
    }
    
    public void printNetInfo() throws UnknownHostException, SocketException{
        
        System.out.println("Interface Info:");
        getAllInterfaceInfo();
     
        ipcnt /= 2;
        for(int i = 0; i < ipcnt; i++){
            
            System.out.println("\tDisplay Name: " + netArr[i].dname);
            System.out.println("\tInterface Name: " + netArr[i].name);
            System.out.println("\tIpv4: " + netArr[i].ip4);
            System.out.println("\tIpv6: " + netArr[i].ip6 + "\n");
        }
    }
    
    public void printInterfaces() throws UnknownHostException, SocketException{
        
        Enumeration<NetworkInterface> iface = getInterfaces();
        
        for(NetworkInterface netint : Collections.list(iface)){
            System.out.println(netint.toString() + "\n");
        }
    }
    
    public InterfaceInfo[] getNetInfo() throws SocketException, UnknownHostException{
        
        getAllInterfaceInfo();
        return netArr;
    }
    
    public String getHostname() throws UnknownHostException, SocketException{
       
        return InetAddress.getLocalHost().getHostName();
    }
    
    public String getIPv4forInterface(String iface) throws UnknownHostException, SocketException{
              
        for(int i = 0; i < ipcnt; i++){
            if(iface.equals(netArr[i].name)){
                return netArr[i].ip4;
            }
        }
        return null;
    }
    
    public String getIPv6forInterface(String iface) throws UnknownHostException, SocketException{
              
        for(int i = 0; i < ipcnt; i++){
            if(iface.equals(netArr[i].name)){
                return netArr[i].ip6;
            }
        }
        return null;
    }
    
    public List<String> getAllIPsforInterface(String iface) throws UnknownHostException, SocketException{
       
        List<String> list = new ArrayList<String>();
        
        String ip4 = getIPv4forInterface(iface);
        String ip6 = getIPv6forInterface(iface);
        
        if((ip4 == null) && (ip6 == null)){
            return null;
        }else{
            if(ip4 != null){
                list.add(ip4);
            }else if(ip6 != null){
                list.add(ip6);
            }else
                return null;
        }
        return list;
    }
    
   public Enumeration<NetworkInterface> getInterfaces() throws UnknownHostException, SocketException{
       
        return NetworkInterface.getNetworkInterfaces();
    }
    
    public void getAllInterfaceInfo() throws SocketException, UnknownHostException {
                
        int cnt = 0;
        int indx = 0;
        int ipv4 = 1;
        int ipv6 = 0;
        netArr[cnt] = new InterfaceInfo();
        
        Enumeration<NetworkInterface> iface = getInterfaces();
        
        for(NetworkInterface netint : Collections.list(iface)){        
        
            netArr[cnt].dname = netint.getDisplayName();
            netArr[cnt].name = netint.getName();      
            List<InterfaceAddress> ipList = netint.getInterfaceAddresses();
            
            for(InterfaceAddress ip : ipList){
            
                if(ip != null){ 
                    ipcnt++;
                }
                
                String tmp = ip.toString().trim();
            
                if(tmp.startsWith("/")){
                    tmp = tmp.substring(1).trim();
                }
               
                int start, end, strStart;
                strStart = tmp.indexOf("%");
                if(strStart > 0){
                    start = 0;
                    end = strStart;
                    
                    if(end > 0){
                        tmp = tmp.substring(start, end).trim();
                    }
                }
                
                strStart = tmp.indexOf("/");
                if(strStart > 0){
                    start = 0;
                    end = strStart;
                    
                    if(end > 0){
                        tmp = tmp.substring(start, end).trim();
                    }
                }
                if(indx == ipv6){
                    netArr[cnt].ip6 = tmp;
                    indx++;
                }else if(indx == ipv4){
                    netArr[cnt].ip4 = tmp; 
                    indx = 0;
                }
                
                if(indx == 0){
                    cnt++;
                    netArr[cnt] = new InterfaceInfo();
                }
            }
        }
    }
}