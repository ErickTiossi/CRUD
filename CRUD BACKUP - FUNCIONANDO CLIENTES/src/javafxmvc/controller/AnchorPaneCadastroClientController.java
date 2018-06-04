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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.DAO.ClientDAO;
import javafxmvc.model.domain.Client;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.database.IDataBase;


public class AnchorPaneCadastroClientController implements Initializable {

    @FXML
    private TableView<Client> tableViewClient;
    @FXML
    private TableColumn<Client, String> tableViewColumnClientName;
    @FXML
    private TableColumn<Client, String> tableViewColumnClientCPF;
    @FXML
    private Label labelClientId;
    @FXML
    private Label labelClientName;
    @FXML 
    private Label labelClientCPF;
    @FXML
    private Label labelClientTelphone;
    @FXML
    private Button isertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;    
    
    private List<Client> clientList;
    private ObservableList<Client> observableClientList;
    
    
    private final IDataBase db = DatabaseFactory.getDataBase("mysql");
    private final Connection conn = db.Connect();
    private final ClientDAO clientDAO = new ClientDAO();
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clientDAO.setConnection(conn);
        loadClientTableView();
        
        //Listener aciona mediante qualquer evento de mouse ou teclado que age sobre a table view
        tableViewClient.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> selectClientTableViewItem(newValue));
    }       
    
    public void loadClientTableView()   {        
        tableViewColumnClientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableViewColumnClientCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        
        clientList = clientDAO.Show();
        
        observableClientList = FXCollections.observableArrayList(clientList);
        tableViewClient.setItems(observableClientList);
    }
    
    // invokado a partir do momento que um item de table view eh selecionado
    public void selectClientTableViewItem(Client client){
        //se nao tiver ninguem selecionado... todo mundo vazio
        if(client != null){
            labelClientId.setText(String.valueOf(client.getClientId()));
            labelClientName.setText(client.getName());
            labelClientCPF.setText(client.getCpf());
            labelClientTelphone.setText(client.getClientTelphone());
        } else {
            labelClientId.setText("");
            labelClientName.setText("");
            labelClientCPF.setText("");
            labelClientTelphone.setText("");
        } // if else
        
    }   
    
    // metodos dos botoes de acoes de CRUD
    @FXML
    public void handlerInsertButton() throws IOException {
        Client client = new Client();
                                     // falso se clicou em cacelar, true se clicou em confirmar
        boolean buttonConfirmClick = ShowAcnhorPaneCadastrosClientDialogController(client);
        if (buttonConfirmClick) {
            clientDAO.Insert(client);
            loadClientTableView();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            
            alert.setContentText("Inserido com sucesso.");
            alert.show();
        } // if
    }
    
    @FXML
    public void handlerUpdateButton() throws IOException {
        // pega o cliente que esta sendo mostrado na table view
        Client client = tableViewClient.getSelectionModel().getSelectedItem();
        if (client != null){
            boolean buttonConfirmClick = ShowAcnhorPaneCadastrosClientDialogController(client);
            if (buttonConfirmClick) {
                clientDAO.Update(client);
                loadClientTableView();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            
                alert.setContentText("Atualizado com sucesso.");
                alert.show();
            } // if
        } else {
            // instancia um objeto alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            
            alert.setContentText("Por favor, escolha um cliente da table.");
            alert.show();
        }
    }
    @FXML    
    public void handlerDeleteButton() throws IOException {
        Client client = tableViewClient.getSelectionModel().getSelectedItem();
        if (client != null){
            clientDAO.Delete(client);
            loadClientTableView();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);            
            alert.setContentText("Por favor, escolha um cliente da table.");
            alert.show();
        }
    }
    
    public boolean ShowAcnhorPaneCadastrosClientDialogController(Client client) throws IOException{
        //instancia um novo loader
        FXMLLoader loader = new FXMLLoader();
        //caminho do arquivo da tela de cadastro de clientes
        loader.setLocation(AcnhorPaneCadastrosClientDialogController.class.getResource("/javafxmvc/view/AcnhorPaneCadastrosClientDialog.fxml"));
        
        // tela ja esta armazenada em anchor
        AnchorPane anchor = (AnchorPane) loader.load();
        
        // Cria um stage novo para poder atribuir os atributos de titulos e cena
        Stage dialog = new Stage();
        dialog.setTitle("Cadastro de Clientes");
        
        Scene scene = new Scene(anchor);
        //Coloca no novo estagio a cena ja armazenada
        dialog.setScene(scene);
        
        //Recupera o controller da pagina que foi carregada acima
        AcnhorPaneCadastrosClientDialogController controller = loader.getController();
        //Mexendo nos atributos da pagina que foi carregada
        controller.setDialogStage(dialog);
        //setando o client na variavel de client da pagina carregada acima
        controller.setClient(client);
        
        //abre a tela e espera uma acao do usuario
        dialog.showAndWait();
        
        return controller.isButtonConfirmClick();
    }
}
  

    
