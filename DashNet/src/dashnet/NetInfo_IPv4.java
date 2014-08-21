/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author TXSTATE\dd27
 */
public class NetInfo_IPv4 {
    
   InetAddress addr;
     public NetInfo_IPv4() throws UnknownHostException, SocketException{
        printIPv4NetInfo();
    }
    
    public void printIPv4NetInfo() throws UnknownHostException, SocketException{
    
        System.out.println("Interface Info:");
        
        for(String info : getAllIPv4forInterface()){
            System.out.println(info);
        }
    }
            
    public String getIPv4forInterface() throws UnknownHostException, SocketException{
       
        
        return "";            
    }
    
    public void getIPv4fromHostname(String host) throws UnknownHostException{
        
        InetAddress inetAddr = InetAddress.getByName(host);
        byte[] addr = inetAddr.getAddress();
        String ipAddr = "";
            
        for(int i = 0; i < addr.length; i++){
            if(i > 0){
                ipAddr += ".";
            }
            ipAddr += addr[i] & 0xFF;
        }
        System.out.println("IP Address: " + ipAddr);
    }   
    
    public List<String> getAllIPv4forInterface() throws SocketException, UnknownHostException {
        
        int cnt = 0;
        int indx = 3;
        List<String> list = new ArrayList<String>();
        NetInfo net = new NetInfo();
        Enumeration<NetworkInterface> iface = net.getInterfaces();
        
        for(NetworkInterface netint : Collections.list(iface)){
                    
            String dname = netint.getDisplayName();
            String name = netint.getName();
            if(!dname.equals(name)){
                indx = 2;
                list.add(dname);
            }
            list.add(name);
 
            List<InterfaceAddress> ipList = netint.getInterfaceAddresses();
        
            for(InterfaceAddress ip : ipList){
                if(cnt < indx){
                    
                    list.add(ip.toString());
                }else if(cnt > indx){
                    indx = 0;
                }else{
                    cnt++;
                }
            }
        }
        return list;
    }
}