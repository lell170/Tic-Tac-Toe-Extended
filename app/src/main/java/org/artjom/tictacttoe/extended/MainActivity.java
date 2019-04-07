package org.artjom.tictacttoe.extended;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ImageView> playItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ConstraintLayout playItemsLayout = findViewById(R.id.itemsLayout);

        this.setPlayItems(AppUtils.getAllImagesForViewGroup(playItemsLayout));

        for (ImageView playItem : this.getPlayItems()) {
            PlayItemService.makeInvisible(playItem);
        }
    }

    public List<ImageView> getPlayItems() {
        return playItems;
    }

    public void setPlayItems(List<ImageView> playItems) {
        this.playItems = playItems;
    }

    // test
    public void onClick(View view) {
        for (ImageView playItem : playItems) {
            PlayItemService.makeVisible(playItem);
            PlayItemService.setDrawableImage(playItem, R.drawable.coffee_gradient);
        }
    }
}
