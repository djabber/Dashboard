package dashnet;

import java.util.ArrayList;
import java.util.List;


public class UserInfo {
    
    List<String> list = new ArrayList<String>();
    
    public UserInfo(){
        printUserInfo();
    }
    
    public List<String> getUserInfo(){
   
        list.add(getUsername());
        list.add(getHomeDirectory());
        return list;
    }
    
    public void printUserInfo(){
        
        System.out.println("User Info:");
        System.out.println("\tName: " + getUsername());
        System.out.println("\tHome Directory: " + getHomeDirectory() + "\n");
    }
    
    
    public String getUsername(){
        
        return System.getProperty("user.name");
    }
    
    public String getHomeDirectory(){
        
        return System.getProperty("user.home");
    }
}