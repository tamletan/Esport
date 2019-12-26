package com.example.esport.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.esport.Activity.EditUserActivity;
import com.example.esport.Adapter.CustomExpandableListAdapter;
import com.example.esport.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersFragment extends Fragment {
    View root;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_users, container, false);
        addComponent();
        expendlist();
        return root;
    }

    private void addComponent() {
        expandableListView = root.findViewById(R.id.expandableListView);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String Uemail = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                String Uid = ExpandableListDataPump.UidList.get(Uemail);
                Intent intent = new Intent(getContext(), EditUserActivity.class);
                intent.putExtra("user", Uid);
                startActivity(intent);
                return false;
            }
        });
    }

    private void expendlist() {
        expandableListDetail = ExpandableListDataPump.getDataUser();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        expendlist();
    }
}
