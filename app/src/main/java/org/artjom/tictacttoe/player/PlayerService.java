package org.artjom.tictacttoe.player;

import org.artjom.tictacttoe.board.PlayItem;
import org.artjom.tictacttoe.board.Board;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerService {

    public static PlayItem getBestPossibleMove(Player player, Board board) {

        //TODO: vertically is not implemented yet

        // first check if player can win
        Optional<PlayItem> canWinWithThisItem = getWinMoveIfPossible(player, board);
        if (canWinWithThisItem.isPresent()) {
            return canWinWithThisItem.get();
        } // if not then best possible move...
        else {
            Optional<PlayItem> possiblePreventMove = getPreventMove(player, board);
            return possiblePreventMove.orElseGet(() -> getRandomMove(board));
        }
    }

    private static Optional<PlayItem> getWinMoveIfPossible(Player player, Board board) {
        for (int i = 1; i <= 3; i++) {
            final int rowNumber = i;

            List<PlayItem> playerItemsInTheLine = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayer() == player && playItem.getPlayItemPosition().getRow() == rowNumber)
                    .collect(Collectors.toList());

            List<PlayItem> freePlayItemsInTheLine = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayItemPosition().getRow() == rowNumber && playItem.getPlayer() == null)
                    .collect(Collectors.toList());

            if (playerItemsInTheLine.size() == 2 && freePlayItemsInTheLine.size() == 1) {
                return freePlayItemsInTheLine.stream().findAny();
            }
        }
        return Optional.empty();
    }

    private static Optional<PlayItem> getGoodStartWithNewLine(Board board) {

        for (int i = 1; i <= 3; i++) {
            final int rowNumber = i;

            List<PlayItem> freePlayItemsInTheLine = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayItemPosition().getRow() == rowNumber && playItem.getPlayer() == null)
                    .collect(Collectors.toList());

            if (freePlayItemsInTheLine.size() == 3 || freePlayItemsInTheLine.size() == 2) {
                return freePlayItemsInTheLine.stream().findAny();
            }
        }
        return Optional.empty();
    }

    public static Optional<PlayItem> getPreventMove(Player player, Board board) {
        for (int i = 1; i <= 3; i++) {
            final int rowNumber = i;

            List<PlayItem> itemsFromAnotherPlayerInTheLine = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayer() != player && playItem.getPlayer() != null && playItem.getPlayItemPosition()
                            .getRow() == rowNumber)
                    .collect(Collectors.toList());

            List<PlayItem> freePlayItemsInTheLine = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayItemPosition().getRow() == rowNumber && playItem.getPlayer() == null)
                    .collect(Collectors.toList());

            if ((itemsFromAnotherPlayerInTheLine.size() == 2 && freePlayItemsInTheLine.size() == 1) || (itemsFromAnotherPlayerInTheLine
                    .size() == 1 && freePlayItemsInTheLine.size() > 1)) {
                return freePlayItemsInTheLine.stream().findAny();
            }
        }
        return Optional.empty();
    }


    public static PlayItem getRandomMove(Board board) {
        for (int i = 1; i <= 3; i++) {
            final int rowNumber = i;

            List<PlayItem> freePlayItemsInTheLine = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayItemPosition().getRow() == rowNumber && playItem.getPlayer() == null)
                    .collect(Collectors.toList());

            if (freePlayItemsInTheLine.size() != 0) {
                return freePlayItemsInTheLine.stream().findAny().get();
            }
        }
        return null;
    }
}