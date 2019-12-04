package com.example.esport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    private ArrayList<E_News> ListData;
    private Activity activity;

    public MyListAdapter(Activity activity, ArrayList<E_News> ListData) {
        this.ListData = ListData;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final E_News newsItem = ListData.get(position);
        holder.mTvTitle.setText(newsItem.getTitle());
        holder.mTvTime.setText(newsItem.getTimeString());
        Picasso.get().load(newsItem.getPicture()).into(holder.mTvPicture);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PostActivity.class);
                intent.putExtra("content", newsItem.getContent());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvTime;
        private ImageView mTvPicture;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_itemTitle);
            mTvPicture = itemView.findViewById(R.id.tv_itemPicture);
            mTvTime = itemView.findViewById(R.id.tv_itemTime);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }

}