package miner;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javafx.scene.shape.*;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class GameProperUI {

    static Stage window;
    static Text minerInfo = new Text();
    static VBox leftArea = new VBox();
    static GridPane mapGrid;
    static GridPane mainFrame = new GridPane();
    static StackPane historyStack;
    static Rectangle historyBox;
    static Text history;
    static int[][] row;
    static int[][] column;
    static Scene stage;
    static ImageView minerImage;
    static Image minerIcon;

    public static void display() {
        // Creation of two columns
        ColumnConstraints col1 = new ColumnConstraints(280);
        col1.setHalignment(HPos.CENTER);
        mainFrame.getColumnConstraints().add(col1);
        ColumnConstraints col2 = new ColumnConstraints(774);
        col2.setHalignment(HPos.CENTER);
        mainFrame.getColumnConstraints().add(col2);
        mainFrame.setGridLinesVisible(true);

        // Building of Map and Miner
        buildMap();
        buildMinerInfo();
        window = new Stage();
        window.setTitle("Gold Miner");
        stage = new Scene(mainFrame, 1024, 768, Color.GRAY);
        window.setScene(stage);
        window.initStyle(StageStyle.DECORATED);
        window.setResizable(false);
        window.show();
    }

    public static void buildMinerInfo() {
        minerInfo.setStyle("-fx-background-color: white;");
        minerInfo.setText("Rotations: 0\nScans: 0\nMoves: 0\n\n");
        minerInfo.setStyle("-fx-font: 20 Verdana;");

        // History Stack
        historyStack = new StackPane();

        // History Box
        historyBox = new Rectangle(50, 50, 200, 200);
        historyBox.setFill(Color.ANTIQUEWHITE);

        // Travel History
        history = new Text("Speedrun starts!\n");

        historyStack.getChildren().addAll(historyBox, history);
        history.setStyle("-fx-font: 10 Verdana;");
        leftArea.getChildren().addAll(minerInfo, historyStack);
        mainFrame.add(leftArea, 0, 0);
        leftArea.setPadding(new Insets(40, 40, 40, 40));
    }

    public static void updatePlayerView()
    {
        // Player statistics
        minerInfo.setText("Rotations: 0\n\nScans: 0\n\nMoves: 0");
        // playerInfo1.setText("Roatations: " + Game.Miner.getNumOfRotates() + "\n\nScans: " +
        // Game.Miner.getNumOfScans() + "\n\nMoves: " + Game.Miner.getNumOfMoves());
        // Player History
    }

    public static void buildMap()
    {
        mapGrid = new GridPane();
        mapGrid.setGridLinesVisible(true);

        int rowCount = 8;
        int columnCount = 8;
        //Situational
        for (int i = 0; i < rowCount; i++)
            mapGrid.getRowConstraints().add(new RowConstraints(96.125));
        for (int i = 0; i < columnCount; i++)
            mapGrid.getColumnConstraints().add(new ColumnConstraints(96));

        // Miner Image
        minerImage = new ImageView();
        minerIcon = new Image("/sample/Miner.png");
        minerImage.setImage(minerIcon);
        // Situational
        minerImage.setFitHeight(40);
        minerImage.setFitWidth(40);

        // Placing of Objects
//        // Beacons
//        for (int i = 0; i < bLoc.length(); i++) {
//            Text beacon = new Text("B");
//            GridPane.setConstraints(beacon, bLoc.get(i).getXPos, bLoc.get(i).getYPos);
//        }
//        // Gold
//        for (int i = 0; i < gLoc.length(); i++) {
//            Text beacon = new Text("G");
//            GridPane.setConstraints(beacon, bLoc.get(i).getXPos, bLoc.get(i).getYPos);
//        }
//        // Pits
//        for (int i = 0; i < pLoc.length(); i++) {
//            Text beacon = new Text("P");
//            GridPane.setConstraints(beacon, bLoc.get(i).getXPos, bLoc.get(i).getYPos);
//        }

        // Miner Image in grid
        GridPane.setConstraints(minerImage, 0, 0);

        mapGrid.getChildren().addAll(minerImage);
        mainFrame.add(mapGrid, 1, 0);
    }

    public static void updateMap (int[] playerPos) {
        int x = playerPos[0];
        int y = playerPos[1];

        // Update position
        GridPane.clearConstraints(minerImage));
        GridPane.setConstraints(minerImage, x, y);
        GridPane.setHalignment(minerImage, HPos.CENTER);
        // Update Orientation
//        if (Miner.direction == 1)
//            minerImage.setRotate(0);
//        else if (Miner.direction == 2)
//            minerImage.setRotate(90);
//        else if (Miner.direction == 3)
//            minerImage.setRotate(180);
//        else if (Miner.direction == 4
//            minerImage.setRotate(270);
    }
    // Alternative to if statements above, will read history instead
    public static void rotateMiner()
    {
        minerImage.setRotate(minerImage.getRotate() + 90);
    }
}
