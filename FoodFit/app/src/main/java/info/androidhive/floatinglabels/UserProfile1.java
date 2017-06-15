package info.androidhive.floatinglabels;

/**
 * Created by SONY on 15-10-2016.
 */
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UserProfile1 extends AppCompatActivity implements View.OnClickListener {

    private EditText height, weight,name,fromDateEtxt , email,Age;

    private TextInputLayout inputname, inputheight, inputweight , inputemail , inputage;
    private Button cancel,save;
    private RadioButton male, female;
    private Spinner height_spinner , weight_spinner;
    private RadioGroup radioSexGroup;
    private TextView gen;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private String username , emailid , gender , hei , wei , age;
    private UserSessionManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile1);
        try {
            fromDateEtxt= (EditText) findViewById(R.id.editText2);
            name = (EditText) findViewById(R.id.input_name);
            email = (EditText) findViewById(R.id.input_email);
            height = (EditText) findViewById(R.id.input_height);
            weight = (EditText) findViewById(R.id.input_weight);
            Age = (EditText) findViewById(R.id.input_age);
            inputname = (TextInputLayout) findViewById(R.id.input_layout_name1);
            inputemail = (TextInputLayout) findViewById(R.id.input_layout_email);
            inputheight = (TextInputLayout) findViewById(R.id.input_layout_height);
            inputweight = (TextInputLayout) findViewById(R.id.input_layout_weight);
            inputage= (TextInputLayout) findViewById(R.id.input_layout_age);
            male = (RadioButton) findViewById(R.id.male);
            gen= (TextView) findViewById(R.id.Gender);
            radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
            height_spinner = (Spinner) findViewById(R.id.spinner);
            weight_spinner = (Spinner) findViewById(R.id.spinner1);
            female= (RadioButton) findViewById(R.id.female);
            cancel = (Button) findViewById(R.id.btn1);
            save = (Button) findViewById(R.id.btn2);
            manager = new UserSessionManager(getApplicationContext());
            //ArrayList<String> barEntryArrayList = new ArrayList<>();

            String email_confirm=manager.getPreferences(UserProfile1.this,"email");
            String password_confirm=manager.getPreferences(UserProfile1.this,"password");

            String[] h = new String[]{
                    "CM",
                    "Feet"
            };

            // Initializing an ArrayAdapter
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,R.layout.spinner_list,h);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_list);
            height_spinner.setAdapter(spinnerArrayAdapter);

            String[] w = new String[]{
                    "Kg",
                    "Pounds"
            };

            // Initializing an ArrayAdapter
            ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(this,R.layout.spinner_list,w);
            spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_list);
            weight_spinner.setAdapter(spinnerArrayAdapter1);

            DB snappydb = DBFactory.open(getApplicationContext(), "users");
            String[] keys = snappydb.findKeys("USER");
            for (int i=0;i<keys.length;i++)
            {
                String[] info = snappydb.getArray(keys[i],String.class);
                if(info[1].equals(email_confirm) && info[2].equals(password_confirm)) {
                    username = info[0];
                    emailid = info[1];
                    gender = info[6];
                    hei = info[4];
                    wei = info[5];
                    age = info[3];
                }

            }
            snappydb.close();

            name.setText(username);
            email.setText(emailid);
            height.setText(hei);
            weight.setText(wei);
            Age.setText(age);
        }
        catch (SnappydbException e)
        {
        }

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();
        setDateTimeField();


        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(UserProfile1.this,LoginPage.class);
                startActivity(i);
            }
        });
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.editText2);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View v) {

    }
}
