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

    public final static String YETI_NAME = "Yeti";
    public final static String FOREST_MAN_NAME = "Forest Man";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Board
        ConstraintLayout playItemsLayout = findViewById(R.id.itemsLayout);
        board.setPlayItems(PlayItemService.getAllImagesForViewGroup(playItemsLayout));

        // initialize Players
        forestMan = new Player(FOREST_MAN_NAME, R.drawable.coffee_green);
        yeti = new Player(YETI_NAME, R.drawable.coffee_gray);

        // start positions will be initialized. Empty state and Positions based of current PlayItem case
        board.clearBoard();
    }

    // handle event on click on play item
    public void onPlayItemClick(View view) {
        // first user try to make move
        board.makeMove(forestMan, view, this);
        // second move is from yeti (computer)
        board.makeMove(yeti, view, this);
    }

    public void gameOver(String winner) {
        board.disableBoard();

        TextView textView = findViewById(R.id.resultTextView);
        textView.setText(winner + " has won!!!");

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
