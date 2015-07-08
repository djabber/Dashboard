package sysmonitor;

import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.exit;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SysMonitorData{

    private final String FILENAME = "usage_stats.txt";
    private DecimalFormat dfmt;  
    private Statement sys_mon_stmt;
    private Statement server_stmt;
    MySqlConnector sys_mon = new MySqlConnector("sys_monitor");
    MySqlConnector server = new MySqlConnector("servers");
    public List<SysMonitorStruct> list;
    public OperatingSystemMXBean osMXBean;
    
    public SysMonitorData() throws IllegalAccessException, InvocationTargetException, SQLException{

        list = new ArrayList<>();
        osMXBean = ManagementFactory.getOperatingSystemMXBean();
        dfmt = new DecimalFormat("#,##0.00");        
        sys_mon_stmt = sys_mon.connect();
        server_stmt = server.connect();
        
        getUsageStats();
        //writeUsageStats()        
    }
    
    private void getUsageStats() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{

        TimeConverter time = new TimeConverter();
        StorageSizeConverter size = new StorageSizeConverter("byte");
        SysMonitorStruct s = null;
                
        for(Method method : osMXBean.getClass().getDeclaredMethods()){

            method.setAccessible(true);

            if(method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())){

                String name = splitString(method.getName().substring(3));
                String sval = method.invoke(osMXBean).toString();
                String test = name.toLowerCase().trim();
                Double dval = 0.0;
                String unit = "", unit2 = "";
                
                if(test.contains("time")){
                    unit = "second";
                    Number num =  time.convertByPrefix(Long.parseLong(sval), "milli", unit);
                    dval = num.doubleValue();
                    s = new SysMonitorStruct(name, num, unit);
                }else if(test.contains("load")){
                    unit = "%";
                    dval = Double.valueOf(sval);
                    s = new SysMonitorStruct(name, (dval * 100.0), unit);
                }else if(test.contains("size")){
                    unit = "mega";
                    unit2 = "byte";
                    Number num =  size.convertByPrefix(Double.parseDouble(sval), unit2, unit);
                    dval = num.doubleValue();
                    unit += unit2;
                    s = new SysMonitorStruct(name, dval, unit);
                }
                
                if(server_stmt != null){
                    
                    List<String> results = getServerList();
                    printServerList(results);
                }
                String errors = "none";
                int server_id = 4;
                String insert="INSERT INTO sys_monitor (errors, name, value, unit, servers_id) VALUES('" + errors + "','" + name + "','" + dval + "','" + unit + "','" + server_id + "');";
                //String insert="insert into dashboard.sys_monitor (name,value,unit) VALUES ('test2',2.0,'testUnit2');";
                //System.out.println(insert);
               
                sys_mon_stmt.executeUpdate(insert);
                //stmt.execute(insert);
                //stmt.execute("select * from sys_monitor;");
                list.add(s);
            } 
       }
        sys_mon_stmt.close();
    }
    
    public List getServerList() throws SQLException{
        
        System.out.println("Getting result set...");
        ResultSet rs = server_stmt.executeQuery("Select * from servers;");
        return server.convertToList(rs);
    }
    
    public void printServerList(List<String> results){
        
        int cnt = 0;
        System.out.println("\n\n****** Show Tables Results ******");
            for(String item : results){
                System.out.println(cnt + " " + item);
                cnt++;
            }
            System.out.println("\n\n");
    }
    
    public List convertToList(ResultSet rs) {
        
        List list = new ArrayList();
        
        try {
            while (rs.next()) {
                rs.getInt("id");
                list.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        } finally {
            //closeConnection();
        }

        return list;
    }
    
    private void writeToFile(String text){
        
        FileWriter fw = null;
        
        try{
            fw = new FileWriter(FILENAME,true); //the true will append the new data
            fw.write(text);//appends the string to the file
            fw.close();
        }catch(IOException ex){
            Logger.getLogger(SysMonitorData.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try{
                fw.close();
            }catch(IOException ex){
                Logger.getLogger(SysMonitorData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     
    private void writeToDB(String text) throws SQLException{
        
        sys_mon_stmt.executeUpdate("INSERT INTO table_name (column1,column2,column3,...)" + "VALUES (value1,value2,value3,...)");
    }
    
    private void writeToCpu(double sys_load, double process_load, double process_time) throws SQLException{
        
        sys_mon_stmt.executeUpdate("INSERT INTO cpu (system_load, process_load, process_time)" + "VALUES (sys_load, process_load, process_time)");
    }
    
    private void writeToMemory(String text) throws SQLException{
        
        sys_mon_stmt.executeUpdate("INSERT INTO table_name (column1,column2,column3,...)" + "VALUES (value1,value2,value3,...)");
    }
    
    private void writeToSwap(String text) throws SQLException{
        
        sys_mon_stmt.executeUpdate("INSERT INTO table_name (column1,column2,column3,...)" + "VALUES (value1,value2,value3,...)");
    }
    
    private void writeUsageStats(){
        
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        Timestamp ts = new Timestamp(now.getTime());
        
        writeToFile(ts.toString() + ", ");
        
        for(int i = 0; i < list.size(); i++){
            
            String text = (list.get(i).getName() + " = " + dfmt.format(list.get(i).getValue()) + " " + list.get(i).getUnit() + ", ");
            writeToFile(text);
        }
        writeToFile(" END_OF_ENTRY");      
    }
    
    public void printUsageStats(){
                
        for(int i = 0; i < list.size(); i++){
            
            System.out.println(list.get(i).getName() + " = " + dfmt.format(list.get(i).getValue()) + " " + list.get(i).getUnit());
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