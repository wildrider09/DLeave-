package com.soprasteria.digital_leave;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerData extends Fragment {
    TextView textView111,textView121,tvnew;
    static String response = "";
    String    path = "http://engaged-partner.000webhostapp.com/manager.php";

    ArrayAdapter ad,ad1;
    ListView lv,lv1;

    ArrayList<String> tv1, tv2, tv3, tv4, tv5,tv6,tv7;


    HashMap<String, String> postDataParams;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.manager, container, false);

        lv = (ListView) v.findViewById(R.id.listView);
        lv1 = (ListView) v.findViewById(R.id.listView1);

        tv1 = new ArrayList<String>();
        tv2 = new ArrayList<String>();
        tv3 = new ArrayList<String>();
        tv4 = new ArrayList<String>();
        tv5 = new ArrayList<String>();
        tv6 = new ArrayList<String>();
        tv7 = new ArrayList<String>();


        textView111 = (TextView) v.findViewById(R.id.textView111);

        tvnew = (TextView) v.findViewById(R.id.tvnew);


        tvnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = "http://engaged-partner.000webhostapp.com/manager.php";
                textView111.setEnabled(true);
                //lv.setVisibility(View.VISIBLE);
                //lv1.setVisibility(View.GONE);
                new GetData().execute();

            }
        });

        textView121 = (TextView) v.findViewById(R.id.allrec);

        textView111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendData();

            }
        });

        textView121.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lv1.setVisibility(View.VISIBLE);
                //lv.setVisibility(View.GONE);
                path = "http://engaged-partner.000webhostapp.com/allrec.php";
                textView111.setEnabled(false);
                new GetData().execute();
            }
        });

        new GetData().execute();

        return v;
    }

    private void sendData() {
        sendMultiplePush();
    }


    private void sendMultiplePush() {


        final String title = "Dleave";
        final String message = "Please check your leave application status";
        final String image = "";

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Sending Notification");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SEND_MULTIPLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                     // Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();


                        new SendData().execute();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);

                if (!TextUtils.isEmpty(image))
                    params.put("image", image);

                params.put("email", (MyAdapter.data.keySet().toString()).replace("[", " ").replace("]", " ").replace(" ", "").trim());
                return params;
            }
        };

        MyVolley.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    class GetData extends AsyncTask<Void, Void, Void> {


        int success = 0;
        ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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

                response = service.ServerData(path, postDataParams);

                Log.d("locality", " result :  :  " + response);

                JSONObject obj = new JSONObject(response);

                JSONArray data = obj.getJSONArray("data");

                success = obj.getInt("success");

                if (success == 1) {

                    tv1.clear();
                    tv2.clear();
                    tv3.clear();
                    tv4.clear();
                    tv5.clear();
                    tv6.clear();
                    tv7.clear();

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);

                        tv1.add(jo.getString("empid"));
                        tv2.add(jo.getString("empname"));
                        tv3.add(jo.getString("startdate"));
                        tv4.add(jo.getString("enddate"));
                        tv5.add(jo.getString("days"));
                        tv6.add(jo.getString("reason"));

                        if(path.contains("allrec.php"))
                            tv7.add(jo.getString("status"));

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


                //Toast.makeText(getContext(), "Found", Toast.LENGTH_SHORT).show();
                if(path.contains("manager.php")) {

                    MyAdapter  ad = new MyAdapter(getActivity(), tv1, tv2, tv3, tv4, tv5, tv6);
                    ad.notifyDataSetChanged();
                    lv.setAdapter(ad);

                }
                else {

                    MyAdapterOld ad1 = new MyAdapterOld(getActivity(), tv1, tv2, tv3, tv4, tv5, tv6, tv7);
                    ad1.notifyDataSetChanged();
                    lv.setAdapter(ad1);
                }


            } else {

                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
            }
        }


    }


    //sending data


    class SendData extends AsyncTask<Void, Void, Void> {


        String empid, status,startdate;

        int success = 0;
        ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            empid = (MyAdapter.data.keySet().toString()).replace("[", " ").replace("]", " ").replace(" ", "").trim();

            status = (MyAdapter.data.values().toString()).replace("[", " ").replace("]", " ").replace(" ", "").trim();

            startdate = (MyAdapter.data2.values().toString()).replace("[", " ").replace("]", " ").replace(" ", "").trim();


            Log.d("sid and data is here ", MyAdapter.data.toString() + "   " + MyAdapter.data2.toString());

            Log.d("sid and data is here ", empid + "   " + status+ "   " + startdate);



            path = "http://engaged-partner.000webhostapp.com/managerdata.php";

            postDataParams = new HashMap<>();
            postDataParams.put("empid", empid);
            postDataParams.put("startdate", startdate);
            postDataParams.put("status", status);

            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Loading... Please wait !!!");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                HTTPURLConnection service = new HTTPURLConnection();

                response = service.ServerData(path, postDataParams);

                Log.d("locality", " result :  :  " + response);

                JSONObject obj = new JSONObject(response);

                success = obj.getInt("success");


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
                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();

                ManagerData  n = new ManagerData();

                FragmentManager ne = getActivity().getSupportFragmentManager();

                ne.beginTransaction().replace(R.id.frame_container, n).commit();
            } else {

                Toast.makeText(getContext(), "No Data Updated", Toast.LENGTH_SHORT).show();
            }
        }


    }


}


