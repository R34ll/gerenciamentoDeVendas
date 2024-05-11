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
import javafx.scene.layout.AnchorPane;
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
    private TextField entradaVendaPreco;

    @FXML
    private TextField entradaVendaProdutoId;

    @FXML
    private DatePicker entradaVendaVenda;

    @FXML
    private TextField vendaPesquisaEntrada;


     public VendaControle(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cenas/VendaCena.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            this.mostrarVendasTabela();;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Venda> carregarVendas(){
        ObservableList<Venda> listVendas = FXCollections.observableArrayList();

        Csv csv = new Csv();
        List<List<String>> records = csv.loadCSV("src\\dados\\vendas.csv");

        try{
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
        Csv csv = new Csv();
        App app = new App();
    
        String precoText = this.entradaVendaPreco.getText();
        String produtoIdText = this.entradaVendaProdutoId.getText();
        String clienteIdText = this.entradaVendaClienteId.getText();
    
        // // Check if the text fields are empty or contain only whitespace
        // if (precoText.trim().isEmpty() || produtoIdText.trim().isEmpty()  || clienteIdText.trim().isEmpty()) {
        //     app.mostrarErro("Erro Produto", "Por favor, preencha os campos de preço e quantidade.");
        //     return; // Exit the method early to prevent further execution
        // }
    
        // try {
        //     Venda produto = new Venda(
        //         0, 
        //             Double.parseDouble(this.preco.get(1)), 
        //             rs.get(2), 

        //             Integer.parseInt( rs.get(3)), 
        //             Integer.parseInt(rs.get(4)), 
        //             Integer.parseInt(rs.get(5))
        //     );
    
        //     csv.adicionar(produto.toString(), "src\\dados\\produtos.csv");
            
        // } catch (IOException e) {
        //     app.mostrarErro("Erro Produto", "Um erro ocorreu ao adicionar novo Produto!");
        //     e.printStackTrace();
        // } catch (NumberFormatException e) {
        //     app.mostrarErro("Erro Produto", "Por favor, insira valores numéricos válidos para preço e quantidade.");
        //     e.printStackTrace();
        // }

    }

    @FXML
    void clickVendasEditar(ActionEvent event) {

    }

    @FXML
    void clickVendasRemover(ActionEvent event) {

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

    
