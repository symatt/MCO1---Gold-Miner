package miner;

import java.util.ArrayList;

public class Miner extends GMObject {

    private int[] frontLocation;
    private GMObject scannedObj;

    public int[] getFrontLocation() {
        return frontLocation;
    }

    public int getNumOfMoves() {
        return numOfMoves;
    }

    public int getNumOfRotates() {
        return numOfRotates;
    }

    public int getNumOfScans() {
        return numOfScans;
    }

    public ArrayList<ArrayList<Integer>> getPreviousLocations() {
        return previousLocations;
    }

    private int direction;
    // U - 1, R - 2, D - 3, L - 4
    private int numOfMoves;
    private int numOfRotates;
    private int numOfScans;
    private ArrayList<ArrayList<Integer>> previousLocations;

    public int getFrontXPos() {
        return frontLocation[x];
    }

    public int getFrontYPos() {
        return frontLocation[y];
    }

    public GMObject getScannedObj() {
        return scannedObj;
    }

    public int getDirection() {
        return direction;
    }

    public Miner() {
        super(1, 1);
        this.calculateFront();
        this.direction = 2;
        this.numOfMoves = 0;
        this.numOfRotates = 0;
        this.numOfScans = 0;
        this.previousLocations = new ArrayList<ArrayList<Integer>>();

    }

    public void addMoveToHistory() {
        ArrayList<Integer> loc = new ArrayList<>();
        loc.add(currLocation[x]);
        loc.add(currLocation[y]);
        previousLocations.add(loc);
    }

    public void calculateFront() {
        switch (this.direction) {
            case 1:
                // UP
                frontLocation[x] = super.currLocation[x] - 1;
                frontLocation[y] = super.currLocation[y];
                break;
            case 2:
                // RIGHT
                frontLocation[x] = super.currLocation[x];
                frontLocation[y] = super.currLocation[y] + 1;
                break;
            case 3:
                // DOWN
                frontLocation[x] = super.currLocation[x] + 1;
                frontLocation[y] = super.currLocation[y];
                break;
            case 4:
                // LEFT
                frontLocation[x] = super.currLocation[x];
                frontLocation[y] = super.currLocation[y] - 1;
                break;
        }
    }

    public void rotateMiner() {
        direction++;
        // if exceeds the number (5, i.e. not a valid direction)
        if (direction > 4)
            direction = 1;
    }

    public void scanFront() {
        ;
    }

    public void moveMiner(int maxPosX, int maxPosY) {
        // move the miner's location to its front
        if (frontLocation[x] <= maxPosX && frontLocation[y] <= maxPosY) {
            addMoveToHistory();
            currLocation[x] = frontLocation[x];
            currLocation[y] = frontLocation[y];
        }
    }
}

