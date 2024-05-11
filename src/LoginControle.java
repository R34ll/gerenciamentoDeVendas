


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginControle extends AnchorPane{ 

    @FXML
    private Button button;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label wrongLogin;   

    @FXML
    void userLogin(ActionEvent event) throws IOException{
        App app = new App();

        if(username.getText().toString().equals("arthur") && password.getText().toString().equals("123")){
            wrongLogin.setText("Sucesso!");
            app.trocarCena("cenas/PrincipalCena.fxml");
        }


        else if(username.getText().isEmpty() && password.getText().isEmpty()){
            app.mostrarErro("Erro Login","Preencha todos os campos.");
            wrongLogin.setText("Preencha todos os campos.");
        }

        else{
            wrongLogin.setText("Senha ou Usuario errado!");
            app.mostrarErro("Erro Login","Senha ou Usuario errado!");

        }

    }

}
