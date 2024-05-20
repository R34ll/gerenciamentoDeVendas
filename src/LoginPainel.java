
import java.io.IOException;
import java.util.List;

import Uteis.Csv;
import Uteis.Erro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelos.Funcionario;

public class LoginPainel{ 

    @FXML
    private Button button; // Botão para realizar o login

    @FXML
    private PasswordField password; // Campo de texto para a senha do usuário

    @FXML
    private TextField username; // Campo de texto para o nome de usuário

    @FXML
    private Label wrongLogin;   // Rótulo para exibir mensagens de erro de login

    private Funcionario funcionario; // Objeto para armazenar o funcionário logado

    private final String FUNCIONARIOS_CSV = "src\\dados\\funcionarios.csv"; // Caminho para o arquivo CSV dos funcionários


    @FXML
    void usuarioLogin(ActionEvent event) throws IOException {
        App app = new App(); // Cria um novo objeto App
        Csv csv = new Csv(this.FUNCIONARIOS_CSV); // Inicializa o objeto CSV

        String enteredUsername = username.getText().trim().toLowerCase(); // Obtém o nome de usuário inserido
        String enteredPassword = password.getText().trim(); // Obtém a senha inserida
    
        // Verifica se os campos estão vazios
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            wrongLogin.setText("Preencha todos os campos."); // Exibe uma mensagem de erro
            Erro.mostrarErro("Error login",Erro.EMPTY_FIELDS); // Exibe uma mensagem de erro
            return; // Sai do método se os campos estiverem vazios
        }
    
        List<List<String>> content = csv.carregaCSV(); // Carrega os registros do arquivo CSV
    
        boolean loggedIn = false;
        for (List<String> c : content) {
            String nome = c.get(1).toLowerCase(); // Obtém o nome do funcionário
            String senha = c.get(4); // Obtém a senha do funcionário
    
            // Verifica se o nome de usuário e a senha inseridos correspondem a um funcionário
            if (enteredUsername.equals(nome) && enteredPassword.equals(senha)) {
                loggedIn = true; // Define loggedIn como verdadeiro
                this.funcionario = new Funcionario(Integer.parseInt(c.get(0)), nome,c.get(2), c.get(3), senha); // Cria um novo objeto Funcionario
                break; // Sai do loop se o login for bem-sucedido
            }
        }
    
        // Verifica se o login foi bem-sucedido
        if (loggedIn) {
            wrongLogin.setText("Sucesso!"); // Exibe uma mensagem de sucesso
            app.trocarCena("cenas/PrincipalCena.fxml", this.funcionario); // Troca a cena para a cena principal
        } else {
            wrongLogin.setText("Senha ou Usuário errado!"); // Exibe uma mensagem de erro
            Erro.mostrarErro("Erro Login", "Senha ou Usuário errado!"); // Exibe uma mensagem de erro
        }
    }
    
    
    public Funcionario getFuncionario() {
        return funcionario; // Retorna o funcionário logado
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario; // Define o funcionário logado
    }

}
