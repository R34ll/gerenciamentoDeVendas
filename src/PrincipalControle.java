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
    private AnchorPane MenuBar; // Barra de menu

    @FXML
    private Button btnProdutos; // Botão para a seção de produtos

    @FXML
    private Button btnSair; // Botão para sair do aplicativo

    @FXML
    private Button btnVendas; // Botão para a seção de vendas

    @FXML
    private Button btnNovaVenda; // Botão para criar uma nova venda

    @FXML
    private BorderPane painelBranco; // Painel principal

    @FXML
    private Text funcionarioNome; // Texto para exibir o nome do funcionário

    private Funcionario funcionario; // Objeto para armazenar o funcionário logado

    @FXML
    void clickClientes(ActionEvent event) {
        AnchorPane clientes = new ClienteControle(this.funcionario); // Cria um novo painel de clientes
        painelBranco.setCenter(clientes); // Define o painel de clientes como o centro do painel principal
    }

    @FXML
    void clickVendas(ActionEvent event) {
        AnchorPane vendas = new VendaControle(this.funcionario); // Cria um novo painel de vendas
        painelBranco.setCenter(vendas); // Define o painel de vendas como o centro do painel principal
    }

    @FXML
    void clickProdutos(ActionEvent event) {
        AnchorPane produtos = new ProdutoControle(); // Cria um novo painel de produtos
        painelBranco.setCenter(produtos); // Define o painel de produtos como o centro do painel principal
    }

    @FXML
    void clickNovaVenda(ActionEvent event) {
        AnchorPane novaVenda = new NovaVendaControle(this.funcionario); // Cria um novo painel de nova venda
        painelBranco.setCenter(novaVenda); // Define o painel de nova venda como o centro do painel principal
    }

    @FXML
    void clickSair(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow(); // Obtém a janela atual
        stage.close(); // Fecha a janela
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario; // Define o funcionário logado
        this.funcionarioNome.setText(this.funcionario.getNome()); // Define o texto do nome do funcionário com o nome do funcionário logado
    }

}
