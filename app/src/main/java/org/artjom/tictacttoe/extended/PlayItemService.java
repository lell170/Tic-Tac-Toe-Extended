package org.artjom.tictacttoe.extended;

import android.widget.ImageView;

//TODO: make as SingleTon?
public class PlayItemService {

    public static void addImage(ImageView playItem, int drawableImageId) {
        playItem.setImageResource(drawableImageId);
    }

    public static void removeImage(ImageView playItem) {
        playItem.setImageResource(0);
    }
}
