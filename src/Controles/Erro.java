package Controles;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Erro {
    // Mensagens de erro pré-definidas
    public static final String EMPTY_FIELDS = "Preencha todos os campos.";
    public static final String WRONG_LOGIN = "Senha ou Usuário errado!";
    public static final String LOGIN_ERROR = "Erro Login";
    public static final String CLIENT_ERROR = "Erro Cliente";
    public static final String ERROR_EDITING_CLIENT = "Erro ao editar Cliente.";
    public static final String ERROR_REMOVING_PRODUCT = "Erro ao remover produto.";

    // Método para exibir uma mensagem de erro
    public static void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR); // Cria um novo alerta do tipo ERRO
        alert.setTitle(titulo); // Define o título do alerta
        alert.setHeaderText(null); // Define o cabeçalho do alerta (neste caso, sem cabeçalho)
        alert.setContentText(mensagem); // Define a mensagem do alerta
        alert.showAndWait(); // Exibe o alerta e espera até que o usuário o feche
    }
}
