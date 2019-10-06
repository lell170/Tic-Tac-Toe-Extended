package org.artjom.tictacttoe.player;

import org.artjom.tictacttoe.MainActivity;
import org.artjom.tictacttoe.board.Board;
import org.artjom.tictacttoe.playitem.PlayItem;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerService {

    private static final Random rand = new Random();

    public static PlayItem getBestPossibleMove(final Board board) {
        // first check if gray cup (computer) can win next step
        final Optional<PlayItem> canGrayCupWin = getWinMoveIfPossible(MainActivity.GRAY_CUP, board);
        if (canGrayCupWin.isPresent()) {
            return canGrayCupWin.get();
        }

        // then check if green cup can win and if yes, prevent him or else make random move
        final Optional<PlayItem> canGreenCup = getWinMoveIfPossible(MainActivity.GREEN_CUP, board);
        return canGreenCup.orElseGet(() -> getRandomMove(board));
    }

    private static Optional<PlayItem> getWinMoveIfPossible(final String playerName, final Board board) {
        // try to find best win position

        final List<WinPosition> winPositionMethods = new ArrayList<>();

        winPositionMethods.add(() -> findHorizontallyWinPosition(playerName, board));
        winPositionMethods.add(() -> findVerticallyWinPosition(playerName, board));
        winPositionMethods.add(() -> findDiagonallyWinPositionFromLeft(playerName, board));
        winPositionMethods.add(() -> findDiagonallyWinPositionFromRight(playerName, board));

        Collections.shuffle(winPositionMethods);

        final Optional<WinPosition> anyWinPosition = winPositionMethods
                .stream().filter(winPosition -> winPosition.getWinPosition().isPresent())
                .findAny();

        if (anyWinPosition.isPresent()) {
            return anyWinPosition.get().getWinPosition();
        }

        return Optional.empty();
    }

    private static Optional<PlayItem> findVerticallyWinPosition(final String playerName, final Board board) {
        for (int i = 1; i <= 3; i++) {
            final int colNumber = i;

            final List<PlayItem> freeItemsInColumn = board.getPlayItems().stream()
                                                          .filter(playItem -> playItem.getPlayItemPosition().getCol() == colNumber && playItem.getPlayer() == null)
                                                          .collect(Collectors.toList());

            final List<PlayItem> playerItemsInColumn = board.getPlayItems().stream()
                                                            .filter(playItem -> playItem.getPlayer() != null && playItem.getPlayer().getName().equals(playerName) && playItem
                            .getPlayItemPosition()
                            .getCol() == colNumber)
                                                            .collect(Collectors.toList());

            if (playerItemsInColumn.size() == 2 && freeItemsInColumn.size() == 1) {
                return Optional.ofNullable(freeItemsInColumn.get(0));
            }
        }
        return Optional.empty();
    }

    private static Optional<PlayItem> findHorizontallyWinPosition(final String playerName, final Board board) {
        for (int i = 1; i <= 3; i++) {
            final int rowNumber = i;

            final List<PlayItem> freeItemsInLine = board.getPlayItems().stream()
                                                        .filter(playItem -> playItem.getPlayItemPosition().getRow() == rowNumber && playItem.getPlayer() == null)
                                                        .collect(Collectors.toList());

            final List<PlayItem> playerItemsInLine = board.getPlayItems().stream()
                                                          .filter(playItem -> playItem.getPlayer() != null && playItem.getPlayer().getName().equals(playerName) && playItem
                            .getPlayItemPosition()
                            .getRow() == rowNumber)
                                                          .collect(Collectors.toList());

            if (playerItemsInLine.size() == 2 && freeItemsInLine.size() == 1) {
                return Optional.ofNullable(freeItemsInLine.get(0));
            }
        }
        return Optional.empty();
    }

    private static Optional<PlayItem> findDiagonallyWinPositionFromLeft(final String playerName, final Board board) {
        final List<PlayItem> playerItemsFromLeft = new ArrayList<>();
        final List<PlayItem> freeItemsFromLeft = new ArrayList<>();

        for (int i = 0; i <= 3; i++) {
            final int index = i;
            final Optional<PlayItem> anyFreePlayItem = board.getPlayItems().stream()
                                                            .filter(playItem -> playItem.getPlayItemPosition().getCol() == index && playItem.getPlayItemPosition()
                            .getRow() == index && playItem.getPlayer() == null).findAny();
            anyFreePlayItem.ifPresent(freeItemsFromLeft::add);
        }

        for (int i = 0; i <= 3; i++) {
            final int index = i;
            final Optional<PlayItem> anyPlayerPlayItem = board.getPlayItems().stream()
                                                              .filter(playItem -> playItem.getPlayer() != null && playItem.getPlayItemPosition().getCol() == index && playItem
                            .getPlayItemPosition()
                            .getRow() == index && playItem.getPlayer().getName().equals(playerName)).findAny();
            anyPlayerPlayItem.ifPresent(playerItemsFromLeft::add);
        }

        if (playerItemsFromLeft.size() == 2 && freeItemsFromLeft.size() == 1) {
            return Optional.ofNullable(freeItemsFromLeft.get(0));
        }

        return Optional.empty();
    }

    private static Optional<PlayItem> findDiagonallyWinPositionFromRight(final String playerName, final Board board) {
        final List<PlayItem> playerItemsFromRight = new ArrayList<>();
        final List<PlayItem> freeItemsFromRight = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            final int index = i;
            final Optional<PlayItem> anyFreePlayItem = board.getPlayItems().stream()
                                                            .filter(playItem -> playItem.getPlayItemPosition().getCol() == index && playItem.getPlayItemPosition()
                            .getRow() == (4 - index) && playItem.getPlayer() == null).findAny();
            anyFreePlayItem.ifPresent(freeItemsFromRight::add);
        }

        for (int i = 1; i <= 3; i++) {
            final int index = i;
            final Optional<PlayItem> anyPlayerPlayItem = board.getPlayItems().stream()
                                                              .filter(playItem -> playItem.getPlayer() != null && playItem.getPlayItemPosition().getCol() == index && playItem
                            .getPlayItemPosition()
                            .getRow() == (4 - index) && playItem.getPlayer().getName().equals(playerName)).findAny();
            anyPlayerPlayItem.ifPresent(playerItemsFromRight::add);
        }

        if (playerItemsFromRight.size() == 2 && freeItemsFromRight.size() == 1) {
            return Optional.ofNullable(freeItemsFromRight.get(0));
        }

        return Optional.empty();
    }

    private static PlayItem getRandomMove(final Board board) {
        final List<PlayItem> freePlayItems = board.getPlayItems()
                                                  .stream().filter(playItem -> playItem.getPlayer() == null)
                                                  .collect(Collectors.toList());

        return freePlayItems.get(rand.nextInt(freePlayItems.size()));
    }
}