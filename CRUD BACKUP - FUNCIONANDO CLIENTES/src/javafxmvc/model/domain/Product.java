
package javafxmvc.model.domain;

import java.io.Serializable;

public class Product implements Serializable{
    private int productId;
    private String name;
    private double price;
    private int quantity;
    private Category category;
    
    public Product(){
        
    }
    
    public Product(int productId, String name, double price, int quantity){
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;                 
    }
    
    public int getProductId(){
        return this.productId;
    }
    
    public void setProductId(int productId){
        this.productId = productId;
    }
    
    public String getProductName(){
        return this.name;
    }
    
    public void setProductName(String name){
        this.name = name;
    }
    
    public double getProductPrice(){
        return this.price;
    }
    
    public void setProductPrice(double price){
        this.price = price;
    }
    
    public int getProductQuantity(){
        return this.quantity;
    }
    
    public void setProductQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public Category getProductCategory(){
        return this.category;
    }
    
    public void setProductCategory(Category category){
        this.category = category;
    }
    
    @Override
    public String toString(){
        return this.name;
    }


}
