package com.example.esport.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.esport.Activity.AddPostActivity;
import com.example.esport.Activity.EditUserActivity;
import com.example.esport.Adapter.CustomExpandableListAdapter;
import com.example.esport.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostFragment extends Fragment {
    View root;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_post, container, false);
        expendlist();
        return root;
    }
    private void expendlist() {
        expandableListView = root.findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getDataPost();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String Ptitle = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                String Uid = ExpandableListDataPump.UidList.get(Ptitle);
                Intent intent = new Intent(getContext(), AddPostActivity.class);
                intent.putExtra("request", 1);
                intent.putExtra("post", Uid);
                intent.putExtra("cata", expandableListTitle.get(groupPosition));
                startActivity(intent);
//                Toast.makeText(getContext(), expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
