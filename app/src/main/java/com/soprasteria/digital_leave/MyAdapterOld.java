package com.soprasteria.digital_leave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Cloud on 19-07-2016.
 */
public class MyAdapterOld extends BaseAdapter {

    List<String> tv1, tv2, tv3, tv4, tv5, tv6,tv7;

    static HashMap<String, String> data = new HashMap<String, String>();

    static HashMap<String, String> data2 = new HashMap<String, String>();

    Context c;

    public MyAdapterOld(Context c, List<String> tv1, List<String> tv2, List<String> tv3, List<String> tv4, List<String> tv5, List<String> tv6, List<String> tv7) {

        super();

        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.tv4 = tv4;
        this.tv5 = tv5;
        this.tv6 = tv6;
        this.tv7 = tv7;


        this.c = c;


    }

    public int getCount() {
        return tv1.size();

    }


    public Object getItem(int arg0) {
        return arg0;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        final ViewHolder holder;

        if (v == null) {

            holder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(c);
            v = vi.inflate(R.layout.customold, null);
            holder.empname = (TextView) v.findViewById(R.id.empname);
            holder.empid = (TextView) v.findViewById(R.id.empid);
            holder.startdate = (TextView) v.findViewById(R.id.startdate);
            holder.enddate = (TextView) v.findViewById(R.id.enddate);
            holder.days = (TextView) v.findViewById(R.id.days);
            holder.reason = (TextView) v.findViewById(R.id.reason);
            holder.status = (TextView) v.findViewById(R.id.status);


            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.empname.setText(tv2.get(position));
        holder.empid.setText(tv1.get(position));
        holder.startdate.setText(tv3.get(position));
        holder.enddate.setText(tv4.get(position));
        holder.days.setText(tv5.get(position));
        holder.reason.setText(tv6.get(position));
        holder.status.setText(tv7.get(position));





        data2.put(holder.empid.getText().toString(), holder.startdate.getText().toString());

        return v;
    }

    private class ViewHolder {

        TextView empname;
        TextView reason;
        TextView empid;
        TextView startdate;
        TextView enddate;
        TextView days;
        TextView status;

    }
}