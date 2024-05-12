



import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;



public class App extends Application {
    
    private static Stage stg; // // Stage object to hold the main stage reference


    /* Main method to launch the application */
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /* Start method called when the application is launched */
    @Override
    public void start(Stage principalStage) throws Exception {
        stg = principalStage; // Store the reference to the main stage

        principalStage.setResizable(false);         // Set the stage to be non-resizable


        // Load the login scene from the FXML file

        try {
            Parent root = FXMLLoader.load(getClass().getResource("cenas/LoginCena.fxml"));
            Scene scene = new Scene(root);

            // Set the title of the main stage
            principalStage.setTitle("Sistema de Vendas");
            principalStage.setScene(scene); // 
            principalStage.show(); //

        } catch (IOException e) {
            mostrarErro("Login Erro", "Um erro ocorreu durante a inicialização do sistema!");
            e.printStackTrace(); // Print the stack trace for debugging

        }
        
    }

    /* Method to switch scenes */
    public void trocarCena(String fxml) throws IOException{
        // Load the new FXML file
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        // Set the root of the current scene to the new FXML file
        stg.getScene().setRoot(pane);
    }

    public void mostrarErro(String titulo, String mensagem){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}