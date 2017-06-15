package info.androidhive.floatinglabels;

/**
 * Created by SONY on 15-10-2016.
 */
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class caloriecounter extends AppCompatActivity {
    private Toolbar toolbar1;
    private EditText age, height, weight;
    private TextInputLayout inputage, inputheight, inputweight;
    private Button btn1,  next;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private boolean status=false;
    private Spinner height_spinner , weight_spinner;
    private  double dweight , dheight, ans;
    private String height_text , weight_text , gender , username, email,password ;
    private int bm , dage;

    static caloriecounter cal;

    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caloriecounter);
        toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);

        Register.getInstance().finish();
        cal=this;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        age = (EditText) findViewById(R.id.input_age);
        height = (EditText) findViewById(R.id.input_height);
        weight = (EditText) findViewById(R.id.input_weight);
        inputage = (TextInputLayout) findViewById(R.id.input_layout_age);
        inputheight = (TextInputLayout) findViewById(R.id.input_layout_height);
        inputweight = (TextInputLayout) findViewById(R.id.input_layout_weight);
        next = (Button) findViewById(R.id.next);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        height_spinner = (Spinner) findViewById(R.id.spinner);
        weight_spinner = (Spinner) findViewById(R.id.spinner1);

        String[] h = new String[]{
                "CM",
                "Feet"
        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_list,h
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_list);
        height_spinner.setAdapter(spinnerArrayAdapter);

        String[] w = new String[]{
                "Kg",
                "Pounds"
        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this,R.layout.spinner_list,w
        );
        spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_list);
        weight_spinner.setAdapter(spinnerArrayAdapter1);

        Intent intent = getIntent();

        username = intent.getStringExtra("Username");
        email = intent.getStringExtra("Email");
        password = intent.getStringExtra("Password");


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                status=validate();
                if(status == true)
                {
                    calculate();
                    Intent i = new Intent(caloriecounter.this, displaycalories.class);
                    i.putExtra("Username", username);
                    i.putExtra("Email", email);
                    i.putExtra("Password", password);
                    i.putExtra("Age",dage+"");
                    i.putExtra("Height",dheight+" CM");
                    i.putExtra("Weight",dweight+" Kg");
                    i.putExtra("Gender",gender);
                    i.putExtra("Total_Calories",""+bm);

                    startActivity(i);
                }
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void calculate()
    {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);


        dheight = Double.parseDouble(height.getText().toString());
        dweight = Double.parseDouble(weight.getText().toString());
        dage = Integer.parseInt(age.getText()+"");

        height_text = height_spinner.getSelectedItem().toString();
        weight_text = weight_spinner.getSelectedItem().toString();
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        gender = radioSexButton.getText().toString();

        if(height_text.equals("Feet"))
            dheight = dheight * 30.48;
        if(weight_text.equals("Pounds"))
            dweight = dweight*0.453592;


        if (gender.equals("Male"))
        {
            ans = (dheight * 0.394 * 12.7) + (dweight * 2.21 * 6.23) - (dage * 6.8) + 66;
        }
        else
        {
            ans = (dheight * 0.394 * 4.7) + (dweight * 2.21 * 4.35) - (dage * 4.7) + 655;
        }

        bm = Integer.parseInt(Math.round(ans * 1.55) + "");
    }
    private boolean validate() {
        String vage= age.getText().toString();
        String vheight=height.getText().toString();
        String vweight=weight.getText().toString();
        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);
        String gender = radioSexButton.getText().toString();
        if (vage.isEmpty())
        {
            inputage.setError(getString(R.string.err_msg_password));
            requestFocus(inputage);
            return false;
        }
        else if (vheight.isEmpty())
        {
            inputheight.setError(getString(R.string.err_msg_password));
            requestFocus(inputheight);
            return false;
        }
        else if (vweight.isEmpty() && gender.isEmpty())
        {
            inputweight.setError(getString(R.string.err_msg_password));
            requestFocus(inputweight);
            return false;
        }
        else if (gender.isEmpty())
        {
            Toast.makeText(caloriecounter.this, "Select gender ", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            inputage.setErrorEnabled(false);
            inputweight.setErrorEnabled(false);
            inputheight.setErrorEnabled(false);
            return true;
        }
    }

    private void requestFocus(View view)
    {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), Register.class);
        startActivityForResult(myIntent, 0);
        return true;

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("caloriecounter Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public static caloriecounter getInstance(){
        return cal;
    }


}