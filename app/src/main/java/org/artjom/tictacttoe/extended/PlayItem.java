package org.artjom.tictacttoe.extended;


import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class PlayItem extends AppCompatImageView {

    private boolean isSet;
    private int position;

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
