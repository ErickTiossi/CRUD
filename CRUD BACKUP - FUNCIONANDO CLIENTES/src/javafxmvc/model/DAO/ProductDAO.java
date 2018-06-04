package javafxmvc.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Category;
import javafxmvc.model.domain.Product;

public class ProductDAO {
    private Connection conn;
    
    public Connection getConnection(){
        return this.conn;
    }
    
    public void setConnection(Connection conn){
        this.conn = conn;
    }
    
    public boolean Insert(Product product){
        String sql = "INSERT INTO products (name, price, quantity, poductId) "
                    + "VALUES (?,?,?,?);";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getProductPrice());
            statement.setInt(3, product.getProductQuantity());
            statement.setInt(4, product.getProductCategory().getId());
            statement.execute();
            return true;
        } catch (SQLException e){
           Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
           return false; 
        } // catch
    } //Insert
    
    public boolean Update(Product product){
        String sql = "UPDATE products "
                    + "SET name=?"
                    + ", price=?"
                    + ", quantity=?"
                    + ", categoryId=?" 
                    + "WHERE productId=?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getProductPrice());
            statement.setInt(3, product.getProductQuantity());
            statement.setInt(4, product.getProductCategory().getId());
            statement.setInt(5, product.getProductId());
            statement.execute();
            return true;
        } catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    public boolean Delete(Product product){
        String sql = "DELETE FROM products"
                    + "WHERE productId=?;";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, product.getProductId());
            statement.execute();
            return true;
        } catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    public List<Product> Show(){
        String sql = "SELECT *"
                    + "FROM products;";
        List<Product> returned = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Product product = new Product();
                Category category = new Category();
                product.setProductId(result.getInt("productId"));
                product.setProductName(result.getString("name"));
                product.setProductPrice(result.getDouble("price"));
                product.setProductQuantity(result.getInt("quantity"));
                category.setCategoryId(result.getInt("category_id")); //fk
                
                // buscando dados da categoria que o produto pertence
                CategoryDAO categoryDAO = new CategoryDAO();
                categoryDAO.setConnection(conn);
                category = categoryDAO.Search(category);
                
                product.setProductCategory(category);
                
                returned.add(product);
            } // while 
        } catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        } // catch
        return returned;
    } // Show
    
    public List<Product> ShowByCategory(Category category){
        String sql = "SELECT * "
                    + "FROM products WHERE category_id=?;";
        List<Product> returned = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, category.getId());
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Product product = new Product();
                product.setProductId(result.getInt("productId"));
                product.setProductName(result.getString("name"));
                product.setProductPrice(result.getDouble("price"));
                product.setProductQuantity(result.getInt("quantity"));
                
                category.setCategoryId(result.getInt("category_id"));
                
                product.setProductCategory(category);
                
                returned.add(product);                       
            } // while
        } catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        } // catch
        return returned;
    } //ShowByCategory
    
    public Product Search(Product product){
        String sql = "SELECT * "
                    + "FROM products WHERE productId=?;";
        Product returned = new Product();
        Category category = new Category();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, product.getProductId());
            ResultSet result = statement.executeQuery();
            if(result.next()){
               returned.setProductId(result.getInt("productId"));
               returned.setProductName(result.getString("name"));
               returned.setProductPrice(result.getDouble("price"));
               returned.setProductQuantity(result.getInt("quantity"));
                
               category.setCategoryId(result.getInt("category_id"));
                
               returned.setProductCategory(category); 
            }//if
        } catch (SQLException e){
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, e);
        }//catch
        return returned;
    }//Search
}
