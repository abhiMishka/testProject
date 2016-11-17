package com.abhishek.testproject;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.abhishek.testproject.model.Story;
import com.bumptech.glide.Glide;

public class StoryDetailActivity extends AppCompatActivity {

    private Story story = new Story();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        Intent intent = getIntent();
        story = intent.getParcelableExtra(getString(R.string.obj));

        Log.i("testUrl","story : " +story.toString());


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(story.getTitle());

        loadBackdrop();
    }

    private void loadBackdrop() {
        final com.flaviofaria.kenburnsview.KenBurnsView imageView =
                (com.flaviofaria.kenburnsview.KenBurnsView) findViewById(R.id.backdrop);
        if(!story.getSi().isEmpty()) {
            Glide.with(this).load(story.getSi()).centerCrop().into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
