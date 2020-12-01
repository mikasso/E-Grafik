import FxControllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;



public class Main extends Application {

    Controller controller;
    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"), bundle);
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        primaryStage.setTitle(bundle.getString("tittle.app"));
        Scene scene = new Scene(root,1500,600);
        scene.getStylesheets().add("stylesheets.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
