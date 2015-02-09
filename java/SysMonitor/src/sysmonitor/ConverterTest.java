package sysmonitor;

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
            System.out.println("from: " + hz.getUnit(i));
            for(int j = 0; j < siPowers.length; j++){
                System.out.print(" to: " + hz.getUnit(j) + " = " + hz.convertByPower(1, siPowers[i], siPowers[j]) + "; ");
            }
           System.out.println("\n");
        }
        
       System.out.println("Result = " + hz.convertByPrefix(1, "hertz", "yotta"));
       System.out.println("Result = " + hz.convertByUnit(1,  "hertz", "yottahertz", "hertz"));
    }

    public void testStorageConverter(){
        
        StorageSizeConverter ssc = new StorageSizeConverter("bit");
        
       System.out.println("Testing Storage Converter...");
        for(int i = 0; i < binaryPowers.length; i++){
           System.out.println("from: " + ssc.getUnit(i));
            for(int j = 0; j < binaryPowers.length; j++){
                System.out.print(" to: " + ssc.getUnit(j) + " = " + ssc.convertByPower(1, binaryPowers[i], binaryPowers[j]) + "; ");
            }
           System.out.println("\n");
        }
        
       System.out.println("Bit Result = " + ssc.convertByPrefix(1, "bit", "mega"));
       System.out.println("Bit Result = " + ssc.convertByUnit(1, "megabit", "bit", "bit"));          
    
       System.out.println("Byte Result = " + ssc.convertByPrefix(1, "mega", "byte"));
       System.out.println("Byte Result = " + ssc.convertByUnit(1, "megabyte", "byte", "byte"));          
    }
    
    public void testTimeConverter(){
        
        try{
            TimeConverter time = new TimeConverter();
            Thread.sleep(3000);
            long runtime = time.getRuntime();
            
           System.out.println("Runtime = " + runtime);
            
            
            for(int i = 0; i < siPowers.length; i++){
           System.out.println("from: " + time.getUnit(i));
            for(int j = 0; j < siPowers.length; j++){
                System.out.print(" to: " + time.getUnit(j) + " = " + time.convertByPower(runtime, siPowers[i], siPowers[j]) + "; ");
            }
           System.out.println("\n");
        }
        
       System.out.println("Result = " + time.convertByPrefix(runtime, "second", "yotta"));
       System.out.println("Result = " + time.convertByUnit(runtime,  "second", "yottasecond", "second"));
        
        } catch(InterruptedException ex){
            Logger.getLogger(ConverterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}