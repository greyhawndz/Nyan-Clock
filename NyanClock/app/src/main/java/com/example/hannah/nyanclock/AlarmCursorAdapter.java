package com.example.hannah.nyanclock;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hannah on 3/12/16.
 */
public class AlarmCursorAdapter extends CursorRecyclerViewAdapter<AlarmCursorAdapter.AlarmViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener m)
    {
        this.mOnItemClickListener = m;
    }

    public interface OnItemClickListener
    {
        public void onItemClick(int id);
    }

    public AlarmCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder viewHolder, Cursor cursor) {
        String time = cursor.getString(cursor.getColumnIndex(Alarm.COLUMN_TIME));

        // Change the RecyclerView alarm time to 12-hour format
        int hour = Integer.parseInt(time.substring(0, 2));
        if(hour > 12)
        {
            hour = hour - 12;
            if(hour < 10)
            {
                time = "0" + String.valueOf(hour) + time.substring(2, 8);
            }
            else
            {
                time = String.valueOf(hour) + time.substring(2, 8);
            }

        }

        viewHolder.tvAlarmItem.setText(time);
        viewHolder.container.setTag(cursor.getInt(cursor.getColumnIndex(Alarm.COLUMN_ID)));
        viewHolder.container.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // call on item click here
                int dbid = Integer.parseInt(v.getTag().toString());
                mOnItemClickListener.onItemClick(dbid);
            }
        });
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new AlarmViewHolder(v);
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvAlarmItem;
        View container;

        public AlarmViewHolder(View itemView)
        {
            super(itemView);
            tvAlarmItem = (TextView) itemView.findViewById(R.id.tv_AlarmItem);
            container = itemView.findViewById(R.id.container);
        }
    }
}
