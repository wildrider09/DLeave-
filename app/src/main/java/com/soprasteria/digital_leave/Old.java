package com.soprasteria.digital_leave;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
public class Old extends Fragment {

    static String response = "";
    String path;
    ArrayAdapter ad;
    ListView lv;


    HashMap<String, String> postDataParams;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.old, container , false);

        lv=(ListView)v.findViewById(R.id.listView);

        new GetData().execute();

        return  v;
    }

class GetData extends AsyncTask<Void, Void, Void> {

    ArrayList<String> al = new ArrayList<>();
    int success = 0;
    ProgressDialog pDialog;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        path = "http://engaged-partner.000webhostapp.com/records.php";

        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        postDataParams = new HashMap<>();
        postDataParams.put("empid", sp.getString("empid", ""));

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading... Please wait !!!");
        pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            HTTPURLConnection service = new HTTPURLConnection();

            SharedPreferences sp = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

            response = service.ServerData(path, postDataParams);

            Log.d("locality", " result :  :  " + response);

            JSONObject obj = new JSONObject(response);

            JSONArray data = obj.getJSONArray("data");

            success = obj.getInt("success");

            if (success == 1) {
              //  Toast.makeText(getContext(), "wow", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < data.length(); i++) {

                    JSONObject jo = data.getJSONObject(i);

                    String sts = jo.getString("status");

                    if(sts.equals(""))
                    {
                        sts = "Pending...";

                        al.add("Start Date: " + jo.getString("startdate") + "\nEnd Date: " + jo.getString("enddate") + "\nNumber Of Days: " + jo.getString("days") + "\nReason: " + jo.getString("reason") + "\nManager: " + sp.getString("manager", "") + "\nStatus: " + sts);
                    }
                    else if(sts.equals("allow"))

                    {
                        sts="Allowed";
                        al.add("Start Date: " + jo.getString("startdate") + "\nEnd Date: " + jo.getString("enddate") + "\nNumber Of Days: " + jo.getString("days") + "\nReason: " + jo.getString("reason") + "\nManager: " + sp.getString("manager", "") + "\nStatus: " + sts);

                    }
                   else if(sts.equals("deny"))
                    {
                        sts="Deny";
                        al.add("Start Date: " + jo.getString("startdate") + "\nEnd Date: " + jo.getString("enddate") + "\nNumber Of Days: " + jo.getString("days") + "\nReason: " + jo.getString("reason") + "\nManager: " + sp.getString("manager", "") + "\nStatus: " + sts);

                    }

                }



            } else {
                Log.d("success  ::", "no");
            }

        } catch (Exception e) {
            Log.d("Exception ", "" + e);

        }
        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        pDialog.dismiss();

        if (success == 1) {
         //   Toast.makeText(getContext(), "Found", Toast.LENGTH_SHORT).show();
            ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, al);
            lv.setAdapter(ad);
        } else {

            Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
        }
    }


}
}


