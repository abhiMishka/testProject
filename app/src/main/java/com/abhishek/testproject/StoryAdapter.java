package com.abhishek.testproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.testproject.model.Story;
import com.abhishek.testproject.model.User;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

/**
 * Created by abhishekkumar on 18/11/16.
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {

    private final Map<String, User> userMap;
    private List<Story> storiesList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        ImageView thumbNailIv;
        View view;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            thumbNailIv = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }


    public StoryAdapter(List<Story> storiesList,Context parentActivityContext) {
        this.storiesList = storiesList;
        this.mContext = parentActivityContext;
        userMap = UtilFunctions.getUsersMapFromJson(UtilFunctions.loadJSONFromAsset());
    }

    public void addStories(List<Story> storiesList){
        this.storiesList.addAll(storiesList);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.story_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Story story = storiesList.get(position);

        User user = (userMap.get(story.getDb()));

        String title = story.getTitle();
        if(story.isLike_flag()){
            title = title +" (You like this!)";
        }
        holder.title.setText(title);
        holder.description.setText(user.getUsername().trim() + " "+ story.getVerb());

        if(!story.getSi().isEmpty()) {
            Glide.with(Global.getInstance()).load(user.getImage()).
                    placeholder(R.drawable.ic_image_black).fitCenter().into(holder.thumbNailIv);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStoryPageActivity(view, story);
            }
        });

        holder.thumbNailIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStoryPageActivity(view, story);
            }
        });
    }

    private void openStoryPageActivity(View view, Story story) {
        Context context = view.getContext();
        Activity origin = (Activity) this.mContext;

        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra(context.getString(R.string.obj),story);

        origin.startActivityForResult(intent, Constants.REQUEST_CODE);

    }

    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        Story changedStory = data.
                getParcelableExtra(Global.getInstance().getString(R.string.obj));

        Log.i("adapterCallBack", "changedStory  :\n\n" +changedStory.toString());

        int count = 0;
        for(Story st : storiesList){
            if(st.getId().equals(changedStory.getId())){
                storiesList.set(count,changedStory);
                break;
            }
            count++;
        }

        notifyItemChanged(count);

        Log.i("adapterCallBack", "\n\n list after item changed  :\n\n" +storiesList.toString());


    }

    @Override
    public int getItemCount() {
        return storiesList.size();
    }
}
