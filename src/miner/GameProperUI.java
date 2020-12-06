package miner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Slider;

public final class GameProperUI {

    static Stage window;
    static Text minerInfo = new Text();
    static VBox leftArea = new VBox();
    static GridPane mapGrid;
    static GridPane mainFrame;
//    static StackPane historyStack;
    static  Slider slider;
    static TextArea history;
    static Scene stage;
    static ImageView minerImage = new ImageView();
    static Image mIcon = new Image("/miner/Miner2.png");
    static Miner m;
    static Board board;
    static final int columnSize = 778;
    static final int rowSize = 768;

    public static Scene generateMainFrame(Board board) {
        // Creation of two columns
        GameProperUI.board = board;
        mainFrame = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints(270);
        col1.setHalignment(HPos.CENTER);
        mainFrame.getColumnConstraints().add(col1);
        ColumnConstraints col2 = new ColumnConstraints(774);
        col2.setHalignment(HPos.CENTER);
        mainFrame.getColumnConstraints().add(col2);
//        mainFrame.setGridLinesVisible(true);

        // Building of Map and Miner

        stage = new Scene(mainFrame, 1024, 758, Color.GRAY);
        return stage;
//        window = new Stage();
//        window.setTitle("Gold Miner");
//        window.setScene(stage);
//        window.initStyle(StageStyle.DECORATED);
//        window.setResizable(false);
//        window.show();
    }

    public static void buildAllData()
    {
        buildGrid(board);
        buildMinerInfo();
    }

    public static void buildMinerInfo() {
        minerInfo.setStyle("-fx-background-color: white;");
        minerInfo.setText("Rotations: 0\nScans: 0\nMoves: 0\n\n");
        minerInfo.setStyle("-fx-font: 30 Minecraft;");
        // Slider for Animation Speed
        slider = new Slider(200 , 1000, 600);
        slider.setMajorTickUnit(200);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener(
            new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number > observable, Number oldValue, Number newValue)
                {
                    Main.speed = (int) newValue;
                }
        });
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
//        historyStack.getChildren().addAll(history);
        leftArea.getChildren().addAll(minerInfo, slider, history);
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

    public static void buildGrid(Board b)
    {
        mapGrid = new GridPane();
        mapGrid.setGridLinesVisible(true);

        // Dimensions drawn from inputs
        int rowCount = b.getGridSize();
        int columnCount = b.getGridSize();
        //Situational
        for (int i = 0; i < rowCount; i++)
            mapGrid.getRowConstraints().add(new RowConstraints(rowSize / rowCount));
        for (int i = 0; i < columnCount; i++)
            mapGrid.getColumnConstraints().add(new ColumnConstraints(columnSize / columnCount));

        // Miner Image in grid
        minerImage.setImage(mIcon);
        double size = rowSize / b.getGridSize() / 2;
        minerImage.setFitHeight(size);
        minerImage.setFitWidth(size);
        mapGrid.setHalignment(minerImage, HPos.LEFT);
        mapGrid.setConstraints(minerImage, 0, 0);

        // Objects
        for (int i = 0; i < b.getGridSize(); i++)
            for (int j = 0; j < b.getGridSize(); j++)
            {
                if (b.getObjectAt(i, j) instanceof Pit)
                {
                    ImageView pImage = new ImageView();
                    Image pIcon = new Image("/miner/Lava.png");
                    pImage.setImage(pIcon);
                    pImage.setFitHeight(size);
                    pImage.setFitWidth(size);
                    mapGrid.setHalignment(pImage, HPos.RIGHT);
                    mapGrid.setConstraints(pImage, i, j);
                    mapGrid.getChildren().addAll(pImage);
                }
                else if (b.getObjectAt(i, j) instanceof Gold)
                {
                    ImageView gImage = new ImageView();
                    Image gIcon = new Image("/miner/Gold.png");
                    gImage.setImage(gIcon);
                    gImage.setFitHeight(size);
                    gImage.setFitWidth(size);
                    mapGrid.setHalignment(gImage, HPos.RIGHT);
                    mapGrid.setConstraints(gImage, i, j);
                    mapGrid.getChildren().addAll(gImage);
                }
                else if (b.getObjectAt(i, j) instanceof Beacon)
                {
                    ImageView bImage = new ImageView();
                    Image bIcon = new Image("/miner/Beacon.png");
                    bImage.setImage(bIcon);
                    bImage.setFitHeight(size);
                    bImage.setFitWidth(size);
                    mapGrid.setHalignment(bImage, HPos.RIGHT);
                    mapGrid.setConstraints(bImage, i, j);
                    mapGrid.getChildren().addAll(bImage);
                }
            }
        mapGrid.getChildren().addAll(minerImage);
        mainFrame.add(mapGrid, 1, 0);
    }

    public static void updateMiner (Miner m) {
        // Update position
        mapGrid.clearConstraints(minerImage);
        mapGrid.setConstraints(minerImage, m.getXPos(), m.getYPos());
        mapGrid.setHalignment(minerImage, HPos.LEFT);
        // Update Orientation
        if (m.getDirection() == "DOWN")
            minerImage.setRotate(0);
        else if (m.getDirection() == "LEFT")
            minerImage.setRotate(90);
        else if (m.getDirection() == "UP")
            minerImage.setRotate(180);
        else if (m.getDirection() == "RIGHT")
            minerImage.setRotate(270);
    }

    public static void updateMinerInfo (Miner m) {
        // Update position
        minerInfo.setText("Rotations: " + m.getNumOfRotates() + "\nScans: " + m.getNumOfScans() + "\nMoves: " + m.getNumOfMoves() + "\n\n");
    }

    public static void updateHistory (String move) {
        // Update position
        minerInfo.setText(minerInfo.getText() + "\n" + move);
    }
}
