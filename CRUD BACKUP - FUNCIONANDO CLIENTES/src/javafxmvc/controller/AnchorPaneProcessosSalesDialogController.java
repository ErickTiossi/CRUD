package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.DAO.ClientDAO;
import javafxmvc.model.DAO.ProductDAO;
import javafxmvc.model.DAO.SaleDAO;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.database.IDataBase;
import javafxmvc.model.domain.Client;
import javafxmvc.model.domain.Product;
import javafxmvc.model.domain.Sale;
import javafxmvc.model.domain.SaleIten;


public class AnchorPaneProcessosSalesDialogController implements Initializable {
    @FXML
    private ComboBox comboBox;
    @FXML
    private DatePicker datePickerSaleDate;
    @FXML
    private CheckBox checkBoxSalePaid;
    @FXML
    private ComboBox comboBoxSaleProduct;
    @FXML
    private TableView<SaleIten> tableViewSaleSaleItens;
    @FXML
    private Button buttonAdd;
    @FXML
    private TableColumn<SaleIten, Product> tableViewColumnProduct;
    @FXML
    private TableColumn<SaleIten, Integer> tableViewColumnQuantity; 
    @FXML
    private TableColumn<SaleIten, Double> tableViewColumnValue;
    @FXML
    private TextField textFieldValue;
    @FXML
    private Button buttonConfirm;
    @FXML
    private Button buttonCancel;
    @FXML
    private TextField textFieldProductQuantity;
    
    private List<Client> clientList;
    private List<Product> productList;
    private ObservableList<Client> obersvableClientList;
    private ObservableList<Product> obersavableProductList;
    private ObservableList<SaleIten> obersableSaleItenList;
    
    private final IDataBase db = DatabaseFactory.getDataBase("mysql");
    private final Connection conn = db.Connect();
    private final ClientDAO clientDAO = new ClientDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final SaleDAO saleDAO = new SaleDAO();
    
    private Stage dialogStage;
    private boolean buttonConfirmClick;
    private Sale sale;
    
    
    public Stage getDialogStage() {
        return dialogStage;
    }

    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    
    public boolean isButtonConfirmClick() {
        return buttonConfirmClick;
    }

    
    public void setButtonConfirmClick(boolean buttonConfirmClick) {
        this.buttonConfirmClick = buttonConfirmClick;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }   
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientDAO.setConnection(conn);
        productDAO.setConnection(conn);
        loadClientComboBox();
        loadProductComboBox();
        tableViewColumnProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        tableViewColumnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));;
        tableViewColumnValue.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    public void loadClientComboBox(){
        clientList = clientDAO.Show();
        //obersvableClientList = FXCollections.observableArrayList(clientList);
        //comboBoxSaleClient.setItems(obersvableClientList);
        comboBox.setItems(FXCollections.observableArrayList(clientList));
    }
    
    public void loadProductComboBox(){
        productList = productDAO.Show();
        obersavableProductList = FXCollections.observableArrayList(productList);
        comboBoxSaleProduct.setItems(obersavableProductList);        
    }   
    
    @FXML
    public void handlerButtonAdd() {
        Product product;
        SaleIten saleIten = new SaleIten();
        if(comboBoxSaleProduct.getSelectionModel().getSelectedItem() != null){
            product = (Product) comboBoxSaleProduct.getSelectionModel().getSelectedItem();
            
            if(product.getProductQuantity()> 0){
                saleIten.setProduct((Product) comboBoxSaleProduct.getSelectionModel().getSelectedItem());
                saleIten.setQuantity(Integer.parseInt(textFieldProductQuantity.getText()));
                saleIten.setPrice(saleIten.getProduct().getProductPrice() * saleIten.getQuantity());
                sale.getSaleItens().add(saleIten);
                sale.setSaleValue(sale.getSaleValue() + saleIten.getPrice());
                obersableSaleItenList = FXCollections.observableArrayList(sale.getSaleItens());
                tableViewSaleSaleItens.setItems(obersableSaleItenList);
                textFieldValue.setText(String.format("%.2f", sale.getSaleValue()));                
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erro na escolha do produto");
                alert.setContentText("Nao existe a quantidade de produto desejada");
                alert.show();
            }      
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro na escolha do produto");
            alert.setContentText("Selecione o produto desejado");
            alert.show();
        }
    }
    
    @FXML
    public void handlerConfirmButton() {
        if (inputValidation()) {
            sale.setClient((Client) comboBox.getSelectionModel().getSelectedItem());
            sale.setSalePaid(checkBoxSalePaid.isSelected());
            sale.setSaleDate(datePickerSaleDate.getValue());
            buttonConfirmClick = true;
        }
    }
    
    @FXML
    public void handlerSaleCancelButton(){
        //apenas fecha a janela
        getDialogStage().close();
    }
    
    public boolean inputValidation(){
        String error = "";
        if (comboBox.getSelectionModel().getSelectedItem() == null){
            error += "Cliente invalido\n";            
        }
        
        if(datePickerSaleDate.getValue() == null){
            error += "Data invalida\n";            
        }
        
        if (obersableSaleItenList == null){
            error += "Itens de venda invalidoss";
        }
        
        if (error.length() == 0){
            return true;
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("Campos invalidos");
            a.show();
            return false;       
        }
    }
    
    
}
