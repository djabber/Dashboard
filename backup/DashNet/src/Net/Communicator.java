package Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Communicator {
       
    private int port = 0;
    private int tries = 0;
    private String host = "localhost";
    private ServerSocket ss;
    private Socket s;
    private BufferedReader in;
    private OutputStream out;

/*
    public Communicator(){ }
    
    public Communicator(String host, int port){
        
        this.host = host;
        this.port = port;
        
    }
    
    public Communicator(String host, int port, String data) throws IOException{
        
        this.host = host;
        this.port = port;
        this.outData = data;
        openCommunication(host, port, outData);
        sendData()
    }      
*/
    
    public void openCommunication(String h, int p) throws IOException{
     
        System.out.println("Starting Communicator...");
        
        if( (h != null) && (h != "") ){ host = h; }
        if( (p > 0) && (p < 65535) ){ port = p; }
        
        createSocket();
        createServerSocket();
         
        s = ss.accept();
    }

    

    public String readData() throws IOException{
        
        System.out.println("Reading data...");
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        in.close();
        
        return in.toString();
    }
    
    public void sendData(String data) throws IOException{
        
        System.out.println("Sending data...");
        out = s.getOutputStream();     // DataOutputStream outToClient = new DataOutputStream(s.getOutputStream());
        out.write(data.getBytes());
        out.flush();
        out.close();
    }
    
    public void closeConnection() throws IOException{
        
        System.out.println("Closing server...");
        s.close();
        ss.close();
    }
    
    private void createServerSocket(){
        try{
                ss = new ServerSocket(port);
        }catch(IOException ex){
            if(tries++ < 5){
                try{
                    Thread.sleep(100);
                }catch(InterruptedException ex1){
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    private void createSocket(){
        try{
            s = new Socket(host, port);
        }catch(IOException ex){
            if(tries++ < 5){
                try{
                    Thread.sleep(100);
                }catch(InterruptedException ex1){
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }
}