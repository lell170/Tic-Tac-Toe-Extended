package org.artjom.tictacttoe;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import org.artjom.tictacttoe.board.Board;
import org.artjom.tictacttoe.board.BoardService;
import org.artjom.tictacttoe.playitem.PlayItem;
import org.artjom.tictacttoe.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final Board board = new Board();

    private Player greenCup;
    private Player grayCup;

    public static final String GREEN_CUP = "Green cup";
    public static final String GRAY_CUP = "Gray cup";
    public static final String NOBODY = "Nobody";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize Board
        final ConstraintLayout playItemsLayout = findViewById(R.id.itemsLayout);
        board.setPlayItems(this.extractAllImagesAsPlayItems(playItemsLayout));

        // initialize Players
        greenCup = new Player(GREEN_CUP, R.drawable.coffee_green);
        grayCup = new Player(GRAY_CUP, R.drawable.coffee_gray);

        // start positions will be initialized. Empty state and Positions based of current PlayItem case
        BoardService.clearBoard(board);
    }

    // handle event on click on play item
    public void onPlayItemClick(final View view) {
        // first user try to make move
        BoardService.makeMove(board, greenCup, view, this);
        // second move from grayCup (computer)
        if (board.getPlayItems().stream().allMatch(playItem -> playItem.getPlayer() != null)) {
            gameOver(NOBODY);
        } else {
            BoardService.makeMove(board, grayCup, view, this);
        }
    }

    public void gameOver(final String winner) {
        BoardService.disableBoard(board);
        final String resultText;

        if (winner.equals(NOBODY)) {
            resultText = "Draw game 1:1";
        } else {
            resultText = winner + " win";
        }

        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_LONG;
        final Toast toast = Toast.makeText(context, resultText, duration);
        toast.show();
    }

    private List<PlayItem> extractAllImagesAsPlayItems(final ViewGroup layout) {
        final List<PlayItem> playItems = new ArrayList<>();
        final int childCount = layout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (layout.getChildAt(i) instanceof ImageView) {
                playItems.add((PlayItem) layout.getChildAt(i));
            }
        }

        return playItems;
    }

    public void onResetClick(final View view) {
        finish();
        startActivity(getIntent());
    }
}
