
package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;


public class MainBoxController implements Initializable {
    @FXML
    private MenuItem menuItemCadastroCategory;
    @FXML
    private MenuItem menuItemCadastroClient;
    @FXML
    private MenuItem menuItemCadastroProduct;
    @FXML
    private MenuItem menuItemProcessosSale;
    @FXML
    private MenuItem menuItemGraficosMonthSales;
    @FXML
    private AnchorPane anchorPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML    
    public void handlerMenuItemCadastroClient() throws IOException {
        // caminho da tela a ser aberta, com o cast para anchorPane
        java.net.URL url = getClass().getResource("/javafxmvc/view/AnchorPaneCadastroClient.fxml");
        AnchorPane anchor = (AnchorPane) FXMLLoader.load(url);           
        //Para abrir a tela, o comando abaixo atribui ao component anchorpane da tela pricipal
        // essa tela que foi carregada acima mais ainda nao foi mostrada
        anchorPane.getChildren().setAll(anchor);
    }
    
    @FXML
    public void handlerMenuItemProcessoSale() throws IOException {
        java.net.URL url = getClass().getResource("/javafxmvc/view/AnchorPaneProcessoSale.fxml");
        AnchorPane anchor = (AnchorPane) FXMLLoader.load(url);                  
        
        anchorPane.getChildren().setAll(anchor);
    }
}