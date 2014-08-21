/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dashnet;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TXSTATE\dd27
 */
public class DashNet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
         SysInfo sys = new SysInfo();
         UserInfo user = new UserInfo();
         
        try {
            NetInfo net = new NetInfo();
        } catch (UnknownHostException ex) {
            Logger.getLogger(DashNet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(DashNet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
