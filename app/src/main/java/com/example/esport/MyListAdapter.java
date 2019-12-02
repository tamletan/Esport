package com.example.esport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

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
        holder.mTvContent.setText(newsItem.getContent());
        holder.mTvPicture.setText(newsItem.getPicture());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
////                Intent intent = new Intent(view.getContext(), AddDiaryActivity.class);
////                Bundle bundle = new Bundle();
////                bundle.putSerializable("journalItem", journalItem);
////                intent.putExtra("package", bundle);
////                intent.putExtra("request", MainActivity.REQUEST_EDIT_JOURNAL);
////                MainActivity.editIndex = position;
////                ((Activity) view.getContext()).startActivityForResult(intent, MainActivity.REQUEST_EDIT_JOURNAL);
                Toast.makeText(activity, newsItem.getTitle()+"-"+newsItem.getContent(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;
        private TextView mTvContent;
        private TextView mTvPicture;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_itemTitle);
            mTvContent = itemView.findViewById(R.id.tv_itemContent);
            mTvPicture = itemView.findViewById(R.id.tv_itemDate);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}