package miner;

public class Gold extends GMObject implements Comparable<GMObject> {
    public Gold(int x, int y) {
        super(x, y);
    }
    @Override
    public String getName() { return "Gold"; }
}
