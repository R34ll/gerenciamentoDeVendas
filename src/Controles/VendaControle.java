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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelos.Funcionario;
import modelos.Venda;

public class VendaControle extends AnchorPane {

    private String VENDA_CENA = "/cenas/VendaCena.fxml"; // Caminho para o arquivo FXML da cena de venda
    private String VENDAS_CSV = "src\\dados\\vendas.csv"; // Caminho para o arquivo CSV das vendas
    @FXML
    private TableColumn<Venda, String> ColData; // Coluna para exibir a data da venda

    @FXML
    private TableColumn<Venda, Integer> ColId; // Coluna para exibir o ID da venda

    @FXML
    private TableColumn<Venda, Double> ColPreco; // Coluna para exibir o preço da venda

    @FXML
    private TableColumn<Venda, Integer> colClienteId; // Coluna para exibir o ID do cliente

    @FXML
    private TableColumn<Venda, Integer> colFuncionarioId; // Coluna para exibir o ID do funcionário

    @FXML
    private TableColumn<Venda, Integer> colProdutoId; // Coluna para exibir o ID do produto

    @FXML
    private TableView<Venda> tabelaVendas; // Tabela para exibir as vendas

    @FXML
    private Button btnVendasAdicionar; // Botão para adicionar uma nova venda

    @FXML
    private Button btnVendasEditar; // Botão para editar uma venda existente

    @FXML
    private Button btnVendasRemover; // Botão para remover uma venda

    @FXML
    private TextField entradaVendaClienteId; // Campo de texto para entrada do ID do cliente

    @FXML
    private DatePicker entradaVendaData; // Seletor de data para entrada da data da venda

    @FXML
    private TextField entradaVendaFuncionarioId; // Campo de texto para entrada do ID do funcionário

    @FXML
    private TextField entradaVendaId; // Campo de texto para entrada do ID da venda

    @FXML
    private TextField entradaVendaPreco; // Campo de texto para entrada do preço da venda

    @FXML
    private TextField entradaVendaProdutoId; // Campo de texto para entrada do ID do produto

    @FXML
    private TextField vendaPesquisaEntrada; // Campo de texto para pesquisa de vendas

    private Funcionario funcionario; // Objeto para armazenar o funcionário logado
    private Csv csv; // Objeto para manipulação do arquivo CSV

