package us.alberto.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import us.alberto.logic.Logica;

import java.net.URL;
import java.util.ResourceBundle;

public class EscribirCorreoController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i < Logica.getInstance().getListaCuentas().size(); i++){
            comboBoxCuentas.getItems().add(Logica.getInstance().getListaCuentas().get(i).getEmail());
        }
    }
}
