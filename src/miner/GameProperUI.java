package miner;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;


public final class GameProperUI {

    static Stage window;
    static Text minerInfo = new Text();
    static VBox leftArea = new VBox();
    static GridPane mapGrid;
    static GridPane mainFrame = new GridPane();
    static StackPane historyStack;
    static Rectangle historyBox;
//    static Text history;
    static TextArea history;
    static int[][] row;
    static int[][] column;
    static Scene stage;
    static ImageView minerImage;
    static Image minerIcon;

    public static void display() {
        // Creation of two columns
        ColumnConstraints col1 = new ColumnConstraints(270);
        col1.setHalignment(HPos.CENTER);
        mainFrame.getColumnConstraints().add(col1);
        ColumnConstraints col2 = new ColumnConstraints(774);
        col2.setHalignment(HPos.CENTER);
        mainFrame.getColumnConstraints().add(col2);
//        mainFrame.setGridLinesVisible(true);

        // Building of Map and Miner
        buildMap();
        buildMinerInfo();
        window = new Stage();
        window.setTitle("Gold Miner");
        stage = new Scene(mainFrame, 1024, 758, Color.GRAY);
        window.setScene(stage);
        window.initStyle(StageStyle.DECORATED);
        window.setResizable(false);
        window.show();
    }

    public static void buildMinerInfo() {
        minerInfo.setStyle("-fx-background-color: white;");
        minerInfo.setText("Rotations: 0\nScans: 0\nMoves: 0\n\n");
        minerInfo.setStyle("-fx-font: 30 Minecraft;");
        // History Stack
        historyStack = new StackPane();

//        // History Box
//        historyBox = new Rectangle(50, 50, 200, 200);
//        historyBox.setFill(Color.ANTIQUEWHITE);

        // Travel History
        history = new TextArea("Speedrun starts");
//        history = new TextArea();
//        history = new Text("Speedrun starts!\n");
        history.setStyle("-fx-font: 13 Minecraft;");
//        Font font = new Font("Minecraft", 20);
//        history.setFont(font);
        history.setPrefHeight(600);
        history.setPrefWidth(50);
        history.setEditable(false);
        // History Area scrollbar
        history.setWrapText(true);
        leftArea.setSpacing(0);
        historyStack.getChildren().addAll(history);
        leftArea.getChildren().addAll(minerInfo, historyStack);
        mainFrame.add(leftArea, 0, 0);
        leftArea.setPadding(new Insets(30, 40, 60, 40));
    }

    public static void updatePlayerView()
    {
        // Player statistics
        minerInfo.setText("Rotations: 0\n\nScans: 0\n\nMoves: 0");
        // playerInfo1.setText("Roatations: " + Game.Miner.getNumOfRotates() + "\n\nScans: " +
        // Game.Miner.getNumOfScans() + "\n\nMoves: " + Game.Miner.getNumOfMoves());
        // Player History
//        history.setText(history.getText + "action\n");
    }

    public static void buildMap()
    {
        mapGrid = new GridPane();
        mapGrid.setGridLinesVisible(true);

        int columnSize = 778;
        int rowSize = 768;
        // Dimensions drawn from inputs
        int rowCount = 14;
        int columnCount = 14;
        //Situational
        for (int i = 0; i < rowCount; i++)
            mapGrid.getRowConstraints().add(new RowConstraints(rowSize / rowCount));
        for (int i = 0; i < columnCount; i++)
            mapGrid.getColumnConstraints().add(new ColumnConstraints(columnSize / columnCount));

        // Miner Image
        ImageView minerImage = new ImageView();
        Image minerIcon = new Image("/sample/Miner2.png");
        minerImage.setImage(minerIcon);
        // Situational
        minerImage.setFitHeight(rowSize / rowCount / 2);
        minerImage.setFitWidth(columnSize / columnCount / 2);
        GridPane.setHalignment(minerImage, HPos.LEFT);

        // Beacons
        for (int i = 0; i < 1; i++) {
            ImageView bImage = new ImageView();
            Image bIcon = new Image("/sample/Beacon.png");
            bImage.setImage(bIcon);
            bImage.setFitHeight(rowSize / rowCount / 2);
            bImage.setFitWidth(rowSize / rowCount / 2);
            GridPane.setHalignment(bImage, HPos.RIGHT);
            GridPane.setConstraints(bImage, 0, 0);
            mapGrid.getChildren().addAll(bImage);
        }
        // Gold
        for (int i = 0; i < 1; i++) {
            ImageView gImage = new ImageView();
            Image gIcon = new Image("/sample/Gold.png");
            gImage.setImage(gIcon);
            gImage.setFitHeight(rowSize / rowCount / 2);
            gImage.setFitWidth(rowSize / rowCount / 2);
            GridPane.setHalignment(gImage, HPos.RIGHT);
            GridPane.setConstraints(gImage, 2, 1);
            mapGrid.getChildren().addAll(gImage);
        }
        // Pits
        for (int i = 0; i < 1; i++) {
            ImageView pImage = new ImageView();
            Image pIcon = new Image("/sample/Lava.png");
            pImage.setImage(pIcon);
            pImage.setFitHeight(rowSize / rowCount / 2);
            pImage.setFitWidth(rowSize / rowCount / 2);
            GridPane.setHalignment(pImage, HPos.RIGHT);
            GridPane.setConstraints(pImage, 3, 1);
            mapGrid.getChildren().addAll(pImage);
        }
        // Miner Image in grid
        GridPane.setConstraints(minerImage, 0, 0);

        mapGrid.getChildren().addAll(minerImage);
        mainFrame.add(mapGrid, 1, 0);
    }

    public static void updateMap (int[] playerPos) {
        int x = playerPos[0];
        int y = playerPos[1];

        // Update position
        GridPane.clearConstraints(minerImage);
        GridPane.setConstraints(minerImage, x, y);
        GridPane.setHalignment(minerImage, HPos.LEFT);
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
}
