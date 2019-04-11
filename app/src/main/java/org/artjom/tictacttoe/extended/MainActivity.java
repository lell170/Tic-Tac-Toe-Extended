package org.artjom.tictacttoe.extended;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<PlayItem> playItems = new ArrayList<>();

    private int idImagePlayItem1;
    private int idImagePlayItem2;

    private List<PlayItemPosition> playItemPositions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout playItemsLayout = findViewById(R.id.itemsLayout);
        playItems = AppUtils.getAllImagesForViewGroup(playItemsLayout);

        idImagePlayItem1 = R.drawable.coffee_yellow_for_android;
        idImagePlayItem2 = R.drawable.coffee_green_for_android;
    }

    // test method...
    public void onClick(View view) {
        for (ImageView playItem : playItems) {
            PlayItemService.addImage(playItem, this.idImagePlayItem1);
        }
    }

    public void playItemClick(View view) {
        PlayItem playItem = (PlayItem) view;

        PlayItemPosition playItemPositionByPlayItem = AppUtils.getPlayItemPositionByPlayItem(playItem);
        playItem.setPlayItemPosition(playItemPositionByPlayItem);

        playItemPositions.add(playItemPositionByPlayItem);
        System.out.println("die Position des Elementen " + playItem.getPlayItemPosition());
    }

    private void computerGo() {
// TODO Comp. move
    }
}
