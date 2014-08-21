/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dashnet;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author TXSTATE\dd27
 */
public class NetInfo_IPv6 {
    
    InetAddress addr;
     public NetInfo_IPv6() throws UnknownHostException, SocketException{
        printIPv6NetInfo();
    }
    
    public void getIPv6NetInfo(){
        
 
    }
    
    public void printIPv6NetInfo() throws UnknownHostException, SocketException{
    
        System.out.println("IP = ");
    }
        
    public String getAllIPv6forInterface() throws UnknownHostException, SocketException{
       
        
        return "";            
    }
    
    public void getIPv6fromHostname(String iface) throws UnknownHostException, SocketException{
        
       
    }   
    
    public String getAllIPv6fromHostname() throws UnknownHostException, SocketException{
      
        return "";          
    }
}