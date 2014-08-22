package dashnet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DashNet{

    public static void main(String[] args){
        
        try{
            Client client = new Client();
            SysInfo sys = new SysInfo();
            List<String> list = sys.getSysInfo();
            
            for(String s : list){  
            
                client.sendInfo(s);
            }
            
        }catch(IOException ex){
            Logger.getLogger(DashNet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}