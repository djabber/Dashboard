package Snmp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OidParser extends AbstractPrint{

    private final String CLASSNAME = "OidParser";
    
    public OidParser(String address){
        
        getPage(address);
        
    }
    
    public String getPage(String address){
        
        String page = "";
        
        try{
            
            URL u = new URL(address);
            InputStream in = u.openStream();
            in = new BufferedInputStream(in);
            Reader r = new InputStreamReader(in);
            int c;
            
            while ((c = r.read()) != -1) {
                page += ((char)c);
            }
            
        } catch(MalformedURLException ex){
            //print("Malformed URL");
            Logger.getLogger(OidParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            //print("IO Exception");
            Logger.getLogger(OidParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return page;
    }
    
    public void parseInfo(){
        
        String address = "http://oid-info.com/get/1.3.6.1.2.1.1";
        
        String page = getPage(address);
        
        
        
        
    }
}
