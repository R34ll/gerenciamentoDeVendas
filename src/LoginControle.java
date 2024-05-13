import java.io.IOException;
import java.util.List;

import dados.Csv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import modelos.Funcionario;

public class LoginControle extends AnchorPane{ 

    @FXML
    private Button button;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label wrongLogin;   

    private Funcionario funcionario;

    @FXML
    void usuarioLogin(ActionEvent event) throws IOException {
        App app = new App();
        Csv csv = new Csv();

        String enteredUsername = username.getText().trim().toLowerCase();
        String enteredPassword = password.getText().trim();
    
        // Check for empty fields
        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            wrongLogin.setText("Preencha todos os campos.");
            app.mostrarErro("Erro Login", "Preencha todos os campos.");
            return; // Exit the method if fields are empty
        }
    
        List<List<String>> content = csv.carregaCSV("src\\dados\\funcionarios.csv");
    
        boolean loggedIn = false;
        for (List<String> c : content) {
            String nome = c.get(1).toLowerCase();
            String senha = c.get(4);


    
            if (enteredUsername.equals(nome) && enteredPassword.equals(senha)) {
                loggedIn = true;
                this.funcionario = new Funcionario(Integer.parseInt(c.get(0)), nome,c.get(2), c.get(3), senha);
                break; // Exit the loop once successful login is found
            }
        }
    
        if (loggedIn) {
            wrongLogin.setText("Sucesso!");
            app.trocarCena("cenas/PrincipalCena.fxml", this.funcionario);
        } else {
            wrongLogin.setText("Senha ou Usuário errado!");
            app.mostrarErro("Erro Login", "Senha ou Usuário errado!");
        }
    }
    
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

}
