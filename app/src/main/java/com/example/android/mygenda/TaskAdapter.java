package com.example.android.mygenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Gabriel on 09/26/15.
 */
public class TaskAdapter extends ArrayAdapter<ParseObject> {

    private Context mContext;
    protected List<ParseObject> mTasks;

    public TaskAdapter(Context context, List<ParseObject> tasks){

        super(context, R.layout.task_item, tasks);
        mContext = context;
        mTasks = tasks;
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

        ParseObject tasks = mTasks.get(position);

        holder.taskLabel.setText(tasks.getString(ParseConstants.KEY_TASK_TITLE));
       holder.monthLabel.setText(tasks.getString(ParseConstants.KEY_TASK_MONTH));
        //holder.monthLabel.setText("SEPT");
       holder.dayLabel.setText(tasks.getInt(ParseConstants.KEY_TASK_DAY) + "");
        //holder.dayLabel.setText("13");


        return convertView;
    }
    public static class ViewHolder{

        TextView taskLabel;
        TextView monthLabel;
        TextView dayLabel;

    }
    public void refill(List<ParseObject> tasks){
        mTasks.clear();
        mTasks.addAll(tasks);
        notifyDataSetChanged();
    }
}
