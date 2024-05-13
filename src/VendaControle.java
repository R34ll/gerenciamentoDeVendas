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
import modelos.Produto;
import modelos.Venda;

public class VendaControle extends AnchorPane{

    @FXML
    private TableColumn<Venda, String> ColData;

    @FXML
    private TableColumn<Venda, Integer> ColId;

    @FXML
    private TableColumn<Venda, Double> ColPreco;

    
    @FXML
    private TableColumn<Venda, Integer> colClienteId;

    @FXML
    private TableColumn<Venda, Integer> colFuncionarioId;

    @FXML
    private TableColumn<Venda, Integer> colProdutoId;

    @FXML
    private TableView<Venda> tabelaVendas;

    @FXML
    private Button btnVendasAdicionar;

    @FXML
    private Button btnVendasEditar;

    @FXML
    private Button btnVendasRemover;

    @FXML
    private TextField entradaVendaClienteId;

    @FXML
    private DatePicker entradaVendaData;

    @FXML
    private TextField entradaVendaFuncionarioId;

    @FXML
    private TextField entradaVendaId;

    @FXML
    private TextField entradaVendaPreco;

    @FXML
    private TextField entradaVendaProdutoId;

    @FXML
    private TextField vendaPesquisaEntrada;

    private Funcionario funcionario;


     public VendaControle(Funcionario funcionario){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cenas/VendaCena.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.mostrarVendasTabela();
            this.funcionario = funcionario;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Venda> carregarVendas(){
        ObservableList<Venda> listVendas = FXCollections.observableArrayList();
        Csv csv = new Csv();

        try{
            List<List<String>> records = csv.carregaCSV("src\\dados\\vendas.csv");
            for(List<String> rs: records){


                Venda venda = new Venda(
                    Integer.parseInt(rs.get(0)),
                    Double.parseDouble(rs.get(1)), 
                    rs.get(2), 

                    Integer.parseInt( rs.get(3)), 
                    Integer.parseInt(rs.get(4)), 
                    Integer.parseInt(rs.get(5))
                );


                listVendas.add(venda);
            }
        }catch(Exception ex){
            System.err.println(ex);
        }

        return listVendas;
    }


    public void mostrarVendasTabela(){
        ObservableList<Venda> lista = this.carregarVendas();

        this.ColId.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("id"));
        this.ColPreco.setCellValueFactory(new PropertyValueFactory<Venda, Double>("preco"));
        this.ColData.setCellValueFactory(new PropertyValueFactory<Venda, String>("data"));
        this.colProdutoId.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("produtoId"));
        this.colClienteId.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("clienteId"));
        this.colFuncionarioId.setCellValueFactory(new PropertyValueFactory<Venda, Integer>("funcionarioId"));

        this.tabelaVendas.setItems(lista);
    }


    @FXML
    void clickVendasAdicionar(ActionEvent event) {
        int vendaSelectionado = Integer.parseInt(this.entradaVendaId.getText());
        App app = new App();

        // Remove o venda selecionado do arquivo CSV
        Csv csv = new Csv();
        try {
            csv.removePorId("src\\dados\\vendas.csv", vendaSelectionado);
        } catch (IOException e) {
            app.mostrarErro("Erro ao editar venda.", "");
            e.printStackTrace();
            return;
        }

        // // Atualiza o arquivo CSV com os novos dados do venda
        String precoText = this.entradaVendaPreco.getText();
        String produtoIdText = this.entradaVendaProdutoId.getText();
        String clientIdText = this.entradaVendaClienteId.getText();
        // String funcionarioIdText = this.entradaVendaFuncionarioId.getText();
        this.entradaVendaFuncionarioId.setText(String.valueOf(this.funcionario.getId()));
        String dataText = this.entradaVendaData.getValue().toString();

        if (!precoText.trim().isEmpty() && 
            !produtoIdText.trim().isEmpty() && 
            !clientIdText.trim().isEmpty() && 
            !dataText.trim().isEmpty()
            ) {
            
                try {
                int ultimoID = csv.getLastID("src\\dados\\vendas.csv");

                Venda vendaAtualizado = new Venda(
                        ultimoID+1, 
                        Double.parseDouble(precoText),
                        dataText,
                        Integer.parseInt(produtoIdText),
                        Integer.parseInt(clientIdText),
                        0//Integer.parseInt(funcionarioIdText)
                    );
                csv.adicionar(vendaAtualizado.toString(), "src\\dados\\vendas.csv");
                this.mostrarVendasTabela();
            } catch (IOException e) {
                app.mostrarErro("Erro ao editar venda.", "");
                e.printStackTrace();
            } catch (NumberFormatException e) {
                app.mostrarErro("Erro Venda", "Por favor, insira valores numéricos válidos para preço e quantidade.");
                e.printStackTrace();
            }
        }

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

        } else {}
    }

    @FXML
    void clickVendasEditar(ActionEvent event) {
        int produtoSelectionado = Integer.parseInt(this.entradaVendaId.getText());
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

        String precoText = this.entradaVendaPreco.getText();
        String quantText = this.entradaVendaProdutoId.getText();

        if (!precoText.trim().isEmpty() && !quantText.trim().isEmpty()) {
            try {
                Produto produtoAtualizado = new Produto(produtoSelectionado, this.entradaVendaId.getText(),
                        Double.parseDouble(precoText), Integer.parseInt(quantText),
                        this.entradaVendaFuncionarioId.getText());
                csv.adicionar(produtoAtualizado.toString(), "src\\dados\\produtos.csv");
                this.mostrarVendasTabela();
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
    void clickVendasRemover(ActionEvent event) {
        Csv csv = new Csv();
        App app = new App();

        Venda vendaSelecionada = this.tabelaVendas.getSelectionModel().getSelectedItem();
        if (vendaSelecionada != null) {
            try {
                csv.removePorId("src\\dados\\vendas.csv", vendaSelecionada.getId());
                mostrarVendasTabela();
            } catch (IOException e) {
                app.mostrarErro("Erro ao remover venda.", "");
                e.printStackTrace();
            }
        } else {}
    
    }

    

    @FXML
    void pesquisaEntradaMudou(KeyEvent event) {
        String search = this.vendaPesquisaEntrada.getText().toLowerCase();
        ObservableList<Venda> filteredList = FXCollections.observableArrayList();
 
        if (search.isEmpty() || event.getCode() == KeyCode.BACK_SPACE){mostrarVendasTabela();}

        for (Venda item : tabelaVendas.getItems()) {
            if (pesquisaAlgo(item, search)) {
                filteredList.add(item);
            }
        }
        tabelaVendas.setItems(filteredList);
    }


    private boolean pesquisaAlgo(Venda item, String searchText) {
        searchText = searchText.toLowerCase();
    
        if (item.getData().contains(searchText)) {
            return true;
        }
        if (String.valueOf(item.getId()).contains(searchText)) {
            return true;
        }
    
        if (String.valueOf(item.getPreco()).contains(searchText)) {
            return true;
        }

        if (String.valueOf(item.getClienteId()).contains(searchText)) {
            return true;
        }

        if (String.valueOf(item.getFuncionarioId()).contains(searchText)) {
            return true;
        }

        if (String.valueOf(item.getProdutoId()).contains(searchText)) {
            return true;
        }

    
        return false;
    }
}

    
