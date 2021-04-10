package com.soprasteria.digital_leave;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by user on 1/8/2018.
 */
public class New extends Fragment {
    String path;
    HashMap<String, String> postDataParams;
    static String response = "";

    private TextView tvDisplayDate;
    private DatePicker dpResult;
    private Button btnChangeDate;
    Button sub;


    EditText et;



    static final int DATE_DIALOG_ID = 999;
    static private String startdate = "";
    static private String enddate = "";
    private EditText reason,sdate,edate;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_f, container, false);

        final Button datePicker = (Button) v.findViewById(R.id.ds1);

        sdate = (EditText) v.findViewById(R.id.sdate);
        edate = (EditText) v.findViewById(R.id.edate);

        datePicker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(getActivity());

                d.setTitle("Select Start Date");

                d.setContentView(R.layout.cal);

                Button b  = (Button)d.findViewById(R.id.clickhere);

                b.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        DatePicker datePicker = (DatePicker)d.findViewById(R.id.datePicker);

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();
                        startdate = day + "/" + month + "/" + year;

                        sdate.setText(startdate);

                        d.dismiss();
                    }
                });

                d.show();

            }
        });
      /*
*/
        Button datePickr = (Button) v.findViewById(R.id.ds2);


        datePickr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(getActivity());

                d.setTitle("Select End Date");

                d.setContentView(R.layout.cal);

                Button b  = (Button)d.findViewById(R.id.clickhere);

                b.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        DatePicker datePicker = (DatePicker)d.findViewById(R.id.datePicker);

                        int day = datePicker.getDayOfMonth();
                        int month = datePicker.getMonth() + 1;
                        int year = datePicker.getYear();
                        enddate = day + "/" + month + "/" + year;

                        edate.setText(enddate);

                        d.dismiss();
                    }
                });

                d.show();

            }
        });




        et = (EditText) v.findViewById(R.id.nod);
        reason = (EditText) v.findViewById(R.id.textView78);



        sub = (Button) v.findViewById(R.id.sub);


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reason.getText().toString().length() == 0 || startdate.length() == 0 || enddate.length() == 0 || et.length()==0)
                {
                   Toast.makeText(getContext(),"Fill complete details",Toast.LENGTH_LONG).show();
                }

                else

                register();
            }
        });

        return v;

    }

    private void register() {
        new GetCount().execute();
    }


    private void sendSinglePush() {
        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        final String title = "Please check leave application";
        final String message = "Application for Leave";
        final String image = "";
        final String email = sp.getString("empid","");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Sending Notification");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SEND_SINGLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                       //  Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();


                        new GstData().execute();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);

                params.put("empid", sp.getString("managerid",""));



                if (!TextUtils.isEmpty(image))
                    params.put("image", image);

                params.put("email", email);
                return params;
            }
        };

        MyVolley.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    class GstData extends AsyncTask<Void, Void, Void> {

        int success = 0;
        ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            path = "http://engaged-partner.000webhostapp.com/applyleave.php";
            SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

            postDataParams = new HashMap<>();
            postDataParams.put("empid", sp.getString("empid", ""));
            postDataParams.put("manager", sp.getString("managerid", ""));
            postDataParams.put("startdate", startdate);
            postDataParams.put("enddate", enddate);
            postDataParams.put("days", et.getText().toString());
            postDataParams.put("reason", reason.getText().toString());


            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading... Please wait !!!");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                HTTPURLConnection service = new HTTPURLConnection();

                response = service.ServerData(path, postDataParams);

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

                Toast.makeText(getActivity(), "Applied successfully. Keep checking for approval ", Toast.LENGTH_SHORT).show();

                Home  n = new Home();

                FragmentManager ne = getActivity().getSupportFragmentManager();

                ne.beginTransaction().replace(R.id.frame_container, n).commit();
            } else {

                Toast.makeText(getActivity(), "Error! User Not Registered", Toast.LENGTH_SHORT).show();

            }


        }

    }


    class GetCount extends AsyncTask<Void, Void, Void> {

        int success = 0;
        ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            path = "http://engaged-partner.000webhostapp.com/count.php";
            SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

            postDataParams = new HashMap<>();
            postDataParams.put("empid", sp.getString("empid", ""));

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading... Please wait !!!");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                HTTPURLConnection service = new HTTPURLConnection();

                response = service.ServerData(path, postDataParams);

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


            if (success == 2) {
                SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);


                if(sp.getString("type","").equals("Employee"))
                sendSinglePush();

            } else {

                Toast.makeText(getActivity(), "You can not go for leave .Your total leaves exceeded the limit.", Toast.LENGTH_SHORT).show();

            }


        }
    }

}
