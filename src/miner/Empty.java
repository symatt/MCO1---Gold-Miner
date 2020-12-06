package miner;

public class Empty extends GMObject implements Comparable<GMObject> {
    public Empty(int x, int y) {
        super(x, y);
        this.val = calculateVal();
    }

    private int calculateVal() {
        if (x == 0 || y == 0)
            return 2;
        else
            return 4;
    }
}
