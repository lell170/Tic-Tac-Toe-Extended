package org.artjom.tictacttoe.extended;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayItemService {

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

    public static boolean checkHorizontallyAndVertically(Board board, Player player) {
        //TODO: implement me:)
        return true;
    }

    public static boolean checkDiagonally(Player player, Board board) {
        List<PlayItem> playItems = board.getPlayItems();

        boolean diagonallyCheck = IntStream.rangeClosed(1, 3)
                .boxed()
                .allMatch(integer -> playItems.stream()
                        .anyMatch(playItem -> {
                            if (playItem.getPlayItemPosition() != null) {
                                boolean fromLeft = playItem.getPlayItemPosition().getRow() == integer &&
                                        playItem.getPlayItemPosition().getCol() == integer &&
                                        playItem.getPlayer() == player;

                                boolean fromRight = playItem.getPlayItemPosition().getRow() == integer &&
                                        playItem.getPlayItemPosition().getCol() == 4 - integer &&
                                        playItem.getPlayer() == player;

                                if (fromLeft || fromRight) {
                                    return true;
                                }
                            }
                            return false;
                        }));
        return diagonallyCheck;
    }
}

