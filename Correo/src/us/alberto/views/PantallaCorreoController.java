package us.alberto.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import us.alberto.logic.Logica;
import us.alberto.models.EmailMessage;
import us.alberto.models.EmailTreeItem;

import javax.mail.MessagingException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ResourceBundle;

public class PantallaCorreoController extends BaseController implements Initializable {

    private Stage stage = new Stage();

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
    void redactarCorreo(ActionEvent event) {
        EscribirCorreoController correo = (EscribirCorreoController) cargarDialogo("EscribirCorreo.fxml", 600, 600);
        correo.getStage().setTitle("Escribir Correo");
        correo.abrirDialogo(true);
    }

    private void mostrarWebView(){
        tableViewCorreos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmailMessage>() {
            @Override
            public void changed(ObservableValue<? extends EmailMessage> observableValue, EmailMessage emailMessage, EmailMessage t1) {
                webEngine = webViewMensaje.getEngine();
                try{
                    if(!tableViewCorreos.getSelectionModel().getSelectedItem().equals(null))
                        webEngine.loadContent(t1.getContenido());
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mostrarWebView();
        Logica.getInstance().getMessage("albertoodaam@gmail.com", "lasgateras26");
        tableViewCorreos.setItems(Logica.getInstance().getListaMensajes());

        try{
            treeViewCuentas.setRoot(Logica.getInstance().actualizarTree());
        }
        catch (GeneralSecurityException e){
            e.printStackTrace();
        }
        catch(MessagingException e){
            e.printStackTrace();
        }
        treeViewCuentas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> stringTreeItem, TreeItem<String> t1) {
                System.out.println(treeViewCuentas.getSelectionModel().getSelectedItem().toString());
                Logica.getInstance().cargarListaCorreos(((EmailTreeItem) t1).getFolder());
            }
        });
        treeViewCuentas.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmailMessage>() {
            @Override
            public void changed(ObservableValue<? extends EmailMessage> observable, EmailMessage oldValue, EmailMessage newValue) {
                try {
                    if (newValue != null)
                        webViewMensaje.getEngine().loadContent(newValue.getContenido());
                    else
                        webViewMensaje.getEngine().loadContent("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            Logica.getInstance().actualizarTree();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}