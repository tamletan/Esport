package com.example.esport.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esport.Comment;
import com.example.esport.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context mContext;
    private List<Comment> listData;

    public CommentAdapter(Context mContext, List<Comment> listData) {
        this.mContext = mContext;
        this.listData = listData;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext)
                .inflate(R.layout.row_comment, parent, false);
        return new CommentViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, int position) {

        holder.tv_name.setText(listData.get(position).getUname());
        holder.tv_content.setText(listData.get(position).getContent());
        holder.tv_date.setText(timestampToString((Long) listData.get(position).getTimestamp()));

//        holder.btn_View_Option.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popupMenu = new PopupMenu(mContext, holder.btn_View_Option);
//                popupMenu.inflate(R.menu.comment_menu_option);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.menu_edit:
//
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView user_img;
        TextView tv_name, tv_date, tv_content;
        TextView btn_View_Option;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.content_comment);
            tv_name = itemView.findViewById(R.id.username_comment);
            tv_date = itemView.findViewById(R.id.date_comment);
            btn_View_Option = itemView.findViewById(R.id.option_comment_btn);
        }
    }



    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd:mm",calendar).toString();
        return date;
    }
}
