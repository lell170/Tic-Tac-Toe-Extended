package org.artjom.tictacttoe.extended;

import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.stream.Collectors;
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
        putPlayItemToBoard(playItem, PlayItemState.USER);

        if (checkForWin(PlayItemState.USER)) {
            gameOver(PlayItemState.USER.name());
            disableAllfields();
        } else {
            computerTurn();
        }
    }

    private void computerTurn() {

        tryToWin();

        if (checkForWin(PlayItemState.COMPUTER)) {
            gameOver(PlayItemState.COMPUTER.name());
            disableAllfields();
        }
    }

    private void putPlayItemToBoard(PlayItem playItem, PlayItemState playItemState) {
        int playItemImageId;

        if (playItemState == PlayItemState.COMPUTER) {
            playItemImageId = computerPlayItemId;
        } else {
            playItemImageId = userPlayItemId;
        }

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

    private boolean checkForWin(PlayItemState playItemState) {

        return checkDiagonalFromLeft(playItems, playItemState) ||
                checkDiagonalFromRight(playItems, playItemState) ||
                GameService.checkHorizontallyAndVertically(playItems, playItemState);
    }

    private void tryToWin() {

        for (int i = 0; i <= 3; i++) {

            final int j = i;

            List<PlayItem> playItemsOfComputerLine = playItems.stream()
                    .filter(playItem -> playItem.getPlayItemState() == PlayItemState.COMPUTER && playItem.getPlayItemPosition()
                            .getRow() == j)
                    .collect(Collectors.toList());

            List<PlayItem> freePlayItemsInTheLine = playItems.stream()
                    .filter(playItem -> playItem.getPlayItemPosition().getRow() == j && playItem.getPlayItemState() == PlayItemState.EMPTY)
                    .collect(Collectors.toList());

            if (3 - freePlayItemsInTheLine.size() - playItemsOfComputerLine.size() == 0) {
                putPlayItemToBoard(freePlayItemsInTheLine.stream().findAny().get(), PlayItemState.COMPUTER);
                return;
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
