package info.androidhive.floatinglabels;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import java.util.ArrayList;

public class BreakfastDetail extends AppCompatActivity {

    ListView breakfastlistview;
    ArrayList<String>breakfastarrayList=new ArrayList<String>();
    Button btnaddbreakfast;

    int cnt=0;
    ArrayAdapter adapter;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Breakfast");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BreakfastDetail.this.finish();
            }
        });
        breakfastlistview=(ListView) findViewById(R.id.breakfastfoodlist);
        btnaddbreakfast=(Button) findViewById(R.id.btnaddbreakfast);

        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
//            Toast.makeText(getApplication(),Integer.toString(snappydb.getInt("breakcount")),Toast.LENGTH_LONG).show();

//            String[] keyscal = snappydb.findKeys("breakcalorie");
//            Toast.makeText(getApplication(),Integer.toString(keyscal.length),Toast.LENGTH_LONG).show();
//            snappydb.del("breakdetail0");
//            snappydb.del("breakdetail1");
//            snappydb.del("breakdetail2");
//            snappydb.del("breakdetail3");
//            snappydb.del("breakdetail4");
//            snappydb.del("breakdetail5");
//            snappydb.del("breakcalorie1");
//            snappydb.del("breakcalorie2");
//            snappydb.del("breakcalorie3");
//            snappydb.del("breakcalorie4");
//            snappydb.del("breakcalorie5");
//            snappydb.del("breakfood1");
//            snappydb.del("breakfood2");
//            snappydb.del("breakfood3");
//            snappydb.del("breakfood4");
//            snappydb.del("breakfood5");
//              snappydb.del("breakcount");

            String[] keys = snappydb.findKeys("breakfood");
            if (!snappydb.exists("breakcount")) {
                snappydb.putInt("breakcount", 0);
            }
            if (!(keys.length >0)) {
                AddText();
            }
            else
            {
                AddText();
                AddData();
            }
            snappydb.close();
        } catch (SnappydbException e) {
        }


        btnaddbreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),FoodActivity.class);
                startActivity(i);

            }
        });
        breakfastlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
                DeleteData(pos);
                return true;
            }
        });


        breakfastlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i=new Intent(getApplicationContext(),ShowDetail.class);
                i.putExtra("pos",position);
                i.putExtra("activityid",1);
                startActivity(i);
            }
        });


    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            String[] keys = snappydb.findKeys("breakfood");
            if (snappydb.exists("tempdataname")) {
                AddCalories();
                return;
            }
            if (keys.length>0) {
                AddData();
            }
            snappydb.close();
        } catch (SnappydbException e) {
        }

    }

    void DeleteData(final int pos)
    {
        new AlertDialog.Builder(BreakfastDetail.this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        try {
                            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
                            String[] keys = snappydb.findKeys("breakfood");
                            String[] keyscal = snappydb.findKeys("breakcalorie");
                            String[] keysdetail = snappydb.findKeys("breakdetail");
                            float totcal=snappydb.getFloat("totalcalories");
                            totcal=totcal- snappydb.getFloat(keyscal[pos]);
                            snappydb.putFloat("totalcalories",totcal);

                            snappydb.del(keys[pos]);
                            snappydb.del(keyscal[pos]);
                            snappydb.del(keysdetail[pos]);
                            keys = snappydb.findKeys("breakfood");

                            if(keys.length<=0)
                            {
                                breakfastarrayList.clear();
                                adapter.clear();
                            }
                            snappydb.close();
                            AddData();
                        } catch (SnappydbException e) {
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    void AddData()
    {
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            String[] keys = snappydb.findKeys("breakfood");
            if (keys.length>0) {
                breakfastarrayList.clear();
                tv1.setVisibility(View.GONE);
                for (int i = 0; i < keys.length; i++) {
                    breakfastarrayList.add(snappydb.get(keys[i]));
                }
                adapter = new ArrayAdapter<String>(BreakfastDetail.this, android.R.layout.simple_list_item_1, breakfastarrayList);
                breakfastlistview.setAdapter(adapter);

            }
            else
            {
                AddText();
            }
            snappydb.close();
        } catch (SnappydbException e) {
        }

    }

    void AddText()
    {
        tv1 = new TextView(this);
        tv1.setText("No food record available");
        tv1.setTextSize(18);
        tv1.setGravity(Gravity.CENTER);
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.content_breakfast_detail);
        ll.addView(tv1);

    }


    void AddCalories() {
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            if (snappydb.exists("tempdataname")) {
                cnt = snappydb.getInt("breakcount");
                String strcnt=Integer.toString(cnt);
                String foodlist="breakfood"+ strcnt;
                String foodcalorie="breakcalorie"+ strcnt;
                String fooddetail="breakdetail"+ strcnt;
                snappydb.put(foodlist,snappydb.get("tempdataname"));
                snappydb.putFloat(foodcalorie,snappydb.getFloat("tempdatacalories"));

                String [] detail=snappydb.getArray("tempdatadetail",String.class);
                snappydb.put(fooddetail,new String[]{detail[0],detail[1],
                        detail[2],detail[3],
                        detail[4],detail[5],
                        detail[6],detail[7],
                        detail[8],detail[9]});
                cnt++;
                snappydb.putInt("breakcount",cnt);
                snappydb.del("tempdataname");
                snappydb.del("tempdatadetail");
            }
            snappydb.close();
            AddData();
        } catch (SnappydbException e) {
        }
    }

}
