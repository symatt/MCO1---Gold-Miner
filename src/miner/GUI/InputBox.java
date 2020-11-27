package miner.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InputBox {

    public static void display() {
        Stage window = new Stage();

        window.setMinWidth(250);

        Label gridSizeLabel = new Label("Grid size");
        Label pitLabel = new Label("Pit");
        Label goldLabel = new Label("Pot of Gold");
        Label beaconLabel = new Label("Beacon");

        TextField gridSizeInput = new TextField("8");
        gridSizeInput.setMaxWidth(80);
        TextArea pitInput = new TextArea("5, 3");
        pitInput.setMaxWidth(100);
        pitInput.setMaxHeight(150);
        TextField goldInput = new TextField("4, 3");
        goldInput.setMaxWidth(80);
        TextArea beaconInput = new TextArea("3, 3");
        beaconInput.setMaxWidth(100);
        beaconInput.setMaxHeight(150);

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.CENTER);
        VBox gridBox = new VBox();
        gridBox.getChildren().addAll(gridSizeLabel, gridSizeInput);
        gridBox.setSpacing(2);
        gridBox.setAlignment(Pos.CENTER);
        VBox pitBox = new VBox();
        pitBox.getChildren().addAll(pitLabel, pitInput);
        pitBox.setSpacing(2);
        pitBox.setAlignment(Pos.CENTER);
        VBox goldBox = new VBox();
        goldBox.getChildren().addAll(goldLabel, goldInput);
        goldBox.setSpacing(2);
        goldBox.setAlignment(Pos.CENTER);
        VBox beaconBox = new VBox();
        beaconBox.getChildren().addAll(beaconLabel, beaconInput);
        beaconBox.setSpacing(2);
        beaconBox.setAlignment(Pos.CENTER);
        final Pane spacer1 = new Pane();
        VBox.setVgrow(spacer1, Priority.ALWAYS);

        ToggleButton randomToggle = new ToggleButton("Random");
        ToggleButton smartToggle = new ToggleButton("Smart");
        ToggleGroup randomSmartGroup = new ToggleGroup();
        randomToggle.setToggleGroup(randomSmartGroup);
        smartToggle.setToggleGroup(randomSmartGroup);
        HBox toggleBox = new HBox();
        toggleBox.setSpacing(3);
        toggleBox.setPadding(new Insets(5, 5, 5, 5));
        toggleBox.setAlignment(Pos.CENTER);
        toggleBox.getChildren().addAll(randomToggle, smartToggle);

        Button confirmButton = new Button("Start");
        confirmButton.setAlignment(Pos.CENTER);
        mainBox.getChildren().addAll(gridBox, pitBox, goldBox, beaconBox, toggleBox, confirmButton, spacer1);
        mainBox.setSpacing(3);
        window.setTitle("Gold Miner");
        Scene scene = new Scene(mainBox, 250, 500);
        window.initStyle(StageStyle.UTILITY);
        window.setScene(scene);
        window.showAndWait();
    }
}
