import java.io.IOException;

import Controles.PrincipalControle;
import Uteis.Erro;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelos.Funcionario;

public class App extends Application {
    private static String LOGIN_CENA = "cenas/LoginCena.fxml"; // Caminho para o arquivo FXML da cena de login
    private static Stage stg; // Objeto Stage para manter a referência do palco principal

    /* Método principal para lançar a aplicação */
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /* Método start chamado quando a aplicação é lançada */
    @Override
    public void start(Stage principalStage) throws Exception {
        stg = principalStage; // Armazena a referência para o palco principal
        principalStage.setResizable(false); // Define o palco para não ser redimensionável

        // Carrega a cena de login do arquivo FXML
        try {
            Parent root = FXMLLoader.load(getClass().getResource(LOGIN_CENA));
            Scene scene = new Scene(root);

            // Define o título do palco principal
            principalStage.setTitle("Sistema de Vendas");
            principalStage.setScene(scene); // Define a cena do palco principal
            principalStage.show(); // Mostra o palco principal

        } catch (IOException e) {
            Erro.mostrarErro("Login Erro", "Um erro ocorreu durante a inicialização do sistema!");
            e.printStackTrace(); // Imprime o rastreamento de pilha para depuração
        }
    }

    /* Método para trocar de cenas */
    public void trocarCena(String fxml, Funcionario funcionario) throws IOException {
        // Carrega o novo arquivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent pane = loader.load();

        // Se o controlador for uma instância de PrincipalControle, define o funcionário
        if (loader.getController() instanceof PrincipalControle) {
            PrincipalControle controle = loader.getController();
            controle.setFuncionario(funcionario);
        }

        // Define a raiz da cena atual para o novo arquivo FXML
        stg.getScene().setRoot(pane);
    }
}
