package javafxmvc.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.DAO.ClientDAO;

public class MySQLDataBase implements IDataBase {
    private Connection conn;
    
    @Override
    public Connection Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/javamvc", "root", "");
            return conn;
        } catch (SQLException | ClassNotFoundException e){
            Logger.getLogger(MySQLDataBase.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    @Override
    public void Disconnect(Connection conn){
        try{
            conn.close();            
        } catch (SQLException e){
            Logger.getLogger(MySQLDataBase.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
