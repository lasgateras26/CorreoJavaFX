package us.alberto.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import us.alberto.logic.Logica;
import us.alberto.models.EmailAccount;
import us.alberto.models.EmailMessage;
import us.alberto.models.EmailTreeItem;

import javax.mail.Folder;
import javax.mail.MessagingException;
import java.net.URL;
import java.util.ResourceBundle;

public class PantallaCorreoController extends BaseController implements Initializable {

    ObservableList<EmailMessage> listaCorreos;

    private Stage stage = new Stage();

    TreeItem raiz;

    @FXML
    private Button btnCuentas;

    @FXML
    private Button buttonRedactar;

    @FXML
    private Button buttonResponder;

    @FXML
    private Button buttonReenviar;

    @FXML
    private Button buttonEliminar;

    @FXML
    private TextField textFieldFiltrar;

    @FXML
    private TreeView treeViewCuentas;

    @FXML
    private TableView<EmailMessage> tableViewCorreos;

    @FXML
    private WebView webViewMensaje;

    private WebEngine webEngine;

    @FXML
    void eliminarCorreo(ActionEvent event) {
        EmailTreeItem emailTreeItem = (EmailTreeItem) treeViewCuentas.getSelectionModel().getSelectedItem();
        EmailMessage emailMessage = tableViewCorreos.getSelectionModel().getSelectedItem();
        Folder folder = emailTreeItem.getFolder();
        EmailAccount mailAccount = emailTreeItem.getEmailAccount();

        Logica.getInstance().borrarEmail(emailMessage, folder, mailAccount);
        tableViewCorreos.setItems(Logica.getInstance().getListaMensajes());
    }

    @FXML
    void gestionarCuentas(ActionEvent event) {
        CuentasController cuentas = (CuentasController) cargarDialogo("cuentas.fxml", 600, 600);
        cuentas.getStage().setTitle("Gestionar Cuentas");
        cuentas.abrirDialogo(true);
    }

    @FXML
    void redactarCorreo(ActionEvent event) {
        EscribirCorreoController correo = (EscribirCorreoController) cargarDialogo("EscribirCorreo.fxml", 600, 600);
        correo.getStage().setTitle("Escribir Correo");
        correo.abrirDialogo(true);
    }

    private void mostrarWebView() {
        tableViewCorreos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmailMessage>() {
            @Override
            public void changed(ObservableValue<? extends EmailMessage> observableValue, EmailMessage emailMessage, EmailMessage t1) {
                webEngine = webViewMensaje.getEngine();
                try {
                    if (!tableViewCorreos.getSelectionModel().getSelectedItem().equals(null))
                        webEngine.loadContent(t1.getContenido());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Logica.getInstance().getMessage("albertoodaam@gmail.com", "lasgateras26");
        tableViewCorreos.setItems(Logica.getInstance().getListaMensajes());
        mostrarWebView();
        try {
            EmailTreeItem item = Logica.getInstance().cargarCarpetas();
            treeViewCuentas.setShowRoot(false);
            treeViewCuentas.setRoot(item);
            treeViewCuentas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object o, Object t1) {
                    Logica.getInstance().cargarMensajes(((EmailTreeItem)treeViewCuentas.getSelectionModel().getSelectedItem()).getFolder());
                    tableViewCorreos.setItems(Logica.getInstance().cargarMensajes(((EmailTreeItem)treeViewCuentas.getSelectionModel().getSelectedItem()).getFolder()));
                }
            });
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}