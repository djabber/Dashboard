package dashnet;

import java.util.ArrayList;
import java.util.List;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;


public class OSInfo{
    
    List<String> list = new ArrayList<String>();
    OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

    public OSInfo(){
        //printSysInfo();
        //getSysInfo();
    }
    
    public List<String> getSysInfo(){
         
        list.add("operating_system_info");
        list.add("name");
        list.add(getName());
        list.add("Version");
        list.add(getVersion());
        list.add("Architecture");
        list.add(getArchitecture());
     
        return list;
    }
    
    public void printSysInfo(){
        
        System.out.println("Operating System Info:");
        System.out.println("\tName: " + getName());
        System.out.println("\tVersion: " + getVersion());
        System.out.println("\tArchitecture: " + getArchitecture());
    }
    
    public String getName(){
        return ("Name: " + System.getProperty("os.name"));
    }
    
    public String getArchitecture(){
        return (System.getProperty("sun.arch.data.model"));
    }
    
    public String getVersion(){
        return (System.getProperty("os.version"));  
    }    
}