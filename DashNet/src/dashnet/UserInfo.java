package dashnet;

import java.util.ArrayList;
import java.util.List;


public class UserInfo {
    
    List<String> list = new ArrayList<String>();
    
    public UserInfo(){
        //printUserInfo();
        //getUserInfo();
    }
    
    public List<String> getUserInfo(){
        
        list.add("user_info");
        list.add("username");
        list.add(getUsername());
        list.add("home_directory");
        list.add(getHomeDirectory());
        return list;
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