package sysmonitor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MySqlConnector {
    
    private static ResultSet rs = null;
    private static Statement stmt = null;
    private static String table = "";
    private static String select = "select * from ";
    private static String select2 = "";

    public MySqlConnector(String tbl) {
        MySqlConnector.table = tbl;
         select += table;
         select2 = select + " where ";
    }
    
    public List getAll() throws SQLException{
        rs = stmt.executeQuery(select);
        return convertToList();
    }
    
    public List getById(int id) throws SQLException{
        rs = stmt.executeQuery(select2 + "id = '" + id + "'");
        return convertToList();
    }
    
    public static Statement connect() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/dashboard";
        String username = "root";
        String password = "a";
        Connection conn = null;
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
     
     private List convertToList() {
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