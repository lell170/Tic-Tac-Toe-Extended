package org.artjom.tictacttoe.board;

import android.view.ViewGroup;
import android.widget.ImageView;
import org.artjom.tictacttoe.MainActivity;
import org.artjom.tictacttoe.player.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PlayItemService {

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

    public static PlayItemPosition getPlayItemPositionByPlayItem(PlayItem playItem) {
        //TODO: implement regex check

        int x = Integer.valueOf(Character.toString(playItem.getTag().toString().charAt(1)));
        int y = Integer.valueOf(Character.toString(playItem.getTag().toString().charAt(3)));

        return new PlayItemPosition(x, y);
    }

    private static Optional<PlayItem> getPlayItemByPosition(List<PlayItem> playItems, int row, int col) {
        return playItems.stream()
                .filter(playItem -> playItem.getPlayItemPosition().getCol() == col && playItem.getPlayItemPosition().getRow() == row)
                .findAny();
    }

    public static void checkForWin(Player player, Board board, MainActivity mainActivity) {
        List<PlayItem> playItems = board.getPlayItems();

        if (isVerticallyWin(player, playItems)) {
            mainActivity.gameOver(player.getName());
            System.out.println("win vertically");
            return;
        }

        if (isHorizontallyWin(player, playItems)) {
            mainActivity.gameOver(player.getName());
            System.out.println("win horizontally");
            return;
        }

        if (isDiagonallyWin(player, playItems)) {
            mainActivity.gameOver(player.getName());
            System.out.println("win diagonally");
        }
    }

    private static boolean isDiagonallyWin(Player player, List<PlayItem> playItems) {
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

    private static boolean isHorizontallyWin(Player player, List<PlayItem> playItems) {
        return IntStream.rangeClosed(1, 3)
                .boxed()
                .anyMatch(rowNr -> IntStream.rangeClosed(1, 3).allMatch(colNr -> {
                    Optional<PlayItem> playItemByPosition = getPlayItemByPosition(playItems, rowNr, colNr);
                    return playItemByPosition.filter(playItem -> playItem.getPlayer() == player).isPresent();
                }));
    }

    private static boolean isVerticallyWin(Player player, List<PlayItem> playItems) {
        return IntStream.rangeClosed(1, 3)
                .boxed()
                .anyMatch(colNr -> IntStream.rangeClosed(1, 3).allMatch(rowNr -> {
                    Optional<PlayItem> playItemByPosition = getPlayItemByPosition(playItems, rowNr, colNr);
                    return playItemByPosition.filter(playItem -> playItem.getPlayer() == player).isPresent();
                }));
    }
}

