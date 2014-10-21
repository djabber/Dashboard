package SnmpManager;

import java.util.List;


public class SnmpPrint extends Thread{

    private int cnt;
    private List<String> list;
    
    public SnmpPrint(List<String> list, int cnt){
        
        this.list = list;
        this.cnt = cnt;
    }
    
    @Override
    public void run(){
        
        printList();
    }
    
   
}
