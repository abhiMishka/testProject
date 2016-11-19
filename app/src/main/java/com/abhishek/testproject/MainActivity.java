package com.abhishek.testproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.abhishek.testproject.model.Story;

import java.util.ArrayList;
import java.util.List;

import static com.abhishek.testproject.UtilFunctions.TYPE_ARRAY_LIST_STORIES;

public class MainActivity extends AppCompatActivity {

    private List<Story> storyList  = new ArrayList<>();
    private StoryAdapter mStoryAdapter;
    Toolbar toolbar;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mStoryAdapter= new StoryAdapter(storyList);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mStoryAdapter);
    }

    private void fetchAndFillStoryData() {

        Log.i("testSomething","here");
        storyList = UtilFunctions.objectify
                (UtilFunctions.getData(Constants.KEY_STORIES),
                        TYPE_ARRAY_LIST_STORIES);

        if(storyList==null) {
            Log.i("testSomething","reading from json");

            storyList = UtilFunctions.readStoriesListFromJson(UtilFunctions.loadJSONFromAsset());
            UtilFunctions.saveStoriesInSharedPreference(storyList);
        }

        mStoryAdapter.addStories(storyList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAndFillStoryData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
