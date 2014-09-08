package dashnet;

import java.util.ArrayList;
import java.util.List;


public class UserInfo {
    
    List<String> list = new ArrayList<String>();
    
    public String getUserInfo(){
        
        return jsonifyUserInfo();
    }
    
    private String jsonifyUserInfo(){
        
        String username = getUsername();
        username = username.replaceAll("\\\\","\\\\\\\\");
         
        String json = "\"User_Info\":["
            + "{\"Username\":\"" + username + "\"},"
            + "{\"Home_Directory\":\"" + getHomeDirectory() + "\"}"
            + "]";
        return json;
    }
     
    public void printUserInfo(){
        
        System.out.println("User Info:");
        System.out.println("\tUsername: " + getUsername());
        System.out.println("\tHome Directory: " + getHomeDirectory() + "\n");
    }
    
    
    public String getUsername(){
        
        return System.getProperty("user.name");
    }
    
    public String getHomeDirectory(){
        
        return System.getProperty("user.home");
    }
}