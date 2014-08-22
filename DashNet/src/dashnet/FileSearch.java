package dashnet;

import java.io.File;


public class FileSearch{
        
    String fname;
    
    public FileSearch(String fname){
        this.fname = fname;               
    }    
    
    public File search(File dir){
        
        File listFile[] = dir.listFiles();
        if(listFile != null){
            for(int i = 0; i < listFile.length; i++){
                if(listFile[i].isDirectory()){
                    search(listFile[i]);
                }else{
                    if(listFile[i].getName().endsWith(fname)){
                        return listFile[i];
                    }
                }
            }
        }
        return null;
    }
}