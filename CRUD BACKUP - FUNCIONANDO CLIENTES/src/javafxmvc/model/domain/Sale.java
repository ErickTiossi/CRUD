package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Sale implements Serializable {
    private int saleId;
    private LocalDate date;
    private double value;
    private boolean paid;
    private List<SaleIten> saleItens;
    private Client client;
    
    public Sale(){
        
    }
    
    public Sale(int saleId, LocalDate date, double value, boolean paid){
        this.saleId = saleId;
        this.date = date;
        this.value = value;
        this.paid = paid;                
    }
    
    public int getId(){
        return this.saleId;
    }
    
    public void setSaleId(int saleId){
        this.saleId = saleId;
    }
    
    public LocalDate getDate(){        
        return this.date;
    }
    
    public void setSaleDate(LocalDate date){
        this.date = date;
    }    
    
    public double getSaleValue(){
        return this.value;
    }
    
    public void setSaleValue(double value){
        this.value = value;
    }
    
    public boolean isPaid(){
        return this.paid;
    }
    
    public void setSalePaid(boolean paid){
        this.paid = paid;
    }
    
    public List<SaleIten> getSaleItens(){
        return this.saleItens;
    }
    
    public void setSaleItens(List<SaleIten> saleItens){
        this.saleItens = saleItens;
    }
    
    public Client getClient(){
        return this.client;
    }
    
    public void setClient(Client client){
        this.client = client;
    }   
}
