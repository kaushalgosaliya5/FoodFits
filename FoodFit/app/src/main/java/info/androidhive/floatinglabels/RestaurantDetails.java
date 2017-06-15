package info.androidhive.floatinglabels;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RestaurantDetails extends AppCompatActivity {

    public TextView rname;
    public TextView rid;
    public TextView raddress;
    public TextView rzipcode;
    public TextView rphone;
    public TextView remail;
    public TextView rcity;
    private Button food_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Restaurant Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               RestaurantDetails.this.finish();
            }
        });






//        rid = (TextView)findViewById(R.id.rid);
        rname = (TextView)findViewById(R.id.rname);
        raddress = (TextView)findViewById(R.id.raddress);
        remail = (TextView)findViewById(R.id.remail);
        rzipcode = (TextView)findViewById(R.id.rzipcode);
        rcity = (TextView)findViewById(R.id.rcity);
        rphone = (TextView)findViewById(R.id.rphone);

        Bundle b = getIntent().getExtras();
        String[] detail = b.getStringArray("key");
        final String[] food_name = b.getStringArray("key2");
        final String[] food_price = b.getStringArray("key3");

        //      rid.setText(detail[0]);
        rname.setText(detail[1]);
        raddress.setText(detail[2]);
        remail.setText(detail[3]);
        rzipcode.setText(detail[4]);
        rcity.setText(detail[5]);
        rphone.setText(detail[6]);


        food_details = (Button)findViewById(R.id.FoodDetails);

        food_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(RestaurantDetails.this,FoodInformation.class);
                j.putExtra("fname",food_name);
                j.putExtra("fprice",food_price);
                startActivity(j);
            }
        });
    }
}
