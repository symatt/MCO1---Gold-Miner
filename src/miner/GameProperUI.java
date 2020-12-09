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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;

import static miner.GUI.InputsController.autoSkip;

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
    static final int rowSize = 760;
    public static Button nextButton;
    public static CheckBox autoMoveBox;

    public static Scene generateMainFrame(Board board) {
        // nextButton of two columns
        GameProperUI.board = board;
        mainFrame = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints(240);
        col1.setHalignment(HPos.CENTER);
        mainFrame.getColumnConstraints().add(col1);
        ColumnConstraints col2 = new ColumnConstraints(804);
        col2.setHalignment(HPos.CENTER);
        mainFrame.getColumnConstraints().add(col2);
//        mainFrame.setGridLinesVisible(true);

        // Building of Map and Miner
        buildGrid(board);
        buildMinerInfo();
        stage = new Scene(mainFrame, 1024, 758, Color.GRAY);
        return stage;
//        window = new Stage();
//        window.setTitle("Gold Miner");
//        window.setScene(stage);
//        window.initStyle(StageStyle.DECORATED);
//        window.setResizable(false);
//        window.show();
    }

//    public static void buildAllData()
//    {}

    public static void buildMinerInfo() {
        minerInfo.setStyle("-fx-background-color: white;");
        minerInfo.setText("Rotations: 0\nScans: 0\nMoves: 0\nFacing: Right");
        minerInfo.setStyle("-fx-font: 30 Minecraft;");
        nextButton = new Button("Next move");
        nextButton.setPrefWidth(200);
        nextButton.setPrefHeight(100);
        nextButton.setOnAction(value ->  {
            Main.nextMove();
        });
        autoMoveBox = new CheckBox("Auto Move");
        autoMoveBox.setOnAction(value ->  {
            autoSkip();
        });


        // Travel History
        history = new TextArea("Speedrun starts");
        history.setStyle("-fx-font: 13 Minecraft;");
        history.setPrefHeight(600);
        history.setPrefWidth(50);
        history.setEditable(false);
        history.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                history.setScrollTop(Double.MAX_VALUE);
            }
        });
        // History Area scrollbar
        history.setWrapText(true);
        leftArea.setSpacing(20);
        leftArea.getChildren().addAll(minerInfo, nextButton, autoMoveBox, history); // , slider
        leftArea.setPadding(new Insets(30, 40, 40, 30));
        mainFrame.add(leftArea, 0, 0);
    }

    public static void updatePlayerView()
    {
        // Player statistics
        minerInfo.setText("Rotations: " + m.getNumOfRotates() + "\nScans: " + m.getNumOfScans() + "\nMoves: " + m.getNumOfMoves() + "\nFacing: " + m.getDirection());
    }

    public static void buildGrid(Board b)
    {
        mapGrid = new GridPane();
        mapGrid.setGridLinesVisible(true);

        // Dimensions drawn from inputs
        int rowCount = b.getGridSize();
        int columnCount = b.getGridSize();
        // Column and Row sizes varies depending on grid size
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
            minerImage.setRotate(90);
        else if (m.getDirection() == "LEFT")
            minerImage.setRotate(180);
        else if (m.getDirection() == "UP")
            minerImage.setRotate(270);
        else if (m.getDirection() == "RIGHT")
            minerImage.setRotate(0);
    }

    public static void updateMinerInfo (Miner m) {
        // Update position
        if (m.getDirection() == "DOWN")
            minerInfo.setText("Rotations: " + m.getNumOfRotates() + "\nScans: " + m.getNumOfScans() + "\nMoves: " + m.getNumOfMoves() + "\nFacing: " + m.getDirection());
        else if (m.getDirection() == "LEFT")
            minerInfo.setText("Rotations: " + m.getNumOfRotates() + "\nScans: " + m.getNumOfScans() + "\nMoves: " + m.getNumOfMoves() + "\nFacing: " + m.getDirection());
        else if (m.getDirection() == "UP")
            minerInfo.setText("Rotations: " + m.getNumOfRotates() + "\nScans: " + m.getNumOfScans() + "\nMoves: " + m.getNumOfMoves() + "\nFacing: " + m.getDirection());
        else if (m.getDirection() == "RIGHT")
            minerInfo.setText("Rotations: " + m.getNumOfRotates() + "\nScans: " + m.getNumOfScans() + "\nMoves: " + m.getNumOfMoves() + "\nFacing: " + m.getDirection());
    }

    public static void updateHistory (String move) {
        // Update position
//        history.setText(history.getText());
        history.appendText("\n" + move);
        history.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                history.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
                //use Double.MIN_VALUE to scroll to the top
            }
        });
    }
}
