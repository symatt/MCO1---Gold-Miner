package miner.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import miner.Board;
import miner.GameProperUI;
import miner.Main;
import javafx.application.Platform;
import miner.Miner;

import java.util.ArrayList;


public class InputsController {

    public static String gridSize;
    public static String pitLoc;
    public static String goldLoc;
    public static String beaconLoc;
    public static String config;

    private static Main main;

    ObservableList<String> toggleChoicesList = FXCollections.observableArrayList("Random", "Smart") ;

    @FXML
    private TextField gridSizeField;
    @FXML
    private TextArea pitField;
    @FXML
    private TextField goldField;
    @FXML
    private TextArea beaconField;

    @FXML
    public ChoiceBox randomSmartBox;

    @FXML
    public void startMiner(ActionEvent actionEvent) {
        System.out.println("Puts in the values from GUI to model and starts the miner");
        gridSize = gridSizeField.getText();
        pitLoc = pitField.getText();
        goldLoc = goldField.getText();
        beaconLoc = beaconField.getText();
        config = (String) randomSmartBox.getValue();
        // initialize a miner
        Miner m = new Miner();

        // get grid size from the UI
        int gridSize = Integer.valueOf(InputsController.gridSize);

        // get the intelligence of the AI from the UI
        String intel = InputsController.config;

        // get beacon, pit and gold locations from the UI
        ArrayList<Integer> bLoc = new ArrayList<>();
        ArrayList<Integer> gLoc = new ArrayList<>();
        ArrayList<Integer> pLoc = new ArrayList<>();
        for (String str : InputsController.beaconLoc.split("\\s"))
            if (!str.equals(""))
                bLoc.add(Integer.valueOf(str));
        for (String str : InputsController.goldLoc.split("\\s"))
            if (!str.equals(""))
                gLoc.add(Integer.valueOf(str));
        for (String str : InputsController.pitLoc.split("\\s"))
            if (!str.equals(""))
                pLoc.add(Integer.valueOf(str));

        // debugging tool
        System.out.println("grid size: " + gridSize);
        System.out.println("intelligence: " + intel);
        System.out.println("beacons: " + bLoc);
        System.out.println("pot of gold: " + gLoc);
        System.out.println("pits: " + pLoc);

        // create a board
        Board gameBoard = new Board(gridSize);
        // set the miner
        gameBoard.setObj(m);
        // set the gold
        gameBoard.initializeGold(gLoc);
        // set the pits
        gameBoard.initializePits(pLoc);
        // set the beacons
        gameBoard.initializeBeacons(bLoc);
        // show the gameBoard
        gameBoard.showBoard();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(GameProperUI.generateMainFrame(gameBoard));

        // random or intelligent AI
        if (intel.equalsIgnoreCase("random")) main.random(m, gameBoard);
        else main.intelligent(m, gameBoard);
//        main.closeWindow();
    }

    @FXML
    public void initialize() {
        randomSmartBox.setValue("Random");
        randomSmartBox.setItems(toggleChoicesList);
    }

    public static void startGame(Board board)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("MAPSHOWYES");
//                  GameProperUI.display(board);
            }
        });
    }

    public static void updateMiner(Miner m)
    {
        /**
         * Possible error:
         * - update is only called after the random/intelligent function
         * - updateMiner is inside of main.intelligent that is called inside inputscontroller
         *
         * What happens:
         * - it calls updateMiner function naman pero it doesnt update the board..
         */
        System.out.println("Updating Miner");
        GameProperUI.updateMiner(m);
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                  GameProperUI.updateMiner(m);
//            }
//        });
    }

    public static void updateMinerInfo(Miner m)
    {
        GameProperUI.updateMinerInfo(m);
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                  GameProperUI.updateMinerInfo(m);
//            }
//        });
    }

    public static void updateHistory(String move)
    {
        GameProperUI.updateHistory(move);
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                GameProperUI.updateHistory(move);
//            }
//        });
    }
}