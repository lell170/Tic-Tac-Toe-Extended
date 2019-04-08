package org.artjom.tictacttoe.extended;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    // TODO: refactor this ugly method...
    public static List<PlayItem> getAllImagesForViewGroup(ViewGroup layout) {
        List<PlayItem> imageViews = new ArrayList<>();
        int childCount = layout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (layout.getChildAt(i) instanceof ImageView) {
                imageViews.add((PlayItem) layout.getChildAt(i));
            }
        }
        return imageViews;
    }

}
