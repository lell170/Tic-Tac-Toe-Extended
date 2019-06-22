package org.artjom.tictacttoe.board;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.ImageView;
import org.artjom.tictacttoe.MainActivity;
import org.artjom.tictacttoe.player.Player;
import org.artjom.tictacttoe.player.PlayerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Board {

    private List<PlayItem> playItems;

    private boolean disabled;

    public List<PlayItem> getPlayItems() {
        return playItems;
    }

    public void setPlayItems(List<PlayItem> playItems) {
        this.playItems = playItems;
    }

    private boolean isDisabled() {
        return disabled;
    }

    private void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void clearBoard() {
        this.setDisabled(false);
        for (PlayItem playItem : playItems) {
            playItem.setPlayItemPosition(getPlayItemPositionByPlayItem(playItem));
            playItem.setPlayer(null);
        }
    }

    public void disableBoard() {
        // disable board
        this.setDisabled(true);
        // disable all play items
        playItems.forEach(playItem -> playItem.setEnabled(false));
    }

    private void putPlayItemToBoard(PlayItem playItem, Player player) {
        playItem.setImageResource(player.getImageId());

        playItem.setTranslationY(-1000);
        playItem.animate().translationYBy(1000).setDuration(500);

        PlayItemPosition playItemPositionByPlayItem = getPlayItemPositionByPlayItem(playItem);
        playItem.setPlayItemPosition(playItemPositionByPlayItem);
        playItem.setPlayer(player);
    }

    public void makeMove(Player player, View view, MainActivity mainActivity) {
        if (!this.isDisabled()) {
            PlayItem playItem;
            if (player.getName().equals(MainActivity.GRAY_CUP)) {
                playItem = PlayerService.getBestPossibleMove(this);
            } else {
                playItem = (PlayItem) view;
            }
            this.putPlayItemToBoard(playItem, player);
            playItem.setEnabled(false);
            checkForWin(player, this, mainActivity);
        }
    }

    private void checkForWin(Player player, Board board, MainActivity mainActivity) {
        List<PlayItem> playItems = board.getPlayItems();

        if (isVerticallyWin(player, playItems)) {
            mainActivity.gameOver(player.getName());
        }

        if (isHorizontallyWin(player, playItems)) {
            mainActivity.gameOver(player.getName());
        }

        if (isDiagonallyWin(player, playItems)) {
            mainActivity.gameOver(player.getName());
        }
    }

    private boolean isDiagonallyWin(Player player, List<PlayItem> playItems) {
        List<PlayItem> winnerPlayItems = new ArrayList<>();

        boolean fromLeft = IntStream.rangeClosed(1, 3)
                .boxed()
                .allMatch(integer -> playItems.stream()
                        .anyMatch(playItem -> {
                            if (playItem.getPlayItemPosition() != null) {
                                return playItem.getPlayItemPosition().getRow() == integer &&
                                        playItem.getPlayItemPosition().getCol() == integer &&
                                        playItem.getPlayer() == player;
                            }
                            return false;
                        }));

        boolean fromRight = IntStream.rangeClosed(1, 3)
                .boxed()
                .allMatch(integer -> playItems.stream()
                        .anyMatch(playItem -> {
                            if (playItem.getPlayItemPosition() != null) {

                                return playItem.getPlayItemPosition().getRow() == integer &&
                                        playItem.getPlayItemPosition().getCol() == 4 - integer &&
                                        playItem.getPlayer() == player;

                            }
                            return false;
                        }));

        if (fromLeft) {
            for (int i = 1; i <= 3; i++) {
                winnerPlayItems.add(getPlayItemByPosition(playItems, i, i).get());
            }
            animateWinnerPlayItems(winnerPlayItems);
            return true;
        } else if (fromRight) {
            for (int i = 1; i <= 3; i++) {
                winnerPlayItems.add(getPlayItemByPosition(playItems, i, 4 - i).get());
            }
            animateWinnerPlayItems(winnerPlayItems);
            return true;
        }

        return false;
    }

    private boolean isHorizontallyWin(Player player, List<PlayItem> playItems) {
        List<PlayItem> winnerPlayItems = new ArrayList<>();

        boolean win = IntStream.rangeClosed(1, 3)
                .boxed()
                .anyMatch(rowNr -> IntStream.rangeClosed(1, 3).allMatch(colNr -> {
                    Optional<PlayItem> playItemByPosition = getPlayItemByPosition(playItems, rowNr, colNr);
                    boolean present = playItemByPosition.filter(playItem -> playItem.getPlayer() == player).isPresent();
                    if (present) {
                        winnerPlayItems.add(playItemByPosition.get());
                    }
                    return present;
                }));

        if (win) {
            this.animateWinnerPlayItems(winnerPlayItems);
        }
        return win;
    }

    private boolean isVerticallyWin(Player player, List<PlayItem> playItems) {
        List<PlayItem> winnerPlayItems = new ArrayList<>();

        boolean win = IntStream.rangeClosed(1, 3)
                .boxed()
                .anyMatch(colNr -> IntStream.rangeClosed(1, 3).allMatch(rowNr -> {
                    Optional<PlayItem> playItemByPosition = getPlayItemByPosition(playItems, rowNr, colNr);
                    boolean present = playItemByPosition.filter(playItem -> playItem.getPlayer() == player).isPresent();
                    if (present) {
                        winnerPlayItems.add(playItemByPosition.get());
                    }
                    return present;
                }));

        if (win) {
            this.animateWinnerPlayItems(winnerPlayItems);
        }
        return win;
    }

    public List<PlayItem> extractAllImagesAsPlayItems(ViewGroup layout) {
        List<PlayItem> playItems = new ArrayList<>();
        int childCount = layout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (layout.getChildAt(i) instanceof ImageView) {
                playItems.add((PlayItem) layout.getChildAt(i));
            }
        }

        return playItems;
    }

    private static PlayItemPosition getPlayItemPositionByPlayItem(PlayItem playItem) {
        //TODO: implement regex check

        int x = Integer.valueOf(Character.toString(playItem.getTag().toString().charAt(1)));
        int y = Integer.valueOf(Character.toString(playItem.getTag().toString().charAt(3)));

        return new PlayItemPosition(x, y);
    }

    private static Optional<PlayItem> getPlayItemByPosition(List<PlayItem> playItems, int row, int col) {
        return playItems.stream()
                .filter(playItem -> playItem.getPlayItemPosition().getCol() == col && playItem.getPlayItemPosition().getRow() == row)
                .findAny();
    }

    private void animateWinnerPlayItems(List<PlayItem> winnerPlayItems) {
        winnerPlayItems.forEach(playItem -> {
            AlphaAnimation animation = new AlphaAnimation(0.1f, 1.5f);
            animation.setDuration(1000);
            animation.setStartOffset(4000);
            animation.willChangeBounds();
            animation.setFillAfter(false);
            playItem.startAnimation(animation);

        });
    }
}