/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dashnet;

/**
 *
 * @author TXSTATE\dd27
 */
public class SysInfo {
    
    public SysInfo(){
        printSysInfo();
    }
    
    public void getSysInfo(){
        
     
        
    }
    
    public void printSysInfo(){
        
        System.out.println("OS Name = " + getPlatform());
        System.out.println("Architecture = " + getArchitecture());
    }
    
    public String getPlatform(){
        
        return System.getProperty("os.name");
    }
    
    public String getArchitecture(){
        
        return System.getProperty("sun.arch.data.model");
    }
    
   
    
}
