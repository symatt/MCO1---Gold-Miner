package miner;

public class GMObject {
    protected int[] currLocation;
    protected int x = 0;
    protected int y = 1;

    public GMObject(int x, int y) {
        this.currLocation = new int[2];
        this.currLocation[0] = x;
        this.currLocation[1] = y;
    }

    public int getXPos() {
        return currLocation[x];
    }

    public int getYPos() {
        return currLocation[y];
    }
}
