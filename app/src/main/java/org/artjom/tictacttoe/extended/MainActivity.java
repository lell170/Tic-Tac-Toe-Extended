package org.artjom.tictacttoe.extended;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<PlayItem> playItems = new ArrayList<>();

    private int idImagePlayItem1;
    private int idImagePlayItem2;

    boolean change = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout playItemsLayout = findViewById(R.id.itemsLayout);
        this.playItems = AppUtils.getAllImagesForViewGroup(playItemsLayout);

        this.idImagePlayItem1 = R.drawable.coffee_yellow_for_android;
        this.idImagePlayItem2 = R.drawable.coffee_green_for_android;
    }

    // test method...
    public void onClick(View view) {
        for (ImageView playItem : playItems) {
            PlayItemService.addImage(playItem, this.idImagePlayItem1);
        }
    }

    public void playItemClick(View playItem) {
        if(change) {
            PlayItemService.addImage((PlayItem) playItem, this.idImagePlayItem2);
            change = false;
        }
        else {
            PlayItemService.addImage((PlayItem) playItem, this.idImagePlayItem1);
            change = true;
        }
    }
}
