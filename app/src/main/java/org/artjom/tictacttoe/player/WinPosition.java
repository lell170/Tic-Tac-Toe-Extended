package org.artjom.tictacttoe.player;

import org.artjom.tictacttoe.playitem.PlayItem;

import java.util.Optional;

public interface WinPosition {

    Optional<PlayItem> getWinPosition();

}
