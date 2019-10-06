package org.artjom.tictacttoe.player;

public class Player {

    private String name;

    private int imageId;

    public Player(final String name, final int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(final int imageId) {
        this.imageId = imageId;
    }
}
