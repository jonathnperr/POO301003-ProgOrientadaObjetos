package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.MarcaDAO;
import br.edu.ifsc.fln.model.dao.ModeloDAO;
import br.edu.ifsc.fln.model.dao.MotorDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.ECategoria;
import br.edu.ifsc.fln.model.domain.ETipoCombustivel;
import br.edu.ifsc.fln.model.domain.Marca;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.Motor;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author J
 */
public class FXMLAnchorPaneCadastroModeloDialogController implements Initializable {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;
    
    @FXML
    private TextField tfDescricao;
    
    @FXML
    private ComboBox<ECategoria> cbCategoria;
    
    @FXML
    private ComboBox<Marca> cbMarca;
    
    @FXML
    private TextField tfPotencia;
    
    @FXML
    private ComboBox<ETipoCombustivel> cbCombustivel;
    
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ModeloDAO modeloDAO = new ModeloDAO();
    private final MarcaDAO marcaDAO = new MarcaDAO();
    private final MotorDAO motorDAO = new MotorDAO();
    
    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Modelo modelo;
    private Motor motor;
    
    private List<Marca> listaMarcas;
    private ObservableList<Marca> observableListMarcas; 
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        marcaDAO.setConnection(connection);
        modeloDAO.setConnection(connection);
        motorDAO.setConnection(connection);
        
        carregarComboBoxMarca();
        carregarComboBoxMotor();
        carregarComboBoxCategoria();
        setFocusLostHandle();
    }    
    
    private void setFocusLostHandle() {
        tfDescricao.focusedProperty().addListener((ov, oldV, newV) -> {
        if (!newV) {
                if (tfDescricao.getText() == null || tfDescricao.getText().isEmpty()) {
                    tfDescricao.requestFocus();
                }
            }
        });
    }
    
    public void carregarComboBoxMarca() {
        listaMarcas = marcaDAO.listar();
        
        observableListMarcas = 
                FXCollections.observableArrayList(listaMarcas);
        cbMarca.setItems(observableListMarcas);
    }
    
    public void carregarComboBoxMotor() {
        cbCombustivel.setItems( FXCollections.observableArrayList( ETipoCombustivel.values()));
    }
    
    public void carregarComboBoxCategoria() {
        cbCategoria.setItems(FXCollections.observableArrayList(ECategoria.values()));
    }
    
    public Stage getDialogStage() {
        return dialogStage;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;   
    }
    
    public boolean isBtConfirmarClicked() {
        return btConfirmarClicked;
    }
    
    public void setBtConfirmarClicked(boolean btConfirmarClicked) {
        this.btConfirmarClicked = btConfirmarClicked;
    }
    
    public Modelo getModelo() {
        return modelo;
    }
    
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
        
        this.tfDescricao.setText(modelo.getDescricao());
        this.cbCategoria.getSelectionModel().select(modelo.getCategoria());
        this.cbMarca.getSelectionModel().select(modelo.getMarca());
        
        this.tfPotencia.setText(Integer.toString(modelo.getMotor().getPotencia()));
        this.cbCombustivel.getSelectionModel().select(modelo.getMotor().getSituacao());
    }
    
    // ON ACTION botoes confirma e cancela
    @FXML
    void handleBtCancelar(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void handleBtConfirmar(ActionEvent event) {
    if (validarEntradaDeDados()) {
            modelo.setDescricao(tfDescricao.getText());
            modelo.setCategoria(cbCategoria.getSelectionModel().getSelectedItem());
            modelo.setMarca(cbMarca.getSelectionModel().getSelectedItem());
            
            modelo.getMotor().setPotencia(Integer.parseInt(tfPotencia.getText()));
            modelo.getMotor().setSituacao(cbCombustivel.getSelectionModel().getSelectedItem());
            
            btConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    //método para validar a entrada de dados
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        int potenciaMin = 0;
        
        try {
            potenciaMin = Integer.parseInt(tfPotencia.getText());
            if(potenciaMin <= /*potenciaMax*/ 0) {
                errorMessage += "O valor da potência informada deve ser maior que 0";
            }
            
        } catch (NumberFormatException e) {
            errorMessage += "Certifique-se de que a potência foi digitada";
        }
           
        if (cbCombustivel.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione o tipo de combustível do veículo!\n";
        }
        
        if (this.tfDescricao.getText() == null || this.tfDescricao.getText().length() == 0) {
            errorMessage += "Descrição inválida.\n";
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
    
    
    
}
