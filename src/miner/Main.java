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
import java.util.Random;

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

    // checks if the game is over
    public static boolean isGameOver(GMObject o) {
        if (o instanceof Pit) {
            System.out.println("GAME OVER.");
            return true;
        }
        else if (o instanceof Gold) {
            System.out.println("YOU WIN!");
            return true;
        }
        return false;
    }

    // Random agent rationality
    public static void random(Miner m, Board b) {
        // o is the object that is checked to know whether the game has ended or not
        // this can be empty, beacon, pit or gold
        // initialized to null, only can be changed by moving the miner
        GMObject o = null;
        int move;
        Random rand = new Random();
        do {
            // gets a random number from 0 to 2
            move = rand.nextInt(3);
            switch (move) {
                // scans front
                case 0:
                    System.out.println("SCAN " + m.getDirection());
                    m.scanFront(b);
                    break;
                // rotates once
                case 1:
                    System.out.println("ROTATE");
                    m.rotateMiner();
                    break;
                // moves miner
                case 2:
                    System.out.println("MOVE " + m.getDirection());
                    o = m.moveMiner(b);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + move);
            }
            if (o instanceof Beacon)
                System.out.println("GOLD IS WITHIN " + ((Beacon) o).beaconScan(b) + " SQUARE/S.");
            b.showBoard();
        } while (!isGameOver(o));
    }

    public static void main(String[] args) {
        launch(args);

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
        Board board = new Board(gridSize);
        // set the miner
        board.setObj(m);
        // set the gold
        board.initializeGold(gLoc);
        // set the pits
        board.initializePits(pLoc);
        // set the beacons
        board.initializeBeacons(bLoc);
        // show the board
        board.showBoard();

        // random or intelligent AI
        if (intel.equalsIgnoreCase("random"))
            random(m, board);
        else
            System.out.println("Intelligent");

//        System.out.println(m.scanFront(board).getName());
//        m.moveMiner(board);
//        board.showBoard();
//        System.out.println(m.scanFront(board).getName());
//        m.moveMiner(board);
//        board.showBoard();
//        System.out.println(m.scanFront(board).getName());
//        m.moveMiner(board);
//        board.showBoard();
//        m.rotateMiner();
//        System.out.println(m.scanFront(board).getName());
//        m.moveMiner(board);
//        board.showBoard();
//        // should move 3 to the right and 1 down

    }


}
