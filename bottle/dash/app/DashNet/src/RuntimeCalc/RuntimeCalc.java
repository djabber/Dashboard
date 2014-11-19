package RuntimeCalc;


public class RuntimeCalc{

    private int sleepCnt;
    private long start; 
    private long end;
    private long runtime;
    
    public RuntimeCalc(){
        
        sleepCnt = 0;
        start = System.currentTimeMillis();
    }
    
    public long end(){ return System.currentTimeMillis(); }
         
    public long getRuntime(){ 
        
        runtime = (end - start); 
        return runtime;
    }
    
    public void printRuntime(){
        System.out.println("\nRuntime:\n\t" + runtime + " milli-seconds\n\t" + (runtime / 10) + " centi-seconds\n\t" + (runtime / 100) + " deci-seconds\n\t" + (runtime / 1000) + " seconds\n\t");
    }

}
