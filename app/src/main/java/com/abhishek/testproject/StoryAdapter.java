package com.abhishek.testproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.testproject.model.Story;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by abhishekkumar on 18/11/16.
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {

    private List<Story> storiesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        com.flaviofaria.kenburnsview.KenBurnsView thumbNailIv;
        View view;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            thumbNailIv = (com.flaviofaria.kenburnsview.KenBurnsView) view.findViewById(R.id.thumbnail);

        }
    }


    public StoryAdapter(List<Story> storiesList) {
        this.storiesList = storiesList;
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
        holder.title.setText(story.getTitle());
        holder.description.setText(story.getDescription());

        if(!story.getSi().isEmpty()) {
            Glide.with(Global.getInstance()).load(story.getSi()).
                    placeholder(R.drawable.ic_image_black).centerCrop().into(holder.thumbNailIv);
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
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra(context.getString(R.string.obj),story);

        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return storiesList.size();
    }
}
