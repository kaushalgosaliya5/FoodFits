package info.androidhive.floatinglabels;


import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.NotificationCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private EditText height, weight, name, fromDateEtxt, email, Age;
    private TextInputLayout inputname, inputheight, inputweight, inputemail, inputage, inputpassword;
    private Button cancel, save;
    private RadioButton male, female;
    private Spinner height_spinner, weight_spinner;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private TextView gen;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String username, emailid, gender, hei, wei, age, height_val, weight_val, pasword;
    private String height_text;
    private String weight_text;
    private String gnder;
    private String usrname , index;
    private String emaild;
    private EditText password;
    private String height1;
    private String weight1, steps;
    private double dweight, dheight, ans;
    private int dage, bm, step ;
    private UserSessionManager manager;


    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);


        try {

            name = (EditText) view.findViewById(R.id.input_name);
            email = (EditText) view.findViewById(R.id.input_email);
            height = (EditText) view.findViewById(R.id.input_height);
            weight = (EditText) view.findViewById(R.id.input_weight);
            password = (EditText) view.findViewById(R.id.input_password);
            Age = (EditText) view.findViewById(R.id.editText2);
            inputname = (TextInputLayout) view.findViewById(R.id.input_layout_name1);
            inputemail = (TextInputLayout) view.findViewById(R.id.input_layout_email);
            inputheight = (TextInputLayout) view.findViewById(R.id.input_layout_height);
            inputweight = (TextInputLayout) view.findViewById(R.id.input_layout_weight);
            inputage = (TextInputLayout) view.findViewById(R.id.input_layout_age);
            inputpassword = (TextInputLayout) view.findViewById(R.id.input_layout_password1);
            male = (RadioButton) view.findViewById(R.id.male);
            gen = (TextView) view.findViewById(R.id.Gender);
            radioSexGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            height_spinner = (Spinner) view.findViewById(R.id.spinner);
            weight_spinner = (Spinner) view.findViewById(R.id.spinner1);
            female = (RadioButton) view.findViewById(R.id.female);
            cancel = (Button) view.findViewById(R.id.btn1);
            save = (Button) view.findViewById(R.id.btn2);
            fromDateEtxt = (EditText) view.findViewById(R.id.editText2);
            fromDateEtxt.setInputType(InputType.TYPE_NULL);
            fromDateEtxt.requestFocus();
            manager = new UserSessionManager(getActivity());
            //ArrayList<String> barEntryArrayList = new ArrayList<>();

            String email_confirm = manager.getPreferences(getActivity(), "email");
            String password_confirm = manager.getPreferences(getActivity(), "password");

            String[] h = new String[]{
                    "CM",
                    "Feet"
            };

            // Initializing an ArrayAdapter
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_list, h);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_list);
            height_spinner.setAdapter(spinnerArrayAdapter);

            String[] w = new String[]{
                    "Kg",
                    "Pounds"
            };

            // Initializing an ArrayAdapter
            ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_list, w);
            spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_list);
            weight_spinner.setAdapter(spinnerArrayAdapter1);

            DB snappydb = DBFactory.open(getActivity(), "user");
            String[] keys = snappydb.findKeys("USER");
            for (int i = 0; i < keys.length; i++) {
                String[] info = snappydb.getArray(keys[i], String.class);
                if (info[1].equals(email_confirm) && info[2].equals(password_confirm)) {
                    index=keys[i];
                    username = info[0];
                    emailid = info[1];
                    pasword = info[2];
                    gender = info[6];
                    hei = info[4];
                    wei = info[5];
                    age = info[3];
                    steps = info[7];
                }

            }
            snappydb.close();
            int n = hei.indexOf(" ");
            height_val = hei.substring(0, n - 1);
            height_text = hei.substring(n+1);
            n = wei.indexOf(" ");
            weight_val = wei.substring(0,n - 1);
            weight_text = wei.substring(n);

            step = Integer.parseInt(steps);
            name.setText(username);
            email.setText(emailid);
            password.setText(pasword);
            height.setText(height_val.substring(0,height_val.length()-1));
            weight.setText(weight_val.substring(0,weight_val.length()-1));
            Age.setText(age);
            if (gender.equals("Male")) {
                male.setChecked(true);
                female.setChecked(false);
            } else {
                male.setChecked(false);
                female.setChecked(true);
            }

        } catch (SnappydbException e) {
        }

        //dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        fromDateEtxt = (EditText) view.findViewById(R.id.editText2);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        //setDateTimeField();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usrname = name.getText().toString();
                emaild = email.getText().toString();
                dheight = Double.parseDouble(height.getText().toString());
                dweight = Double.parseDouble(weight.getText().toString());
                dage = Integer.parseInt(Age.getText() + "");
                height_text = height_spinner.getSelectedItem().toString();
                pasword = password.getText().toString();
                weight_text = weight_spinner.getSelectedItem().toString();
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) view.findViewById(selectedId);
                gender = radioSexButton.getText().toString();
                height1 = dheight + " CM";
                weight1 = dweight + " Kg";



                if (height_text.equals("Feet"))
                    dheight = dheight * 30.48;
                if (weight_text.equals("Pounds"))
                    dweight = dweight * 0.453592;


                if (gender.equals("Male")) {
                    ans = (dheight * 0.394 * 12.7) + (dweight * 2.21 * 6.23) - (dage * 6.8) + 66;
                } else {
                    ans = (dheight * 0.394 * 4.7) + (dweight * 2.21 * 4.35) - (dage * 4.7) + 655;
                }

                bm = Integer.parseInt(Math.round(ans * 1.55) + "");

                Toast.makeText(getActivity(), bm + "", Toast.LENGTH_LONG).show();
                if (DetectConnection.checkInternetConnection(getActivity()))
                {
                    new SendPostRequest().execute();
                }
                else
                {
                    Toast.makeText(getActivity(), "You Do not have Internet Connection", Toast.LENGTH_LONG).show();
                    getActivity().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                }

            }
        });
      cancel.setOnClickListener(new View.OnClickListener()
      {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),MainActivity.class);
                getActivity().finish();
                startActivity(i);
            }
        });

        return view;
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {
            try {

                URL url = new URL("http://sen6.herokuapp.com/appuser/register/update");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("fname", usrname);
                postDataParams.put("uname", usrname);
                postDataParams.put("email", emaild);
                postDataParams.put("pass", pasword);
                postDataParams.put("age", dage);
                postDataParams.put("height", height1);
                postDataParams.put("weight", weight1);
                postDataParams.put("gender", gender);
                postDataParams.put("calin", bm);
                postDataParams.put("goalstep", step);
                Log.e("params", postDataParams.toString());

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

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            if(result.equals("1")) {

                try {
                    DB snappydb = DBFactory.open(getActivity(), "user");
                    String bmi = bm + "";
                    String steps = step + "";
                    String agee = dage + "";
                    snappydb.put(index, new String[]{usrname, emaild, pasword, agee, height1, weight1, gender, bmi, steps});
                    snappydb.close();
                } catch (SnappydbException e) {

                }
                manager.setPreferences(getActivity(), "status", "1");
                manager.setPreferences(getActivity(), "email", emaild);
                manager.setPreferences(getActivity(), "password", pasword);
                NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Food Fit")
                        .setContentText("Your BMI has been updated to " + bm + "Calories !!");

                Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(contentIntent);

                // Add as notification
                NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());
                Toast.makeText(getActivity(),"Your Profile has been Updated !!",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(),MainActivity.class);
                getActivity().finish();
                startActivity(i);
            }
            else
            {
                Toast.makeText(getActivity(),"Fail to Update",Toast.LENGTH_LONG).show();
            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
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
}
