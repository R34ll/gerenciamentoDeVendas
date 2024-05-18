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

public class ClienteControle extends AnchorPane {

    private static final String CLIENTE_CENA = "cenas/ClienteCena.fxml";
    private static final String CLIENTES_CSV = "src\\dados\\clientes.csv";

    @FXML
    private TextField ClientePesquisaEntrada;

    @FXML
    private TableView<Cliente> tabelaClientes;

    @FXML
    private TableColumn<Cliente, String> ColCPF;

    @FXML
    private TableColumn<Cliente, Integer> colId;

    @FXML
    private TableColumn<Cliente, String> ColNome;

    @FXML
    private TableColumn<Cliente, String> colCadastro;

    @FXML
    private TableColumn<Cliente, String> colEmail;

    @FXML
    private TableColumn<Cliente, Integer> colTelefone;

    @FXML
    private TableColumn<Cliente, String> colUltimaCompra;

    @FXML
    private Button btnClientesAdicionar;

    @FXML
    private Button btnClientesEditar;

    @FXML
    private Button btnClientesRemover;

    @FXML
    private TextField entradaClienteCpf;

    @FXML
    private TextField entradaClienteNome;

    @FXML
    private TextField entradaClienteTelefone;

    private Csv csv;

    public ClienteControle(Funcionario funcionarioArg) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CLIENTE_CENA));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.csv = new Csv(CLIENTES_CSV);
            this.mostrarclientesTabela();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Cliente> carregarclientes() {
        ObservableList<Cliente> listclientes = FXCollections.observableArrayList();

        try {
            List<List<String>> records = this.csv.carregaCSV();

            for (List<String> rs : records) {
                Cliente cliente = new Cliente(Integer.parseInt(rs.get(0)), rs.get(1), rs.get(2), rs.get(3), rs.get(4), rs.get(5));
                listclientes.add(cliente);
            }
        } catch (Exception ex) { 
            Erro.mostrarErro("Erro Clientes", "Erro ao tentar carregar lista de Clientes.");
            ex.printStackTrace();
        }

        return listclientes;
    }

    private void mostrarclientesTabela() {
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
    private void clickClientesAdicionar(ActionEvent event) {

        // TODO: Check if CPF or telefone is already in database
        if (this.entradaClienteNome.getText().isEmpty() && this.entradaClienteCpf.getText().isEmpty()
                && this.entradaClienteCpf.getText().isEmpty()) {
            Erro.mostrarErro("Erro Cliente", "Por favor, preencha os campos (Nome, CPF e Telefone).");
            return;
        }

        try {
            int ultimoId = this.csv.getLastID();
            Cliente cliente = new Cliente(ultimoId + 1, this.entradaClienteNome.getText(),
                    this.entradaClienteCpf.getText(), this.entradaClienteCpf.getText(),
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString(),
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
            cliente.salvar();
            this.mostrarclientesTabela();

        } catch (Exception e) {
            Erro.mostrarErro("Erro Clientes", "Erro adicionar novo Cliente.");
            e.printStackTrace();
        }

    }

    @FXML
    private void clickClientesEditar(ActionEvent event) {
        Cliente clienteSelectionado = tabelaClientes.getSelectionModel().getSelectedItem();

        try {
            csv.removePorId(clienteSelectionado.getId());
        } catch (IOException e) {
            Erro.mostrarErro("Erro Cliente.", "Erro interno ao tentar editar Cliente.");
            e.printStackTrace();
            return;
        }

        String nomeText = this.entradaClienteNome.getText();
        String cpfText = this.entradaClienteCpf.getText();
        String telText = this.entradaClienteTelefone.getText();

        if (nomeText.trim().isEmpty() && cpfText.trim().isEmpty() && telText.trim().isEmpty()) {
            Erro.mostrarErro("Erro Cliente.", "Preencha todos os campoas.");
        }
        try {
            Cliente clienteAtualizado = new Cliente(clienteSelectionado.getId(), nomeText, cpfText, telText, clienteSelectionado.getCadastro(), clienteSelectionado.getUltimaCompra());
            clienteAtualizado.update();
            this.mostrarclientesTabela();
        } catch (Exception e) {
            Erro.mostrarErro("Erro Cliente.", "Erro interno ao tentar editar Cliente.");
            e.printStackTrace();
        }
    }

    @FXML
    private void clickClientesRemover(ActionEvent event) {


        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            try {
                csv.removePorId(clienteSelecionado.getId());
                this.mostrarclientesTabela();
            } catch (IOException e) {
                Erro.mostrarErro("Erro Cliente.", "Erro ao tentar remover Cliente do banco de dados.");
                e.printStackTrace();
            }
        } else {
            Erro.mostrarErro("Erro Cliente", "Por favor, Selecione na tabela o cliente a ser removido.");
            return;
        }

    }

    @FXML
    private void clickTabela(MouseEvent event) {

        Cliente selecionadoCliente = tabelaClientes.getSelectionModel().getSelectedItem();

        if (selecionadoCliente != null) {
            this.entradaClienteNome.setText(selecionadoCliente.getNome());
            this.entradaClienteCpf.setText(String.valueOf(selecionadoCliente.getCpf()));
            this.entradaClienteTelefone.setText(String.valueOf(selecionadoCliente.getTelefone()));
        } else {}

    }

    @FXML
    private void pesquisaEntradaMudou(KeyEvent event) {
        String pesquisa = this.ClientePesquisaEntrada.getText().toLowerCase();
        ObservableList<Cliente> filteredList = FXCollections.observableArrayList();

        if (pesquisa.isEmpty() || event.getCode() == KeyCode.BACK_SPACE) {
            mostrarclientesTabela();
        }

        for (Cliente item : tabelaClientes.getItems()) {
            if (pesquisaAlgo(item, pesquisa)) {
                filteredList.add(item);
            }
        }
        tabelaClientes.setItems(filteredList);
    }

    private boolean pesquisaAlgo(Cliente item, String searchText) {

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
