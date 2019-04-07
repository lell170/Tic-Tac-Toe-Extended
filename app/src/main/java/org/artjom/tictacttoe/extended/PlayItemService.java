package org.artjom.tictacttoe.extended;

import android.view.View;
import android.widget.ImageView;

//TODO: make as SingleTon?
public class PlayItemService {

    public static void makeInvisible(ImageView imageView) {
        imageView.setVisibility(View.INVISIBLE);
    }

    public static void makeVisible(ImageView imageView) {
        imageView.setVisibility(View.VISIBLE);
    }

    public static void setDrawableImage(ImageView imageView, int drawableImageId) {
        imageView.setBackgroundResource(drawableImageId);
    }


}
