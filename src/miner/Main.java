package miner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import miner.GUI.InputsController;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gold Miner");
        showInputMenu();
        //InputBox.display();
    }

    public static void showInputMenu() throws IOException {
        Parent inputRoot = FXMLLoader.load(Main.class.getResource("GUI/inputBox.fxml"));
        Scene scene = new Scene(inputRoot);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void closeWindow() {
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
        Miner m = new Miner();
        int gridSize = Integer.valueOf(InputsController.gridSize);
        String intel = InputsController.config;
        ArrayList<Integer> bLoc = new ArrayList<>();
        ArrayList<Integer> gLoc = new ArrayList<>();
        ArrayList<Integer> pLoc = new ArrayList<>();

        for (String str : InputsController.beaconLoc.split("\\s")) bLoc.add(Integer.valueOf(str));
        for (String str : InputsController.goldLoc.split("\\s")) gLoc.add(Integer.valueOf(str));
        for (String str : InputsController.pitLoc.split("\\s")) pLoc.add(Integer.valueOf(str));


    }
}
