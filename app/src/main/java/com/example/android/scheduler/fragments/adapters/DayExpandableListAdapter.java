package com.example.android.scheduler.fragments.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.android.scheduler.R;

import java.util.ArrayList;

public class DayExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<ArrayList<ExpandableListView>> mGroups;
    private ArrayList<Pair<String, String>> groupContent;

    public DayExpandableListAdapter(
            Context context,
            ArrayList<ArrayList<ExpandableListView>> groups,
            ArrayList<Pair<String, String>> groupContent
    ) {
        mContext = context;
        mGroups = groups;
        this.groupContent = groupContent;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.day_view, null);
        }

        if (isExpanded) {
            //Изменяем что-нибудь, если текущая Group раскрыта
        } else {
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView day = convertView.findViewById(R.id.day);
        day.setText(groupContent.get(groupPosition).first);

        TextView date = convertView.findViewById(R.id.date);
        date.setText(groupContent.get(groupPosition).second);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {// TODO: 13.07.2019 event onclick
        if (convertView == null) {
            convertView = (View) getChild(groupPosition, childPosition);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
