package com.craps.myapplication.View.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.TMDBHelper;
import com.squareup.picasso.Picasso;


public class ActivityPoster extends AppCompatActivity {
    public static final String POSTERID="POSTERID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        Intent unIntent = getIntent();
        Bundle bundle = unIntent.getExtras();
        String posterId= bundle.getString(POSTERID);

        ImageView imageView = (ImageView)findViewById(R.id.poster_completo);
        Picasso.with(this)
                .load(TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W1280, posterId))
                .placeholder(R.drawable.loading)
                .error(R.drawable.noimage)
                .into(imageView)
        ;
    }
}
