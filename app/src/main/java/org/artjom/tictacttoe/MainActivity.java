package org.artjom.tictacttoe;

import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import org.artjom.tictacttoe.board.Board;
import org.artjom.tictacttoe.player.Player;

public class MainActivity extends AppCompatActivity {

    private Board board = new Board();

    private Player greenCup;
    private Player grayCup;

    public final static String GREEN_CUP = "Green cup";
    public final static String GRAY_CUP = "Gray cup";
    public final static String NOBODY = "Nobody";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Board
        ConstraintLayout playItemsLayout = findViewById(R.id.itemsLayout);
        board.setPlayItems(board.extractAllImagesAsPlayItems(playItemsLayout));

        // initialize Players
        greenCup = new Player(GREEN_CUP, R.drawable.coffee_green);
        grayCup = new Player(GRAY_CUP, R.drawable.coffee_gray);

        // start positions will be initialized. Empty state and Positions based of current PlayItem case
        board.clearBoard();
    }

    // handle event on click on play item
    public void onPlayItemClick(View view) {
        // first user try to make move
        board.makeMove(greenCup, view, this);
        // second move from grayCup (computer)
        if (board.getPlayItems().stream().allMatch(playItem -> playItem.getPlayer() != null)) {
            gameOver(NOBODY);
        } else {
            board.makeMove(grayCup, view, this);
        }
    }

    public void gameOver(String winner) {
        board.disableBoard();

        //TODO: optimize win message
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
