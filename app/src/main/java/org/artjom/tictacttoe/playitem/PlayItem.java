package org.artjom.tictacttoe.playitem;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import org.artjom.tictacttoe.player.Player;

public class PlayItem extends AppCompatImageView {

    private PlayItemPosition playItemPosition;

    private Player player;

    public PlayItem(final Context context) {
        super(context);
    }

    public PlayItem(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayItem(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlayItemPosition getPlayItemPosition() {
        return playItemPosition;
    }

    public void setPlayItemPosition(final PlayItemPosition playItemPosition) {
        this.playItemPosition = playItemPosition;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

}
