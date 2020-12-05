package miner;

import java.util.Objects;

public class GMObject implements Comparable<GMObject> {
    protected int[] currLocation;
    protected int x = 0;
    protected int y = 1;
    protected int val;

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

    public String getName() { return "Empty";}

    public int getVal() { return this.val; }

    public void setVal(int newVal) {
        this.val = newVal;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GMObject gmObject = (GMObject) o;
        return x == gmObject.x &&
                y == gmObject.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(GMObject obj) {
        // compares the values of the GMObjects
        // special case for the empty spaces which take into consideration the number of adjacent squares that it has
        // those empty spaces are given a higher priority than the less number of adjacent squares
        if (this.getVal() > obj.getVal())
            return -1;
        else if (this.getVal() < obj.getVal())
            return 1;
        else
            return 0;
    }
}
