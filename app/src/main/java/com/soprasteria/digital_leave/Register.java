package com.soprasteria.digital_leave;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText emp, name, email, pass, etkey;
    Button go;
    RadioGroup rg;
    boolean f = false, f1 = false, f2 = false, f3 = false;
    static String type = "";
    String path, key;
    HashMap<String, String> postDataParams;
    static String response = "";
    private ProgressDialog progressDialog;
    public String sname, semail, stype;
    public String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        {


            emp = (EditText) findViewById(R.id.empid);
            pass = (EditText) findViewById(R.id.password);




            go = (Button) findViewById(R.id.go);

            boolean connected = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                connected = true;
            } else
                connected = false;
            if (!connected) {
                Toast.makeText(getApplicationContext(), "CHECK YOUR INTERNET CONNECTION",
                        Toast.LENGTH_LONG).show();
            }

            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (emp.getText().toString().length() == 0 || pass.getText().toString().length() == 0) {

                        Toast.makeText(Register.this, "Please enter complete details", Toast.LENGTH_SHORT).show();

                    } else {
                        register();
                    }
                }
            });

        }
    }


    private void register() {

        new GetData().execute();
    }


    //storing token to mysql server
    private void sendTokenToServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        final String email = emp.getText().toString();

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(Register.this, obj.getString("message"), Toast.LENGTH_LONG).show();

                            Intent i = new Intent(Register.this, Main4Activity.class);

                            startActivity(i);

                            finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("token", token);
                return params;
            }
        };
        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }


    class GetData extends AsyncTask<Void, Void, Void> {

        int success = 0;
        ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            path = "http://engaged-partner.000webhostapp.com/registeruser.php";

            postDataParams = new HashMap<String, String>();
            postDataParams.put("empid", emp.getText().toString());
            postDataParams.put("pass", pass.getText().toString());

            pDialog = new ProgressDialog(Register.this);
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
                    //  Toast.makeText(getContext(), "wow", Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject jo = data.getJSONObject(i);


                        sname = jo.getString("name");
                        semail = jo.getString("email");
                        stype = jo.getString("type");
                        type = jo.getString("mname");
                        _id = jo.getString("empid");
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

                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor et = sp.edit();

                et.putString("status", "1");

                et.putString("empid", emp.getText().toString());
                et.putString("name", sname);
                et.putString("mail", semail);
                et.putString("type", stype);
                et.putString("manager", type);
                et.putString("managerid", _id);
                et.commit();


                sendTokenToServer();




            } else {


                Toast.makeText(Register.this, "Error! User Not Registered", Toast.LENGTH_SHORT).show();

            }


        }

    }


}
