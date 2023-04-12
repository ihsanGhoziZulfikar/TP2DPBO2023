
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dell
 */
public class dbConnection {
    private Statement stmt = null;
    private Statement stmt2 = null;
    private Connection conn = null;
    
    public dbConnection(){
        String conAddress = "jdbc:mysql://localhost/db_dpbo_tp2";
        String user = "root";
        String pass = "";
        connect(conAddress, user, pass);
    }
    
    private void connect(String conAddress, String username, String pass){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(conAddress, username, pass);
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet selectQuery(String sql){
        try {
            stmt.executeQuery(sql);
            return stmt.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet selectQuery2(String sql){
        try {
            stmt2.executeQuery(sql);
            return stmt2.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int updateQuery(String sql){
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public Statement getStmt() {
        return stmt;
    }
    public Statement getStmt2() {
        return stmt2;
    }
    public com.mysql.jdbc.PreparedStatement getPrepStmt(String sql){
        try {
            return (com.mysql.jdbc.PreparedStatement) conn.prepareStatement(sql);
                    } catch (SQLException ex) {
            Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
