package javafxmvc.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Client;

public class ClientDAO {
    private Connection conn;
    
    public Connection getConnection(){
        return conn;
    }
    
    public void setConnection(Connection connection){
        this.conn = connection;
    }
    
    public boolean Insert(Client client){
        String sql = "INSERT INTO clients(name, cpf, telphone) "
                    + "VALUES (?, ?, ?);";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, client.getName());
            statement.setString(2, client.getCpf());
            statement.setString(3,client.getClientTelphone());
            statement.execute();
            return true;            
        } catch (SQLException e){
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } // catch    
    } //Inset
    
    public boolean Update(Client client){
        String sql = "UPDATE clients "
                    + "SET name=?"
                    + ", cpf=?"
                    + ", telphone=? "
                    + "WHERE clientId=?;";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, client.getName());
            statement.setString(2, client.getCpf());
            statement.setString(3, client.getClientTelphone());
            statement.setInt(4, client.getClientId());
            statement.execute();
            return true;
        } catch (SQLException e){
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;            
        } // catch
    } // Update 
    
    public boolean Delete(Client client){
        String sql = "DELETE FROM clients "
                    + "WHERE clientId=?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, client.getClientId());
            statement.execute();
            return true;
        } catch (SQLException e){
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } // catch
    } // Delete
    
    public List<Client> Show(){
        String sql = "SELECT * "
                    + "FROM clients;";
        List<Client> returned = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Client client = new Client();
                client.setClientId(result.getInt("clientId"));
                client.setClientName(result.getString("name"));
                client.setClientCPF(result.getString("cpf"));
                client.setClientTelphone(result.getString("telphone"));
                returned.add(client);
            } //while
        } catch (SQLException e){ //
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
        } //catch
        return returned;
    } // Show
    
    public Client Search(Client client){
        String sql = "SELECT * "
                    + "FROM clients "
                    + "WHERE clientId = ?;";
        Client returned = new Client();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, client.getClientId());
            ResultSet result = statement.executeQuery();
            if(result.next()){ //Vai devolver 1 so, se existir
                client.setClientName(result.getString("name"));
                client.setClientCPF(result.getString("cpf"));
                client.setClientTelphone(result.getString("telphone"));
                returned = client;
            } // if
        } catch (SQLException e){
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
        } // catch
        return returned;
    } // Search
    
}
