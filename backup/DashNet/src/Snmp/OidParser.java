package Snmp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OidParser extends AbstractPrint{

    public OidParser(String address){
        
        getPage(address);
        
    }
    
    public static void getPage(String address){
        
        address = "http://oid-info.com/get/1.3.6.1.2.1.1.";
        
        try{
            
            URL u = new URL(address);
            InputStream in = u.openStream();
            in = new BufferedInputStream(in);
            Reader r = new InputStreamReader(in);
            int c;
            
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }   
        
        } catch(MalformedURLException ex){
            print("MalformedURL!")
            Logger.getLogger(OidParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            Logger.getLogger(OidParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void parseInfo(){
        
    }
}
