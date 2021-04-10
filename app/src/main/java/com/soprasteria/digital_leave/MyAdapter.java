package com.soprasteria.digital_leave;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Cloud on 19-07-2016.
 */
public class MyAdapter extends BaseAdapter {

    List<String> tv1, tv2, tv3, tv4, tv5, tv6;

    static HashMap<String, String> data = new HashMap<String, String>();

    static HashMap<String, String> data2 = new HashMap<String, String>();

    Context c;

    public MyAdapter(Context c, List<String> tv1, List<String> tv2, List<String> tv3, List<String> tv4, List<String> tv5, List<String> tv6) {

        super();

        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.tv4 = tv4;
        this.tv5 = tv5;
        this.tv6 = tv6;


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
            v = vi.inflate(R.layout.custom, null);
            holder.empname = (TextView) v.findViewById(R.id.empname);
            holder.empid = (TextView) v.findViewById(R.id.empid);
            holder.startdate = (TextView) v.findViewById(R.id.startdate);
            holder.enddate = (TextView) v.findViewById(R.id.enddate);
            holder.days = (TextView) v.findViewById(R.id.days);
            holder.rg = (RadioGroup) v.findViewById(R.id.rg);
            holder.reason = (TextView) v.findViewById(R.id.reason);
            holder.rb1 = (RadioButton) v.findViewById(R.id.allow);
            holder.rb2 = (RadioButton) v.findViewById(R.id.deny);


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



            holder.rb1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                      data.remove(holder.empid.getText().toString());
                      data.put(holder.empid.getText().toString(), "allow");


                }
            });

        holder.rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    data.remove(holder.empid.getText().toString());
                    data.put(holder.empid.getText().toString(), "deny");


            }
        });



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
        RadioGroup rg;
        RadioButton rb1;
        RadioButton rb2;

    }
}