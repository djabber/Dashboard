package dashnet;

import java.io.IOException;


public class SysInfo{
           
    public String getSysInfo() throws IOException{
        
        OSInfo os = new OSInfo();
        CPUInfo cpu = new CPUInfo();
        UserInfo user = new UserInfo();
        NetInfo net = new NetInfo();
                        
        return jsonifySysInfo(os.getOSInfo(), cpu.getCPUInfo(os.getName().toLowerCase()), user.getUserInfo(), net.getNetInfo());
    }
    
    private String jsonifySysInfo(String os, String cpu, String user, String net){
        
        String json = "{" + os + "," + cpu + "," + user + "," + net + "}"; 
        return json;
    }
}