package com.example.esport.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esport.Comment;
import com.example.esport.Fragment.CommentsFragment;
import com.example.esport.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ManageCommentAdapter extends RecyclerView.Adapter<ManageCommentAdapter.ManageCommentViewHolder> implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List<Comment> listData;
    private CommentsFragment frag;
    private String TAG = ">>>>>>>>";
    ManageCommentAdapter adapter;

    public ManageCommentAdapter(Context mContext, CommentsFragment frag, List<Comment> listData) {
        this.mContext = mContext;
        this.listData = listData;
        this.frag = frag;
    }

    @NonNull
    @Override
    public ManageCommentAdapter.ManageCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext)
                .inflate(R.layout.row_manage_comment, parent, false);
        return new ManageCommentAdapter.ManageCommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final ManageCommentAdapter.ManageCommentViewHolder holder, final int position) {

        holder.tv_name.setText(listData.get(position).getUname());
        holder.tv_content.setText(listData.get(position).getContent());
        holder.tv_date.setText(timestampToString((Long) listData.get(position).getTimestamp()));
        holder.tv_post.setText(listData.get(position).getPid());
        //holder.edit_comment.setText(holder.tv_content.getText().toString());
        adapter = new ManageCommentAdapter(mContext, frag, listData);
        holder.delete_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+listData.get(position).getContent());
                frag.deleteComment(listData.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+listData.size());
        return listData.size();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public class ManageCommentViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_date, tv_content, tv_post;
        FloatingActionButton delete_comment;
        //EditText edit_comment;
        public ManageCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.comment_mng_content);
            tv_name = itemView.findViewById(R.id.comment_mng_username);
            tv_date = itemView.findViewById(R.id.comment_mng_date);
            tv_post = itemView.findViewById(R.id.comment_mng_post);
            delete_comment = itemView.findViewById(R.id.delete_comment);
        }
    }


    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd:mm",calendar).toString();
        return date;
    }
}
