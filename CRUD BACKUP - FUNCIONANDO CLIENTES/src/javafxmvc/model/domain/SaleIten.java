package javafxmvc.model.domain;

import java.io.Serializable;

public class SaleIten implements Serializable{
    private int saleItemId;
    private int quantity;
    private double price;
    private Product product;
    private Sale sale;    
    
    public SaleIten(){
        
    }
    
    public int getSaleItenId(){
        return this.saleItemId;
    }
    
    public void setSaleItenId(int saleItenId){
        this.saleItemId = saleItenId;
    }
    
    public int getQuantity(){
        return this.quantity;
    }
    
    public void setQuantity(int qtt){
        this.quantity = qtt;
    }
    
    public double getPrice(){
        return this.price;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
    public Product getProduct(){
        return this.product;
    }
    
    public void setProduct(Product product){
        this.product = product;
    }
    
    public Sale getSale(){
        return this.sale;
    }
    
    public void setSale(Sale sale){
        this.sale = sale;
    }
    
    
    
}
