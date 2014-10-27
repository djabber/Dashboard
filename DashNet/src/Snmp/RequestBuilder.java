package Snmp;

import java.io.IOException;
import org.snmp4j.mp.SnmpConstants;

public class RequestBuilder extends AbstractGet{   
    
   
    private RequestBuilder(final Builder builder) throws IOException{ 
        
        RequestBuilder.address = builder.address;
        RequestBuilder.community = builder.community;
        RequestBuilder.version = builder.version;
        RequestBuilder.timeout = builder.timeout;
        RequestBuilder.retries = builder.retries;
        RequestBuilder.nonRepeaters = builder.nonRepeaters;
        RequestBuilder.maxRepetitions = builder.maxRepetitions;
        RequestBuilder.maxSizeRequestPDU = builder.maxSizeRequestPDU;
        
        //AbstractGet.init();
    }
    
    public static int getRetries(){ return retries; }
    public static int getTimeout(){ return timeout; }
    public static int getNonRepeaters(){ return nonRepeaters; }
    public static int getMaxRepetitions(){ return maxRepetitions; }
    public static int getMaxSizeRequestPDU(){ return maxSizeRequestPDU; }
    public static String getCommunity(){ return community; }
    public static String getAddress(){ return address; }
    public static int getVersion(){ return version; }
    
    public static class Builder{
        
    private static int retries = 3;
    private static int timeout = 5000;
    private static int nonRepeaters = 0;
    private static int maxRepetitions = 2500;
    private static int maxSizeRequestPDU = 65535;
    private static String community = "public";
    private static String address = "127.0.0.1/161";
    private static int version = SnmpConstants.version2c;

        public Builder address(final String address){
            this.address = address;
            return this;
        }

        public Builder community(final String community){
            this.community = community;
            return this;
        }

        public Builder version(final int version){
            this.version = version;
            return this;
        }

        public Builder timeout(final int timeout){
            this.timeout = timeout;
            return this;
        }

        public Builder retries(final int retries){
            this.retries = retries;
            return this;
        }
        
        public Builder nonRepeaters(final int nonRepeaters){
            this.nonRepeaters = nonRepeaters;
            return this;
        }
        
        public Builder maxSizeRequestPDU(final int maxSizeRequestPDU){
            this.maxSizeRequestPDU = maxSizeRequestPDU;
            return this;
        }

        public Builder maxRepetitions(final int maxRepetitions){
            this.maxRepetitions = maxRepetitions;
            return this;
        }

        public RequestBuilder build() throws IOException{
            return new RequestBuilder(this);
        }
    }
}