package com.abhishek.testproject;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.testproject.model.Story;
import com.abhishek.testproject.model.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;

import java.util.ArrayList;

import static com.abhishek.testproject.UtilFunctions.TYPE_ARRAY_LIST_STORIES;
import static com.abhishek.testproject.UtilFunctions.TYPE_ARRAY_LIST_USERS;

public class StoryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Story story = new Story();

    private User user = new User();

    private TextView userNameTv,handleTv,articleDescription;
    private FloatingActionButton followFab,likeFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        userNameTv = (TextView) findViewById(R.id.userNameTv);
        handleTv = (TextView) findViewById(R.id.userHandleTv);
        articleDescription = (TextView) findViewById(R.id.articleDescriptionTv);

        followFab = (FloatingActionButton) findViewById(R.id.followFab);
        likeFab = (FloatingActionButton) findViewById(R.id.likeFab);

        Intent intent = getIntent();
        story = intent.getParcelableExtra(getString(R.string.obj));

        fetchUserData();

        userNameTv.setText(user.getUsername());
        handleTv.setText(user.getHandle());
        articleDescription.setText(story.getDescription());

        followFab.setOnClickListener(this);
        likeFab.setOnClickListener(this);

        if(user.isIs_following()){
            followFab.setImageResource(R.drawable.ic_star_rate_white);
        }else{
            followFab.setImageResource(R.drawable.ic_star_border_white);
        }


        if(story.isLike_flag()){
            likeFab.setImageResource(R.drawable.ic_like_filled);
        }else{
            likeFab.setImageResource(R.drawable.ic_like);
        }



        Log.i("testUrl","story : " +story.toString()
        +"  user : " +user);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(story.getTitle());

        loadBackdrop();
    }

    ArrayList<User> userList;

    private void fetchUserData() {

        userList = UtilFunctions.objectify
                (UtilFunctions.getData(Constants.KEY_USERS),
                        TYPE_ARRAY_LIST_USERS);

        if(userList==null) {
            userList = (ArrayList<User>) UtilFunctions.readUsersFromJson(UtilFunctions.loadJSONFromAsset());
            getUser(userList);
            UtilFunctions.saveUsersInSharedPreference(UtilFunctions.readUsersFromJson(UtilFunctions.loadJSONFromAsset()));
        }else{
            getUser(userList);
        }
    }

    private void getUser(ArrayList<User> userList) {
        if(userList.get(0).getId().equals(story.getDb())){
            user = userList.get(0);
        }else{
            user = userList.get(1);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.followFab:
                if(user.isIs_following()){
                    updateUserValueAndSave(false);
                    followFab.setImageResource(R.drawable.ic_star_border_white);
                    showToast("You unfollowed " +user.getUsername());

                }else{
                    updateUserValueAndSave(true);
                    followFab.setImageResource(R.drawable.ic_star_rate_white);
                    showToast("You are now following " +user.getUsername());
                }
                break;

            case R.id.likeFab:
                if(story.isLike_flag()){
                    updateStoryLikeValueAndSave(false);
                    likeFab.setImageResource(R.drawable.ic_like);
                    showToast("You dislike this story");
                }else{
                    updateStoryLikeValueAndSave(true);
                    likeFab.setImageResource(R.drawable.ic_like_filled);
                    showToast("You like this story");
                }
                break;

            default:
                break;
        }
    }

    private void updateStoryLikeValueAndSave(boolean b) {
        story.setLike_flag(b);

        ArrayList<Story> storyList = UtilFunctions.objectify
                (UtilFunctions.getData(Constants.KEY_STORIES),
                        TYPE_ARRAY_LIST_STORIES);
        int count = 0;
        for(Story st : storyList){
            Log.i("testEqual","st.getDv  " +st.getDescription() +
            "  ,  storyLISt.getDb : " +storyList.get(count).getDescription());
            if(st.getId().equals(story.getId())){
                storyList.get(count).setLike_flag(b);
                UtilFunctions.saveStoriesInSharedPreference(storyList);
                break;
            }
            count++;
        }

    }

    private void updateUserValueAndSave(boolean b) {
        user.setIs_following(b);
        if(userList.get(0).getId().equals(user.getId())){
            userList.get(0).setIs_following(b);
        }else{
            userList.get(1).setIs_following(b);
        }

        UtilFunctions.saveUsersInSharedPreference(userList);

    }

    private void showToast(String message){
        Toast.makeText(Global.getInstance(),message,Toast.LENGTH_LONG).show();
    }
}
