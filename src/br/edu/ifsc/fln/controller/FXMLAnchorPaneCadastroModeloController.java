package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.ModeloDAO;
import br.edu.ifsc.fln.model.dao.MotorDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Modelo;
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

/**
 * FXML Controller class
 *
 * @author J
 */
public class FXMLAnchorPaneCadastroModeloController implements Initializable {

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnInserir;
    
    @FXML
    private Label lbId;
    
    @FXML
    private Label lbDescricao;
    
    @FXML
    private Label lbCategoria;
    
    @FXML
    private Label lbMarca;
    
    @FXML
    private Label lbPotencia;
    
    @FXML
    private Label lbCombustivel;
    
    @FXML
    private TableColumn<Modelo, String> tcCadastroModelo;

    @FXML
    private TableView<Modelo> tvCadastroModelo;
    
    private List<Modelo> listaModelo;
    private ObservableList<Modelo> observableListModelo;
    
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ModeloDAO modeloDAO = new ModeloDAO();
    private final MotorDAO motorDAO = new MotorDAO();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modeloDAO.setConnection(connection);
        motorDAO.setConnection(connection);
        carregarTableViewModelo();

        tvCadastroModelo.getSelectionModel().selectedItemProperty().
            addListener((observable, oldValue, newValue) ->
                selecionarItemTableViewModelo(newValue));
    }    
    
    //carrega tv
    public void carregarTableViewModelo() {
        tcCadastroModelo.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        listaModelo = modeloDAO.listar();
        
        observableListModelo = FXCollections.observableArrayList(listaModelo);
        tvCadastroModelo.setItems(observableListModelo);
    }
    
    //seleciona o item
    public void selecionarItemTableViewModelo(Modelo modelo) {
        if (modelo != null) {
            lbId.setText(String.valueOf(modelo.getId())); 
            lbDescricao.setText(modelo.getDescricao());
            lbCategoria.setText(modelo.getCategoria().name());
            lbMarca.setText(modelo.getMarca().getNome());
            lbPotencia.setText(Integer.toString(modelo.getMotor().getPotencia()));
            lbCombustivel.setText(modelo.getMotor().getSituacao().name());
        } else {
            lbId.setText(""); 
            lbDescricao.setText("");
            lbCategoria.setText("");
            lbMarca.setText("");
            lbPotencia.setText("");
            lbCombustivel.setText("");
        }
        
    }
   
    //botoes e etcssss
    @FXML 
    public void handleBtnAlterar() throws IOException {
        Modelo modelo = tvCadastroModelo.getSelectionModel().getSelectedItem();
        if (modelo != null) {
            modelo.getMotor().setModelo(modelo);
            boolean btConfirmarClicked = showFXMLAnchorPaneCadastroModeloDialog(modelo);
            if (btConfirmarClicked) {
                modeloDAO.alterar(modelo);
                motorDAO.alterar(modelo.getMotor());
                carregarTableViewModelo();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Marca na tabela ao lado");
            alert.show();
        }
    }
    
    
    @FXML
    public void handleBtnExcluir() throws IOException {
        Modelo modelo = tvCadastroModelo.getSelectionModel().getSelectedItem();
        if (modelo != null) {
            modeloDAO.remover(modelo);
            carregarTableViewModelo();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde uma Marca na tabela ao lado");
            alert.show();
        }
    }
    
    @FXML
    public void handleBtnInserir() throws IOException {
        Modelo modelo = new Modelo();
        boolean btConfirmarClicked = showFXMLAnchorPaneCadastroModeloDialog(modelo);
        if (btConfirmarClicked) {
            modeloDAO.inserir(modelo);
            carregarTableViewModelo();
        }
    }
    
    //dialogo de cadastro
    private boolean showFXMLAnchorPaneCadastroModeloDialog(Modelo modelo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(FXMLAnchorPaneCadastroModeloDialogController.class.getResource("../view/FXMLAnchorPaneCadastroModeloDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        //criação de um estágio de diálogo (StageDialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Modelo");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //enviando o objeto modelo para o controller
        FXMLAnchorPaneCadastroModeloDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setModelo(modelo);
        
        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();
        
        return controller.isBtConfirmarClicked();
    }
    
    
    
    
    
}
