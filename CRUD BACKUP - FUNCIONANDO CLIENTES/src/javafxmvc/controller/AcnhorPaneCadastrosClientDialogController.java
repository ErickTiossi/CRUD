package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.domain.Client;


public class AcnhorPaneCadastrosClientDialogController implements Initializable {
 
    @FXML
    private Label labelClientName;
    @FXML
    private Label labelClientCPF;
    @FXML
    private Label labelClientTelphone;
    @FXML
    private TextField textFieldClientName;
    @FXML
    private TextField textFieldClientCPF;
    @FXML
    private TextField textFieldClientTelphone;
    @FXML    
    private Button buttonConfirm;
    @FXML   
    private Button buttonCancel; 
    
    // Necessario para representar quando esta tela esta aberta ou fechada
    private Stage dialogStage;
    // para saber qual botao foi clicado na tela de cadastro
    // controller precisa saber qual foi o botao, anchorpaneclientcontroller
    private boolean buttonConfirmClick = false;
    // tipo de objeto que vai ser usado para atribuir os dados de entrada a serem
    // gravados no banco de dados
    private Client client;
    
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

    
    public Client getClient() {
        return client;
    }

    
    public void setClient(Client client) {
        this.client = client;
        this.textFieldClientCPF.setText(client.getCpf());
        this.textFieldClientName.setText(client.getName());
        this.textFieldClientTelphone.setText(client.getClientTelphone() == null ? "": client.getClientTelphone());
    }
    
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    @FXML
    public void handlerConfirmButton(){
        client.setClientName(textFieldClientName.getText());
        client.setClientCPF(textFieldClientCPF.getText());
        client.setClientTelphone(textFieldClientTelphone.getText());
        
        // diz que o botao de confirmar foi clicado.
        buttonConfirmClick = true;
        // fecha tela de cadastro de cliente        
        dialogStage.close();
    }
    
    public void handlerCancelButton(){
        //apenas fecha a janela
        dialogStage.close();
    }
    
    private boolean inputValidation(){
        String error = "";
        
        if(textFieldClientName.getText() == null || textFieldClientName.getText().length() == 0){
            error += "Nome invalido\n";
        }
        
        if (textFieldClientCPF.getText() == null || textFieldClientCPF.getText().length() == 0){
            error += "Cpf invalido\n";
        }
        
        if (textFieldClientTelphone.getText() == null || textFieldClientTelphone.getText().length() == 0){
            error += "Telefone invalido\n";
        }
        if (error.length() == 0) {
            return true;
        } else {
            //Cria nova caixa de alerta para mostrar mensagens de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao tentar Cadastro de Cliente");
            alert.setHeaderText("Campos digitados de maneira errada, por favor corrigir.");
            alert.setContentText(error);
            alert.show();
            return false;
            
        }
    }
    
    
    
}
