package miner;

public class Pit extends GMObject implements Comparable<GMObject> {
    public Pit(int x, int y) {
        super(x, y);
        this.val = 0;
    }

    @Override
    public String getName() { return "Pit"; }
}
