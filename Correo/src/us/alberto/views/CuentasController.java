package us.alberto.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import us.alberto.logic.Logica;
import us.alberto.models.EmailAccount;
import java.net.URL;
import java.util.ResourceBundle;

public class CuentasController extends BaseController implements Initializable {

    @FXML
    private TableView<EmailAccount> tableViewCuentas;

    @FXML
    private Button btnAñadirCuenta;

    @FXML
    private Button btnEliminarCuenta;

    @FXML
    void añadirCuenta(ActionEvent event) {

    }

    @FXML
    void eliminarCuenta(ActionEvent event) {
        Logica.getInstance().borrarCuenta(tableViewCuentas.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewCuentas.setItems(Logica.getInstance().getListaCuentas());
    }
}
