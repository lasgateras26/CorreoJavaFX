package us.alberto.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import us.alberto.logic.Logica;
import us.alberto.models.EmailAccount;
import java.net.URL;
import java.util.ResourceBundle;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class PantallaPrincipalController extends BaseController implements Initializable {

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
        Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }

    @FXML
    void iniciarSesion(ActionEvent event) {
        String usuario = textFieldUsuario.getText();
        String contraseña = passwordField.getText();
        EmailAccount emailAccount = new EmailAccount(usuario, contraseña);
        boolean comprobar = Logica.getInstance().conexion(emailAccount);
        if(comprobar == true){
            if(emailAccount.getEmail().equals(usuario) && emailAccount.getPassword().equals(contraseña)){
                PantallaCorreoController correo = (PantallaCorreoController) cargarDialogo("PantallaCorreo.fxml", 700, 580);
                correo.getStage().setTitle("Correo");
                correo.abrirDialogo(true);
                Logica.getInstance().añadirCuenta(emailAccount);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("El usuario y/o la contraseña introducidos no son validos");
            alert.showAndWait();
            textFieldUsuario.setText("");
            passwordField.setText("");
        }
    }

    @Override
    /**
     * Inicializa la vista con datos en los textField y utiliza la clase ValidationSupport para que los campos
     * no sean vacíos, en el caso de que uno de los dos lo sea, se deshabilita el botón IniciarSesion
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldUsuario.setText("albertoodaam@gmail.com");
        passwordField.setText("lasgateras26");
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(textFieldUsuario, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        validationSupport.registerValidator(passwordField, Validator.createEmptyValidator("Este campo no puede estar vacío"));
        validationSupport.invalidProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                buttonIniciarSesion.setDisable(newValue);
            }
        });
    }
}
