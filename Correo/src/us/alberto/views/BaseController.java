package us.alberto.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    private Stage stage;

    protected Stage getStage(){
        return stage;
    }

    private void cargarStage(Parent root, int x, int y){
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, x, y));
    }

    protected BaseController cargarDialogo(String fxml, int x, int y){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = fxmlLoader.load();
            BaseController controller = fxmlLoader.getController();
            controller.cargarStage(root, x, y);
            return controller;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public void abrirDialogo(boolean showAndWait){
        if(stage==null){
            throw new IllegalStateException("Abrir dialogo primero");
        }
        if(showAndWait)
            stage.showAndWait();
        else
            stage.show();
    }
}
