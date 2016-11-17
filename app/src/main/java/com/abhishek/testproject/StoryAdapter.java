package com.abhishek.testproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhishek.testproject.model.Story;

import java.util.List;

/**
 * Created by abhishekkumar on 18/11/16.
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {

    private List<Story> storiesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, type, description;

        View view;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.genre);
            type = (TextView) view.findViewById(R.id.year);
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
        holder.description.setText(story.getId());
        holder.type.setText(story.getType());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, StoryDetailActivity.class);
                intent.putExtra(context.getString(R.string.obj),story);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storiesList.size();
    }
}
