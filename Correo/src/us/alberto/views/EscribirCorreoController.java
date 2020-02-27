package us.alberto.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import us.alberto.logic.Logica;
import us.alberto.models.EmailAccount;

import java.net.URL;
import java.util.ResourceBundle;

public class EscribirCorreoController extends BaseController implements Initializable {

    @FXML
    private TextField textFieldDestinatario;

    @FXML
    private TextField textFieldAsunto;

    @FXML
    private HTMLEditor mensaje;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ComboBox<String> comboBoxCuentas;

    @FXML
    void enviarCorreo(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Enviar Correo");
        alert.setHeaderText("Enviar Correo");
        alert.setContentText("¿Seguro que quiere enviar el correo?");
        alert.showAndWait();
        if ((alert.getResult() == ButtonType.OK)){
            EmailAccount emailAccount = null;
            String correo = comboBoxCuentas.getSelectionModel().getSelectedItem();
            for(int i = 0; i < Logica.getInstance().getListaCuentas().size(); i++){
                if(correo.equals(Logica.getInstance().getListaCuentas().get(i).getEmail()))
                    emailAccount = Logica.getInstance().getListaCuentas().get(i);
            }
            String destinatario = textFieldDestinatario.getText();
            String asunto = textFieldAsunto.getText();
            Logica.getInstance().escribirCorreo(emailAccount, correo, destinatario, asunto, mensaje);
            getStage().close();
        }
        else{
            alert.close();
        }
    }

    @FXML
    void cancelarCorreo(ActionEvent event) {
        getStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < Logica.getInstance().getListaCuentas().size(); i++) {
            comboBoxCuentas.getItems().add(Logica.getInstance().getListaCuentas().get(i).getEmail());
        }
    }
}
