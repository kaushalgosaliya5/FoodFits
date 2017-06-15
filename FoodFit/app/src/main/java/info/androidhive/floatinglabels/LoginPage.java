package info.androidhive.floatinglabels;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class LoginPage extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText email, password;
    private TextInputLayout inputEmail, inputPassword;
    private Button btnlogin;
    private TextView signup , forgetpass;
    private UserSessionManager manager;

    static LoginPage loginPage;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        loginPage=this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FoodFit");
        setSupportActionBar(toolbar);
        inputEmail = (TextInputLayout) findViewById(R.id.layout_email);
        inputPassword = (TextInputLayout) findViewById(R.id.layout_password);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        btnlogin = (Button) findViewById(R.id.btn_login);
        signup=(TextView) findViewById(R.id.textView3);
        forgetpass=(TextView) findViewById(R.id.textView2);
        email.setText("");
        password.setText("");
        email.addTextChangedListener(new MyTextWatcher(email));
        password.addTextChangedListener(new MyTextWatcher(password));
        manager = new UserSessionManager(getApplicationContext());

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        signup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginPage.this,Register.class);

                startActivity(intent);
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginPage.this,Forgot_password.class);

                startActivity(intent);
            }
        });

    }
    public static LoginPage getInstance(){
        if( loginPage == null )
        {
            return null;
        }
        return loginPage;
    }
    public class SendPostRequest extends AsyncTask<String, Void, String>
    {

        private ProgressDialog dialog = new ProgressDialog(LoginPage.this);
        protected void onPreExecute()
        {
            this.dialog.setMessage("Please wait ");
            this.dialog.show();
        }
        String emailid = email.getText().toString();
        String passwrd = password.getText().toString();
        protected String doInBackground(String... arg0)
        {
            try{

                URL url = new URL("http://sen6.herokuapp.com/appuser/login/validateAppLogin");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("email",emailid);
                postDataParams.put("password",passwrd);
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
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            //int res = Integer.parseInt(result);
            if(result.equals("true"))
            {
                manager.setPreferences(LoginPage.this, "status", "1");
                manager.setPreferences(LoginPage.this, "email", emailid);
                manager.setPreferences(LoginPage.this, "password", passwrd);
                String status=manager.getPreferences(LoginPage.this,"status");

                Log.d("status", status);
                Intent i=new Intent(LoginPage.this,MainActivity.class);
                startActivity(i);

//                Toast.makeText(getApplicationContext(), "Login Successful !", Toast.LENGTH_LONG).show();
//                Intent i = new Intent(LoginPage.this,MainActivity.class);
//                startActivity(i);
            }
            else if(result.equals("false"))
            {
                Toast.makeText(getApplicationContext(), "Incorrect Email id or Password. Please Enter Correct Credentials.", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Incorrect Email id or Password. Please Enter Correct Credentials." , Toast.LENGTH_LONG).show();
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

    private void submitForm()
    {
        if (validateEmail() && validatePassword())
        {
            if (DetectConnection.checkInternetConnection(LoginPage.this))
            {
                new SendPostRequest().execute();
            }
            else
            {
                Toast.makeText(LoginPage.this, "You Do not have Internet Connection", Toast.LENGTH_LONG).show();
                LoginPage.this.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
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
        if (password.getText().toString().trim().isEmpty()) {
            inputPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputPassword.setErrorEnabled(false);
        }

        return true;
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
            }
        }
    }
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
    }
}


