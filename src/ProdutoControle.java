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

    private static String PRODUTO_CENA = "cenas/ProdutoCena.fxml"; // Caminho para o arquivo FXML da cena do produto
    private static String PRODUTOS_CSV = "src\\dados\\produtos.csv"; // Caminho para o arquivo CSV dos produtos

    @FXML
    private Button btnProdutosAdicionar; // Botão para adicionar um novo produto

    @FXML
    private Button btnProdutosEditar; // Botão para editar um produto existente

    @FXML
    private Button btnProdutosRemover; // Botão para remover um produto

    @FXML
    private TableView<Produto> tabelaProdutos; // Tabela para exibir os produtos

    @FXML
    private TableColumn<Produto, Integer> ColId; // Coluna para exibir o ID do produto

    @FXML
    private TableColumn<Produto, String> ColNome; // Coluna para exibir o nome do produto

    @FXML
    private TableColumn<Produto, Double> ColPreco; // Coluna para exibir o preço do produto

    @FXML
    private TableColumn<Produto, String> colDescricao; // Coluna para exibir a descrição do produto

    @FXML
    private TableColumn<Produto, Integer> colQuant; // Coluna para exibir a quantidade do produto

    @FXML
    private TextField produtoPesquisaEntrada; // Campo de texto para pesquisa de produtos

    @FXML
    private TextField entradaProdutoDescricao; // Campo de texto para entrada da descrição do produto

    @FXML
    private TextField entradaProdutoNome; // Campo de texto para entrada do nome do produto

    @FXML
    private TextField entradaProdutoPreco; // Campo de texto para entrada do preço do produto

    @FXML
    private TextField entradaProdutoQuant; // Campo de texto para entrada da quantidade do produto

    @FXML
    private TextField entradaProdutoId; // Campo de texto para entrada do ID do produto

    private Csv csv; // Objeto para manipulação do arquivo CSV

    public ProdutoControle() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PRODUTO_CENA)); // Carrega o arquivo FXML da cena do produto

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load(); // Carrega a cena do produto
            this.csv = new Csv(PRODUTOS_CSV); // Inicializa o objeto CSV

            this.mostrarProdutosTabela(); // Mostra os produtos na tabela
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Produto> carregarProdutos() {
        ObservableList<Produto> listProdutos = FXCollections.observableArrayList(); // Lista para armazenar os produtos

        try {
            List<List<String>> records = csv.carregaCSV(); // Carrega os registros do arquivo CSV

            for (List<String> rs : records) {
                Produto produto = new Produto(Integer.parseInt(rs.get(0)), rs.get(1), Double.parseDouble(rs.get(2)),Integer.parseInt(rs.get(3)), rs.get(4)); // Cria um objeto Produto para cada registro
                listProdutos.add(produto); // Adiciona o produto à lista
            }
        } catch (Exception ex) {
            Erro.mostrarErro("Erro Produto.", "Erro ao tentar carregar dados dos produtos");
            System.err.println(ex);
        }

        return listProdutos; // Retorna a lista de produtos
    }

    public void mostrarProdutosTabela() {
        ObservableList<Produto> lista = this.carregarProdutos(); // Carrega a lista de produtos

        this.ColId.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("id")); // Define a propriedade a ser exibida na coluna ID
        this.ColNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome")); // Define a propriedade a ser exibida na coluna Nome
        this.ColPreco.setCellValueFactory(new PropertyValueFactory<Produto, Double>("preco")); // Define a propriedade a ser exibida na coluna Preço
        this.colQuant.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quant")); // Define a propriedade a ser exibida na coluna Quantidade
        this.colDescricao.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricao")); // Define a propriedade a ser exibida na coluna Descrição

        this.tabelaProdutos.setItems(lista); // Define a lista de produtos a ser exibida na tabela
    }

    @FXML
    void clickProdutosAdicionar(ActionEvent event) {

        String precoText = this.entradaProdutoPreco.getText(); // Obtém o texto do campo Preço
        String quantText = this.entradaProdutoQuant.getText(); // Obtém o texto do campo Quantidade

        // Verifica se os campos de preço e quantidade estão vazios ou contêm apenas espaços em branco
        if (precoText.trim().isEmpty() || quantText.trim().isEmpty()) {
            Erro.mostrarErro("Erro Produto", "Por favor, preencha os campos de preço e quantidade.");
            return; // Sai do método se os campos estiverem vazios
        }

        try {
            int ultimoID = csv.getLastID(); // Obtém o último ID do arquivo CSV
            Produto produto = new Produto(ultimoID + 1, this.entradaProdutoNome.getText(), Double.parseDouble(precoText), Integer.parseInt(quantText), this.entradaProdutoDescricao.getText()); // Cria um novo objeto Produto

            csv.adicionar(produto.toString()); // Adiciona o produto ao arquivo CSV
            this.mostrarProdutosTabela(); // Atualiza a tabela de produtos

        } catch (IOException e) {
            Erro.mostrarErro("Erro Produto", "Um erro ocorreu ao adicionar novo Produto!");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Erro.mostrarErro("Erro Produto", "Por favor, insira valores numéricos válidos para preço e quantidade.");
            e.printStackTrace();
        }
    }

    @FXML
    void clickTabela(MouseEvent event) {

        Produto selecionadoProduto = tabelaProdutos.getSelectionModel().getSelectedItem(); // Obtém o produto selecionado na tabela
        if (selecionadoProduto != null) {
            this.entradaProdutoNome.setText(selecionadoProduto.getNome()); // Define o texto do campo Nome com o nome do produto selecionado
            this.entradaProdutoPreco.setText(String.valueOf(selecionadoProduto.getPreco())); // Define o texto do campo Preço com o preço do produto selecionado
            this.entradaProdutoQuant.setText(String.valueOf(selecionadoProduto.getQuant())); // Define o texto do campo Quantidade com a quantidade do produto selecionado
            this.entradaProdutoDescricao.setText(selecionadoProduto.getDescricao()); // Define o texto do campo Descrição com a descrição do produto selecionado
            this.entradaProdutoId.setText(String.valueOf(selecionadoProduto.getId())); // Define o texto do campo ID com o ID do produto selecionado
        } else {}
    }

    @FXML
    void clickProdutosEditar(ActionEvent event) {
        int produtoSelectionado = Integer.parseInt(this.entradaProdutoId.getText()); // Obtém o ID do produto selecionado

        // Remove o produto selecionado do arquivo CSV
        try {
            csv.removePorId(produtoSelectionado);
        } catch (IOException e) {
            Erro.mostrarErro("Erro produto.", "Erro interno ao tentar editar produto.");
            e.printStackTrace();
            return;
        }

        // Atualiza o arquivo CSV com os novos dados do produto
        String precoText = this.entradaProdutoPreco.getText(); // Obtém o texto do campo Preço
        String quantText = this.entradaProdutoQuant.getText(); // Obtém o texto do campo Quantidade
        if (!precoText.trim().isEmpty() && !quantText.trim().isEmpty()) {
            try {
                Produto produtoAtualizado = new Produto(produtoSelectionado, this.entradaProdutoNome.getText(),
                        Double.parseDouble(precoText), Integer.parseInt(quantText),
                        this.entradaProdutoDescricao.getText()); // Cria um novo objeto Produto com os dados atualizados
                csv.adicionar(produtoAtualizado.toString()); // Adiciona o produto atualizado ao arquivo CSV
                this.mostrarProdutosTabela(); // Atualiza a tabela de produtos
            } catch (IOException e) {
                Erro.mostrarErro("Erro ao editar produto.", "");
                e.printStackTrace();
            } catch (NumberFormatException e) {
                Erro.mostrarErro("Erro Produto", "Por favor, insira valores numéricos válidos para preço e quantidade.");
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    void clickProdutosRemover(ActionEvent event) {

        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado != null) {
            try {
                csv.removePorId(produtoSelecionado.getId());
                mostrarProdutosTabela();
            } catch (IOException e) {
                Erro.mostrarErro("Erro ao remover produto.", "");
                e.printStackTrace();
            }
        } else {
            Erro.mostrarErro("Erro produto.", "Por Favor, selecione o produto a ser removido");
            return;
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
