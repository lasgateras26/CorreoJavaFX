package us.alberto.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import us.alberto.logic.Logica;
import us.alberto.models.EmailAccount;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PantallaPrincipalController implements Initializable {

    @FXML
    private Button buttonIniciarSesion;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField textFieldUsuario;

    @FXML
    private Button buttonCancelar;

    @FXML
    void salir(ActionEvent event) {
    }

    @FXML
    void iniciarSesion(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/alberto/views/PantallaCorreo.fxml"));
            Parent root = fxmlLoader.load();
            stage.setTitle("Correo");
            stage.setScene(new Scene(root, 700, 580));
            stage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldUsuario.setText("albertoodaam@gmail.com");
        passwordField.setText("lasgateras26");
        EmailAccount cuenta = new EmailAccount(textFieldUsuario.getText(), passwordField.getText());
        Logica.getInstance().añadirCuenta(cuenta);
    }
}
