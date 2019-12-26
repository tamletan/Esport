package com.example.esport.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.widget.PopupMenu;
import android.view.MenuItem;
import androidx.recyclerview.widget.RecyclerView;

import com.example.esport.Activity.PostActivity;
import com.example.esport.Comment;
import com.example.esport.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> implements AdapterView.OnItemClickListener {
    private Context mContext;
    private List<Comment> listData;
    private String comment_edited, comment_editing;
    private int position_cmt;
    private String TAG = ">>>>>>>>";
    private Boolean isChange = false;
    private View dialogView;
    private AlertDialog alertDialog;
    CommentAdapter adapter;
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
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, final int position) {

        holder.tv_name.setText(listData.get(position).getUname());
        holder.tv_content.setText(listData.get(position).getContent());
        comment_editing = holder.tv_content.getText().toString();
        holder.tv_date.setText(timestampToString((Long) listData.get(position).getTimestamp()));
        //holder.edit_comment.setText(holder.tv_content.getText().toString());
        adapter = new CommentAdapter(mContext, listData);

        holder.btn_View_Option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.btn_View_Option);
                popupMenu.inflate(R.menu.comment_menu_option);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_edit:
                                position_cmt = position;
                                showAlertDialog();
                                Log.d(TAG, "onMenuItemClick: "+holder.tv_content.getText().toString());
                                break;
                            case R.id.menu_delete:
                                ((PostActivity) mContext).deleteComment(listData.get(position));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.edit_comment_dialog);
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        dialogView = layoutInflater.inflate(R.layout.edit_comment_dialog, null);
        final EditText editText = ((EditText) dialogView.findViewById(R.id.edit_comment));
        editText.setText(comment_editing);
        dialogView.findViewById(R.id.Cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChange = false;
                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.Save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChange = true;
                Log.d(TAG, "onClick: "+listData.get(position_cmt).getCid());
                comment_edited = editText.getText().toString();
                listData.get(position_cmt).setContent(comment_edited);
                ((PostActivity) mContext).editComment(listData.get(position_cmt));
                adapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        ImageView user_img;
        TextView tv_name, tv_date, tv_content;
        TextView btn_View_Option;
        //EditText edit_comment;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.content_comment);
            tv_name = itemView.findViewById(R.id.username_comment);
            tv_date = itemView.findViewById(R.id.date_comment);
            btn_View_Option = itemView.findViewById(R.id.option_comment_btn);
            //edit_comment = itemView.findViewById(R.id.edit_comment);
        }
    }



    private String timestampToString(long time) {

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("dd:mm",calendar).toString();
        return date;
    }
}
