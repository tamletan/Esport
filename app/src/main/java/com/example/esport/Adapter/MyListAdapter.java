package com.example.esport.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.esport.Activity.PostActivity;
import com.example.esport.E_News;
import com.example.esport.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    ArrayList<E_News> ListData;
    Context mContext;

    public MyListAdapter(Context mContext, ArrayList<E_News> ListData) {
        this.ListData = ListData;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final E_News newsItem = ListData.get(position);
        holder.mTvTitle.setText(newsItem.getTitle());
        holder.mTvTime.setText(newsItem.getTimeString());
        Picasso.get().load(newsItem.getPicture()).into(holder.mTvPicture);
        /*holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PostActivity.class);
                intent.putExtra("content", newsItem.getContent());
                activity.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView mTvTitle;
         TextView mTvTime;
         ImageView mTvPicture;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_itemTitle);
            mTvPicture = itemView.findViewById(R.id.tv_itemPicture);
            mTvTime = itemView.findViewById(R.id.tv_itemTime);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent postActivity = new Intent(mContext, PostActivity.class);
                    int position = getAdapterPosition();

                    postActivity.putExtra("PostKey", ListData.get(position).getPostKey());
                    postActivity.putExtra("id", ListData.get(position).getId());
                    postActivity.putExtra("title", ListData.get(position).getTitle());
                    postActivity.putExtra("content", ListData.get(position).getContent());
                    postActivity.putExtra("picture", ListData.get(position).getPicture());
                    postActivity.putExtra("time", ListData.get(position).getTime());
                    mContext.startActivity(postActivity);
                }
            });
        }
    }

}