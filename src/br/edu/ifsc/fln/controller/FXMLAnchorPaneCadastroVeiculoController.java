package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.VeiculoDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Veiculo;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
public class FXMLAnchorPaneCadastroVeiculoController implements Initializable {

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExlcuir;

    @FXML
    private Button btnInserir;
    
    @FXML
    private Label lbCor;

    @FXML
    private Label lbId;

    @FXML
    private Label lbModelo;

    @FXML
    private Label lbObs;

    @FXML
    private Label lbPlaca;
    
    @FXML
    private Label lbMarca;
    
    @FXML
    private TableColumn<Veiculo, String> tcCadastroVeiculo;

    @FXML
    private TableView<Veiculo> tvCadastroVeiculo;
    
    private List<Veiculo> listaVeiculo;
    private ObservableList<Veiculo> observableListVeiculo;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final VeiculoDAO veiculoDAO = new VeiculoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        veiculoDAO.setConnection(connection);
        carregarTableViewVeiculo();

        tvCadastroVeiculo.getSelectionModel().selectedItemProperty().
            addListener((observable, oldValue, newValue) -> selecionarItemTableViewVeiculo(newValue));
    }
    
    public void carregarTableViewVeiculo() {
        tcCadastroVeiculo.setCellValueFactory(new PropertyValueFactory<>("placa"));
        listaVeiculo = veiculoDAO.listar();
        
        observableListVeiculo = FXCollections.observableArrayList(listaVeiculo);
        tvCadastroVeiculo.setItems(observableListVeiculo);
    }
    
    
    public void selecionarItemTableViewVeiculo(Veiculo veiculo) {
        if (veiculo != null) {
            lbId.setText(String.valueOf(veiculo.getId()));
            lbPlaca.setText(veiculo.getPlaca());
            lbObs.setText(veiculo.getObservacoes());
            lbCor.setText(veiculo.getCor().getNome());
            lbModelo.setText(veiculo.getModelo().getDescricao());
            lbMarca.setText(veiculo.getModelo().getMarca().getNome());
        } else {
            lbId.setText("");
            lbPlaca.setText("");
            lbObs.setText("");
            lbCor.setText("");
            lbModelo.setText("");
            lbMarca.setText("");
        }
    }
    
    @FXML
    void handleBtInserir(ActionEvent event) throws IOException {
        Veiculo veiculo = new Veiculo();
        boolean btConfirmarClicked = showFXMLAnchorPaneCadastroVeiculoDialog(veiculo);
        if (btConfirmarClicked) {
            veiculoDAO.inserir(veiculo);
            carregarTableViewVeiculo();
        } 
    }
    
    @FXML
    void handleBtAlterar(ActionEvent event) throws IOException {
        Veiculo veiculo = tvCadastroVeiculo.getSelectionModel().getSelectedItem();
        if (veiculo != null) {
            boolean btConfirmarClicked = showFXMLAnchorPaneCadastroVeiculoDialog(veiculo);
            if (btConfirmarClicked) {
                veiculoDAO.alterar(veiculo);
                carregarTableViewVeiculo();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde um Veículo na tabela ao lado");
            alert.show();
        }
    }
    
    @FXML
    void handleBtExcluir(ActionEvent event) throws IOException {
        Veiculo veiculo = tvCadastroVeiculo.getSelectionModel().getSelectedItem();
        if (veiculo != null) {
            veiculoDAO.remover(veiculo);
            carregarTableViewVeiculo();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Esta operação requer a seleção \nde um Veículo na tabela ao lado");
            alert.show();
        }
    }
    
    private boolean showFXMLAnchorPaneCadastroVeiculoDialog(Veiculo veiculo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(FXMLAnchorPaneCadastroVeiculoController.class.getResource("../view/FXMLAnchorPaneCadastroVeiculoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        //criação de um estágio de diálogo (StageDialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Veículo");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        //enviando o objeto categoria para o controller
        FXMLAnchorPaneCadastroVeiculoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setVeiculo(veiculo);
        
        //apresenta o diálogo e aguarda a confirmação do usuário
        dialogStage.showAndWait();
        
        return controller.isBtConfirmarClicked();
    }
}
