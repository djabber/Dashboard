package SysMonitor;

import Snmp.AbstractPrint;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConverterTest {

    private final int[] siPowers = {-24, -21, -18, -15, -12, -9, -6, -3, -2, -1, 0, 1, 2, 3, 6, 9, 12, 15, 18, 21, 24};
    private final int[] binaryPowers = {0, 3, 13, 23, 33, 43, 53, 63, 73, 83};
    
    public ConverterTest(){

        //SysMonitorData data = new SysMonitorData();
        testStorageConverter();
        //testTimeConverter();
        //testHertzConverter();
    } 
   
    public void testHertzConverter(){
        
        HertzConverter hz = new HertzConverter();
        
        for(int i = 0; i < siPowers.length; i++){
            AbstractPrint.print("from: " + hz.getUnit(i));
            for(int j = 0; j < siPowers.length; j++){
                System.out.print(" to: " + hz.getUnit(j) + " = " + hz.convertByPower(1, siPowers[i], siPowers[j]) + "; ");
            }
           AbstractPrint.print("\n");
        }
        
       AbstractPrint.print("Result = " + hz.convertByPrefix(1, "hertz", "yotta"));
       AbstractPrint.print("Result = " + hz.convertByUnit(1,  "hertz", "yottahertz", "hertz"));
    }

    public void testStorageConverter(){
        
        StorageSizeConverter ssc = new StorageSizeConverter("bit");
        
       AbstractPrint.print("Testing Storage Converter...");
        for(int i = 0; i < binaryPowers.length; i++){
           AbstractPrint.print("from: " + ssc.getUnit(i));
            for(int j = 0; j < binaryPowers.length; j++){
                System.out.print(" to: " + ssc.getUnit(j) + " = " + ssc.convertByPower(1, binaryPowers[i], binaryPowers[j]) + "; ");
            }
           AbstractPrint.print("\n");
        }
        
       AbstractPrint.print("Bit Result = " + ssc.convertByPrefix(1, "bit", "mega"));
       AbstractPrint.print("Bit Result = " + ssc.convertByUnit(1, "megabit", "bit", "bit"));          
    
       AbstractPrint.print("Byte Result = " + ssc.convertByPrefix(1, "mega", "byte"));
       AbstractPrint.print("Byte Result = " + ssc.convertByUnit(1, "megabyte", "byte", "byte"));          
    }
    
    public void testTimeConverter(){
        
        try{
            TimeConverter time = new TimeConverter();
            Thread.sleep(3000);
            long runtime = time.getRuntime();
            
           AbstractPrint.print("Runtime = " + runtime);
            
            
            for(int i = 0; i < siPowers.length; i++){
           AbstractPrint.print("from: " + time.getUnit(i));
            for(int j = 0; j < siPowers.length; j++){
                System.out.print(" to: " + time.getUnit(j) + " = " + time.convertByPower(runtime, siPowers[i], siPowers[j]) + "; ");
            }
           AbstractPrint.print("\n");
        }
        
       AbstractPrint.print("Result = " + time.convertByPrefix(runtime, "second", "yotta"));
       AbstractPrint.print("Result = " + time.convertByUnit(runtime,  "second", "yottasecond", "second"));
        
        } catch(InterruptedException ex){
            Logger.getLogger(ConverterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}