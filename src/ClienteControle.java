import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelos.Cliente;
import modelos.Funcionario;

public class ClienteControle extends AnchorPane{

    @FXML
    private TextField ClientePesquisaEntrada;

    @FXML
    private TableColumn<Cliente, String> ColCPF;

    @FXML
    private TableColumn<Cliente, Integer> colId;

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
    private TextField entradaClienteCpf;

    @FXML
    private TextField entradaClienteNome;

    @FXML
    private TextField entradaClienteTelefone;


    @FXML
    private TableView<Cliente> tabelaClientes;

    private Funcionario funcionario;
    private Csv csv;
    
 

    public ClienteControle(Funcionario funcionarioArg){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cenas/ClienteCena.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.csv = new Csv("src\\dados\\clientes.csv");
            this.funcionario = funcionarioArg;
            this.mostrarclientesTabela();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ObservableList<Cliente> carregarclientes(){
        ObservableList<Cliente> listclientes = FXCollections.observableArrayList();


        try{
            // List<List<String>> records = this.csv.carregaCSV("src\\dados\\clientes.csv");
            List<List<String>> records = this.csv.carregaCSV();

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

        this.colId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("id"));
        this.ColNome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        this.ColCPF.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
        this.colTelefone.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("telefone"));
        this.colCadastro.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cadastro"));
        this.colUltimaCompra.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ultima_compra"));


        this.tabelaClientes.setItems(lista);
    }

    @FXML
    void clickClientesAdicionar(ActionEvent event) {
        App app = new App();
        // Check if CPF or telefone is already in database

        if(this.entradaClienteNome.getText().isEmpty() && this.entradaClienteCpf.getText().isEmpty() && this.entradaClienteCpf.getText().isEmpty()){
            app.mostrarErro("Erro Cliente", "Por favor, preencha os campos de Nome, CPF e Telefone.");
            return;
        }

        try {
            int ultimoId = this.csv.getLastID();
            Cliente cliente = new Cliente(ultimoId+1, this.entradaClienteNome.getText(), this.entradaClienteCpf.getText(), this.entradaClienteCpf.getText(), LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString(), LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
            cliente.salvar();
            this.mostrarclientesTabela();

        } catch (Exception e) {
            // TODO: handle exception
        }
        

    }

    @FXML
    void clickClientesEditar(ActionEvent event) {
        Cliente clienteSelectionado = tabelaClientes.getSelectionModel().getSelectedItem();
        App app = new App();

        try {
            csv.removePorId(clienteSelectionado.getId());
        } catch (IOException e) {
            app.mostrarErro("Erro ao editar Cliente.", "");
            e.printStackTrace();
            return;
        }

        String nomeText = this.entradaClienteNome.getText();
        String cpfText = this.entradaClienteCpf.getText();
        String telText = this.entradaClienteTelefone.getText();

        if (nomeText.trim().isEmpty() && cpfText.trim().isEmpty() && telText.trim().isEmpty()) {
            app.mostrarErro("Erro Cliente", "Preencha todos os campoas.");
        }
            try {
                Cliente clienteAtualizado = new Cliente(clienteSelectionado.getId(), nomeText, cpfText, telText, clienteSelectionado.getCadastro(), clienteSelectionado.getUltimaCompra());
                clienteAtualizado.update();
                this.mostrarclientesTabela();
            } catch (Exception e) {
                app.mostrarErro("Erro ao editar Cliente.", "");
                e.printStackTrace();
            } 
    }

    @FXML
    void clickClientesRemover(ActionEvent event) {

        App app = new App();

        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            try {
                csv.removePorId(clienteSelecionado.getId());
                this.mostrarclientesTabela();
            } catch (IOException e) {
                app.mostrarErro("Erro ao remover produto.", "");
                e.printStackTrace();
            }
        } else {
        }

    }

    public void setFuncionario(Funcionario funcionarioArg) {
        this.funcionario = funcionarioArg;
    }


    @FXML
    void clickTabela(MouseEvent event) {

        Cliente selecionadoCliente = tabelaClientes.getSelectionModel().getSelectedItem();

        System.out.println(">>>>>"+this.colId.getText());

        if (selecionadoCliente != null) {
            this.entradaClienteNome.setText(selecionadoCliente.getNome());
            this.entradaClienteCpf.setText(String.valueOf(selecionadoCliente.getCpf()));
            this.entradaClienteTelefone.setText(String.valueOf(selecionadoCliente.getTelefone()));
        } else {
        }
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

        if (item.getUltimaCompra().contains(searchText)) {
            return true;
        }
    
    
        return false;
    }
}

