package org.artjom.tictacttoe.playitem;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.util.List;
import java.util.Optional;

public class PlayItemService {

    public static PlayItemPosition getPlayItemPositionByPlayItem(PlayItem playItem) {
        //TODO: implement regex check

        int x = Integer.valueOf(Character.toString(playItem.getTag().toString().charAt(1)));
        int y = Integer.valueOf(Character.toString(playItem.getTag().toString().charAt(3)));

        return new PlayItemPosition(x, y);
    }

    public static Optional<PlayItem> getPlayItemByPosition(List<PlayItem> playItems, int row, int col) {
        return playItems.stream()
                .filter(playItem -> playItem.getPlayItemPosition().getCol() == col && playItem.getPlayItemPosition().getRow() == row)
                .findAny();
    }

    public static void animateWinnerPlayItems(List<PlayItem> winnerPlayItems) {
        winnerPlayItems.forEach(playItem -> {
            Animation animation = new AlphaAnimation(0.1f, 1.0f);
            animation.setDuration(150);
            animation.setRepeatCount(15);
            animation.willChangeBounds();
            animation.setFillAfter(false);
            playItem.startAnimation(animation);
        });
    }
}
