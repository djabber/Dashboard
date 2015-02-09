package runtimecalc;


public class RuntimeCalc{

    private static int sleepCnt;
    private static long start; 
    private static long end;
    private static long runtime;
    
    public static void start(){
        
        sleepCnt = 0;
        start = System.currentTimeMillis();
    }
    
    public static long end(){ return System.currentTimeMillis(); }
         
    public static long getRuntime(){ 
        
        runtime = (end - start); 
        return runtime;
    }
    
    public static void printRuntime(){
        System.out.println("\nRuntime:\n\t" + runtime + " milli-seconds\n\t" + (runtime / 10) + " centi-seconds\n\t" + (runtime / 100) + " deci-seconds\n\t" + (runtime / 1000) + " seconds\n\t");
    }

    private static void main(String[] args){
        
        RuntimeCalc run = new RuntimeCalc();
    }
}
