package SysMonitor;

import static java.lang.Math.pow;

public abstract class AbstractConverter{
    
    private final int basePower;
    private final String baseUnit;

    private int fromIndex;
    private int toIndex; 
    private int[] powers;
    private String[] prefixes;
    
    public AbstractConverter(int basePower, String baseUnit){
        
        fromIndex = 0;
        toIndex = 0;
        this.basePower = basePower;
        this.baseUnit = baseUnit;
    }
    
    public String getBaseUnit(){ return baseUnit; }
    public String getPrefix(int index){ return prefixes[index]; }
    public String getUnit(int index){ return prefixes[index] + baseUnit; }
    public String getFromPrefix(){ return prefixes[fromIndex]; }
    public String getToPrefix(){ return prefixes[toIndex]; }
    public String getFromUnit(){ return prefixes[fromIndex] + baseUnit; }
    public String getToUnit(){ return prefixes[toIndex] + baseUnit; }
    public String getPrefixList(){ return prefixes.toString(); }
    public void setPowers(int[] powers){ this.powers = powers; }
    public void setPrefixes(String[] prefixes){ this.prefixes = prefixes; }
    public int getPower(int index){ return powers[index]; }
    public int getNumPowers(){ return powers.length; }
    public int getNumPrefixes(){ return prefixes.length; }
    
    public Number convertByPower(Number num, int from, int to){ 
        
        findIndex(from, to);
        
        if(fromIndex == toIndex){ 
            return num;
        }else{
            return (num.doubleValue() * pow(basePower, (from - to))); 
        }
    }
    
    public Number convertByPrefix(Number num, String from, String to){
        
        findPrefixIndex(from, to);
        
        return convertByPower(num, powers[fromIndex], powers[toIndex]);
    }
    
    public Number convertByUnit(Number num, String from, String to, String baseUnit){
        
        int fromLen = from.length(),
            toLen = to.length(),
            baseUnitLen = baseUnit.length();
        String fromPrefix = from, toPrefix = to;
        
        if(fromLen > baseUnitLen){
            fromPrefix = from.toLowerCase().trim().substring(0, (fromLen - baseUnitLen));
        }
        if(toLen > 5){
            toPrefix = to.toLowerCase().trim().substring(0, (toLen - baseUnitLen));
        }
        
        return convertByPrefix(num, fromPrefix, toPrefix);
    }
    
    private void findIndex(int from, int to){

        boolean foundFrom = false, foundTo = false; 
        
        for(int i = 0; i < powers.length; i++){
            
            if(powers[i] == from){ 
                fromIndex = i;
                foundFrom = true;
            }
            if(powers[i] == to){ 
                toIndex = i;
                foundTo = true;
            }else if(foundFrom && foundTo){
                break;
            }
        }
        
        if(!foundFrom || !foundTo){
            //System.out.println("Invalid from or to arguments: from = " + from + ", to = " + to);
            throw new RuntimeException("Invalid from or to arguments!");
        }
    }
        
    private void findPrefixIndex(String from, String to){

        boolean foundFrom = false, foundTo = false; 
        if(from.equals(baseUnit)){
            fromIndex = 10;
            foundFrom = true;
        }
        if(to.equals(baseUnit)){
            toIndex = 10;
            foundTo = true;
        }
        for(int i = 0; i < prefixes.length; i++){
            
            if(prefixes[i].equals(from)){ 
                fromIndex = i;
                foundFrom = true;
            }
            if(prefixes[i].equals(to)){ 
                toIndex = i;
                foundTo = true;
            }else if(foundFrom && foundTo){
                break;
            }
        }
        if(!foundFrom || !foundTo){
            throw new RuntimeException("Invalid from or to arguments!");
        }
    }
}