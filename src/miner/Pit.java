package miner;

public class Pit extends GMObject implements Comparable<GMObject> {
    public Pit(int x, int y) {
        super(x, y);
        this.val = 1;
    }

    @Override
    public String getName() { return "Pit"; }
}
