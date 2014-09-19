package dashnet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DashNet{

    public static void main(String[] args){
       
        int port = 10000;
        String host = "txssc-top", data = "";
        
        try{
            SysInfo sys = new SysInfo();
            data = sys.getSysInfo();
            Client c = new Client(host, port, data);
      
        }catch(IOException ex){
            Logger.getLogger(DashNet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DashNet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DashNet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}