    public VendaControle(Funcionario funcionarioArg) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VENDA_CENA)); // Carrega o arquivo FXML da cena de venda

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(); // Carrega a cena de venda
            this.csv = new Csv(VENDAS_CSV); // Inicializa o objeto CSV
            this.funcionario = funcionarioArg; // Armazena o funcionário logado
            this.entradaVendaFuncionarioId.setText(String.valueOf(this.funcionario.getId())); // Define o texto do campo ID do Funcionário com o ID do funcionário logado


            this.mostrarVendasTabela(); // Mostra as vendas na tabela
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Venda> carregarVendas() {
        ObservableList<Venda> listVendas = FXCollections.observableArrayList(); // Lista para armazenar as vendas

        try {
            List<List<String>> records = csv.carregaCSV(); // Carrega os registros do arquivo CSV
            for (List<String> rs : records) {

                Venda venda = new Venda(Integer.parseInt(rs.get(0)), Double.parseDouble(rs.get(1)), rs.get(2),
                        rs.get(3), Integer.parseInt(rs.get(4)), Integer.parseInt(rs.get(5))); // Cria um objeto Venda para cada registro
                listVendas.add(venda); // Adiciona a venda à lista
            }
        } catch (Exception ex) {
            Erro.mostrarErro("Erro venda.", "Erro ao carregar dados para a tabela.");
            System.err.println(ex);
        }

        return listVendas; // Retorna a lista de vendas
    }

    public void mostrarVendasTabela() {
        ObservableList<Venda> lista = this.carregarVendas(); // Carrega a lista de vendas

        this.ColId.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("id")); // Define a propriedade a ser exibida na coluna ID
        this.ColPreco.setCellValueFactory(new PropertyValueFactory<Venda, Double>("preco")); // Define a propriedade a ser exibida na coluna Preço
        this.ColData.setCellValueFactory(new PropertyValueFactory<Venda, String>("data")); // Define a propriedade a ser exibida na coluna Data
        this.colProdutoId.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("produtoId")); // Define a propriedade a ser exibida na coluna ID do Produto
        this.colClienteId.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("clienteId")); // Define a propriedade a ser exibida na coluna ID do Cliente
        this.colFuncionarioId.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("funcionarioId")); // Define a propriedade a ser exibida na coluna ID do Funcionário

        this.tabelaVendas.setItems(lista); // Define a lista de vendas a ser exibida na tabela
    }

    
    @FXML
    void clickTabela(MouseEvent event) {

        Venda selecionadoVenda = tabelaVendas.getSelectionModel().getSelectedItem();
        if (selecionadoVenda != null) {

            this.entradaVendaId.setText(String.valueOf(selecionadoVenda.getId()));
            this.entradaVendaPreco.setText(String.valueOf(selecionadoVenda.getPreco()));
            this.entradaVendaProdutoId.setText(String.valueOf(selecionadoVenda.getProdutoId()));
            this.entradaVendaClienteId.setText(String.valueOf(selecionadoVenda.getClienteId()));
            this.entradaVendaFuncionarioId.setText(String.valueOf(selecionadoVenda.getFuncionarioId()));
            this.entradaVendaData.setValue(LocalDate.parse(selecionadoVenda.getData(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        } else {
        }
    }


    

    @FXML
    void clickVendasAdicionar(ActionEvent event) {

        // Atualiza o arquivo CSV com os novos dados da venda
        // String idVendaText = this.entradaVendaId.getText();
        String precoText = this.entradaVendaPreco.getText(); // Obtém o texto do campo Preço
        String produtoIdText = this.entradaVendaProdutoId.getText(); // Obtém o texto do campo ID do Produto
        String clientIdText = this.entradaVendaClienteId.getText(); // Obtém o texto do campo ID do Cliente
        String dataText = null; // Obtém o texto do campo Data
        // this.entradaVendaFuncionarioId.setText(String.valueOf(this.funcionario.getId())); // Define o texto do campo ID do Funcionário com o ID do funcionário logado


        if (this.entradaVendaData.getValue() != null) {
            dataText = this.entradaVendaData.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }



        // System.out.println(idVendaText);
        // if (idVendaText != null){
        //     Erro.mostrarErro("Erro Venda", "Id de venda já cadastrado.");
        //     return;
        // }

        if (precoText.trim().isEmpty() || produtoIdText.trim().isEmpty() || clientIdText.trim().isEmpty() || dataText == null || dataText.trim().isEmpty()) {
            Erro.mostrarErro("Erro Venda", "Por favor, preencha todos os campos.");
            return;
        }


        try {
            int ultimoID = csv.getLastID(); // Obtém o último ID do arquivo CSV

            Venda vendaAtualizado = new Venda(
                    ultimoID + 1,
                    Double.parseDouble(precoText.trim()),
                    dataText,
                    produtoIdText,
                    Integer.parseInt(clientIdText.trim()),
                    this.funcionario.getId()// Integer.parseInt(funcionarioIdText) // TODO: Adicionar id do funcionario
            );
            csv.adicionar(vendaAtualizado.toString()); // Adiciona a venda atualizada ao arquivo CSV
            this.mostrarVendasTabela(); // Atualiza a tabela de vendas
        } catch (IOException e) {
            Erro.mostrarErro("Erro ao editar venda.", "");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Erro.mostrarErro("Erro Venda","Por favor, insira valores numéricos válidos para preço e quantidade.");
            e.printStackTrace();
        }
    }


    
    @FXML
    void clickVendasEditar(ActionEvent event) {
        int vendaSelectionado = Integer.parseInt(this.entradaVendaId.getText()); // Obtém o ID da venda selecionada

        // Remove a venda selecionada do arquivo CSV
        try {
            csv.removePorId(vendaSelectionado);
        } catch (IOException e) {
            Erro.mostrarErro("Erro venda.", "Erro ao tentar remover venda selecionada.");
            e.printStackTrace();
            return;
        }

        // Atualiza o arquivo CSV com os novos dados da venda
        String idVendaText = this.entradaVendaId.getText();
        String precoText = this.entradaVendaPreco.getText(); // Obtém o texto do campo Preço
        String produtoIdText = this.entradaVendaProdutoId.getText(); // Obtém o texto do campo ID do Produto
        String clientIdText = this.entradaVendaClienteId.getText(); // Obtém o texto do campo ID do Cliente
        String dataText = null; // Obtém o texto do campo Data
        this.entradaVendaFuncionarioId.setText(String.valueOf(this.funcionario.getId())); // Define o texto do campo ID do Funcionário com o ID do funcionário logado

        if (this.entradaVendaData.getValue() != null) {
            dataText = this.entradaVendaData.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }


        if (precoText.trim().isEmpty() && produtoIdText.trim().isEmpty() && clientIdText.trim().isEmpty()
                && dataText.trim().isEmpty()) {
            Erro.mostrarErro("Erro Venda", "Por favor, preencha todos os campos.");
        }

        try {

            Venda vendaAtualizado = new Venda(
                    Integer.parseInt(idVendaText),
                    Double.parseDouble(precoText),
                    dataText,
                    produtoIdText,
                    Integer.parseInt(clientIdText),
                    0// Integer.parseInt(funcionarioIdText)
            );
            csv.adicionar(vendaAtualizado.toString()); // Adiciona a venda atualizada ao arquivo CSV
            this.mostrarVendasTabela(); // Atualiza a tabela de vendas
        } catch (IOException e) {
            Erro.mostrarErro("Erro ao editar venda.", "");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Erro.mostrarErro("Erro Venda",
                    "Por favor, insira valores numéricos válidos para preço e quantidade.");
            e.printStackTrace();
        }

    }

    @FXML
    void clickVendasRemover(ActionEvent event) {

        Venda vendaSelecionada = this.tabelaVendas.getSelectionModel().getSelectedItem();
        if (vendaSelecionada != null) {
            try {
                csv.removePorId(vendaSelecionada.getId());
                mostrarVendasTabela();
            } catch (IOException e) {
                Erro.mostrarErro("Erro Venda.", "Erro ao tentar remover venda.");
                e.printStackTrace();
            }
        } else {
            Erro.mostrarErro("Erro Venda.", "Por favor, selecione a venda a ser removida.");
            return;
        }

    }

    @FXML
    void pesquisaEntradaMudou(KeyEvent event) {
        String search = this.vendaPesquisaEntrada.getText().toLowerCase(); // Obtém o texto do campo de pesquisa
        ObservableList<Venda> filteredList = FXCollections.observableArrayList(); // Lista para armazenar as vendas filtradas

        if (search.isEmpty() || event.getCode() == KeyCode.BACK_SPACE) {
            mostrarVendasTabela(); // Mostra todas as vendas na tabela se o campo de pesquisa está vazio ou se a tecla Backspace foi pressionada
        }

        for (Venda item : tabelaVendas.getItems()) { // Para cada venda na tabela
            if (pesquisaAlgo(item, search)) { // Se a venda corresponde ao texto de pesquisa
                filteredList.add(item); // Adiciona a venda à lista de vendas filtradas
            }
        }
        tabelaVendas.setItems(filteredList); // Define a lista de vendas filtradas a ser exibida na tabela
    }

    private boolean pesquisaAlgo(Venda item, String searchText) {
        // Verifica se o ID, o preço, o ID do produto, o ID do cliente ou o ID do funcionário da venda contêm o texto de pesquisa
        if (String.valueOf(item.getId()).contains(searchText) || String.valueOf(item.getPreco()).contains(searchText)
                || String.valueOf(item.getProdutoId()).contains(searchText)
                || String.valueOf(item.getClienteId()).contains(searchText)
                || String.valueOf(item.getFuncionarioId()).contains(searchText)) {
            return true; // Retorna verdadeiro se a venda corresponde ao texto de pesquisa
        } else {
            return false; // Retorna falso se a venda não corresponde ao texto de pesquisa
        }
    }
}
