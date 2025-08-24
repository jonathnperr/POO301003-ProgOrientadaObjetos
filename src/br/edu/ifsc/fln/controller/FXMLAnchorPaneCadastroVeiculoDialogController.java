package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.CorDAO;
import br.edu.ifsc.fln.model.dao.ModeloDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Cor;
import br.edu.ifsc.fln.model.domain.Modelo;
import br.edu.ifsc.fln.model.domain.Veiculo;
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
public class FXMLAnchorPaneCadastroVeiculoDialogController implements Initializable {

    @FXML
    private Button btCancelar;

    @FXML
    private Button btConfirmar;
    
    @FXML
    private ComboBox<Cor> cbCor;

    @FXML
    private ComboBox<Modelo> cbModelo;
    
    @FXML
    private TextField tfObs;

    @FXML
    private TextField tfPlaca;
    
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final CorDAO corDAO = new CorDAO();
    private final ModeloDAO modeloDAO = new ModeloDAO();
    
    private List<Cor> listaCor;
    private ObservableList<Cor> observableListCor; 
    
    public void carregarComboBoxCor() {
        listaCor = corDAO.listar();
        observableListCor = FXCollections.observableArrayList(listaCor);
        cbCor.setItems(observableListCor);
    }
    
    private List<Modelo> listaModelo;
    private ObservableList<Modelo> observableListModelo;
    
    public void carregarComboBoxModelo() {
        listaModelo = modeloDAO.listar();
        observableListModelo = FXCollections.observableArrayList(listaModelo);
        cbModelo.setItems(observableListModelo);
    }    
    
    private Stage dialogStage;
    private boolean btConfirmarClicked = false;
    private Veiculo veiculo;

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

    public Veiculo getVeiculo() {
        return veiculo;
    }
    
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
        
        tfPlaca.setText(veiculo.getPlaca());
        tfObs.setText(veiculo.getObservacoes());
        cbCor.getSelectionModel().select(veiculo.getCor());
        cbModelo.getSelectionModel().select(veiculo.getModelo());
    }
    
    @FXML
    void handleBtCancelar(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void handleBtConfirmar(ActionEvent event) {
        if (validarEntradaDeDados()) {
            veiculo.setPlaca(tfPlaca.getText());
            veiculo.setObservacoes(tfObs.getText());
            veiculo.setCor(cbCor.getSelectionModel().getSelectedItem());
            veiculo.setModelo(cbModelo.getSelectionModel().getSelectedItem());

            btConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if (tfPlaca.getText() == null || tfPlaca.getText().isEmpty()) {
            errorMessage += "Placa inválido!\n";
        }
        
        if (cbCor.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione uma cor!\n";
        }
        
        if (cbModelo.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione um modelo!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campo(s) inválido(s), por favor corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        corDAO.setConnection(connection);
        carregarComboBoxCor();
        modeloDAO.setConnection(connection);
        carregarComboBoxModelo();
        setFocusLostHandle();
    }    
    
    private void setFocusLostHandle() {
        tfPlaca.focusedProperty().addListener((ov, oldV, newV) -> {
        if (!newV) {
                if (tfPlaca.getText() == null || tfPlaca.getText().isEmpty()) {
                    tfPlaca.requestFocus();
                }
            }
        });
    }
    
    
    
}
