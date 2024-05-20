package controles;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import Uteis.Csv;
import Uteis.Erro;
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

    private static final String CLIENTE_CENA = "/cenas/ClienteCena.fxml"; // Caminho para o arquivo FXML da cena do cliente
    private static final String CLIENTES_CSV = "src\\dados\\clientes.csv"; // Caminho para o arquivo CSV dos clientes

    @FXML
    private TextField ClientePesquisaEntrada; // Campo de texto para pesquisa de clientes

    @FXML
    private TableView<Cliente> tabelaClientes; // Tabela para exibir os clientes

    @FXML
    private TableColumn<Cliente, String> ColCPF; // Coluna para exibir o CPF do cliente

    @FXML
    private TableColumn<Cliente, Integer> colId; // Coluna para exibir o ID do cliente

    @FXML
    private TableColumn<Cliente, String> ColNome; // Coluna para exibir o nome do cliente

    @FXML
    private TableColumn<Cliente, String> colCadastro; // Coluna para exibir a data de cadastro do cliente

    @FXML
    private TableColumn<Cliente, String> colEmail; // Coluna para exibir o email do cliente

    @FXML
    private TableColumn<Cliente, Integer> colTelefone; // Coluna para exibir o telefone do cliente

    @FXML
    private TableColumn<Cliente, String> colUltimaCompra; // Coluna para exibir a data da última compra do cliente

    @FXML
    private Button btnClientesAdicionar; // Botão para adicionar um novo cliente

    @FXML
    private Button btnClientesEditar; // Botão para editar um cliente existente

    @FXML
    private Button btnClientesRemover; // Botão para remover um cliente

    @FXML
    private TextField entradaClienteCpf; // Campo de texto para entrada do CPF do cliente

    @FXML
    private TextField entradaClienteNome; // Campo de texto para entrada do nome do cliente

    @FXML
    private TextField entradaClienteTelefone; // Campo de texto para entrada do telefone do cliente

    private Csv csv; // Objeto para manipulação do arquivo CSV

    public ClienteControle(Funcionario funcionarioArg) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CLIENTE_CENA));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.csv = new Csv(CLIENTES_CSV); // Inicializa o objeto CSV
            this.mostrarclientesTabela(); // Mostra os clientes na tabela

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Cliente> carregarclientes() {
        ObservableList<Cliente> listclientes = FXCollections.observableArrayList(); // Lista para armazenar os clientes

        try {
            List<List<String>> records = this.csv.carregaCSV(); // Carrega os registros do arquivo CSV

            for (List<String> rs : records) {
                Cliente cliente = new Cliente(Integer.parseInt(rs.get(0)), rs.get(1), rs.get(2), rs.get(3), rs.get(4), rs.get(5)); // Cria um objeto Cliente para cada registro
                listclientes.add(cliente); // Adiciona o cliente à lista
            }
        } catch (Exception ex) { 
            Erro.mostrarErro("Erro Clientes", "Erro ao tentar carregar lista de Clientes.");
            ex.printStackTrace();
        }

        return listclientes; // Retorna a lista de clientes
    }

    private void mostrarclientesTabela() {
        ObservableList<Cliente> lista = this.carregarclientes(); // Carrega a lista de clientes

        this.colId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("id")); // Define a propriedade a ser exibida na coluna ID
        this.ColNome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome")); // Define a propriedade a ser exibida na coluna Nome
        this.ColCPF.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf")); // Define a propriedade a ser exibida na coluna CPF
        this.colTelefone.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("telefone")); // Define a propriedade a ser exibida na coluna Telefone
        this.colCadastro.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cadastro")); // Define a propriedade a ser exibida na coluna Cadastro
        this.colUltimaCompra.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ultimaCompra")); // Define a propriedade a ser exibida na coluna Última Compra

        this.tabelaClientes.setItems(lista); // Define a lista de clientes a ser exibida na tabela
    }

    @FXML
    private void clickClientesAdicionar(ActionEvent event) {

        // TODO: Verificar se o CPF ou telefone já estão no banco de dados
        if (this.entradaClienteNome.getText().isEmpty() && this.entradaClienteCpf.getText().isEmpty()
                && this.entradaClienteCpf.getText().isEmpty()) {
            Erro.mostrarErro("Erro Cliente", "Por favor, preencha os campos (Nome, CPF e Telefone).");
            return;
        }

        try {
            int ultimoId = this.csv.getLastID(); // Obtém o último ID do arquivo CSV
            Cliente cliente = new Cliente(ultimoId + 1, this.entradaClienteNome.getText(),
                    this.entradaClienteCpf.getText(), this.entradaClienteCpf.getText(),
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString(),
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString()); // Cria um novo objeto Cliente
            cliente.salvar(); // Salva o cliente no arquivo CSV
            this.mostrarclientesTabela(); // Atualiza a tabela de clientes

        } catch (Exception e) {
            Erro.mostrarErro("Erro Clientes", "Erro ao adicionar novo Cliente.");
            e.printStackTrace();
        }

    }

    @FXML
    private void clickClientesEditar(ActionEvent event) {
        Cliente clienteSelectionado = tabelaClientes.getSelectionModel().getSelectedItem(); // Obtém o cliente selecionado na tabela

        try {
            csv.removePorId(clienteSelectionado.getId()); // Remove o cliente selecionado do arquivo CSV
        } catch (IOException e) {
            Erro.mostrarErro("Erro Cliente.", "Erro interno ao tentar editar Cliente.");
            e.printStackTrace();
            return;
        }

        String nomeText = this.entradaClienteNome.getText(); // Obtém o texto do campo Nome
        String cpfText = this.entradaClienteCpf.getText(); // Obtém o texto do campo CPF
        String telText = this.entradaClienteTelefone.getText(); // Obtém o texto do campo Telefone

        if (nomeText.trim().isEmpty() && cpfText.trim().isEmpty() && telText.trim().isEmpty()) {
            Erro.mostrarErro("Erro Cliente.", "Preencha todos os campos.");
        }
        try {
            Cliente clienteAtualizado = new Cliente(clienteSelectionado.getId(), nomeText, cpfText, telText, clienteSelectionado.getCadastro(), clienteSelectionado.getUltimaCompra()); // Cria um novo objeto Cliente com os dados atualizados
            // clienteAtualizado.update(); // Atualiza o cliente no arquivo CSV
            csv.adicionar(clienteAtualizado.toString());
            this.mostrarclientesTabela(); // Atualiza a tabela de clientes
        } catch (Exception e) {
            Erro.mostrarErro("Erro Cliente.", "Erro interno ao tentar editar Cliente.");
            e.printStackTrace();
        }
    }

    @FXML
    private void clickClientesRemover(ActionEvent event) {


        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem(); // Obtém o cliente selecionado na tabela
        if (clienteSelecionado != null) {
            try {
                csv.removePorId(clienteSelecionado.getId()); // Remove o cliente selecionado do arquivo CSV
                this.mostrarclientesTabela(); // Atualiza a tabela de clientes
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
        } else {
        }

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
