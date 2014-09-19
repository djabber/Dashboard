package dashnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server{
    
    private int port = 0;
    private int tries = 0;
    private String data = "";
    private ServerSocket ss;
    private Socket s;
    private BufferedReader in;
    private OutputStream out;

    public Server(){ }
    
    public Server(int port){
        
        this.port = port;
        startServer(port);
    }
    
    public Server(int port, String send) throws IOException{
        
        this.port = port;
        this.startServer(port);
        this.sendData(send);
    }    
    
    public void startServer(int port){
     
        System.out.println("Starting server...");
        
        while(true){
            try{
                System.out.println("Create ServerSocket...");
                ss = new ServerSocket(port);
                int aport = ss.getLocalPort();
                if(ss != null){
                    System.out.println("Accepting Connections on port " + aport + "...");
                    s = ss.accept();  
                    break;
                }
            }catch(IOException ex){
                if(tries++ < 5){
                    System.out.println("Trying again soon...");
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException ex1){
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
    }
    
    public String readData() throws IOException{
        
        System.out.println("Reading data...");
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String readIn = in.readLine();
        in.close();
        
        return readIn;
    }
    
    public void sendData(String Data) throws IOException{
        
        System.out.println("Sending data...");
        out = s.getOutputStream();     // DataOutputStream outToClient = new DataOutputStream(s.getOutputStream());
        out.close();
    }
    
    public void closeServer() throws IOException{
        
        System.out.println("Closing server...");
        s.close();
        ss.close();
    }
}