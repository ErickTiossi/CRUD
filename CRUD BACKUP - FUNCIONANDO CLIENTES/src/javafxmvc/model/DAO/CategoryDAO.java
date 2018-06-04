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

public class CategoryDAO {
    private static Connection conn;
    
    public Connection getConnection(){
        return conn;
    }
    
    public void setConnection(Connection connection){
        this.conn = connection;
    }
    
    public boolean Insert(Category category){
        String sql = "INSERT INTO categories(description) "
                    + "VALUES(?);";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, category.getDescription());
            statement.setInt(2, category.getId());
            statement.execute();
            return true;          
        } catch (SQLException e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } //catch
    } //Insert
    
    public boolean Delete(Category category){
        String sql = "DELETE FROM categories "
                    + "WHERE idCategory = ?;";
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, category.getId());
            statement.execute();
            return true;
        } catch (SQLException e){
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } // catch
    } // Delete
    
    public List<Category> Show() {
        String sql = "SELECT * "
                    + "FROM categories;";
        List<Category> returned = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Category category = new Category();
                category.setCategoryId(result.getInt("idCategory"));
                category.setCategoryDescription(result.getString("description"));
                returned.add(category);
            } // while
        } catch (SQLException e){
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        } // cathc
        return returned;    
    } // Show
    
    public Category Search(Category category){
        String sql = "SELECT * "
                    + "FROM categories"
                    + " WHERE categoryId = ?;";
        Category returned = new Category();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, category.getId());
            ResultSet result = statement.executeQuery();
            if(result.next()){
                category.setCategoryDescription(result.getString("description"));
                returned = category;
            }
        } catch (SQLException e){
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);            
        } //catch
        return returned;        
    } //Search
}
