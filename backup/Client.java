package dashnet; 

import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Client{
	 
    private final int MAX_TRIES = 5;
    
    private String host = "localhost";
    private int port = 0;
    private int tries = 0;
    private String data = "";
    private Socket s = null;
    private BufferedReader in = null;
    private DataOutputStream  out = null;
    
    public Client(String host, int port) throws IOException{
        
        this.host = host;
        this.port = port;
        openConnection(host, port);
    }
    
    public Client(String host, int port, int msgSize, String cmd, String data) throws IOException, InterruptedException, ResponseException{
        
        this.host = host;
        this.port = port;
        this.data = data;
        openConnection(host, port);
        sendData(msgSize, cmd, data);
    }
    
    private boolean isConnected(){ return s.isConnected(); }
    
    private void openConnection(String host, int port){ 
                
        while(true){
            try{
                s = new Socket(host, port);    
                
                if(s.isConnected()){ break;
                }else{
                    if(tries++ < MAX_TRIES){
                        try{
                            System.out.println("Waiting for server...");
                            Thread.sleep(5000 * tries);
                        }catch(InterruptedException ex1){
                            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }            
            }catch(IOException ex){
                if(tries++ < MAX_TRIES){
                    System.out.println("Trying again soon...");
                    try{
                        Thread.sleep(5000 * tries);
                    }catch(InterruptedException ex1){
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
    }

    public String readData() throws IOException, InterruptedException{
       
        String readIn = "", allRead = "";
        openConnection(host, port);
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        Thread.sleep(3000);
       
        System.out.println("Reading data...");
        
        while((readIn = in.readLine()) != null){
            System.out.println("READ_IN = " + readIn);
            
            if( (readIn.equals("DONE")) || (readIn.equals("QUIT")) ){ break; }
            
            allRead += readIn;
            System.out.println("ALL_READ = " + allRead);
        }    
       
        in.close();
        if(!s.isClosed()){ s.close(); }     
        return allRead;
    }
    
    public void sendData(int size, String cmd, String data) throws IOException, InterruptedException, ResponseException{
        
        //do{
            openConnection(host, port);     
            out = new DataOutputStream(s.getOutputStream());
            Thread.sleep(3000);   
            
            data = size + " " + cmd + " " + data;
            System.out.println("SENDING_DATA: " + data);        
            //out.writeBytes(data);
            out.writeUTF(data);
            out.flush();
  
            System.out.println("Data sent...");
            out.close();
            if(!s.isClosed()){ s.close(); }
        //}while(!dataReceived());
    }
    
    private boolean dataReceived() throws IOException, InterruptedException, ResponseException{
      
        String in = readData();
        
        if(in == "RESEND"){ return false;
        }else if( (in == "QUIT") || (in == "DONE") ){ return true;
        }else{
            throw new ResponseException(new String("Invalid response!"));
        }     
    }
    
    
    class ResponseException extends Exception{  
    
        public ResponseException(String s){  
            super(s);  
        }  
    } 
}


