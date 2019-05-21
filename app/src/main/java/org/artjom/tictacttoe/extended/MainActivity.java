package org.artjom.tictacttoe.extended;

import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Board board = new Board();

    private Player forestMan;
    private Player yeti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Board
        ConstraintLayout playItemsLayout = findViewById(R.id.itemsLayout);
        board.setPlayItems(PlayItemService.getAllImagesForViewGroup(playItemsLayout));

        // initialize Players
        forestMan = new Player("Forest Man", R.drawable.coffee_green);
        yeti = new Player("Yeti", R.drawable.coffee_gray);

        // start positions will be initialized. Empty state and Positions based of current PlayItem case
        board.clearBoard();
    }

    // handle event on click on play item
    public void onPlayItemClick(View view) {
        PlayItem playItem = (PlayItem) view;
        board.putPlayItemToBoard(playItem, forestMan);
        //TODO: redundant code...
        if (PlayItemService.checkForWin(forestMan, board)) {
            gameOver(forestMan.getName());
            board.disableBoard();
        } else {
            PlayItem playItemForNextMove = PlayerService.getBestPossibleMove(yeti, board);
            board.putPlayItemToBoard(playItemForNextMove, yeti);
            if (PlayItemService.checkForWin(yeti, board)) {
                gameOver(yeti.getName());
                board.disableBoard();
            }
        }
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

    public void onResetClick(View view) {
        finish();
        startActivity(getIntent());
    }
}
