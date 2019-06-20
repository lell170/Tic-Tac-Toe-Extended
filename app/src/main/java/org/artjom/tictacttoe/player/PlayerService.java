package org.artjom.tictacttoe.player;

import org.artjom.tictacttoe.MainActivity;
import org.artjom.tictacttoe.board.PlayItem;
import org.artjom.tictacttoe.board.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class PlayerService {

    public static PlayItem getBestPossibleMove(Player player, Board board) {

        // first check if player can win
        Optional<PlayItem> canYetiWinWithThisItem = getWinMoveIfPossible(MainActivity.YETI_NAME, board);
        if (canYetiWinWithThisItem.isPresent()) {
            return canYetiWinWithThisItem.get();
        }

        // then check if forest man can win and if true - prevent him
        Optional<PlayItem> canForestManWinWithThisItem = getWinMoveIfPossible(MainActivity.FOREST_MAN_NAME, board);
        if (canForestManWinWithThisItem.isPresent()) {
            return canForestManWinWithThisItem.get();
        }

        // or els random move
        return getRandomMove(board);

    }

    private static Optional<PlayItem> getWinMoveIfPossible(String playerName, Board board) {
        // try to find horizontally win position
        Optional<PlayItem> freeItemsInLine = findHorizontallyWinPosition(playerName, board);
        if (freeItemsInLine.isPresent()) {
            return freeItemsInLine;
        }

        Optional<PlayItem> freeItemsInColumn = findVerticallyWinPosition(playerName, board);
        if (freeItemsInColumn.isPresent()) {
            return freeItemsInColumn;
        }

        Optional<PlayItem> freeItemDiagonallyFromLeft = findDiagonallyWinPositionFromLeft(playerName, board);
        if (freeItemDiagonallyFromLeft.isPresent()) {
            return freeItemDiagonallyFromLeft;
        }

        Optional<PlayItem> freeItemDiagonallyFromRight = findDiagonallyWinPositionFromRight(playerName, board);
        if (freeItemDiagonallyFromRight.isPresent()) {
            return freeItemDiagonallyFromRight;
        }
        return Optional.empty();
    }

    private static Optional<PlayItem> findVerticallyWinPosition(String playerName, Board board) {
        for (int i = 1; i <= 3; i++) {
            final int colNumber = i;

            List<PlayItem> freeItemsInColumn = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayItemPosition().getCol() == colNumber && playItem.getPlayer() == null)
                    .collect(Collectors.toList());

            List<PlayItem> playerItemsInColumn = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayer() != null && playItem.getPlayer().getName().equals(playerName) && playItem
                            .getPlayItemPosition()
                            .getCol() == colNumber)
                    .collect(Collectors.toList());

            if (playerItemsInColumn.size() == 2 && freeItemsInColumn.size() == 1) {
                return freeItemsInColumn.stream().findAny();
            }
        }
        return Optional.empty();
    }

    private static Optional<PlayItem> findHorizontallyWinPosition(String playerName, Board board) {
        for (int i = 1; i <= 3; i++) {
            final int rowNumber = i;

            List<PlayItem> freeItemsInLine = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayItemPosition().getRow() == rowNumber && playItem.getPlayer() == null)
                    .collect(Collectors.toList());

            List<PlayItem> playerItemsInLine = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayer() != null && playItem.getPlayer().getName().equals(playerName) && playItem
                            .getPlayItemPosition()
                            .getRow() == rowNumber)
                    .collect(Collectors.toList());

            if (playerItemsInLine.size() == 2 && freeItemsInLine.size() == 1) {
                return freeItemsInLine.stream().findAny();
            }
        }
        return Optional.empty();
    }

    private static Optional<PlayItem> findDiagonallyWinPositionFromLeft(String playerName, Board board) {
        List<PlayItem> playerItemsFromLeft = new ArrayList<>();
        List<PlayItem> freeItemsFromLeft = new ArrayList<>();

        for (int i = 0; i <= 3; i++) {
            final int index = i;
            Optional<PlayItem> any = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayItemPosition().getCol() == index && playItem.getPlayItemPosition()
                            .getRow() == index && playItem.getPlayer() == null).findAny();
            any.ifPresent(freeItemsFromLeft::add);
        }

        for (int i = 0; i <= 3; i++) {
            final int index = i;
            Optional<PlayItem> any = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayer() != null && playItem.getPlayItemPosition().getCol() == index && playItem
                            .getPlayItemPosition()
                            .getRow() == index && playItem.getPlayer().getName().equals(playerName)).findAny();
            any.ifPresent(playerItemsFromLeft::add);
        }

        if (playerItemsFromLeft.size() == 2 && freeItemsFromLeft.size() == 1) {
            return freeItemsFromLeft.stream().findAny();
        }

        return Optional.empty();
    }

    private static Optional<PlayItem> findDiagonallyWinPositionFromRight(String playerName, Board board) {
        List<PlayItem> playerItemsFromRight = new ArrayList<>();
        List<PlayItem> freeItemsFromRight = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            final int index = i;
            Optional<PlayItem> any = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayItemPosition().getCol() == index && playItem.getPlayItemPosition()
                            .getRow() == (4 - index) && playItem.getPlayer() == null).findAny();
            any.ifPresent(freeItemsFromRight::add);
        }

        for (int i = 1; i <= 3; i++) {
            final int index = i;
            Optional<PlayItem> any = board.getPlayItems().stream()
                    .filter(playItem -> playItem.getPlayer() != null && playItem.getPlayItemPosition().getCol() == index && playItem
                            .getPlayItemPosition()
                            .getRow() == (4 - index) && playItem.getPlayer().getName().equals(playerName)).findAny();
            any.ifPresent(playerItemsFromRight::add);
        }

        if (playerItemsFromRight.size() == 2 && freeItemsFromRight.size() == 1) {
            return freeItemsFromRight.stream().findAny();
        }

        return Optional.empty();
    }

    private static PlayItem getRandomMove(Board board) {
        List<PlayItem> playItemsWithNoPosition = board.getPlayItems()
                .stream().filter(playItem -> playItem.getPlayer() == null)
                .collect(Collectors.toList());

        Random rand = new Random();
        return playItemsWithNoPosition.get(rand.nextInt(playItemsWithNoPosition.size()));

    }
}