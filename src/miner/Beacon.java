package miner;

public class Beacon extends  GMObject {
    public Beacon(int x, int y) {
        super(x, y);
    }

    @Override
    public String getName() { return "Beacon"; }

    public int beaconScan(Board b) {
        int len = 0;

        for (int i = 1; i <= 4; i++) {
            if (len != 0)
                break;
            else
                len = scanDirection(i ,b);
        }
        return len;
    }

    public int scanDirection(int direction, Board b) {
        int scanX = this.x, scanY = this.y;
        switch (direction) {
            case 1:
                // UP
                for (int i = 1; i <= b.getGridSize(); i++) {
                    if ( scanY - i == -1 || b.getObjectAt(scanX, scanY - i) instanceof Pit)
                        return 0;
                    else if (b.getObjectAt(scanX, scanY - i) instanceof Gold)
                        return i;
                }
                break;
            case 2:
                // RIGHT
                for (int i = 1; i < b.getGridSize(); i++) {
                    if (scanX + i == 8 || b.getObjectAt(scanX + i, scanY) instanceof Pit )
                        return 0;
                    else if (b.getObjectAt(scanX + i, scanY) instanceof Gold)
                        return i;
                }
                break;
            case 3:
                // DOWN
                for (int i = 1; i < b.getGridSize(); i++) {
                    if (scanY + i == 8 || b.getObjectAt(scanX, scanY + i) instanceof Pit)
                        return 0;
                    else if (b.getObjectAt(scanX, scanY + i) instanceof Gold)
                        return i;
                }
                break;
            case 4:
                // LEFT
                for (int i = 1; i < b.getGridSize(); i++) {
                    if (scanX - i == -1 || b.getObjectAt(scanX, scanY - i) instanceof Pit)
                        return 0;
                    else if (b.getObjectAt(scanX - i, scanY) instanceof Gold)
                        return i;
                }
                break;
        }
        return 0;
    }
}
