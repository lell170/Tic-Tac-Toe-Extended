package org.artjom.tictacttoe.extended;

import java.util.List;

public class Board {

    private List<PlayItem> playItems;

    public void clearBoard() {
        for (PlayItem playItem : playItems) {
            playItem.setPlayItemPosition(PlayItemService.getPlayItemPositionByPlayItem(playItem));
            playItem.setPlayer(null);
        }
    }

    public void disableBoard() {
        for (PlayItem playItem : playItems) {
            playItem.setEnabled(false);
        }
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

    public boolean checkForWin(Player player) {
        return PlayItemService.checkDiagonally(player, this);
    }

}
