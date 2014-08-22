package dashnet;

import java.io.*;
import java.net.*;

class Client{
	
    public void sendInfo(String message) throws IOException{
	
	Socket socket = new Socket("txssc-top", 10000);
        InputStream in = socket.getInputStream();
        BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
	OutputStream out = socket.getOutputStream();
        
        message += "\n";
	out.write(message.getBytes());
        out.flush();
        
        String data = inReader.readLine();
        System.out.println("Server response: " + data);
        
        in.close();
        inReader.close();
        out.close();
	socket.close();
    }
}
