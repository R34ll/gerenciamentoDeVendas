import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Erro {
    public static final String EMPTY_FIELDS = "Preencha todos os campos.";
    public static final String WRONG_LOGIN = "Senha ou Usuário errado!";
    public static final String LOGIN_ERROR = "Erro Login";
    public static final String CLIENT_ERROR = "Erro Cliente";
    public static final String ERROR_EDITING_CLIENT = "Erro ao editar Cliente.";
    public static final String ERROR_REMOVING_PRODUCT = "Erro ao remover produto.";

    public static void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
