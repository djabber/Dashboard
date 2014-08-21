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
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author TXSTATE\dd27
 */
public class NetInfo {
    
    private static class InterfaceInfo {
        String dname;
        String name;
        String ip4;
        String ip6;
    }
    int ipcnt = 0;
    InetAddress addr;
    InterfaceInfo[] infoArr = new InterfaceInfo[25];
    
     public NetInfo() throws UnknownHostException, SocketException{
       
        //printAllNetInfo();
        printInterfaceInfo();
    }
    
    public void getAllNetInfo(){
        
 
    }
    
    public void printAllNetInfo() throws UnknownHostException, SocketException{
        
        System.out.println("IP = ");
    }
    
    public void printInterfaceInfo() throws UnknownHostException, SocketException{
        
        System.out.println("\nInterface Info:");
        getAllInterfaceInfo();
     
        ipcnt /= 2;
        for(int i = 0; i < ipcnt; i++){
            
            System.out.println("Display Name: " + infoArr[i].dname + "\nInterface Name: " + infoArr[i].name  + "\nIpv4: " + infoArr[i].ip4 + "\nIpv6: " + infoArr[i].ip6 + "\n");
        }
    }
    
    public void printInterfaces() throws UnknownHostException, SocketException{
        
        Enumeration<NetworkInterface> iface = getInterfaces();
        
        for(NetworkInterface netint : Collections.list(iface)){
            System.out.println(netint.toString() + "\n");
        }
    }
    
    public String getHostname() throws UnknownHostException, SocketException{
       
        return InetAddress.getLocalHost().getHostName();
    }
    
    public String getAllIPsforInterface(String iface) throws UnknownHostException, SocketException{
       
        
        return "";            
    }
    
   public Enumeration<NetworkInterface> getInterfaces() throws UnknownHostException, SocketException{
       
        return NetworkInterface.getNetworkInterfaces();
    }
    
    public void getAllInterfaceInfo() throws SocketException, UnknownHostException {
                
        int cnt = 0;
        int indx = 0;
        int ipv4 = 1;
        int ipv6 = 0;
        infoArr[cnt] = new InterfaceInfo();
        
        Enumeration<NetworkInterface> iface = getInterfaces();
        
        for(NetworkInterface netint : Collections.list(iface)){        
        
            infoArr[cnt].dname = netint.getDisplayName();
            infoArr[cnt].name = netint.getName();      
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
                    infoArr[cnt].ip6 = tmp;
                    indx++;
                }else if(indx == ipv4){
                    infoArr[cnt].ip4 = tmp; 
                    indx = 0;
                }
                
                if(indx == 0){
                    cnt++;
                    infoArr[cnt] = new InterfaceInfo();
                }
            }
        }
    }
}