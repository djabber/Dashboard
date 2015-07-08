package Snmp;

import java.io.IOException;
import org.snmp4j.mp.SnmpConstants;

public class RequestBuilder extends AbstractGet{   
    
   
    private RequestBuilder(final Builder builder) throws IOException{ 
        
        this.address = builder.address;
        this.community = builder.community;
        this.version = builder.version;
        this.timeout = builder.timeout;
        this.retries = builder.retries;
        this.nonRepeaters = builder.nonRepeaters;
        this.maxRepetitions = builder.maxRepetitions;
        this.maxSizeRequestPDU = builder.maxSizeRequestPDU;
        
        //AbstractGet.init();
    }
    
    public int getRetries(){ return retries; }
    public int getTimeout(){ return timeout; }
    public int getNonRepeaters(){ return nonRepeaters; }
    public int getMaxRepetitions(){ return maxRepetitions; }
    public int getMaxSizeRequestPDU(){ return maxSizeRequestPDU; }
    public String getCommunity(){ return community; }
    public String getAddress(){ return address; }
    public int getVersion(){ return version; }
    
    public static class Builder{
        
    private  int retries = 3;
    private int timeout = 5000;
    private int nonRepeaters = 0;
    private int maxRepetitions = 50; //2500;
    private int maxSizeRequestPDU = 65535;
    private String community = "public";
    private String address = "127.0.0.1/161";
    private int version = SnmpConstants.version2c;

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