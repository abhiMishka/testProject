package com.abhishek.testproject;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abhishek.testproject.model.Story;
import com.abhishek.testproject.model.User;
import com.bumptech.glide.Glide;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;

import static com.abhishek.testproject.UtilFunctions.TYPE_ARRAY_LIST_STORIES;
import static com.abhishek.testproject.UtilFunctions.TYPE_ARRAY_LIST_USERS;

public class StoryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Story story = new Story();

    private User user = new User();

    private TextView userNameTv,handleTv,articleDescription;
    private FloatingActionButton followFab,likeFab;
    ShowcaseView showcaseView;

    ArrayList<User> userList;
    private TextView visitPage;

    public interface storyUpdateCallBack{
        public void onStoryUpdate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        userNameTv = (TextView) findViewById(R.id.userNameTv);
        handleTv = (TextView) findViewById(R.id.userHandleTv);
        articleDescription = (TextView) findViewById(R.id.articleDescriptionTv);
        visitPage = (TextView) findViewById(R.id.visitPageTv);


        visitPage.setClickable(true);
        visitPage.setMovementMethod(LinkMovementMethod.getInstance());

        userNameTv.setClickable(true);
        userNameTv.setMovementMethod(LinkMovementMethod.getInstance());

        followFab = (FloatingActionButton) findViewById(R.id.followFab);
        likeFab = (FloatingActionButton) findViewById(R.id.likeFab);

        Intent intent = getIntent();
        story = intent.getParcelableExtra(getString(R.string.obj));

        fetchUserData();

        String descriptionTextWithLink = "<a href='" +story.getUrl() +"'> " +"visit page" +" </a>";
        String userNameTextWithLink = "<a href='" +user.getUrl() +"'> " +user.getUsername() +" </a>";


        userNameTv.setText(Html.fromHtml(userNameTextWithLink));
        articleDescription.setText(story.getDescription());
        visitPage.setText(Html.fromHtml(descriptionTextWithLink));
        handleTv.setText(user.getHandle());

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

        if(UtilFunctions.shouldShowFavoriteInfo()){
            showFollowTutorial();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onPressingBack();
                break;
        }
        return true;
    }

    private void showFollowTutorial() {
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(R.id.followFab, this))
                .setContentTitle("Favorite")
                .setContentText("Click here to mark favorite!")
                .hideOnTouchOutside()
                .build();

        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
    }


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
                if(showcaseView!=null){
                    showcaseView.hide();
                }
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

            if(st.getId().equals(story.getId())){
                storyList.get(count).setLike_flag(b);
                break;
            }
            count++;
        }

        UtilFunctions.saveStoriesInSharedPreference(storyList);


    }

    @Override
    public void onBackPressed() {

        onPressingBack();
        //NavUtils.navigateUpFromSameTask(this);
    }

    private void onPressingBack() {
        Intent intent=new Intent();
        intent.putExtra(Global.getInstance().getString(R.string.obj),story);
        setResult(12,intent);
        finish();
        super.onBackPressed();
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
