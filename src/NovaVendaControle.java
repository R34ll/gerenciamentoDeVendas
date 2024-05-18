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
    private Button btnVendasAdicionar;

    @FXML
    private TableColumn<Cliente, Integer> colIdCliente;

    @FXML
    private TableColumn<Produto, Integer> colIdProduto;

    @FXML
    private TableColumn<Cliente, String> colNomeCliente;

    @FXML
    private TableColumn<Produto, String> colNomeProduto;

    @FXML
    private TableColumn<Produto, Double> colPreco;

    @FXML
    private TableColumn<Cliente, String> colUltimaCompra;

    @FXML
    private TextField entradaProdutoPreco;

    @FXML
    private TextField entradaProdutoQuant;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableView<Cliente> tabelaClientes;

    private Funcionario funcionario;

    public NovaVendaControle(Funcionario funcionarioArg) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cenas/NovaVendaCena.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.funcionario = funcionarioArg;
            this.mostrarTabelas();
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarTabelas() {
        this.tabelaProdutos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ProdutoControle pc = new ProdutoControle();
        ClienteControle cc = new ClienteControle(this.funcionario);

        ObservableList<Produto> listaProdutos = pc.carregarProdutos();
        ObservableList<Cliente> listaCliente = cc.carregarclientes();

        this.colIdProduto.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("id"));
        this.colPreco.setCellValueFactory(new PropertyValueFactory<Produto, Double>("preco"));
        this.colNomeProduto.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));

        this.colIdCliente.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("id"));
        this.colNomeCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        this.colUltimaCompra.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ultima_compra"));

        this.tabelaProdutos.setItems(listaProdutos);
        this.tabelaClientes.setItems(listaCliente);
    }

    @FXML
    void clickVendasAdicionar(ActionEvent event) {
        ObservableList<Produto> produtoSelecionado = this.tabelaProdutos.getSelectionModel().getSelectedItems();
        Cliente selecionadoCliente = this.tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionadoCliente != null) {

        }

        // App app = new App();

        if (!produtoSelecionado.isEmpty() && (selecionadoCliente != null)) {
            String produtosId = "";
            for (Produto p : produtoSelecionado) {
                int quant = p.getQuant();
                if (quant <= 0) {
                    Erro.mostrarErro("Erro Venda", "Produto não existe no estoque!");
                    return;
                }

                p.setQuant(quant - 1);
                produtosId += p.getId() + "-";

                try {
                    p.update();
                } catch (Exception e) {
                    Erro.mostrarErro("Erro Venda", "Erro ao editar produto.");
                }

            }

            produtosId = produtosId.substring(0, produtosId.length() - 1);
            Venda venda = new Venda(0, Double.parseDouble(this.entradaProdutoPreco.getText()),
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString(), produtosId,
                    selecionadoCliente.getId(), this.funcionario.getId());

            try {
                venda.setId(venda.getLastId() + 1);
                venda.salvar();

            } catch (Exception e) {
            }

        } else {
            Erro.mostrarErro("Erro Venda", "Selecione o Cliente e o(s) produto(s).");
        }
    }

    @FXML
    void clickTabelaProduto(MouseEvent event) {
        ObservableList<Produto> produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItems();

        if (!produtoSelecionado.isEmpty()) {
            double preco_total = 0.0;
            for (Produto p : produtoSelecionado) {
                preco_total += p.getPreco();
            }

            this.entradaProdutoPreco.setText(String.valueOf(preco_total));
            this.entradaProdutoQuant.setText(String.valueOf(produtoSelecionado.size()));
        }
    }

}
