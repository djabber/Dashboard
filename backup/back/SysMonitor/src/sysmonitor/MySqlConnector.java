package sysmonitor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MySqlConnector {
    
    private ResultSet rs = null;
    private Connection conn = null;
    private Statement stmt = null;
//    private static String table = "";
    private String select = "select * from ";
    private String select2 = "";

    public MySqlConnector(String tbl) {
/*        MySqlConnector.table = tbl;
         select += table;
         select2 = select + " where ";
        */
    }
    
    public List getAll() throws SQLException{
        rs = stmt.executeQuery(select);
        return convertToList(rs);
    }
    
    public List getById(int id) throws SQLException{
        rs = stmt.executeQuery(select2 + "id = '" + id + "'");
        return convertToList(rs);
    }
    
    public Statement connect() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/dashboard";
        String username = "root";
        String password = "a";
        //Connection conn = null;
        Statement stmt = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("Unable to load jdbc driver.");
        }
        
        try{
            conn = DriverManager.getConnection(url, username, password);
        }catch(SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        }
        
        return conn.createStatement();
    }
     
     
    public List query(String field, String val) throws SQLException{ 
         List<Object> list = new ArrayList();
                    
        if(stmt != null){
          
                switch(field.toLowerCase()){
                    case "all": return this.getAll();
                    case "id": return this.getById(Integer.getInteger(val));
                    default: return this.getAll();
                }                
        }
        return list;
    }
     
    public List getTableList() {
        
        List<String> list = null;
        
        try {
            
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet tableSet = dbmd.getTables(null,null,null,new String[]{"TABLE"});
            
            list = convertToList(tableSet); // .new ArrayList();
            
            System.out.println("****** MyTable List ******");
            for(String item : list){
                
                System.out.println("Item = " + item);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        } finally {
            //closeConnection();
        }

        return list;
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
}