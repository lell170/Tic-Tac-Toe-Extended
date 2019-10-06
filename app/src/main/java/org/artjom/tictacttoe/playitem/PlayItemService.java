package org.artjom.tictacttoe.playitem;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.util.List;
import java.util.Optional;

public class PlayItemService {

    public static PlayItemPosition getPlayItemPositionByPlayItem(final PlayItem playItem) {
        //TODO: implement regex check

        final int x = Integer.valueOf(Character.toString(playItem.getTag().toString().charAt(1)));
        final int y = Integer.valueOf(Character.toString(playItem.getTag().toString().charAt(3)));

        return new PlayItemPosition(x, y);
    }

    public static Optional<PlayItem> getPlayItemByPosition(final List<PlayItem> playItems, final int row, final int col) {
        return playItems.stream()
                .filter(playItem -> playItem.getPlayItemPosition().getCol() == col && playItem.getPlayItemPosition().getRow() == row)
                .findAny();
    }

    public static void animateWinnerPlayItems(final List<PlayItem> winnerPlayItems) {
        winnerPlayItems.forEach(playItem -> {
            final Animation animation = new AlphaAnimation(0.1f, 1.0f);
            animation.setDuration(150);
            animation.setRepeatCount(15);
            animation.willChangeBounds();
            animation.setFillAfter(false);
            playItem.startAnimation(animation);
        });
    }
}
