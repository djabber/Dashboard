package Net;


public class OSInfo{

    public String getOSInfo(){
         
        return jsonifyOSInfo();
    }
    
    private String jsonifyOSInfo(){
        
        String json = "\"Operating_System_Info\":["
            + "{\"Name\":\"" + getName() + "\"},"
            + "{\"Version\":\"" + getVersion() + "\"},"
            + "{\"Architecture\":\"" + getArchitecture() + "\"}"
            + "]";
        return json;
    }
    
    public void printSysInfo(){
        
        System.out.println("Operating System Info:");
        System.out.println("\tName: " + getName());
        System.out.println("\tVersion: " + getVersion());
        System.out.println("\tArchitecture: " + getArchitecture());
    }
    
    public String getName(){
        return (System.getProperty("os.name"));
    }
    
    public String getArchitecture(){
        return (System.getProperty("sun.arch.data.model"));
    }
    
    public String getVersion(){
        return (System.getProperty("os.version"));  
    }    
}