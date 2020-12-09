package miner;

import miner.GUI.InputsController;

import java.util.ArrayList;

public class Board {

    private ArrayList<ArrayList<GMObject>> board;
    private int gridSize;

    // instantiates a board using a parameter for its grid size
    public Board(int gridSize) {
        this.gridSize = gridSize;
        board = new ArrayList<ArrayList<GMObject>>(this.gridSize);
        for (int i = 0; i < this.gridSize; i++) {
            board.add(new ArrayList<GMObject>(this.gridSize));
            for (int j = 0; j < this.gridSize; j++) {
                board.get(i).add(new Empty(j, i));
            }
        }
    }

    // sets the location of an object on the board
    // used by the miner and how it moves
    public void setObj(GMObject o) {
        board.get(o.getYPos()).set(o.getXPos(), o);
    }

    // initializes the gold location input by the user
    public void initializeGold(ArrayList<Integer> g) {
        int gX = g.get(0) - 1, gY = g.get(1) - 1;
        board.get(gY).set(gX, new Gold(gX, gY));
//        InputsController.buildGold(this, gY, gX);
    }

    // initializes the pits input by the user
    public void initializePits(ArrayList<Integer> p) {
        for (int i = 0; 2 * i + 1 < p.size(); i++) {
            int pitX = p.get(2 * i) - 1, pitY = p.get(2 * i + 1) - 1;
            board.get(pitY).set(pitX, new Pit(pitX, pitY));
//            InputsController.buildPits(this, pitY, pitX);
        }
    }

    // initializes the beacons input by the user
    public void initializeBeacons(ArrayList<Integer> b) {
        for (int i = 0; 2* i + 1 < b.size(); i++) {
            int beaconX = b.get(2 * i) - 1, beaconY = b.get(2 * i + 1) - 1;
            board.get(beaconY).set(beaconX, new Beacon(beaconX, beaconY));
//            InputsController.buildBeacons(this, beaconY, beaconX);
        }

//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (board.get(i).get(j) instanceof Pit)
//                    System.out.println("Pit X: " + board.get(i).get(j).getXPos() + " Pit Y: " + board.get(i).get(j).getYPos());
//                else if (board.get(i).get(j) instanceof Gold)
//                    System.out.println("Gold X: " + board.get(i).get(j).getXPos() + " Gold Y: " + board.get(i).get(j).getYPos());
//                else if (board.get(i).get(j) instanceof Miner)
//                    System.out.println("Miner X: " + board.get(i).get(j).getXPos() + " Miner Y: " + board.get(i).get(j).getYPos());
//                else if (board.get(i).get(j) instanceof Beacon)
//                    System.out.println("Beacon X: " + board.get(i).get(j).getXPos() + " Beacon Y: " + board.get(i).get(j).getYPos());
//                else
//                    System.out.println("EMPTY X: " + board.get(i).get(j).getXPos() + " EMPTY Y: " + board.get(i).get(j).getYPos());
//            }
//        }
    }

    // get the object at coordinates (x, y) (cartesian style)
    public GMObject getObjectAt(int x, int y) {
        return board.get(y).get(x);
    }

    // returns the grid size of the board
    public int getGridSize() { return this.gridSize; }

    // shows the board in the terminal
    public void showBoard() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (board.get(i).get(j) instanceof Pit)
                    System.out.print("P ");
                else if (board.get(i).get(j) instanceof Gold)
                    System.out.print("G ");
                else if (board.get(i).get(j) instanceof Miner)
                    System.out.print("M ");
                else if (board.get(i).get(j) instanceof Beacon)
                    System.out.print("B ");
                else
                    System.out.print("X ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
