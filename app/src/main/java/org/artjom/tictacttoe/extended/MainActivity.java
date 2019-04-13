package org.artjom.tictacttoe.extended;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private List<PlayItem> playItems;

    private int userPlayItemId;
    private int computerPlayItemId;

    private List<PlayItemPosition> playItemPositions = new ArrayList<>();

    public enum WHICH_ITEM {COMPUTER_ITEM, USER_ITEM}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout playItemsLayout = findViewById(R.id.itemsLayout);
        playItems = GameService.getAllImagesForViewGroup(playItemsLayout);

        userPlayItemId = R.drawable.coffee_yellow_for_android;
        computerPlayItemId = R.drawable.coffee_green_for_android;
    }

    public void playItemClick(View view) {
        PlayItem playItem = (PlayItem) view;
        putPlayItemToBoard(playItem, userPlayItemId, WHICH_ITEM.USER_ITEM);

        if (checkDiagonalFromLeft(playItems, WHICH_ITEM.USER_ITEM) || checkDiagonalFromRight(playItems, WHICH_ITEM.USER_ITEM) || checkHorizontally(playItems, WHICH_ITEM.USER_ITEM) || checkVertically(playItems, WHICH_ITEM.USER_ITEM)) {
            gameOver();
        } else {
            computerGoGoGo();
        }
    }

    private void computerGoGoGo() {
        PlayItemPosition freePlayItemPosition = GameService.getFreePlayItemPosition(this.playItemPositions);

        if (freePlayItemPosition != null) {
            PlayItem playItem = playItems.stream()
                    .filter(plItm -> plItm
                            .getTag()
                            .toString()
                            .equals("x" + freePlayItemPosition.getRow() + "y" + freePlayItemPosition.getCol()))
                    .findAny()
                    .get();

            putPlayItemToBoard(playItem, computerPlayItemId, WHICH_ITEM.COMPUTER_ITEM);
        }
    }

    private void putPlayItemToBoard(PlayItem playItem, int playItemImageId, WHICH_ITEM whichItem) {
        GameService.addImage(playItem, playItemImageId);
        PlayItemPosition playItemPositionByPlayItem = GameService.getPlayItemPositionByPlayItem(playItem);
        playItem.setPlayItemPosition(playItemPositionByPlayItem);
        playItem.setWhichItem(whichItem);
        playItemPositions.add(playItemPositionByPlayItem);
    }

    //TODO: some ugly methods.... :-(
    private boolean checkDiagonalFromLeft(List<PlayItem> playItems, WHICH_ITEM whichItem) {
        return IntStream.rangeClosed(1, 3)
                .boxed()
                .allMatch(integer -> playItems.stream()
                        .anyMatch(playItem -> {
                            if (playItem.getPlayItemPosition() != null) {
                                return playItem.getPlayItemPosition().getRow() == integer &&
                                        playItem.getPlayItemPosition().getCol() == integer &&
                                        playItem.getWhichItem() == whichItem;
                            } else return false;
                        }));
    }

    private boolean checkDiagonalFromRight(List<PlayItem> playItems, WHICH_ITEM whichItem) {
        return IntStream.rangeClosed(1, 3).boxed()
                .allMatch(integer -> playItems.stream()
                        .anyMatch(playItem -> {
                            if (playItem.getPlayItemPosition() != null) {
                                return playItem.getPlayItemPosition().getRow() == integer &&
                                        playItem.getPlayItemPosition().getCol() == (3 - integer + 1) &&
                                        playItem.getWhichItem() == whichItem;
                            } else return false;
                        }));
    }

    private boolean checkHorizontally(List<PlayItem> playItems, WHICH_ITEM whichItem) {
        for (int i = 1; i < 4; i++) {
            final int rowNumber = i;
            if (IntStream.rangeClosed(1, 3)
                    .boxed()
                    .allMatch(integer -> playItems.stream()
                            .anyMatch(playItem -> {
                                if (playItem.getPlayItemPosition() != null) {
                                    return playItem.getPlayItemPosition().getRow() == rowNumber &&
                                            playItem.getPlayItemPosition().getCol() == integer &&
                                            playItem.getWhichItem() == whichItem;
                                } else return false;
                            }))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkVertically(List<PlayItem> playItems, WHICH_ITEM whichItem) {
        for (int i = 1; i < 4; i++) {
            final int colNumber = i;
            if (IntStream.rangeClosed(1, 3)
                    .boxed()
                    .allMatch(integer -> playItems.stream()
                            .anyMatch(playItem -> {
                                if (playItem.getPlayItemPosition() != null) {
                                    return playItem.getPlayItemPosition().getRow() == integer &&
                                            playItem.getPlayItemPosition().getCol() == colNumber &&
                                            playItem.getWhichItem() == whichItem;
                                } else return false;
                            }))) {
                return true;
            }
        }
        return false;
    }


    private void gameOver() {
        System.out.println("Game over");

    }
}
