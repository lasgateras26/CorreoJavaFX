package us.alberto.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import us.alberto.logic.Logica;
import us.alberto.models.EmailMessage;
import us.alberto.models.EmailTreeItem;

import java.net.URL;
import java.util.ResourceBundle;

public class PantallaCorreoController implements Initializable {

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
        TreeItem<String> raiz = new TreeItem<>();
        raiz.getChildren().add(new EmailTreeItem("Mi GMail", null, null));

        treeViewCuentas.setRoot(raiz);
    }
}