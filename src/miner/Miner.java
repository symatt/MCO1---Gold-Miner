package miner;

import javax.management.ObjectName;
import java.util.ArrayList;

public class Miner extends GMObject {

    private int[] frontLocation;
    private GMObject scannedObj;
    private GMObject prevObj;
    private int direction;
    // U - 1, R - 2, D - 3, L - 4
    private int numOfMoves;
    private int numOfRotates;
    private int numOfScans;
    private ArrayList<ArrayList<Integer>> previousLocations;

    public Miner() {
        super(0, 0);
        this.calculateFront();
        this.direction = 2;
        this.numOfMoves = 0;
        this.numOfRotates = 0;
        this.numOfScans = 0;
        this.previousLocations = new ArrayList<ArrayList<Integer>>();
        this.frontLocation = new int[2];
        this.scannedObj = null;
        this.prevObj = new Empty(0, 0);
    }

    // adds the previous location to the history
    public void addMoveToHistory() {
        ArrayList<Integer> loc = new ArrayList<>();
        loc.add(currLocation[x]);
        loc.add(currLocation[y]);
        previousLocations.add(loc);
    }

    // calculates the front coordinates of the miner
    public void calculateFront() {
        switch (this.direction) {
            case 1:
                // UP
                frontLocation[x] = super.currLocation[x];
                frontLocation[y] = super.currLocation[y] - 1;
                break;
            case 2:
                // RIGHT
                frontLocation[x] = super.currLocation[x] + 1;
                frontLocation[y] = super.currLocation[y];
                break;
            case 3:
                // DOWN
                frontLocation[x] = super.currLocation[x];
                frontLocation[y] = super.currLocation[y] + 1;
                break;
            case 4:
                // LEFT
                frontLocation[x] = super.currLocation[x] - 1;
                frontLocation[y] = super.currLocation[y];
                break;
        }
    }

    // rotates the miner once clockwise
    public void rotateMiner() {
        direction++;
        // if exceeds the number (5, i.e. not a valid direction)
        if (direction > 4)
            direction = 1;
        numOfRotates++;
    }

    // calculates the front and then scans for what is in front of the miner
    public GMObject scanFront(Board b) {
        this.calculateFront();
        numOfScans++;
        // check if location is valid
        if (frontLocation[x] <= b.getGridSize() - 1 &&  frontLocation[x] >= 0 &&
                frontLocation[y] >= 0 && frontLocation[y] <= b.getGridSize() - 1)
            return this.scannedObj = b.getObjectAt(this.getFrontXPos(), this.getFrontYPos());
        else
            return null;
    }

    // move the miner's location to its front and replaces its trail with the previous object in that place
    // this returns the object that is where the miner is headed to
    public GMObject moveMiner(Board b) {
        // check if the next location is within the grid size
        this.calculateFront();
        if (frontLocation[x] <= b.getGridSize() - 1 &&  frontLocation[x] >= 0 &&
                frontLocation[y] >= 0 && frontLocation[y] <= b.getGridSize() - 1) {
            // adds the old location to the history
            addMoveToHistory();
            // set the prevObj 's location back to its original
            b.setObj(this.prevObj);
            // gets the obj that miner will move to
            this.prevObj = b.getObjectAt(frontLocation[x], frontLocation[y]);
            // update miner's position
            currLocation[x] = frontLocation[x];
            currLocation[y] = frontLocation[y];
            // set miner's new position on the board
            b.setObj(this);
            numOfMoves++;
            // return the original obj on the miner's current position
            return prevObj;
        }
        return null;
    }

    public int getFrontXPos() { return frontLocation[x]; }

    public int getFrontYPos() {
        return frontLocation[y];
    }

    public GMObject getScannedObj() {
        return scannedObj;
    }

    public String getDirection() {
        String dir = "NULL";
        switch (this.direction) {
            case 1:
                dir = "UP";
                break;
            case 2:
                dir = "RIGHT";
                break;
            case 3:
                dir = "DOWN";
                break;
            case 4:
                dir = "LEFT";
                break;

        }
        return dir;
    }

    public int[] getFrontLocation() { return frontLocation; }

    public int getNumOfMoves() { return numOfMoves; }

    public int getNumOfRotates() { return numOfRotates; }

    public int getNumOfScans() { return numOfScans; }

    public ArrayList<ArrayList<Integer>> getPreviousLocations() {
        return previousLocations;
    }

    @Override
    public String getName() { return "Miner";}
}

