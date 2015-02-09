package sysmonitor;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SysMonitor{

    
    public static void main(String[] args){

        try{
            SysMonitorData data = new SysMonitorData();
            data.printUsageStats();
            
        }catch(IllegalAccessException | InvocationTargetException | SQLException ex){
            Logger.getLogger(SysMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}