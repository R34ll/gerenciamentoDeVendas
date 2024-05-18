import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelos.Cliente;
import modelos.Funcionario;
import modelos.Produto;
import modelos.Venda;

public class NovaVendaControle extends AnchorPane {

    @FXML
    private Button btnVendasAdicionar; // Botão para adicionar uma nova venda

    @FXML
    private TableColumn<Cliente, Integer> colIdCliente; // Coluna para exibir o ID do cliente

    @FXML
    private TableColumn<Produto, Integer> colIdProduto; // Coluna para exibir o ID do produto

    @FXML
    private TableColumn<Cliente, String> colNomeCliente; // Coluna para exibir o nome do cliente

    @FXML
    private TableColumn<Produto, String> colNomeProduto; // Coluna para exibir o nome do produto

    @FXML
    private TableColumn<Produto, Double> colPreco; // Coluna para exibir o preço do produto

    @FXML
    private TableColumn<Cliente, String> colUltimaCompra; // Coluna para exibir a data da última compra do cliente

    @FXML
    private TextField entradaProdutoPreco; // Campo de texto para entrada do preço do produto

    @FXML
    private TextField entradaProdutoQuant; // Campo de texto para entrada da quantidade do produto

    @FXML
    private TableView<Produto> tabelaProdutos; // Tabela para exibir os produtos

    @FXML
    private TableView<Cliente> tabelaClientes; // Tabela para exibir os clientes

    private Funcionario funcionario; // Objeto para armazenar o funcionário logado

    public NovaVendaControle(Funcionario funcionarioArg) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cenas/NovaVendaCena.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.funcionario = funcionarioArg; // Armazena o funcionário logado
            this.mostrarTabelas(); // Mostra as tabelas de produtos e clientes
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarTabelas() {
        this.tabelaProdutos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Define a seleção múltipla para a tabela de produtos

        ProdutoControle pc = new ProdutoControle(); // Cria um novo objeto ProdutoControle
        ClienteControle cc = new ClienteControle(this.funcionario); // Cria um novo objeto ClienteControle

        ObservableList<Produto> listaProdutos = pc.carregarProdutos(); // Carrega a lista de produtos
        ObservableList<Cliente> listaCliente = cc.carregarclientes(); // Carrega a lista de clientes

        this.colIdProduto.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("id")); // Define a propriedade a ser exibida na coluna ID do produto
        this.colPreco.setCellValueFactory(new PropertyValueFactory<Produto, Double>("preco")); // Define a propriedade a ser exibida na coluna Preço
        this.colNomeProduto.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome")); // Define a propriedade a ser exibida na coluna Nome do produto

        this.colIdCliente.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("id")); // Define a propriedade a ser exibida na coluna ID do cliente
        this.colNomeCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome")); // Define a propriedade a ser exibida na coluna Nome do cliente
        this.colUltimaCompra.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ultima_compra")); // Define a propriedade a ser exibida na coluna Última Compra

        this.tabelaProdutos.setItems(listaProdutos); // Define a lista de produtos a ser exibida na tabela de produtos
        this.tabelaClientes.setItems(listaCliente); // Define a lista de clientes a ser exibida na tabela de clientes
    }

    @FXML
    void clickVendasAdicionar(ActionEvent event) {
        ObservableList<Produto> produtoSelecionado = this.tabelaProdutos.getSelectionModel().getSelectedItems(); // Obtém os produtos selecionados na tabela de produtos
        Cliente selecionadoCliente = this.tabelaClientes.getSelectionModel().getSelectedItem(); // Obtém o cliente selecionado na tabela de clientes

        if (!produtoSelecionado.isEmpty() && (selecionadoCliente != null)) { // Verifica se algum produto e cliente foram selecionados
            String produtosId = "";
            for (Produto p : produtoSelecionado) { // Para cada produto selecionado
                int quant = p.getQuant(); // Obtém a quantidade do produto
                if (quant <= 0) { // Verifica se a quantidade é menor ou igual a zero
                    Erro.mostrarErro("Erro Venda", "Produto não existe no estoque!"); // Exibe uma mensagem de erro
                    return; // Sai do método
                }

                p.setQuant(quant - 1); // Decrementa a quantidade do produto
                produtosId += p.getId() + "-"; // Adiciona o ID do produto à string de IDs

                try {
                    p.update(); // Atualiza o produto no arquivo CSV
                } catch (Exception e) {
                    Erro.mostrarErro("Erro Venda", "Erro ao editar produto."); // Exibe uma mensagem de erro
                }

            }

            produtosId = produtosId.substring(0, produtosId.length() - 1); // Remove o último caractere da string de IDs
            Venda venda = new Venda(0, Double.parseDouble(this.entradaProdutoPreco.getText()), // Cria um novo objeto Venda
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString(), produtosId,
                    selecionadoCliente.getId(), this.funcionario.getId());

            try {
                venda.setId(venda.getLastId() + 1); // Define o ID da venda
                venda.salvar(); // Salva a venda no arquivo CSV

            } catch (Exception e) {
            }

        } else {
            Erro.mostrarErro("Erro Venda", "Selecione o Cliente e o(s) produto(s)."); // Exibe uma mensagem de erro
        }
    }

    @FXML
    void clickTabelaProduto(MouseEvent event) {
        ObservableList<Produto> produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItems(); // Obtém os produtos selecionados na tabela de produtos

        if (!produtoSelecionado.isEmpty()) { // Verifica se algum produto foi selecionado
            double preco_total = 0.0;
            for (Produto p : produtoSelecionado) { // Para cada produto selecionado
                preco_total += p.getPreco(); // Adiciona o preço do produto ao preço total
            }

            this.entradaProdutoPreco.setText(String.valueOf(preco_total)); // Define o texto do campo Preço com o preço total
            this.entradaProdutoQuant.setText(String.valueOf(produtoSelecionado.size())); // Define o texto do campo Quantidade com a quantidade de produtos selecionados
        }
    }

}
