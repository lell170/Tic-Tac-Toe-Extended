package org.artjom.tictacttoe.extended;

public class PlayItemPosition {

    private int x;
    private int y;

    public PlayItemPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "PlayItemPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
