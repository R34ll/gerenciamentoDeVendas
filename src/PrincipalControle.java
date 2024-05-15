import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelos.Funcionario;

public class PrincipalControle {
    @FXML
    private AnchorPane MenuBar;

    @FXML
    private Button btnProdutos;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnVendas;

    @FXML
    private Button btnNovaVenda;

    @FXML
    private BorderPane painelBranco;

    @FXML
    private Text funcionarioNome;
    
    private Funcionario funcionario;


    @FXML
    void clickClientes(ActionEvent event) {
        AnchorPane clientes = new ClienteControle(this.funcionario);
        painelBranco.setCenter(clientes);
    }


    @FXML
    void clickVendas(ActionEvent event) {
        AnchorPane vendas = new VendaControle(this.funcionario);
        painelBranco.setCenter(vendas);
    }

    @FXML
    void clickProdutos(ActionEvent event) {
        AnchorPane produtos = new ProdutoControle();
        painelBranco.setCenter(produtos);
    }


    @FXML
    void clickNovaVenda(ActionEvent event) {
        AnchorPane novaVenda = new NovaVendaControle(this.funcionario);
        painelBranco.setCenter(novaVenda);
    }


    @FXML
    void clickSair(ActionEvent event) {
            Stage stage = (Stage) btnSair.getScene().getWindow();
            stage.close();
    }



    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        this.funcionarioNome.setText(this.funcionario.getNome());
    }

}


