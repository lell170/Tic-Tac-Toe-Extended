package org.artjom.tictacttoe.extended;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ImageView image1 = findViewById(R.id.playItem1);

        image1.setVisibility(View.INVISIBLE);
    }

    public void click(View view) {
    }
}
