package org.artjom.tictacttoe.extended;

import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private List<PlayItem> playItems;

    private int userPlayItemId;
    private int computerPlayItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout playItemsLayout = findViewById(R.id.itemsLayout);
        playItems = GameService.getAllImagesForViewGroup(playItemsLayout);

        userPlayItemId = R.drawable.coffee_green;
        computerPlayItemId = R.drawable.coffee_gray;

        // start positions will be initialized. Empty state and Positions based of current PlayItem case
        this.initializeStartPositions();
    }

    public void playItemClick(View view) {
        PlayItem playItem = (PlayItem) view;
        putPlayItemToBoard(playItem, userPlayItemId, PlayItemState.USER);

        if (checkForWin(PlayItemState.USER)) {
            gameOver(PlayItemState.USER.name());
            disableAllfields();
        } else {
            computerTurn();
        }
    }

    private void computerTurn() {

/*        final PlayItemPosition freePlayItemPosition = GameService.getFreeRandomPlayItemPosition(playItems);

        if (freePlayItemPosition != null) {
            PlayItem playItem = playItems.stream()
                    .filter(plItm -> plItm
                            .getTag()
                            .toString()
                            .equals("x" + freePlayItemPosition.getRow() + "y" + freePlayItemPosition.getCol()))
                    .findAny()
                    .get();

            putPlayItemToBoard(playItem, computerPlayItemId, PlayItemState.COMPUTER);
        } */

        tryToWin(PlayItemState.COMPUTER);

        if (checkForWin(PlayItemState.COMPUTER)) {
            gameOver(PlayItemState.COMPUTER.name());
            disableAllfields();
        }
    }

    private void putPlayItemToBoard(PlayItem playItem, int playItemImageId, PlayItemState playItemState) {
        GameService.addImage(playItem, playItemImageId);
        PlayItemPosition playItemPositionByPlayItem = GameService.getPlayItemPositionByPlayItem(playItem);
        playItem.setPlayItemPosition(playItemPositionByPlayItem);
        playItem.setPlayItemState(playItemState);
    }

    //TODO: some ugly methods.... :-(
    private boolean checkDiagonalFromLeft(List<PlayItem> playItems, PlayItemState playItemState) {
        return IntStream.rangeClosed(1, 3)
                .boxed()
                .allMatch(integer -> playItems.stream()
                        .anyMatch(playItem -> {
                            if (playItem.getPlayItemPosition() != null) {
                                return playItem.getPlayItemPosition().getRow() == integer &&
                                        playItem.getPlayItemPosition().getCol() == integer &&
                                        playItem.getPlayItemState() == playItemState;
                            } else return false;
                        }));
    }

    private boolean checkDiagonalFromRight(List<PlayItem> playItems, PlayItemState playItemState) {
        return IntStream.rangeClosed(1, 3).boxed()
                .allMatch(integer -> playItems.stream()
                        .anyMatch(playItem -> {
                            if (playItem.getPlayItemPosition() != null) {
                                return playItem.getPlayItemPosition().getRow() == integer &&
                                        playItem.getPlayItemPosition().getCol() == (3 - integer + 1) &&
                                        playItem.getPlayItemState() == playItemState;
                            } else return false;
                        }));
    }

    private boolean checkHorizontally(List<PlayItem> playItems, PlayItemState whichItem) {
        for (int i = 1; i < 4; i++) {
            final int rowNumber = i;
            if (IntStream.rangeClosed(1, 3)
                    .boxed()
                    .allMatch(integer -> playItems.stream()
                            .anyMatch(playItem -> {
                                if (playItem.getPlayItemPosition() != null) {
                                    return playItem.getPlayItemPosition().getRow() == rowNumber &&
                                            playItem.getPlayItemPosition().getCol() == integer &&
                                            playItem.getPlayItemState() == whichItem;
                                } else return false;
                            }))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkVertically(List<PlayItem> playItems, PlayItemState whichItem) {
        for (int i = 1; i < 4; i++) {
            final int colNumber = i;
            if (IntStream.rangeClosed(1, 3)
                    .boxed()
                    .allMatch(integer -> playItems.stream()
                            .anyMatch(playItem -> {
                                if (playItem.getPlayItemPosition() != null) {
                                    return playItem.getPlayItemPosition().getRow() == integer &&
                                            playItem.getPlayItemPosition().getCol() == colNumber &&
                                            playItem.getPlayItemState() == whichItem;
                                } else return false;
                            }))) {
                return true;
            }
        }
        return false;
    }


    private void gameOver(String whoHasWin) {
        TextView textView = findViewById(R.id.resultTextView);
        textView.setText(whoHasWin + " has won!!!");

        final float startSize = 42; // Size in pixels
        final float endSize = 12;
        long animationDuration = 600; // Animation duration in ms

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        animator.setDuration(animationDuration);

        animator.addUpdateListener(valueAnimator -> {
            float animatedValue = (float) valueAnimator.getAnimatedValue();
            textView.setTextSize(animatedValue);
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        });

        animator.start();
    }

    public void onClickReset(View view) {
        finish();
        startActivity(getIntent());
    }

    private void disableAllfields() {
        for (PlayItem playItem : playItems) {
            playItem.setEnabled(false);
        }
    }

    private boolean checkForWin(PlayItemState whichItem) {

        return checkDiagonalFromLeft(playItems, whichItem) ||
                checkDiagonalFromRight(playItems, whichItem) ||
                checkHorizontally(playItems, whichItem) ||
                checkVertically(playItems, whichItem);
    }

    private void tryToWin(PlayItemState playItemState) {

        // count of play item positions of given type in all lines
        long countPlayItemTypesInLine = playItems.stream()
                .filter(playItem -> playItem.getPlayItemState() == playItemState && playItem.getPlayItemPosition().getRow() == 1)
                .count();

        // count of empty positions in all lines
        long countEmptyPositionsInLine = playItems.stream()
                .filter(playItem -> playItem.getPlayItemPosition().getRow() == 1 && playItem.getPlayItemState() == PlayItemState.EMPTY)
                .count();

        System.out.println("countPlayItemTypesInLine "+countPlayItemTypesInLine);
        System.out.println("countEmptyPositionsInLine "+countEmptyPositionsInLine);

        for (PlayItem playItem : playItems) {
            if (playItem.getPlayItemPosition() != null) {
                if (playItem.getPlayItemState() == playItemState) {

                }
            }
        }

    }

    private void tryToPrevent(PlayItemState playItemState, PlayItemState opponentItemType) {
        //TODO: implement me:)
    }

    private void initializeStartPositions() {
        for (PlayItem playItem : playItems) {
            playItem.setPlayItemPosition(GameService.getPlayItemPositionByPlayItem(playItem));
            playItem.setPlayItemState(PlayItemState.EMPTY);
        }
    }

}
