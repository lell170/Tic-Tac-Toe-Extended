package org.artjom.tictacttoe.extended;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.*;
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

    public static Optional<PlayItem> getPlayItemByPosition(List<PlayItem> playItems, int row, int col) {
        Optional<PlayItem> optionalPlayItem = playItems.stream()
                .filter(playItem -> playItem.getPlayItemPosition().getCol() == col && playItem.getPlayItemPosition().getRow() == row)
                .findAny();

        return optionalPlayItem;
    }

    public static void checkForWin(Player player, Board board, MainActivity mainActivity) {
        List<PlayItem> playItems = board.getPlayItems();

        if (isVertically(player, playItems)) {
            mainActivity.gameOver(player.getName());
            System.out.println("win vertically");
            return;
        }

        if (isHorizontally(player, playItems)) {
            mainActivity.gameOver(player.getName());
            System.out.println("win horizontally");
            return;
        }

        if (isDiagonallyCheck(player, playItems)) {
            mainActivity.gameOver(player.getName());
            System.out.println("win diagonally");
            return;
        }
    }

    private static boolean isDiagonallyCheck(Player player, List<PlayItem> playItems) {
        boolean fromLeft = IntStream.rangeClosed(1, 3)
                .boxed()
                .allMatch(integer -> playItems.stream()
                        .anyMatch(playItem -> {
                            if (playItem.getPlayItemPosition() != null) {
                                return playItem.getPlayItemPosition().getRow() == integer &&
                                        playItem.getPlayItemPosition().getCol() == integer &&
                                        playItem.getPlayer() == player;


                            }
                            return false;
                        }));

        boolean fromRight = IntStream.rangeClosed(1, 3)
                .boxed()
                .allMatch(integer -> playItems.stream()
                        .anyMatch(playItem -> {
                            if (playItem.getPlayItemPosition() != null) {

                                return playItem.getPlayItemPosition().getRow() == integer &&
                                        playItem.getPlayItemPosition().getCol() == 4 - integer &&
                                        playItem.getPlayer() == player;

                            }
                            return false;
                        }));

        return fromRight || fromLeft;
    }

    private static boolean isHorizontally(Player player, List<PlayItem> playItems) {
        return IntStream.rangeClosed(1, 3)
                .boxed()
                .anyMatch(rowNr -> IntStream.rangeClosed(1, 3).allMatch(colNr -> {
                    Optional<PlayItem> playItemByPosition = getPlayItemByPosition(playItems, rowNr, colNr);
                    if (playItemByPosition.isPresent()) {
                        if (playItemByPosition.get().getPlayer() == player) {
                            return true;
                        }
                    }
                    return false;
                }));
    }

    private static boolean isVertically(Player player, List<PlayItem> playItems) {
        return IntStream.rangeClosed(1, 3)
                .boxed()
                .anyMatch(colNr -> IntStream.rangeClosed(1, 3).allMatch(rowNr -> {
                    Optional<PlayItem> playItemByPosition = getPlayItemByPosition(playItems, rowNr, colNr);
                    if (playItemByPosition.isPresent()) {
                        if (playItemByPosition.get().getPlayer() == player) {
                            return true;
                        }
                    }
                    return false;
                }));
    }
}

