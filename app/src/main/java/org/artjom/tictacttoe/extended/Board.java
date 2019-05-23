package org.artjom.tictacttoe.extended;

import android.view.View;

import java.util.List;

public class Board {

    private List<PlayItem> playItems;

    private boolean disabled;

    public void clearBoard() {
        this.setDisabled(false);
        for (PlayItem playItem : playItems) {
            playItem.setPlayItemPosition(PlayItemService.getPlayItemPositionByPlayItem(playItem));
            playItem.setPlayer(null);
        }
    }

    public void disableBoard() {
        // disable board
        this.setDisabled(true);
        // disable all play items
        playItems.forEach(playItem -> playItem.setEnabled(false));
    }

    public List<PlayItem> getPlayItems() {
        return playItems;
    }

    public void setPlayItems(List<PlayItem> playItems) {
        this.playItems = playItems;
    }

    public void putPlayItemToBoard(PlayItem playItem, Player player) {
        playItem.setImageResource(player.getImageId());

        PlayItemPosition playItemPositionByPlayItem = PlayItemService.getPlayItemPositionByPlayItem(playItem);
        playItem.setPlayItemPosition(playItemPositionByPlayItem);
        playItem.setPlayer(player);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void makeMove(Player player, View view, MainActivity mainActivity) {
        if (!this.isDisabled()) {
            PlayItem playItem;
            if (player.getName().equals(MainActivity.YETI_NAME)) {
                playItem = PlayerService.getBestPossibleMove(player, this);
            } else {
                playItem = (PlayItem) view;
            }
            this.putPlayItemToBoard(playItem, player);
            playItem.setEnabled(false);
            PlayItemService.checkForWin(player, this, mainActivity);
        }
    }
}
