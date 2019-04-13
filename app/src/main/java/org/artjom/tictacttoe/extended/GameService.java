package org.artjom.tictacttoe.extended;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    public static PlayItemPosition getFreePlayItemPosition(List<PlayItem> playItems) {
        PlayItem playItemWithNoPosition = playItems.stream()
                .filter(playItem -> playItem.getPlayItemPosition() == null).findAny().orElse(null);

        if (playItemWithNoPosition != null) {
            return getPlayItemPositionByPlayItem(playItemWithNoPosition);
        }
        return null;
    }

    public static PlayItemPosition getFreeRandomPlayItemPosition(List<PlayItem> playItems) {
        List<PlayItem> playItemsWithNoPosition = playItems.stream()
                .filter(playItem -> playItem.getPlayItemPosition() == null).collect(Collectors.toList());

        if (!playItemsWithNoPosition.isEmpty()) {
            return getPlayItemPositionByPlayItem(playItemsWithNoPosition.stream()
                    .skip(playItemsWithNoPosition.isEmpty() ? 0 : new Random().nextInt(playItemsWithNoPosition.size()))
                    .findFirst().get());
        }
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
