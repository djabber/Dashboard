package dashnet;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SysInfo{
       
    public SysInfo(){
     
        try{
            OSInfo os = new OSInfo();
            CPUInfo cpu = new CPUInfo(os.getName().toLowerCase());
            UserInfo user = new UserInfo();
            NetInfo net = new NetInfo();
            
        }catch (UnknownHostException | SocketException ex){
            Logger.getLogger(DashNet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SysInfo.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}