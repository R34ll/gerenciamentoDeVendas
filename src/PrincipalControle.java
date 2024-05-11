import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
    private BorderPane painelBranco;




    @FXML
    void clickClientes(ActionEvent event) {
        // System.out.println("Click Funcionarios");
        AnchorPane clientes = new ClienteControle();
        painelBranco.setCenter(clientes);
    }

    @FXML
    void clickSair(ActionEvent event) {
        // Redirect to login Scene
        // AnchorPane login = new LoginPainel();
        // painelBranco.setCenter(login);        
            Stage stage = (Stage) btnSair.getScene().getWindow();
            stage.close();
    }

    @FXML
    void clickVendas(ActionEvent event) {
        AnchorPane vendas = new VendaControle();
        painelBranco.setCenter(vendas);
    }

    @FXML
    void clickProdutos(ActionEvent event) {
        
        AnchorPane produtos = new ProdutoControle();
        painelBranco.setCenter(produtos);
    }

}
