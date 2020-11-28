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
        GMObject o;
        int move;
        Random rand = new Random();
        do {
            b.showBoard();
            move = rand.nextInt(3);
            switch (move) {
                // scans front
                case 0:
                    System.out.println("SCAN " + m.getDirection());
                    m.scanFront(b);
                    o = null;
                    break;
                // rotates once
                case 1:
                    System.out.println("ROTATE");
                    m.rotateMiner();
                    o = null;
                    break;
                // moves miner
                case 2:
                    System.out.println("MOVE " + m.getDirection());

                    o = m.moveMiner(b);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + move);
            }
        } while (!isGameOver(o));
    }

    public static void main(String[] args) {
        launch(args);

        // for testing purposes
        Miner m = new Miner();
        int gridSize = Integer.valueOf(InputsController.gridSize);
        String intel = InputsController.config;
        ArrayList<Integer> bLoc = new ArrayList<>();
        ArrayList<Integer> gLoc = new ArrayList<>();
        ArrayList<Integer> pLoc = new ArrayList<>();

        for (String str : InputsController.beaconLoc.split("\\s"))
            bLoc.add(Integer.valueOf(str));
        for (String str : InputsController.goldLoc.split("\\s"))
            gLoc.add(Integer.valueOf(str));
        for (String str : InputsController.pitLoc.split("\\s"))
            pLoc.add(Integer.valueOf(str));

        System.out.println("grid size: " + gridSize);
        System.out.println("intelligence: " + intel);
        System.out.println("beacons: " + bLoc);
        System.out.println("pot of gold: " + gLoc);
        System.out.println("pits: " + pLoc);

        Board board = new Board(gridSize);
        board.setObj(m);
        board.initializeGold(gLoc);
        board.initializePits(pLoc);
        board.initializeBeacons(bLoc);
        board.showBoard();
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
