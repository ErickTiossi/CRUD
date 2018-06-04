
package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.DAO.SaleDAO;
import javafxmvc.model.DAO.SaleItensDAO;
import javafxmvc.model.DAO.ProductDAO;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.database.IDataBase;
import javafxmvc.model.domain.Sale;
import javafxmvc.model.domain.SaleIten;


public class AnchorPaneProcessoSaleController implements Initializable {

@FXML
private TableView<Sale> tableViewSale;
@FXML
private TableColumn<Sale, Integer> tableColumnSaleId;
@FXML
private TableColumn<Sale, LocalDate> tableColumnSaleDate;
@FXML
private TableColumn<Sale, Sale> tableColumnSaleClient;
@FXML
private Label labelSaleId;
@FXML
private Label labelSaleDate;
@FXML
private Label labelSaleValue;
@FXML
private Label labelSalePaid;
@FXML
private Label labelSaleClient;
@FXML
private Button insertButton;
@FXML
private Button updateButton;
@FXML
private Button deleteButton;

// preciso para lisstar todas as vendas armazenadas no db
private List<Sale> saleList;
// pra poder mostrar na tableview
private ObservableList<Sale> obersavableSaleList;

//coisas do banco de dados
private final IDataBase db = DatabaseFactory.getDataBase("mysql");
private final Connection conn = db.Connect();
private final SaleDAO saleDAO = new SaleDAO();
private final SaleItensDAO saleItensDAO = new SaleItensDAO();
private final ProductDAO productDAO = new ProductDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        saleDAO.setConnection(conn);
        loadSaleTableView();
        
        tableViewSale.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldvValue, newValue) -> selectTableViewSaleItem(newValue));
    }    
 
    public void loadSaleTableView(){
        tableColumnSaleId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnSaleDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnSaleClient.setCellValueFactory(new PropertyValueFactory<>("client"));
        
        saleList = saleDAO.Show();
        
        obersavableSaleList = FXCollections.observableArrayList(saleList);
        
        tableViewSale.setItems(obersavableSaleList);
        
    }
    
    public void selectTableViewSaleItem(Sale sale){
        if(sale != null){
            labelSaleId.setText(String.valueOf(sale.getId()));
            labelSaleDate.setText(String.valueOf(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelSaleValue.setText(String.format("%.2f", sale.getSaleValue()));
            labelSalePaid.setText(String.valueOf(sale.isPaid()== false? "Nao": "Sim"));
            labelSaleClient.setText(sale.getClient().toString());
        } else {
            labelSaleId.setText("");
            labelSaleDate.setText("");
            labelSaleValue.setText("");
            labelSalePaid.setText("");
            labelSaleClient.setText("");
        }
    }
    
    @FXML
    public void handlerSaleInsertButton() throws IOException {
        Sale sale = new Sale();
        List<SaleIten> saleItens = new ArrayList<SaleIten>();
        sale.setSaleItens(saleItens);
                                     // falso se clicou em cacelar, true se clicou em confirmar
        boolean buttonConfirmClick = ShowAnchorPaneProcessosSalesDialog(sale);
        if (buttonConfirmClick) {
            saleDAO.Insert(sale);
            loadSaleTableView();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            
            alert.setContentText("Inserido com sucesso.");
            alert.show();
        } // if
    }
    
     public boolean ShowAnchorPaneProcessosSalesDialog(Sale sale) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AnchorPaneProcessosSalesDialogController.class.getResource("/javafxmvc/view/AnchorPaneProcessosSalesDialog.fxml"));
            
           AnchorPane page = (AnchorPane) loader.load();
            
           Stage dialogStage = new Stage();
           dialogStage.setTitle("Registros de Vendas");
           Scene scene = new Scene(page);
           
           dialogStage.setScene(scene);
           
           AnchorPaneProcessosSalesDialogController controller = loader.getController();
           
           controller.setSale(sale);
           
           dialogStage.showAndWait();
           return controller.isButtonConfirmClick();           
    }    
}
