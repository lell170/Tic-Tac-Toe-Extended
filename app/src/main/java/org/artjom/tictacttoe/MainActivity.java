package org.artjom.tictacttoe;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
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
        final String resultText;

        if (winner.equals(NOBODY)) {
            resultText = "Draw game 1:1";
        } else {
            resultText = winner + " win";
        }

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, resultText, duration);
        toast.show();
    }

    public void onResetClick(View view) {
        finish();
        startActivity(getIntent());
    }
}
