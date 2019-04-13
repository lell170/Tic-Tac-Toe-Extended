package org.artjom.tictacttoe.extended;


import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class PlayItem extends AppCompatImageView {

    private boolean isSet;
    private PlayItemPosition playItemPosition;

    private MainActivity.WHICH_ITEM whichItem;

    public PlayItem(Context context) {
        super(context);
    }

    public PlayItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    public PlayItemPosition getPlayItemPosition() {
        return playItemPosition;
    }

    public void setPlayItemPosition(PlayItemPosition playItemPosition) {
        this.playItemPosition = playItemPosition;
    }

    public MainActivity.WHICH_ITEM getWhichItem() {
        return whichItem;
    }

    public void setWhichItem(MainActivity.WHICH_ITEM whichItem) {
        this.whichItem = whichItem;
    }
}
