package javafxmvc.model.domain;

import java.io.Serializable;

public class Client {
    private int clientId;
    private String name;
    private String cpf;
    private String telphone = null;
    
    public Client(){
        
    }
    
    public Client(int clientId, String name, String cpf){
        this.clientId = clientId;
        this.name = name;
        this.cpf = cpf;
    }
    
    public int getClientId(){
        return this.clientId;                
    }
    
    public void setClientId(int clientId){
        this.clientId = clientId;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setClientName(String name){
        this.name = name;
    }
    
    public String getCpf(){
        return this.cpf;
    }
    
    public void setClientCPF(String cpf){
        this.cpf = cpf;
    }
    
    public String getClientTelphone(){
        return this.telphone;
    }
    
    public void setClientTelphone(String telphone){
        this.telphone = telphone;
    }
    
    @Override
    public String toString(){
        return this.name;
    }

}
