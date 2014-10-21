package SnmpAgent;

import java.net.*;


public class udpc{
    
    static private int x;
   
    public static String getRequest(String var, String targetIp) throws Exception{
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName(targetIp);
        
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        String command = "get-" + var + "-";
        sendData = command.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, 10000);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.setSoTimeout(500);
        clientSocket.receive(receivePacket);
        String value = new String(receivePacket.getData());
        String[] parts = value.split("-");
        clientSocket.close();
        return parts[0];
    }

    public static String setRequest(String var, String val, String targetIp) throws Exception{
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress ip = InetAddress.getByName(targetIp);
        
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        String command = "set-" + var + "-" + val +"-";
        sendData = command.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, 10000);

        //String checker = "";
        //while(!checker.equals(val))
        //{
        x = 0;
        while(x<2) {
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.setSoTimeout(200);
        clientSocket.receive(receivePacket);
        String value = new String(receivePacket.getData());
        String[] parts = value.split("-");
        //checker = parts[0];
        //}
        x++;
        }

        clientSocket.close();
        return "Value correctly set";
    }
}