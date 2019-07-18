package com.example.android.scheduler.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.android.scheduler.R;
import com.example.android.scheduler.global.Constants;
import com.example.android.scheduler.models.Event;

import java.util.ArrayList;
import java.util.function.Consumer;

public class HourExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<ArrayList<Event>> mGroups;
    private Consumer<Event> eventConsumer;

    public HourExpandableListAdapter(
            Context context,
            ArrayList<ArrayList<Event>> groups,
            Consumer<Event> eventConsumer
    ) {
        mContext = context;
        mGroups = groups;
        this.eventConsumer = eventConsumer;
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
            convertView = inflater.inflate(R.layout.hour_view, null);
        }

        TextView hour = convertView.findViewById(R.id.hour);
        hour.setText("" + groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_view, null);
        }

        Event event = mGroups.get(groupPosition).get(childPosition);

        TextView event_name = convertView.findViewById(R.id.event_name);
        event_name.setText(event.name);

        TextView event_interval = convertView.findViewById(R.id.event_interval);
        event_interval.setText(
                String.format(
                        "from %s to %s",
                        Constants.dateTimeFormat.format(event.interval.getFrom().getTime()),
                        Constants.dateTimeFormat.format(event.interval.getTo().getTime())
                )
        );
        convertView.findViewById(R.id.event_layout).setOnClickListener(
                v -> eventConsumer.accept(event)
        );

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
