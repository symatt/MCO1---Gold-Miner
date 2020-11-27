package miner;

public class GMObject {
    protected int[] currLocation;
    protected int x = 0;
    protected int y = 1;

    public GMObject(int x, int y) {
        this.currLocation[this.x] = x;
        this.currLocation[this.y] = y;
    }

    public int getXPos() {
        return currLocation[x];
    }

    public int getYPos() {
        return currLocation[y];
    }
}
