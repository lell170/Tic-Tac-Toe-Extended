package org.artjom.tictacttoe.extended;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class PlayItem extends AppCompatImageView {

    private PlayItemPosition playItemPosition;

    private Player player;

    public PlayItem(Context context) {
        super(context);
    }

    public PlayItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlayItemPosition getPlayItemPosition() {
        return playItemPosition;
    }

    public void setPlayItemPosition(PlayItemPosition playItemPosition) {
        this.playItemPosition = playItemPosition;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "PlayItem{" +
                "playItemPosition=" + playItemPosition +
                ", player=" + player +
                '}';
    }
}
