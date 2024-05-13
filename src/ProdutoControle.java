import java.io.IOException;
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
import modelos.Produto;

class ProdutoControle extends AnchorPane {

    @FXML
    private Button btnProdutosAdicionar;

    @FXML
    private Button btnProdutosEditar;

    @FXML
    private Button btnProdutosRemover;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, Integer> ColId;

    @FXML
    private TableColumn<Produto, String> ColNome;

    @FXML
    private TableColumn<Produto, Double> ColPreco;

    @FXML
    private TableColumn<Produto, String> colDescricao;

    @FXML
    private TableColumn<Produto, Integer> colQuant;

    @FXML
    private TextField produtoPesquisaEntrada;

    @FXML
    private TextField entradaProdutoDescricao;

    @FXML
    private TextField entradaProdutoNome;

    @FXML
    private TextField entradaProdutoPreco;

    @FXML
    private TextField entradaProdutoQuant;

    @FXML
    private TextField entradaProdutoId;

    public ProdutoControle() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cenas/ProdutoCena.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.mostrarProdutosTabela();
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Produto> carregarProdutos() {
        ObservableList<Produto> listProdutos = FXCollections.observableArrayList();
        Csv csv = new Csv();

        try {
            List<List<String>> records = csv.carregaCSV("src\\dados\\produtos.csv");

            for (List<String> rs : records) {
                Produto produto = new Produto(Integer.parseInt(rs.get(0)), rs.get(1), Double.parseDouble(rs.get(2)),Integer.parseInt(rs.get(3)), rs.get(4));
                listProdutos.add(produto);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }

        return listProdutos;
    }

    public void mostrarProdutosTabela() {
        ObservableList<Produto> lista = this.carregarProdutos();

        this.ColId.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("id"));
        this.ColNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        this.ColPreco.setCellValueFactory(new PropertyValueFactory<Produto, Double>("preco"));
        this.colQuant.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quant"));
        this.colDescricao.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricao"));

        this.tabelaProdutos.setItems(lista);
    }

    @FXML
    void clickProdutosAdicionar(ActionEvent event) {
        Csv csv = new Csv();
        App app = new App();

        String precoText = this.entradaProdutoPreco.getText();
        String quantText = this.entradaProdutoQuant.getText();

        // Check if the text fields are empty or contain only whitespace
        if (precoText.trim().isEmpty() || quantText.trim().isEmpty()) {
            app.mostrarErro("Erro Produto", "Por favor, preencha os campos de preço e quantidade.");
            return; // Exit the method early to prevent further execution
        }

        try {
            int ultimoID = csv.getLastID("src\\dados\\produtos.csv");

            Produto produto = new Produto(
                    ultimoID + 1,
                    this.entradaProdutoNome.getText(),
                    Double.parseDouble(precoText),
                    Integer.parseInt(quantText),
                    this.entradaProdutoDescricao.getText());

            csv.adicionar(produto.toString(), "src\\dados\\produtos.csv");
            this.mostrarProdutosTabela();

        } catch (IOException e) {
            app.mostrarErro("Erro Produto", "Um erro ocorreu ao adicionar novo Produto!");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            app.mostrarErro("Erro Produto", "Por favor, insira valores numéricos válidos para preço e quantidade.");
            e.printStackTrace();
        }
    }

    @FXML
    void clickTabela(MouseEvent event) {

        Produto selecionadoProduto = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (selecionadoProduto != null) {
            this.entradaProdutoNome.setText(selecionadoProduto.getNome());
            this.entradaProdutoPreco.setText(String.valueOf(selecionadoProduto.getPreco()));
            this.entradaProdutoQuant.setText(String.valueOf(selecionadoProduto.getQuant()));
            this.entradaProdutoDescricao.setText(selecionadoProduto.getDescricao());
            this.entradaProdutoId.setText(String.valueOf(selecionadoProduto.getId()));
        } else {
        }
    }

    @FXML
    void clickProdutosEditar(ActionEvent event) {

        int produtoSelectionado = Integer.parseInt(this.entradaProdutoId.getText());
        App app = new App();

        // Remove o produto selecionado do arquivo CSV
        Csv csv = new Csv();
        try {
            csv.removePorId("src\\dados\\produtos.csv", produtoSelectionado);
        } catch (IOException e) {
            app.mostrarErro("Erro ao editar produto.", "");
            e.printStackTrace();
            return;
        }

        // // Atualiza o arquivo CSV com os novos dados do produto
        String precoText = this.entradaProdutoPreco.getText();
        String quantText = this.entradaProdutoQuant.getText();
        if (!precoText.trim().isEmpty() && !quantText.trim().isEmpty()) {
            try {
                Produto produtoAtualizado = new Produto(produtoSelectionado, this.entradaProdutoNome.getText(),
                        Double.parseDouble(precoText), Integer.parseInt(quantText),
                        this.entradaProdutoDescricao.getText());
                csv.adicionar(produtoAtualizado.toString(), "src\\dados\\produtos.csv");
                this.mostrarProdutosTabela();
            } catch (IOException e) {
                app.mostrarErro("Erro ao editar produto.", "");
                e.printStackTrace();
            } catch (NumberFormatException e) {
                app.mostrarErro("Erro Produto", "Por favor, insira valores numéricos válidos para preço e quantidade.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    void clickProdutosRemover(ActionEvent event) {
        Csv csv = new Csv();
        App app = new App();

        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            try {
                csv.removePorId("src\\dados\\produtos.csv", produtoSelecionado.getId());
                mostrarProdutosTabela();
            } catch (IOException e) {
                app.mostrarErro("Erro ao remover produto.", "");
                e.printStackTrace();
            }
        } else {
        }

    }

    @FXML
    void pesquisaEntradaMudou(KeyEvent event) {
        String search = this.produtoPesquisaEntrada.getText().toLowerCase();
        ObservableList<Produto> filteredList = FXCollections.observableArrayList();

        if (search.isEmpty() || event.getCode() == KeyCode.BACK_SPACE) {
            mostrarProdutosTabela();
        }

        for (Produto item : tabelaProdutos.getItems()) {
            if (pesquisaAlgo(item, search)) {
                filteredList.add(item);
            }
        }
        tabelaProdutos.setItems(filteredList);
    }

    private boolean pesquisaAlgo(Produto item, String searchText) {
        searchText = searchText.toLowerCase();

        if (item.getDescricao().contains(searchText)) {
            return true;
        }

        if (item.getNome().contains(searchText)) {
            return true;
        }

        if (String.valueOf(item.getPreco()).contains(searchText)) {
            return true;
        }

        if (String.valueOf(item.getId()).contains(searchText)) {
            return true;
        }

        return false;
    }

}
