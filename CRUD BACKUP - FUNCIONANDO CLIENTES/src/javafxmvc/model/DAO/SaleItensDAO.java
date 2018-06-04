package javafxmvc.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.SaleIten;
import javafxmvc.model.domain.Product;
import javafxmvc.model.domain.Sale;

public class SaleItensDAO {
    private Connection conn;
    
    public Connection getConnection(){
        return this.conn;                
    } 
    
    public void setConnection(Connection conn){
        this.conn = conn;
    }
    
    public boolean Insert(SaleIten saleIten){
        String sql = "INSERT INTO saleItens(quantity, value, product_id, sale_id)"
                    + " VALUES(?, ?, ?, ?);";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, saleIten.getQuantity());
            statement.setDouble(2, saleIten.getPrice());
            statement.setInt(3, saleIten.getProduct().getProductId());
            statement.setInt(4, saleIten.getSale().getId());
            
            statement.execute();
            return true;
        } catch (SQLException e){
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } // try catch
    } // Insert
        
    
    // FAZER ESSE !!!!
    public boolean Update(SaleIten saleIten){
        return true;
    }
    
    public List<SaleIten> Show(){
        String sql = "SELECT * "
                    + "FROM saleItens;";
        List<SaleIten> returned = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                SaleIten saleIten = new SaleIten();
                Product product = new Product();
                Sale sale = new Sale();
                
                saleIten.setSaleItenId(result.getInt("saleItenId"));
                saleIten.setQuantity(result.getInt("quantity"));
                saleIten.setPrice(result.getDouble("price"));
                
                // seta propriedades das FK's para acessar outros objetos
                product.setProductId(result.getInt("product_id"));
                sale.setSaleId(result.getInt("sale_id"));
                
                //Recuperando dados do produto
                ProductDAO productDAO = new ProductDAO();
                productDAO.setConnection(conn);
                product = productDAO.Search(product);
                
                //Recuperando dados da Venda
                SaleDAO saleDAO = new SaleDAO();
                saleDAO.setConnection(conn);
                sale = saleDAO.Search(sale);
                
                // atribuindo os dados recuperados
                saleIten.setProduct(product);
                saleIten.setSale(sale);
                
                returned.add(saleIten);
            }     // while                      
            
        } catch (SQLException e) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
        } // Show
        return returned;
    } // Show
    
    public List<SaleIten> ShowBySale(Sale sale){
        String sql = "SELECT * "
                    + "FROM saleItens "
                    + " WHERE sale_id = ?;";
        List<SaleIten> returned = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, sale.getId());
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                SaleIten saleIten = new SaleIten();
                Product product = new Product();
                Sale s = new Sale();
                
                saleIten.setSaleItenId(result.getInt("saleItenId"));
                saleIten.setQuantity(result.getInt("quantity"));
                saleIten.setPrice(result.getDouble("value"));
                
                // atribui os ids para recuperar os dados dos objetos das FK
                product.setProductId(result.getInt("product_id"));
                s.setSaleId(result.getInt("sale_id"));
                
                //Recuperando dados do produto
                SaleDAO saleDAO = new SaleDAO();
                saleDAO.setConnection(conn);
                sale = saleDAO.Search(sale);
                
                saleIten.setProduct(product);
                saleIten.setSale(s);
                
                
                returned.add(saleIten);
                
            } // while
        } catch (SQLException e){
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e);
        } // catch
        return returned;
    } // ShowBySale
    
    public SaleIten Search(SaleIten saleIten){
        String sql = "SELECT * "
                    + "FROM saleItens "
                    + "WHERE saleItenId=?;";
        SaleIten returned = new SaleIten();
        try{
           PreparedStatement statement = conn.prepareStatement(sql);
           statement.setInt(1, saleIten.getSaleItenId());
           ResultSet result = statement.executeQuery(); 
           
           if(result.next()){ // se tem resultado..
               Product product = new Product();
               Sale sale = new Sale();
               
               saleIten.setSaleItenId(result.getInt("saleItenId"));
               saleIten.setQuantity(result.getInt("quantity"));
               saleIten.setPrice(result.getDouble("price"));
               
                // seta propriedades das FK's para acessar outros objetos
                product.setProductId(result.getInt("product_id"));
                sale.setSaleId(result.getInt("sale_id"));
               
                //Recuperando dados do produto
                ProductDAO productDAO = new ProductDAO();
                productDAO.setConnection(conn);
                product = productDAO.Search(product);
                
                //Recuperando dados da Venda
                SaleDAO saleDAO = new SaleDAO();
                saleDAO.setConnection(conn);
                sale = saleDAO.Search(sale);
                
                // atribuindo os dados recuperados
                saleIten.setProduct(product);
                saleIten.setSale(sale);
                
                returned = saleIten;
               
           } // if
        } catch (SQLException e){
           Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, e); 
        } // catch
        return returned;
    } // Search
} // SaleItenDAO
