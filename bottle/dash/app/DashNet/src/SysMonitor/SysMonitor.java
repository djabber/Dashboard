package SysMonitor;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SysMonitor{

    
    public SysMonitor(){

        try{
            SysMonitorData data = new SysMonitorData();
            data.printUsageStats();
            
        }catch(IllegalAccessException | InvocationTargetException ex){
            Logger.getLogger(SysMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}