package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
        MainController mainController = new MainController();
        mainLoader.setController(mainController);
        Scene scene  = new Scene(mainLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Machine Learning Project");
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(400);
        primaryStage.show();
    }
}
