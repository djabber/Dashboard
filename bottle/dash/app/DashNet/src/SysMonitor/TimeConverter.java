package SysMonitor;


public class TimeConverter extends AbstractSIConverter{

    private long start, end, current, runtime;
    
    public TimeConverter(){
        
        super("second");
        start = getCurrent();
        
    }
    
    public long getCurrent(){ return System.currentTimeMillis(); }
    public long getStart(){ return start; }
    public long getEnd(){ return end = getCurrent(); }
    public long getRuntime(){ return (getEnd() - getStart()); }
}