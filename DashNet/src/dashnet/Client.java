package dashnet;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Client{
	
    public void sendInfo(String message){ //throws IOException, InterruptedException{
	
        int tries = 0;
        Socket socket = null;
        
        while(true){
            try{
                socket = new Socket("txssc-top", 10000);
                
                if(socket != null){
                    InputStream in = socket.getInputStream();
                    BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
                    OutputStream out = socket.getOutputStream();
        
                    out.write(message.getBytes());
                    out.flush();
        
                    String data = inReader.readLine();
            
                    in.close();
                    inReader.close();
                    out.close();
                    socket.close();
                    break;
                }
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
}
