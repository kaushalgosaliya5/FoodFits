package info.androidhive.floatinglabels;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
 * Created by SONY on 08-10-2016.
 */
public class Register extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText name,email, password,cpassword;
    private TextInputLayout inputEmail, inputPassword,inputname,inputcpassword;
    private Button btnregister;

    static Register registeractivity;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        registeractivity=this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        inputEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputname = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputcpassword = (TextInputLayout) findViewById(R.id.input_layout_confirm_password);
        inputPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        email = (EditText) findViewById(R.id.input_email);
        name = (EditText)findViewById(R.id.input_name);
        password = (EditText) findViewById(R.id.input_password);
        cpassword=(EditText)findViewById(R.id.input_confirm_password);
        btnregister = (Button) findViewById(R.id.btn_signup);


        email.addTextChangedListener(new MyTextWatcher(email));
        password.addTextChangedListener(new MyTextWatcher(password));
        name.addTextChangedListener(new MyTextWatcher(name));
        cpassword.addTextChangedListener(new MyTextWatcher(cpassword));

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateCpassword();
               submitForm();
            }
        });

    }

    private void submitForm()
    {
        if (validateEmail() && validatePassword() && validateCpassword() && validateName()  )
        {
            if (DetectConnection.checkInternetConnection(Register.this))
            {
                new SendPostRequest().execute();
            }
            else
            {
                Toast.makeText(Register.this, "You Do not have Internet Connection", Toast.LENGTH_LONG).show();
                Register.this.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        }
    }


    private boolean validateEmail() {
        String Vemail = email.getText().toString().trim();

        if (Vemail.isEmpty() || !isValidEmail(Vemail)) {
            inputEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        String vpassword = password.getText().toString();
        if (vpassword.isEmpty()) {
            inputPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else
        {
            inputPassword.setErrorEnabled(false);
            return true;
        }

    }
    private boolean validateName() {
        if (name.getText().toString().trim().isEmpty()) {
            inputname.setError(getString(R.string.err_msg_name));
            requestFocus(inputname);
            return false;
        } else {
            inputname.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateCpassword()
    {
        String pass = password.getText().toString();
        String cpass = cpassword.getText().toString();
        if(! pass.equals(cpass))
        {
            inputcpassword.setErrorEnabled(true);
            inputcpassword.setError(getString(R.string.err_msg_cpassword));
            requestFocus(inputcpassword);
            return false;
        }
        else
        {
            inputcpassword.setErrorEnabled(false);
            return true;
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_name:
                    validateName();
                    break;
            }
        }

    }

    public class SendPostRequest extends AsyncTask<String, Void, String>
    {
        private ProgressDialog dialog = new ProgressDialog(Register.this);
        protected void onPreExecute()
        {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }
        String emailid = email.getText().toString();
        String username = name.getText().toString();
        String pasword = password.getText().toString();
        String cpasswrd = cpassword.getText().toString();

        protected String doInBackground(String... arg0)
        {
            try{

                URL url = new URL("http://sen6.herokuapp.com/appuser/login/appvalidate");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("email",emailid);
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
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
            if (dialog.isShowing())
            {
                dialog.dismiss();
            }
            if(result.equalsIgnoreCase("0"))
            {
                Toast.makeText(getApplicationContext(), "Username or Email already taken.", Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent i = new Intent(Register.this,caloriecounter.class);
                i.putExtra("Username", username);
                i.putExtra("Email", emailid);
                i.putExtra("Password", pasword);

                startActivity(i);
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

    public static Register getInstance(){
        return  registeractivity;
    }
}


