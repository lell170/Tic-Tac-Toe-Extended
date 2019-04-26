package org.artjom.tictacttoe.extended;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class PlayItem extends AppCompatImageView {

    private PlayItemPosition playItemPosition;

    private PlayItemState playItemState;

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

    public PlayItemState getPlayItemState() {
        return playItemState;
    }

    public void setPlayItemState(PlayItemState playItemState) {
        this.playItemState = playItemState;
    }
}
