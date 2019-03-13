package com.pineapple.woke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import android.support.design.widget.Snackbar;

import com.pineapple.woke.StudySession.SavedSession;


public class HistoryAdapter extends ArrayAdapter<SavedSession> implements View.OnClickListener{

    private ArrayList<SavedSession> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtDuration;
        ImageView info;
    }

    public HistoryAdapter(ArrayList<SavedSession> data, Context context) {
        super(context, R.layout.history_listview_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        SavedSession savedSession=(SavedSession)object;


        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Duration: " +savedSession.getDuration(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SavedSession savedSession = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.history_listview_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtDuration = (TextView) convertView.findViewById(R.id.type);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(savedSession.getName());
        viewHolder.txtDuration.setText(Long.toString(savedSession.getDuration()));
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}

