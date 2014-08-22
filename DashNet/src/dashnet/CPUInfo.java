package dashnet;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CPUInfo{
    
    private static class ProcessorInfo {
        
        String make;
        String modelName;
        int model;
        int cpuFamily;
        int processorCnt;
        int numCores;
        float speed;
        int cacheSize;
    }
   
    ProcessorInfo info = new ProcessorInfo();
    List<String> list = new ArrayList<String>();
    OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    
    public CPUInfo(String os) throws IOException{
        getCPUInfo(os);
        printCPUInfo(os);
    }
    
    public void getCPUInfo(String os) throws IOException{
         
        if(os == "windows"){
            list.add(getNumProcessors());
            list.add(getArchitecture());
        }/*else if(os == "mac" || os == "apple"){
            continue;
        }*/else{
            getAllLinuxCPUInfo();
        }
 
       // return list;
    }
    
    public void printCPUInfo(String os){
        
        System.out.println("CPU Info:");
        if(os == "windows"){
            printWindowsCPUInfo();
        }/*else if(os == "mac" || os == "apple"){
            continue;
        }*/else{
            printLinuxCPUInfo();
        }
    }
    
    
    public void printWindowsCPUInfo(){
        
        System.out.println("\tNumber of Cores: " + getNumProcessors());
        System.out.println("\tArchitecture: " + getArchitecture());
    }
    
    public void printLinuxCPUInfo(){
        
        System.out.println("\tArchitecture: " + getArchitecture());
        System.out.println("\tMake: " + info.make);
        System.out.println("\tModel Name: " + info.modelName);
        System.out.println("\tModel: " + info.model);
        System.out.println("\tFamily: " + info.cpuFamily);
        System.out.println("\tNumber of Processors: " + info.processorCnt);
        System.out.println("\tNumber of Cores: " + info.numCores);
        System.out.println("\tCore Speed: " + info.speed);
        System.out.println("\tCache Size: " + info.cacheSize + "\n");
    }
    
    
    
    public String getNumProcessors(){
        return String.valueOf(osBean.getAvailableProcessors());
    }
    
    public String getArchitecture(){
        return System.getProperty("sun.arch.data.model");
    }
    
    public void getAllLinuxCPUInfo() throws IOException{
     
        FileSearch fileSearch = new FileSearch("cpuinfo");
 
	File found = fileSearch.search(new File("/proc/"));
        
        if(found != null){
            File file = new File(found.getCanonicalPath());
            try(Scanner input = new Scanner(file)){
                while(input.hasNext()){
                    String nextLine = input.nextLine();
                    processLinuxCPUInfo(nextLine);
                }
            }
        }
    }
    
    public void processLinuxCPUInfo(String str){
        
        str = str.trim();
        if(str.startsWith("processor")){
            int start, end;
            start = str.indexOf(":") + 1;
            if(start > 0){
                end = str.length();
                str = str.substring(start, end).trim();
                info.processorCnt = Integer.parseInt(str) + 1;
            }
        }else if(str.startsWith("vendor_id")){            
            int start, end;
            start = str.indexOf(":") + 1;
            if(start > 0){
                end = str.length();
                str = str.substring(start, end).trim();
                info.make = str;
            }
        }else if(str.startsWith("cpu family")){            
            int start, end;
            start = str.indexOf(":") + 1;
            if(start > 0){
                end = str.length();
                str = str.substring(start, end).trim();
                info.cpuFamily = Integer.parseInt(str);
            }
        }else if(str.startsWith("model name")){            
            int start, end;
            start = str.indexOf(":") + 1;
            if(start > 0){
                end = str.length();
                str = str.substring(start, end).trim();
                info.modelName = str;
            }
        }else if(str.startsWith("model")){            
            int start, end;
            start = str.indexOf(":") + 1;
            if(start > 0){
                end = str.length();
                str = str.substring(start, end).trim();
                info.model = Integer.parseInt(str);                
            }
        }else if(str.startsWith("cpu MHz")){            
            int start, end;
            start = str.indexOf(":") + 1;
            if(start > 0){
                end = str.length();
                str = str.substring(start, end).trim();
                info.speed = Float.parseFloat(str);
            }
        }else if(str.startsWith("cache size")){            
            int start, end;
            start = str.indexOf(":") + 1;
            if(start > 0){
                end = str.length()-3;
                str = str.substring(start, end).trim();
                info.cacheSize = Integer.parseInt(str);
            }
        }else if(str.startsWith("cpu cores")){            
            int start, end;
            start = str.indexOf(":") + 1;
            if(start > 0){
                end = str.length();
                str = str.substring(start, end).trim();
                info.numCores = Integer.parseInt(str);
            }
        }
    }
}