package info.androidhive.floatinglabels;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by Aarti Radadiya on 25/11/2016.
 */

public class Forgot_password extends AppCompatActivity
{
    EditText email;
    TextInputLayout inputemail;
    Button submit;
    private UserSessionManager manager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FoodFit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        email = (EditText)findViewById(R.id.input_email);
        inputemail = (TextInputLayout) findViewById(R.id.input_layout_email);
        submit = (Button)findViewById(R.id.btn_submit);
        manager = new UserSessionManager(getApplicationContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendPostRequest().execute();
            }
        });
    }

    public class SendPostRequest extends AsyncTask<String, Void, String>
    {

        private ProgressDialog dialog = new ProgressDialog(Forgot_password.this);
        protected void onPreExecute()
        {
            this.dialog.setMessage("Please wait ");
            this.dialog.show();
        }
        String emailid = email.getText().toString();

        protected String doInBackground(String... arg0)
        {
            try{

                URL url = new URL("http://sen6.herokuapp.com/sendmail");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("emailid",emailid);

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
            if (dialog.isShowing())
            {
                dialog.dismiss();
            }
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            //int res = Integer.parseInt(result);
            if(result.equals("true"))
            {
                AlertDialog alertDialog = new AlertDialog.Builder(Forgot_password.this).create();

                // Setting Dialog Title
                //alertDialog.setTitle("Forgot Password");

                // Setting Dialog Message
                alertDialog.setMessage("An Email with new Password has been sent to You !! ");

                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        Intent intent = new Intent(Forgot_password.this, LoginPage.class);
                        finish();
                        startActivity(intent);
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
            else if(result.equals("false"))
            {
                Toast.makeText(getApplicationContext(), "Please Enter Correct Email-Id.", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Please Enter Correct Email-Id." , Toast.LENGTH_LONG).show();
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

}
