package org.artjom.tictacttoe.extended;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GameService {

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

    public static PlayItemPosition getFreePlayItemPosition(List<PlayItemPosition> playItemPositions) {
        int[] x = new int[]{1, 2, 3};
        int[] y = new int[]{1, 2, 3};

        for (int yy : y) {
            for (int xx : x) {
                if (!playItemPositions.contains(new PlayItemPosition(xx, yy))) {
                    return new PlayItemPosition(xx, yy);
                }
            }
        }
        return null;
    }

    public static PlayItemPosition getFreeRandomPlayItemPosition() {
        //TODO: implement me
        return null;
    }

    public static PlayItemPosition getPlayItemPositionByPlayItem(PlayItem playItem) {
        //TODO: implement regex check

        int x = Integer.valueOf(Character.toString(playItem.getTag().toString().charAt(1)));
        int y = Integer.valueOf(Character.toString(playItem.getTag().toString().charAt(3)));

        return new PlayItemPosition(x, y);
    }

    public static void addImage(ImageView playItem, int drawableImageId) {
        playItem.setImageResource(drawableImageId);
    }


}
