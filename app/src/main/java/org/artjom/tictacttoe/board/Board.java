package org.artjom.tictacttoe.board;

import org.artjom.tictacttoe.playitem.PlayItem;

import java.util.List;

public class Board {

    private List<PlayItem> playItems;

    private boolean disabled;

    public List<PlayItem> getPlayItems() {
        return playItems;
    }

    public void setPlayItems(final List<PlayItem> playItems) {
        this.playItems = playItems;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(final boolean disabled) {
        this.disabled = disabled;
    }
}
