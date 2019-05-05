package org.artjom.tictacttoe.extended;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    // TODO: activate it for simple play level
    public static PlayItemPosition getFreePlayItemPosition(List<PlayItem> playItems) {
        PlayItem playItemWithNoPosition = playItems.stream()
                .filter(playItem -> playItem.getPlayItemPosition() == null).findAny().orElse(null);

        if (playItemWithNoPosition != null) {
            return getPlayItemPositionByPlayItem(playItemWithNoPosition);
        }
        return null;
    }

    // TODO: activate it for simple play level
    public static PlayItemPosition getFreeRandomPlayItemPosition(List<PlayItem> playItems) {
        List<PlayItem> playItemsWithNoPosition = playItems.stream()
                .filter(playItem -> playItem.getPlayItemPosition() == null).collect(Collectors.toList());

        if (!playItemsWithNoPosition.isEmpty()) {
            return getPlayItemPositionByPlayItem(Objects.requireNonNull(playItemsWithNoPosition.stream()
                    .skip(new Random().nextInt(playItemsWithNoPosition.size()))
                    .findFirst().orElse(null)));
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

    public static boolean checkHorizontallyAndVertically(List<PlayItem> playItems, PlayItemState playItemState) {

        boolean horizontallyAndVerticallyCheck =
                IntStream.rangeClosed(1, 3).boxed()
                        .anyMatch(outerIterationNumber -> IntStream.rangeClosed(1, 3).boxed()
                                .allMatch(innerIterationNumber -> {
                                    boolean horizontally = playItems.stream()
                                            .anyMatch(playItem -> playItem.getPlayItemState() == playItemState && playItem
                                                    .getPlayItemPosition().getRow() == outerIterationNumber && playItem
                                                    .getPlayItemPosition()
                                                    .getCol() == innerIterationNumber);

                                    boolean vertically = playItems.stream()
                                            .anyMatch(playItem -> playItem.getPlayItemState() == playItemState && playItem
                                                    .getPlayItemPosition().getCol() == outerIterationNumber && playItem
                                                    .getPlayItemPosition()
                                                    .getRow() == innerIterationNumber);

                                    if (horizontally || vertically) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }));

        return horizontallyAndVerticallyCheck;
    }
}
