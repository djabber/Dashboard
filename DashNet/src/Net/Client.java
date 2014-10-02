package Net; 

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

class Client{

    String host = "";
    int port = 0;
    String data = "";
    
    public Client(String host, int port, String data) throws Exception{
        this.host = host;
        this.port = port;
        this.data = data;
        openConnection(host, port, data);
    }
        
    private void openConnection(String host, int port, String data) throws Exception{
           
        try{
            InetAddress hostname = InetAddress.getByName(host);
            Socket socket = new Socket(hostname.getHostName(), port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Thread.sleep(1000);
            
            System.out.println("Sending size...");
            out.writeBytes(String.valueOf(data.length()) + "~");
            
            //out.writeBytes("");
            Thread.sleep(3000);
            out.writeBytes(data);
            out.flush();
            System.out.println("Closing connection...");
            out.close();
            
        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}