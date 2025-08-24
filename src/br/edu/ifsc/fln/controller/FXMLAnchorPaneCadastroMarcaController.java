package br.edu.ifsc.fln.controller;

import br.edu.ifsc.fln.model.dao.MarcaDAO;
import br.edu.ifsc.fln.model.database.Database;
import br.edu.ifsc.fln.model.database.DatabaseFactory;
import br.edu.ifsc.fln.model.domain.Marca;
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
public class FXMLAnchorPaneCadastroMarcaController implements Initializable {

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnRemover;

    @FXML
    private Button btnInserir;

    @FXML
    private Label labelMarcaId;

    @FXML
    private Label labelMarcaNome;

    @FXML
    private TableColumn<Marca, String> tableColumnMarca;

    @FXML
    private TableView<Marca> tableViewMarca;
    
    private List<Marca> listaMarcas;
    private ObservableList<Marca> observableListMarcas;
    
    //conexao com o banco
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final MarcaDAO marcaDAO = new MarcaDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        marcaDAO.setConnection(connection);
        carregarTableViewMarca();
        
        //mostra cada item da tabela com detalhes 
        tableViewMarca.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
        -> selecionarItemTableViewMarca(newValue));
    }    
    
    public void carregarTableViewMarca() {//lista pra tabela da esquerda
        
        tableColumnMarca.setCellValueFactory(new PropertyValueFactory<>("nome"));

        listaMarcas = marcaDAO.listar();

        observableListMarcas = FXCollections.observableArrayList(listaMarcas);
        tableViewMarca.setItems(observableListMarcas);
    
    }
    
    public void selecionarItemTableViewMarca(Marca marca) {
        if (marca != null) {
            labelMarcaId.setText(String.valueOf(marca.getId()));
            labelMarcaNome.setText(marca.getNome());
        } else {
            labelMarcaId.setText("");
            labelMarcaNome.setText("");
        }
    }
    
    @FXML 
    public void handleBtnAlterar() throws IOException {
        Marca marca = tableViewMarca.getSelectionModel().getSelectedItem();
        if (marca != null) {
            boolean btnConfirmarClicked = showFXMLAnchorPaneCadastroMarcaDialog(marca);
            if (btnConfirmarClicked) {
                marcaDAO.alterar(marca);
                carregarTableViewMarca();
            }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Escolha uma opção ao lado!");
                alert.show();
            }
    }

    @FXML
    public void handleBtnExcluir() throws IOException {
        Marca marca = tableViewMarca.getSelectionModel().getSelectedItem();
        if (marca != null) {
            marcaDAO.remover(marca);
            carregarTableViewMarca();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha uma opção ao lado!");
            alert.show();
        }
    }

    @FXML
    public void handleBtnInserir() throws IOException {
        Marca categoria = new Marca();
        boolean btConfirmarClicked = showFXMLAnchorPaneCadastroMarcaDialog(categoria);
        if (btConfirmarClicked) {
            marcaDAO.inserir(categoria);
            carregarTableViewMarca();
        } 
    }
    
    private boolean showFXMLAnchorPaneCadastroMarcaDialog(Marca marca) throws IOException {
        
    FXMLLoader loader = new FXMLLoader();

    loader.setLocation(FXMLAnchorPaneCadastroMarcaController.class.getResource("../view/FXMLAnchorPaneCadastroMarcaDialog.fxml"));
    AnchorPane page = (AnchorPane) loader.load();

    //criação de um estágio de diálogo(StageDialog) - telinha
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Cadastro de Marca");
    Scene scene = new Scene(page);
    dialogStage.setScene(scene);

    //enviando o objeto categoria para o controller
    FXMLAnchorPaneCadastroMarcaDialogController controller = loader.getController();
    controller.setDialogStage(dialogStage);
    controller.setMarca(marca);

    //mostra a tela e espera o usuário fechar
    dialogStage.showAndWait();

    return controller.isBtnConfirmarClicked();
    }
    
    
    }
    
   