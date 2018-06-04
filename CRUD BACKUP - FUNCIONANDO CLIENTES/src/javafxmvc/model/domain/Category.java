package javafxmvc.model.domain;


import java.io.Serializable;

public class Category implements Serializable{
    private int categoryId;
    private String description;
    
    public Category(){
        
    }
    
    public Category(int categoryId, String description){
        this.categoryId = categoryId;
        this.description = description;      
    }
    
    public int getId(){
        return this.categoryId;
    }
    
    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setCategoryDescription(String description){
        this.description = description;
    }
    
    @Override
    public String toString(){
        return this.description;
    }
    
}
