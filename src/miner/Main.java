package miner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage primaryStage;
    private static AnchorPane inputPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gold Miner");
        showInputMenu();
        //InputBox.display();
    }

    public static void showInputMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("GUI/inputBox.fxml"));
        inputPane = loader.load();
        Scene scene = new Scene(inputPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void closeWindow() {
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
