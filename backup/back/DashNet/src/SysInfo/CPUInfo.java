package SysInfo;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
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
    OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    
    public String getCPUInfo(String os) throws IOException{
         
        if(os == "windows"){
            getWindowsCPUInfo();
            return jsonifyWindowsCPUInfo();
            
        }/*else if(os == "mac" || os == "apple"){
            continue;
        }*/else{
            getLinuxCPUInfo();            
            return jsonifyLinuxCPUInfo();
        }
    }
    
    private String jsonifyWindowsCPUInfo(){
        
        String json = "\"CPU_Info\": ["
            + "{\"ID\":\"" + System.getenv("PROCESSOR_IDENTIFIER") + "\"},"
            + "{\"Architecture\":\"" + System.getenv("PROCESSOR_ARCHITECTURE") + "\"},"
            + "{\"Architecture64\":\"" + System.getenv("PROCESSOR_ARCHITEW6432") + "\"},"
            + "{\"Number_of_Processors\":\"" + System.getenv("NUMBER_OF_PROCESSORS") + "\"},"
            + "{\"Number_of_Cores\":\"" + getNumProcessors() + "\"}"
            + "]";
        return json;
    }
    
    private String jsonifyLinuxCPUInfo(){
        
        String json = "\"CPU_Info\":["
            + "{\"Architecture\":\"" + getArchitecture() + "\"},"
            + "{\"Make\":\"" + info.make + "\"},"
            + "{\"Model_Name\":\"" + info.modelName + "\"},"
            + "{\"Model\":\"" + info.model + "\"},"
            + "{\"Family\":\"" + info.cpuFamily + "\"},"
            + "{\"Number_of_Processors\":\"" + info.processorCnt + "\"},"
            + "{\"Number_of_Cores\":\"" + info.numCores + "\"},"
            + "{\"Core_Speed\":\"" + info.speed + "\"},"
            + "{\"Cache_Size\":\"" + info.cacheSize + "\"}"
            + "]";
        return json;
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
    
    private void printWindowsCPUInfo(){
        
        System.out.println("\tID: " + System.getenv("PROCESSOR_IDENTIFIER"));
        System.out.println("\tArchitecture: " + System.getenv("PROCESSOR_ARCHITECTURE"));
        System.out.println("\tArchitecture 64 bit: " + System.getenv("PROCESSOR_ARCHITEW6432"));
        System.out.println("\tNumber of Processors: " + System.getenv("NUMBER_OF_PROCESSORS"));
        System.out.println("\tNumber of Cores: " + getNumProcessors());
    }
    
    private void printLinuxCPUInfo(){
        
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
    
    public void getWindowsCPUInfo(){
          
        processWindowsCPUInfo();
    }
    
    private void processWindowsCPUInfo(){
        
        String id = System.getenv("PROCESSOR_IDENTIFIER");
        System.out.println("id = " + id);
           
    }
    
    private String getNumProcessors(){
        return String.valueOf(osBean.getAvailableProcessors());
    }
    
    private String getArchitecture(){
        return System.getProperty("sun.arch.data.model");
    }
    
    public void getLinuxCPUInfo() throws IOException{
     
        findLinuxCPUInfo();    
    }
    
    private void findLinuxCPUInfo() throws IOException{
        
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
    
    private void processLinuxCPUInfo(String str){
        
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