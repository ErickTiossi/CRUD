package javafxmvc.model.DAO;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Client;
import javafxmvc.model.domain.SaleIten;
import javafxmvc.model.domain.Sale;

public class SaleDAO {
    private Connection conn;
    
    public  Connection getConnection(){
        return this.conn;
    }
    
    public void setConnection(Connection conn){
        this.conn = conn;
    }
    
    public  boolean Insert(Sale sale){
        String sql = "INSERT INTO sales(date, value, paid, client_id) "
                    + "VALUES (?,?,?,?);";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(sale.getDate()));
            statement.setDouble(2, sale.getSaleValue());
            statement.setBoolean(3, sale.isPaid());
            statement.setInt(4, sale.getClient().getClientId());
            statement.execute();
            return true;
        } catch (SQLException e){
            Logger.getLogger(SaleDAO.class.getName()).log(Level.SEVERE, null, e);
            return false; 
        } //catch
    } // insert
    
    public  boolean Update(Sale sale){
        String sql = "UPDATE sales "
                    + "SET date=?,"
                    + "value=?"
                    + "paid=?"
                    + "client_id=?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(sale.getDate()));
            statement.setDouble(2, sale.getSaleValue());
            statement.setBoolean(3, sale.isPaid());
            statement.setInt(4, sale.getClient().getClientId());
            statement.setInt(5, sale.getId());
            statement.execute();
            return true;
        } catch (SQLException e){
            Logger.getLogger(SaleDAO.class.getName()).log(Level.SEVERE, null, e);
            return false; 
        } //catch
    } // update
    
    
    public  boolean Delete(Sale sale){
        String sql = "DELETE FROM sales"
                    + "WHERE saleId=?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, sale.getId());
            statement.execute();
            return true;
        } catch (SQLException e){
            Logger.getLogger(SaleDAO.class.getName()).log(Level.SEVERE, null, e);
            return false; 
        } //catch
    } // delete
    
    public List<Sale> Show(){
        String sql = "SELECT *"
                    + "FROM sales;";
        List<Sale> returned = new ArrayList();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Sale sale = new Sale();
                Client client = new Client();
                List<SaleIten> saleItens = new ArrayList<>();                
                
                sale.setSaleId(result.getInt("saleId"));
                sale.setSaleDate(result.getDate("dat").toLocalDate());
                sale.setSaleValue(result.getDouble("value"));
                sale.setSalePaid(result.getBoolean("paid"));
                client.setClientId(result.getInt("client_id"));
                
                // Recuperando dados do Cliente desta venda
                ClientDAO clientDAO = new ClientDAO();
                clientDAO.setConnection(conn);
                client = clientDAO.Search(client);
                
                //Recuperando dados completoss dos itens da Venda
                SaleItensDAO saleItensDAO = new SaleItensDAO();
                saleItensDAO.setConnection(conn);
                saleItens = saleItensDAO.ShowBySale(sale);
                
                //atribuindo os resultados ao objeto de retorno
                sale.setClient(client);
                sale.setSaleItens(saleItens);
                returned.add(sale);
                
            } // while
        } catch (SQLException e){
            Logger.getLogger(SaleDAO.class.getName()).log(Level.SEVERE, null, e);
        } // catch
        return returned;
    } //Show
    
    public  Sale Search(Sale sale){
        String sql = "SELECT * "
                    + "FROM sales"
                    + " WHERE saleId=?;";
        Sale returned = new Sale();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, sale.getId());
            ResultSet result = statement.executeQuery();
            if(result.next()) { //se existir resultado
                Client client = new Client();
                sale.setSaleId(result.getInt("saleId"));
                sale.setSaleDate(result.getDate("dat").toLocalDate());
                sale.setSaleValue(result.getDouble("value"));
                sale.setSalePaid(result.getBoolean("paid"));
                
                client.setClientId(result.getInt("client_id"));
                
                sale.setClient(client);
                
                returned = sale;
                
            }  // if              
        } catch (SQLException e){
           Logger.getLogger(SaleDAO.class.getName()).log(Level.SEVERE, null, e);
        } //catch
        return returned;
    }   // Search
    
    public  Sale SearchLastSale(){
        String sql = "SELECT MAX(saleId))"
                   + "FROM sales;";
        Sale returned = new Sale();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            
            if(result.next()){
                returned.setSaleId((result.getInt("max")));
                return returned;
            } // if
        } catch (SQLException e) {
            Logger.getLogger(SaleDAO.class.getName()).log(Level.SEVERE, null, e);
        } //try catch
        return returned;
    } // SearchLastSale
    
    public  Map<Integer, ArrayList> ShowSaleQuantityByMonth(){
        String sql = "SELECT COUNT(saleId) AS COUNT"
                    + ",EXTRACT(YEAR FROM date) AS YEAR"
                    + ",EXTRACT(MONTH FROM date) AS MONTH"
                    + "FROM sales"
                    + "GROUP BY YEAR"
                            + ",MONTH"
                    + "ORDER BY YEAR"
                            + ",MONTH;";
        Map<Integer, ArrayList> returned = new HashMap();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            
            while(result.next()) { //enquanto tiver linhas..
                ArrayList row = new ArrayList();
                if(!returned.containsKey(result.getInt("YEAR"))){
                    row.add(result.getInt("MONTH"));
                    row.add(result.getInt("COUNT"));
                    returned.put(result.getInt("YEAR"), row);
                } else {
                    ArrayList newRow = returned.get(result.getInt("YEAR"));
                    newRow.add(result.getInt("MONTH"));
                    newRow.add(result.getInt("COUNT"));                    
                } // if else
            } // while
            
            return returned;
        } catch (SQLException e){
            Logger.getLogger(SaleDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return returned;
    } // ShowSaleQuantityByMonth
} //SaleDAO
