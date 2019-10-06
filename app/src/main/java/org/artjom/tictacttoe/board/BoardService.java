package org.artjom.tictacttoe.board;

import android.view.View;
import org.artjom.tictacttoe.MainActivity;
import org.artjom.tictacttoe.player.Player;
import org.artjom.tictacttoe.player.PlayerService;
import org.artjom.tictacttoe.playitem.PlayItem;
import org.artjom.tictacttoe.playitem.PlayItemPosition;
import org.artjom.tictacttoe.playitem.PlayItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.artjom.tictacttoe.MainActivity.GRAY_CUP;

public class BoardService {

    public static void clearBoard(final Board board) {
        board.setDisabled(false);
        for (final PlayItem playItem : board.getPlayItems()) {
            playItem.setPlayItemPosition(PlayItemService.getPlayItemPositionByPlayItem(playItem));
            playItem.setPlayer(null);
        }
    }

    public static void disableBoard(final Board board) {
        // disable board
        board.setDisabled(true);
        // disable all play items
        board.getPlayItems().forEach(playItem -> playItem.setEnabled(false));
    }

    private static void putPlayItemToBoard(final PlayItem playItem, final Player player) {
        playItem.setImageResource(player.getImageId());

        playItem.setTranslationY(-3000);
        if (player.getName().equals(GRAY_CUP)) {
            playItem.animate().setStartDelay(500);
        }
        playItem.animate().translationYBy(3000).setDuration(500);

        final PlayItemPosition playItemPositionByPlayItem = PlayItemService.getPlayItemPositionByPlayItem(playItem);
        playItem.setPlayItemPosition(playItemPositionByPlayItem);
        playItem.setPlayer(player);
    }

    public static void makeMove(final Board board, final Player player, final View view, final MainActivity mainActivity) {
        if (!board.isDisabled()) {
            final PlayItem playItem;
            if (player.getName().equals(GRAY_CUP)) {
                playItem = PlayerService.getBestPossibleMove(board);
            } else {
                playItem = (PlayItem) view;
            }
            putPlayItemToBoard(playItem, player);
            playItem.setEnabled(false);
            checkForWin(player, board, mainActivity);
        }
    }

    private static void checkForWin(final Player player, final Board board, final MainActivity mainActivity) {
        final List<PlayItem> playItems = board.getPlayItems();

        if (isVerticallyWin(player, playItems)) {
            mainActivity.gameOver(player.getName());
        }

        if (isHorizontallyWin(player, playItems)) {
            mainActivity.gameOver(player.getName());
        }

        if (isDiagonallyWin(player, playItems)) {
            mainActivity.gameOver(player.getName());
        }
    }

    private static boolean isDiagonallyWin(final Player player, final List<PlayItem> playItems) {
        final List<PlayItem> winnerPlayItems = new ArrayList<>();

        final boolean fromLeft = IntStream.rangeClosed(1, 3)
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

        final boolean fromRight = IntStream.rangeClosed(1, 3)
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

        if (fromLeft) {
            for (int i = 1; i <= 3; i++) {
                winnerPlayItems.add(PlayItemService.getPlayItemByPosition(playItems, i, i).get());
            }
            PlayItemService.animateWinnerPlayItems(winnerPlayItems);
            return true;
        } else if (fromRight) {
            for (int i = 1; i <= 3; i++) {
                winnerPlayItems.add(PlayItemService.getPlayItemByPosition(playItems, i, 4 - i).get());
            }
            PlayItemService.animateWinnerPlayItems(winnerPlayItems);
            return true;
        }

        return false;
    }

    private static boolean isHorizontallyWin(final Player player, final List<PlayItem> playItems) {
        final List<PlayItem> winnerPlayItems = new ArrayList<>();

        final boolean win = IntStream.rangeClosed(1, 3)
                                     .boxed()
                                     .anyMatch(rowNr -> IntStream.rangeClosed(1, 3).allMatch(colNr -> {
                    final Optional<PlayItem> playItemByPosition = PlayItemService.getPlayItemByPosition(playItems, rowNr, colNr);
                    final boolean present = playItemByPosition.filter(playItem -> playItem.getPlayer() == player).isPresent();
                    if (present) {
                        winnerPlayItems.add(playItemByPosition.get());
                    }
                    return present;
                }));

        if (win) {
            PlayItemService.animateWinnerPlayItems(winnerPlayItems);
        }
        return win;
    }

    private static boolean isVerticallyWin(final Player player, final List<PlayItem> playItems) {
        final List<PlayItem> winnerPlayItems = new ArrayList<>();

        final boolean win = IntStream.rangeClosed(1, 3)
                                     .boxed()
                                     .anyMatch(colNr -> IntStream.rangeClosed(1, 3).allMatch(rowNr -> {
                    final Optional<PlayItem> playItemByPosition = PlayItemService.getPlayItemByPosition(playItems, rowNr, colNr);
                    final boolean present = playItemByPosition.filter(playItem -> playItem.getPlayer() == player).isPresent();
                    if (present) {
                        winnerPlayItems.add(playItemByPosition.get());
                    }
                    return present;
                }));

        if (win) {
            PlayItemService.animateWinnerPlayItems(winnerPlayItems);
        }
        return win;
    }

}
