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


public class LunchDetail extends AppCompatActivity {
    ListView lunchlistview;
    ArrayList<String> luncharrayList=new ArrayList<String>();
    Button btnaddlunch;
    ArrayAdapter adapter;
    int cnt=0;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lunch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LunchDetail.this.finish();
            }
        });
        lunchlistview=(ListView) findViewById(R.id.lunchfoodlist);
        btnaddlunch=(Button) findViewById(R.id.btnaddlunch);

        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
//            snappydb.del("lunchcalorie0");
//            snappydb.del("lunchcalorie1");
//            snappydb.del("lunchcalorie2");
//            snappydb.del("lunchcalorie3");
//            snappydb.del("lunchcalorie4");
//            snappydb.del("lunchcalorie5");
//            snappydb.del("lunchcalorie6");
//            snappydb.del("lunchcalorie7");
//            snappydb.del("lunchcalorie8");
//            snappydb.del("lunchcalorie9");
//            snappydb.del("lunchcalorie10");
//            snappydb.del("lunchcalorie11");
//            snappydb.del("lunchcount");

//            String[] keyscal = snappydb.findKeys("lunchcalorie");
//            Toast.makeText(getApplication(),Integer.toString(keyscal.length),Toast.LENGTH_LONG).show();

            String[] keys = snappydb.findKeys("lunchfood");
            if (!snappydb.exists("lunchcount")) {
                snappydb.putInt("lunchcount", 0);
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


        btnaddlunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),FoodActivity.class);
                startActivity(i);

            }
        });
        lunchlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long id) {
                DeleteData(pos);
                return true;
            }
        });

        lunchlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i=new Intent(getApplicationContext(),ShowDetail.class);
                i.putExtra("pos",position);
                i.putExtra("activityid",2);
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
            String[] keys = snappydb.findKeys("lunchfood");
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
        new AlertDialog.Builder(LunchDetail.this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        try {
                            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
                            String[] keys = snappydb.findKeys("lunchfood");
                            String[] keyscal = snappydb.findKeys("lunchcalorie");
                            String[] keysdetail = snappydb.findKeys("lunchdetail");
                            float totcal=snappydb.getFloat("totalcalories");
                            totcal=totcal- snappydb.getFloat(keyscal[pos]);
                            snappydb.putFloat("totalcalories",totcal);

                            snappydb.del(keys[pos]);
                            snappydb.del(keyscal[pos]);
                            snappydb.del(keysdetail[pos]);
                            keys = snappydb.findKeys("lunchfood");
                            if(keys.length<=0)
                            {

                                luncharrayList.clear();
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
            String[] keys = snappydb.findKeys("lunchfood");
            if (keys.length>0) {
                luncharrayList.clear();
                tv1.setVisibility(View.GONE);
                for (int i = 0; i < keys.length; i++) {
                    luncharrayList.add(snappydb.get(keys[i]));
                }
                adapter = new ArrayAdapter<String>(LunchDetail.this, android.R.layout.simple_list_item_1, luncharrayList);
                lunchlistview.setAdapter(adapter);
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
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.content_lunch_detail);
        ll.addView(tv1);

    }


    void AddCalories() {
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            if (snappydb.exists("tempdataname")) {
                cnt = snappydb.getInt("lunchcount");
                String strcnt=Integer.toString(cnt);
                String foodlist="lunchfood"+ strcnt;
                String foodcalorie="lunchcalorie"+ strcnt;
                String fooddetail="lunchdetail"+ strcnt;
                snappydb.put(foodlist,snappydb.get("tempdataname"));
                snappydb.putFloat(foodcalorie,snappydb.getFloat("tempdatacalories"));
                String [] detail=snappydb.getArray("tempdatadetail",String.class);
                snappydb.put(fooddetail,new String[]{detail[0],detail[1],
                        detail[2],detail[3],
                        detail[4],detail[5],
                        detail[6],detail[7],
                        detail[8],detail[9]});
                cnt++;
                snappydb.putInt("lunchcount",cnt);
                snappydb.del("tempdataname");
                snappydb.del("tempdatadetail");
            }
            snappydb.close();
            AddData();
        } catch (SnappydbException e) {
        }
    }



}
