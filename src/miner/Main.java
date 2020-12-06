package miner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import miner.GUI.InputsController;

//import java.awt.desktop.SystemSleepEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.Thread;

public class Main extends Application {
    private static Stage primaryStage;
    public static int speed;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gold Miner");
        this.speed = 600;
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
            InputsController.updateHistory("GAME OVER.");
            return true;
        }
        else if (o instanceof Gold) {
            System.out.println("YOU WIN!");
            InputsController.updateHistory("YOU WIN!");
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
                    InputsController.updateHistory("SCAN " + m.getDirection());
                    m.scanFront(b);
                    break;
                // rotates once
                case 1:
                    System.out.println("ROTATE");
                    InputsController.updateHistory("ROTATE");
                    m.rotateMiner();
                    break;
                // moves miner
                case 2:
                    System.out.println("MOVE " + m.getDirection());
                    InputsController.updateHistory("MOVE " + m.getDirection());
                    o = m.moveMiner(b);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + move);
            }
            if (o instanceof Beacon)
            {
                System.out.println("GOLD IS WITHIN " + ((Beacon) o).beaconScan(b) + " SQUARE/S.");
                InputsController.updateHistory("GOLD IS WITHIN " + ((Beacon) o).beaconScan(b) + " SQUARE/S.");
            }

            b.showBoard();
            InputsController.updateMiner(m);
            InputsController.updateMinerInfo(m);
            // Delay part (5000 will be replaced with slider)
            try {
                Thread.sleep(speed);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        } while (!isGameOver(o));
    }


    // the miner uses an algorithm in order to find the gold on the board
    public static void intelligent(Miner m, Board b) {
        // scPlaces is a priorityQueue which is needed to evaluate which direction the miner should choose to go
        // the toCompare() function is customized in the GMObject class
        PriorityQueue<GMObject> scPlaces = new PriorityQueue<>();
        boolean foundGold = false;
        int rotateCtr = 0, beaconMoveCtr = 0; // number of rotates and number of moves when beacon is in use
        GMObject scObj; // scanned object

        // loop through the board while the gold is not found or a pit is the only space that can be moved to
        while (!foundGold) {
            // rotate 4 times to scan all the directions adjacent to the current position of the miner
            while(rotateCtr < 4) {
                // scan the front of the current direction
                scObj = m.scanFront(b);
//                System.out.println("Miner's current location: " + m.getXPos() + ", " + m.getYPos());
//                System.out.println(m.getDirection());
//                if (scObj != null) {
//                    System.out.println(scObj.getName());
//                }

                // check what type of object was scanned
                // gold = move to that square and the miner wins
                // if its a null (not a valid square, i.e. an edge), rotate the miner
                // otherwise, push into the PQ and rotate the miner
                if (scObj instanceof Gold) {
                    m.moveMiner(b);
                    System.out.println("MOVE " + m.getDirection());
                    InputsController.updateHistory("MOVE " + m.getDirection());
                    foundGold = true;
//                    System.out.println(m.getPreviousLocations());
                    System.out.println("YOU WIN!");
                    InputsController.updateHistory("YOU WIN!");
                    break;
                }
                else if (scObj == null) {
                    m.rotateMiner();
                    System.out.println("ROTATE");
                    InputsController.updateHistory("ROTATE");
                    rotateCtr++;
                }
                else {
                    // check first if the space has been visited so it is a low priority
                    if (m.didVisit(scObj.getXPos(), scObj.getYPos())) scObj.setVal(0);
                    scPlaces.add(scObj);
                    m.rotateMiner();
                    System.out.println("ROTATE");
                    InputsController.updateHistory("ROTATE");
                    rotateCtr++;
                }
            }

//            GMObject[] events = scPlaces.toArray(new GMObject[scPlaces.size()]);
//            Arrays.sort(events, scPlaces.comparator());
//            for (GMObject e : events) {
//                System.out.println("obj name: " + e.getName() + " AT " + e.getXPos() + ", " + e.getYPos() + " with value: " + e.getVal());
//            }

            //execute only when gold has not been found
            if (!foundGold) {
                rotateCtr = 0;
                // pop out the object out and find the direction of that square
                GMObject goObj = scPlaces.remove();
                int xPosDir = goObj.getXPos();
                int yPosDir = goObj.getYPos();
//                System.out.println("GO TO THIS OBJ: " + goObj.getName() + " AT " + xPosDir + ", " + yPosDir);

                if (xPosDir == m.getXPos()) {
                    if (yPosDir > m.getYPos()) {
                        // move down
                        while (!m.getDirection().equalsIgnoreCase("DOWN")) {
                            m.rotateMiner();
                            System.out.println("ROTATE");
                            InputsController.updateHistory("ROTATE");
                        }
                    } else {
                        // move up
                        while (!m.getDirection().equalsIgnoreCase("UP")){
                            m.rotateMiner();
                            System.out.println("ROTATE");
                            InputsController.updateHistory("ROTATE");
                        }
                    }
                } else {
                    if (xPosDir > m.getXPos()) {
                        // move right
                        while (!m.getDirection().equalsIgnoreCase("RIGHT")){
                            m.rotateMiner();
                            System.out.println("ROTATE");
                            InputsController.updateHistory("ROTATE");
                        }
                    } else {
                        // move left
                        while (!m.getDirection().equalsIgnoreCase("LEFT")){
                            m.rotateMiner();
                            System.out.println("ROTATE");
                            InputsController.updateHistory("ROTATE");
                        }
                    }
                }

                // move the miner to the priority space popped out from the PQ
                GMObject prevObj = m.moveMiner(b);
                System.out.println("MOVE " + m.getDirection());
                InputsController.updateHistory("MOVE " + m.getDirection());
                // check if it was a beacon
                if (prevObj instanceof Beacon) {
//                    System.out.println("BEACON BEACON BEACON");
                    // get how far the gold is from the beacon
                    int howFar = ((Beacon) prevObj).beaconScan(b);
//                    System.out.println("HOW FAR IS GOLD: " + howFar);
                    // if the beacon tells us 0, that means that a pit is covering the gold, so proceed back to the earlier algorithm
                    // in the case that it is not 0, search each direction by scanning and moving along each of the 4 directions of the beacon
                    if (howFar != 0) {
                        System.out.println("BEACON IS BEING USED.");
                        InputsController.updateHistory("BEACON IS BEING USED.");
                        while (rotateCtr < 4) {
//                            System.out.println("Miner's current location: " + m.getXPos() + ", " + m.getYPos());
//                            System.out.println(m.getDirection());
                            scObj = m.scanFront(b);
//                            if (scObj != null) {
//                                System.out.println("SCANNED OBJ: " + scObj.getName());
//                            }
                            if (scObj instanceof Gold) {
                                m.moveMiner(b);
                                System.out.println("MOVE " + m.getDirection());
                                InputsController.updateHistory("MOVE " + m.getDirection());
                                foundGold = true;
                                System.out.println(m.getPreviousLocations());
                                System.out.println("USING A BEACON, YOU WIN!");
                                InputsController.updateHistory("USING A BEACON, YOU WIN!");
                                break;
                            } else if (beaconMoveCtr == howFar) {
                                // check if the number of steps is equal to the beacon's signal
                                // move the miner back to the position of the beacon
                                m.rotateMiner();
                                m.rotateMiner();
                                for (int i = 1; i <= howFar; i++){
                                    m.moveMiner(b);
                                    System.out.println("MOVE " + m.getDirection());
                                    InputsController.updateHistory("MOVE " + m.getDirection());
                                }
                                beaconMoveCtr = 0;
                                m.rotateMiner();
                                m.rotateMiner();
                                m.rotateMiner();
                                rotateCtr++;
                                System.out.println("ROTATE");
                                InputsController.updateHistory("ROTATE");
                            }
                            else if (scObj instanceof Pit || scObj instanceof Beacon) {
                                // if its a pit or beacon that was scanned, go back to the position of the first beacon
                                m.rotateMiner();
                                m.rotateMiner();
                                for (int i = 1; i <= beaconMoveCtr; i++) {
                                    m.moveMiner(b);
                                    System.out.println("MOVE " + m.getDirection());
                                    InputsController.updateHistory("MOVE " + m.getDirection());
                                }
                                beaconMoveCtr = 0;
                                m.rotateMiner();
                                m.rotateMiner();
                                m.rotateMiner();
                                rotateCtr++;
                                System.out.println("ROTATE");
                                InputsController.updateHistory("ROTATE");
                            } else if (m.didVisit(scObj.getXPos(), scObj.getYPos())) {
                                // if it was visited square already, return to the beacon's position
                                m.rotateMiner();
                                m.rotateMiner();
                                for (int i = 1; i <= beaconMoveCtr; i++){
                                    m.moveMiner(b);
                                    System.out.println("MOVE " + m.getDirection());
                                    InputsController.updateHistory("MOVE " + m.getDirection());
                                }
                                beaconMoveCtr = 0;
                                m.rotateMiner();
                                m.rotateMiner();
                                m.rotateMiner();
                                rotateCtr++;
                                System.out.println("ROTATE");
                                InputsController.updateHistory("ROTATE");
                            }
                            else if (scObj instanceof Empty){
                                // if it is empty, move the miner
                                m.moveMiner(b);
                                System.out.println("MOVE " + m.getDirection());
                                InputsController.updateHistory("MOVE " + m.getDirection());
                                beaconMoveCtr++;
                            }
                            else if (scObj == null){
                                // rotate the miner if it scans a non valid square (i.e. edges)
                                m.rotateMiner();
                                rotateCtr++;
                                System.out.println("ROTATE");
                                InputsController.updateHistory("ROTATE");
                            }
                        }
                    }
                }
                rotateCtr = 0;

                // if there is no choice but a pit, foundGold is changed to true to stop the loop but the output is a game over.
                if (prevObj instanceof Pit) {
                    foundGold = true;
                    System.out.println("GAME OVER");
                }

                // the PQ is emptied out
                scPlaces.clear();
            }
            b.showBoard();
            InputsController.updateMiner(m);
            InputsController.updateMinerInfo(m);
            try {
                Thread.sleep(speed);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
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
        if (intel.equalsIgnoreCase("random")){
            InputsController.startGame(gridSize);
            random(m, board);
        }
        else {
            intelligent(m, board);
            InputsController.startGame(gridSize);
        }

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
