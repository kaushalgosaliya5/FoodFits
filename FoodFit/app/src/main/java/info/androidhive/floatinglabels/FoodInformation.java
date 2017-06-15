package info.androidhive.floatinglabels;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by admin on 15/11/2016.
 */
public class FoodInformation extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodetail);



        toolbar = (Toolbar) findViewById(R.id.toolbar12);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FoodInformation.this.finish();
            }
        });




        Intent i = getIntent();
        String[] fname = i.getStringArrayExtra("fname");
        String[] fprice = i.getStringArrayExtra("fprice");

        String[] detail = new String[fname.length];

        for(int j=0;j<fname.length;j++)
        {
           detail[j] =  fname[j]+ "\t\t\t\t\t\t\t" + fprice[j];
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item2,detail);
        ListView listView = (ListView) findViewById(R.id.list_food);
        listView.setAdapter(adapter);
  }
}
