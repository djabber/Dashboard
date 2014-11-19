package SysMonitor;

public class StorageSizeConverter extends AbstractConverter{
    
    private final String STANDARD_1 = "JEDEC";
    private final String STANDARD_2 = "IEC";    
    private final int[] powers = {0, 3, 13, 23, 33, 43, 53, 63, 73, 83};
    private final String[] prefixes = {"bit", "byte", "kilo", "mega", "giga", "tera", "peta", "exa", "zetta", "yotta"};
    
    private String baseUnit;
    private String standard;
    
    public StorageSizeConverter(){
        
        super(2, "bit");
        this.baseUnit = "bit";
        this.standard = STANDARD_1;
        setPowers(powers);
        setPrefixes(prefixes);
    }

    public StorageSizeConverter(String baseUnit){
        
        super(2, baseUnit);
        this.baseUnit = baseUnit;
        this.standard = STANDARD_1;
        setPowers(powers);
        setPrefixes(prefixes);
    }
    
    public StorageSizeConverter(String baseUnit, String standard){
    
        super(2, baseUnit);
        this.baseUnit = baseUnit;
        this.standard = standard;
        setPowers(powers);
        setPrefixes(prefixes);
    }
  
    private String convertToIEC(int prefixIndex){

        if(prefixIndex > 1){
            return prefixes[prefixIndex].substring(0, 2) + "bi";    
        }
        return prefixes[prefixIndex];
    }   
}