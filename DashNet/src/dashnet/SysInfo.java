package dashnet;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SysInfo{
           
    public List<String> getSysInfo() throws IOException{
        
        List<String> infoList = new ArrayList<String>();
        
            OSInfo os = new OSInfo();
            CPUInfo cpu = new CPUInfo(os.getName().toLowerCase());
            UserInfo user = new UserInfo();
            NetInfo net = new NetInfo();
            
            infoList.addAll(os.getSysInfo());
            infoList.addAll(cpu.getCPUInfo(os.getName().toLowerCase()));
            infoList.addAll(user.getUserInfo());
            infoList.addAll(net.getNetInfo());
                    
        return infoList;
    }
}