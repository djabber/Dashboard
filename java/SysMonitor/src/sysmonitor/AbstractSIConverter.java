package sysmonitor;

public abstract class AbstractSIConverter extends AbstractConverter{

    private final int[] powers = {-24, -21, -18, -15, -12, -9, -6, -3, -2, -1, 0, 1, 2, 3, 6, 9, 12, 15, 18, 21, 24};
    private final String[] prefixes = {"yocto", "zepto", "atto", "femto", "pico", "nano", "micro", "milli", "centi", "deci", "base", "deca", "hecto", "kilo", "mega", "giga", "tera", "peta", "exa", "zetta", "yotta"};
    
    public AbstractSIConverter(String baseUnit){
        
        super(10, baseUnit);
        setPowers(powers);
        setPrefixes(prefixes);
    }
}