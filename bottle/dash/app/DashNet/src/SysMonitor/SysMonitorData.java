package SysMonitor;

import Snmp.AbstractPrint;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SysMonitorData{

    private DecimalFormat dfmt;  
    
    public List<SysMonitorStruct> list;
    public OperatingSystemMXBean osMXBean;
    
    public SysMonitorData() throws IllegalAccessException, InvocationTargetException{

        list = new ArrayList<>();
        osMXBean = ManagementFactory.getOperatingSystemMXBean();
        dfmt = new DecimalFormat("#,##0.00");
        
        getUsageStats();
    }
    
    private void getUsageStats() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

        Number num;
        TimeConverter time = new TimeConverter();
        StorageSizeConverter size = new StorageSizeConverter("byte");
        SysMonitorStruct s = null;
                
        for(Method method : osMXBean.getClass().getDeclaredMethods()){

            method.setAccessible(true);

            if(method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())){

                String name = splitString(method.getName().substring(3));
                String sval = method.invoke(osMXBean).toString();
                String test = name.toLowerCase().trim();
                String unit;
                
                if(test.contains("time")){
                    unit = "second";
                    num = time.convertByPrefix(Long.parseLong(sval) , "milli", "second");
                    s = new SysMonitorStruct(name, num, unit);
                }else if(test.contains("load")){
                    unit = "%";
                    double val = (Double.valueOf(sval) * 100.0);
                    s = new SysMonitorStruct(name, val, unit);
                }else if(test.contains("size")){
                    unit = "MB";
                    num = size.convertByPrefix(Double.parseDouble(sval), "byte", "mega");
                    s = new SysMonitorStruct(name, num, unit);
                }
                list.add(s);
            }
        }
    }
    
    public void printUsageStats(){
                
        for(int i = 0; i < list.size(); i++){
            
            AbstractPrint.print(list.get(i).getName() + " = " + dfmt.format(list.get(i).getValue()) + " " + list.get(i).getUnit());
        }
    }

    private String splitString(String str){

        int 
            i = 0,
            upCnt = 0;
        char c = ' ';
        char[] arr = str.toCharArray();
        String 
            nameStr = "",
            upper = "";
        boolean
            isConsecutive = false,
            prevIsUpper = false;     
        
        while(i < str.length()){
            
            if(isUpper(c = arr[i++])){
                
                if(prevIsUpper){
                    nameStr += c;
                }else{
                    nameStr = nameStr + ' ' + c;
                }
                upCnt++;
                prevIsUpper = true;
            }else{
                if(upCnt > 1){
                    char t = nameStr.charAt(nameStr.length()-1);
                    nameStr = nameStr.substring(0, nameStr.length()-1);
                    nameStr = nameStr + ' ' + t + c;
                }else{
                    nameStr += c;
                }
                prevIsUpper = false;
                upCnt = 0;
            }
        }
        return nameStr;
    }
   
    private boolean isUpper(char c){ return Character.isUpperCase(c); }
}
