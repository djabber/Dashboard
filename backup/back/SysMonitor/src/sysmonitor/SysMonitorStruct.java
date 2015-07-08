package sysmonitor;

public class SysMonitorStruct{
    
    private String name;
    private Number val;
    private String unit;
    
    public SysMonitorStruct(String name, Number val, String unit){
        
        this.name = name;
        this.val = val;
        this.unit = unit;
    }
    
    public String getName(){ return name; }
    public Number getValue(){ return val; }
    public String getUnit(){ return unit; }
    public void setName(String name){ this.name = name; }
    public void setValue(Number val){ this.val = val; }
    public void setUnit(String unit){ this.unit = unit; }
}