package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.domain.Marca;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author J
 */
public class FXMLAnchorPaneCadastroMarcaDialogController implements Initializable {
    
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;
    
    @FXML
    private TextField tfMarcaNome;
    
    @FXML
    private Label labelMarcaNome;
    
    private Stage dialogStage;
    private boolean btnConfirmarClicked = false;
    private Marca marca;
    
    /**
     * @return the dialogStage
     */
    public Stage getDialogStage() {
        return dialogStage;
    }

    /**
     * @param dialogStage the dialogStage to set
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * @return the btnConfirmarClicked
     */
    public boolean isBtnConfirmarClicked() {
        return btnConfirmarClicked;
    }

    /**
     * @param btnConfirmarClicked the btnConfirmarClicked to set
     */
    public void setBtnConfirmarClicked(boolean btnConfirmarClicked) {
        this.btnConfirmarClicked = btnConfirmarClicked;
    }

    /**
     * @return the marca
     */
    public Marca getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(Marca marca) {
        this.marca = marca;
        this.tfMarcaNome.setText(marca.getNome());
    }
    
    @FXML
    void handleBtnConfirmar(ActionEvent event) {
        
        if(validarEntradaDeDados()){
        marca.setNome(tfMarcaNome.getText());

        btnConfirmarClicked = true;
        dialogStage.close();
        }
    }
    
    @FXML
    void handleBtnCancelar(ActionEvent event) {
        dialogStage.close();
    }
    
    //método para validar a entrada de dados
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        if (this.tfMarcaNome.getText() == null || this.tfMarcaNome.getText().length() == 0) {
            errorMessage += "Input inválido.\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            //exibindo uma mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Corrija os campos inválidos!");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
