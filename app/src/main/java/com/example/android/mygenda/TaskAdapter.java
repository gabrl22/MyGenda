package com.example.android.mygenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Gabriel on 09/26/15.
 */
public class TaskAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mTasks;
    public TaskAdapter(Context context, String[] tasks){

        mContext = context;
        mTasks = tasks;
    }

    @Override
    public int getCount() {
        return mTasks.length;
    }

    @Override
    public Object getItem(int position) {
        return mTasks[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){
            //brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.task_item, null);
            holder = new ViewHolder();
            holder.dayLabel = (TextView)convertView.findViewById(R.id.day_textview);
            holder.monthLabel = (TextView)convertView.findViewById(R.id.month_textview);
            holder.taskLabel = (TextView)convertView.findViewById(R.id.task_textview);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.taskLabel.setText(mTasks[position]);
        holder.monthLabel.setText("SEPT");
        holder.dayLabel.setText("26");


        return convertView;
    }
    public static class ViewHolder{

        TextView taskLabel;
        TextView monthLabel;
        TextView dayLabel;

    }
}
