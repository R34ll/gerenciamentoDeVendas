import java.util.List;

import dados.Csv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import modelos.Cliente;
import modelos.Funcionario;

public class ClienteControle extends AnchorPane{

    @FXML
    private TextField ClientePesquisaEntrada;

    @FXML
    private TableColumn<Cliente, String> ColCPF;

    @FXML
    private TableColumn<Cliente, Integer> ColId;

    @FXML
    private TableColumn<Cliente, String> ColNome;

    @FXML
    private Button btnClientesAdicionar;

    @FXML
    private Button btnClientesEditar;

    @FXML
    private Button btnClientesRemover;

    @FXML
    private TableColumn<Cliente, String> colCadastro;

    @FXML
    private TableColumn<Cliente, String> colEmail;

    @FXML
    private TableColumn<Cliente, Integer> colTelefone;

    @FXML
    private TableColumn<Cliente, String> colUltimaCompra;

    @FXML
    private TextField entradaClienteClienteId;

    @FXML
    private TextField entradaClienteNome;

    @FXML
    private TextField entradaClienteNome1;

    @FXML
    private TextField entradaClienteProdutoId;

    @FXML
    private TableView<Cliente> tabelaClientes;

    private Funcionario funcionario;
    
 

    public ClienteControle(Funcionario funcionario){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cenas/ClienteCena.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.setFuncionario(funcionario);
            this.mostrarclientesTabela();;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Cliente> carregarclientes(){
        ObservableList<Cliente> listclientes = FXCollections.observableArrayList();

        Csv csv = new Csv();

        try{
            List<List<String>> records = csv.carregaCSV("src\\dados\\clientes.csv");

            for(List<String> rs: records){

                Cliente cliente = new Cliente(
                    Integer.parseInt(rs.get(0)), 
                    rs.get(1), 
                     rs.get(2), 
                    rs.get(3), 
                    rs.get(4),
                    rs.get(5)
                    );
                listclientes.add(cliente);
            }
        }catch(Exception ex){
            System.err.println(ex);
        }

        return listclientes;
    }


    public void mostrarclientesTabela(){
        ObservableList<Cliente> lista = this.carregarclientes();

        this.ColId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("id"));
        this.ColNome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        this.ColCPF.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
        this.colTelefone.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("telefone"));
        this.colCadastro.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cadastro"));
        this.colUltimaCompra.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ultima_compra"));


        this.tabelaClientes.setItems(lista);
    }

    @FXML
    void clickClientesAdicionar(ActionEvent event) {

    }

    @FXML
    void clickClientesEditar(ActionEvent event) {

    }

    @FXML
    void clickClientesRemover(ActionEvent event) {

    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @FXML
    void pesquisaEntradaMudou(KeyEvent event) {
        String search = this.ClientePesquisaEntrada.getText().toLowerCase();
        ObservableList<Cliente> filteredList = FXCollections.observableArrayList();
 
        if (search.isEmpty() || event.getCode() == KeyCode.BACK_SPACE){mostrarclientesTabela();}

        for (Cliente item : tabelaClientes.getItems()) {
            if (pesquisaAlgo(item, search)) {
                filteredList.add(item);
            }
        }
        tabelaClientes.setItems(filteredList);
    }


    private boolean pesquisaAlgo(Cliente item, String searchText) {
        searchText = searchText.toLowerCase();

        if (String.valueOf(item.getId()).contains(searchText)) {
            return true;
        }
    
        if (item.getNome().contains(searchText)) {
            return true;
        }

        if (item.getCpf().contains(searchText)) {
            return true;
        }

        if (item.getTelefone().contains(searchText)) {
            return true;
        }

        if (item.getCadastro().contains(searchText)) {
            return true;
        }

        if (item.getUltima_compra().contains(searchText)) {
            return true;
        }
    
    
        return false;
    }
}

