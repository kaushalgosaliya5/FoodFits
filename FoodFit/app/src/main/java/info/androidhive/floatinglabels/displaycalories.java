package info.androidhive.floatinglabels;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class displaycalories extends AppCompatActivity
{
    private Button track;
    private TextView calories;
    private Toolbar toolbar;
    private String usrname , emailid, password,age,height,weight,gender,cal , str;
    private int goalstep , calorieintake ,Age;
    private UserSessionManager manager;
    private  DB snappydb;

    static displaycalories dispcal;

    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaycalories);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();

        dispcal = this;
        caloriecounter.getInstance().finish();

        usrname = intent.getStringExtra("Username");
        emailid = intent.getStringExtra("Email");
        password = intent.getStringExtra("Password");
        age = intent.getStringExtra("Age");
        height = intent.getStringExtra("Height");
        weight = intent.getStringExtra("Weight");
        gender = intent.getStringExtra("Gender");
        cal = intent.getStringExtra("Total_Calories");
        manager = new UserSessionManager(getApplicationContext());
        //Toast.makeText(getApplicationContext(),usrname+""+emailid+""+password+""+age+""+height+""+weight+""+gender+""+cal,Toast.LENGTH_LONG).show();
        goalstep=6000;
        calorieintake=Integer.parseInt(cal);
        Age = Integer.parseInt(age);

        track = (Button)findViewById(R.id.button6);

        calories = (TextView) findViewById(R.id.textView8);
        calories.setText(cal+" Calories");
        caloriecounter.getInstance().finish();

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                new SendPostRequest().execute();
            }
        });
    }
    public class SendPostRequest extends AsyncTask<String, Void, String>
    {
        protected void onPreExecute()
        {
        }
        protected String doInBackground(String... arg0)
        {
            try{

                URL url = new URL("http://sen6.herokuapp.com/appuser/register/addApp");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("fname",usrname);
                postDataParams.put("uname",usrname);
                postDataParams.put("email",emailid);
                postDataParams.put("pass",password);
                postDataParams.put("age",Age);
                postDataParams.put("height",height);
                postDataParams.put("weight",weight);
                postDataParams.put("gender",gender);
                postDataParams.put("calin",calorieintake);
                postDataParams.put("goalstep",goalstep);
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result)
        {

          //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
//            Log.e("result",result);
//            //int res = Integer.parseInt(result);

            try {
                JSONObject obj = new JSONObject(result);
                String uname  = obj.getString("userName");
                String _id  = obj.getString("_id");
                String passwordd  = obj.getString("password");
                String gender  = obj.getString("gender");
                String email  = obj.getString("email");
                int Age  = obj.getInt("age");
                String height  = obj.getString("height");
                String weight  = obj.getString("weight");
                int calorieintake  = obj.getInt("calorieintake");
                int dailystepgoal  = obj.getInt("dailystepgoal");

                //Toast.makeText(getApplicationContext(),_id+""+uname+""+email+""+passwordd+""+age+""+height+""+weight+""+gender+""+calorieintake+""+dailystepgoal,Toast.LENGTH_LONG).show();
                String age = Age+"";
                String cal = calorieintake+"";
                String steps= dailystepgoal+"";
                String str = "USER"+_id;
                try
                {
                    snappydb= DBFactory.open(getApplicationContext(),"user");
                    snappydb.put(str,new String[]{uname,email,passwordd,age,height,weight,gender,cal,steps});
                    snappydb.close();

                }

                catch (SnappydbException e)
                {

                }
                Toast.makeText(getApplicationContext(), "Registered Successfully !! ", Toast.LENGTH_LONG).show();
                manager.setPreferences(displaycalories.this, "status", "1");
                manager.setPreferences(displaycalories.this, "_id", str);
                manager.setPreferences(displaycalories.this, "email", email);
                manager.setPreferences(displaycalories.this, "password", passwordd);
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);

            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
            }
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


    public static displaycalories getInstance(){
        if( dispcal == null )
        {
            return null;
        }
        return dispcal;
    }

}
