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

public class SnacksDetail extends AppCompatActivity {


    ListView snackslistview;
    ArrayList<String> snacksarrayList = new ArrayList<String>();
    Button btnaddsnacks;
    ArrayAdapter adapter;
    int cnt = 0;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Snacks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SnacksDetail.this.finish();
            }
        });

        snackslistview = (ListView) findViewById(R.id.snacksfoodlist);
        btnaddsnacks = (Button) findViewById(R.id.btnaddsnacks);

        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");

//            snappydb.del("snackscalorie0");
//            snappydb.del("snackscalorie1");
//            snappydb.del("snackscalorie2");
//            snappydb.del("snackscalorie3");
//            snappydb.del("snackscalorie4");
//            snappydb.del("snackscalorie5");
//            snappydb.del("snackscalorie6");
//            snappydb.del("snackscalorie7");
//            snappydb.del("snackscalorie8");
//            snappydb.del("snackscalorie9");
//            snappydb.del("snackscalorie10");
//            snappydb.del("snackscalorie11");
//            snappydb.del("snackscount");

//            String[] keyscal = snappydb.findKeys("snackscalorie");
//            Toast.makeText(getApplication(),Integer.toString(keyscal.length),Toast.LENGTH_LONG).show();

            String[] keys = snappydb.findKeys("snacksfood");
            if (!snappydb.exists("snackscount")) {
                snappydb.putInt("snackscount", 0);
            }
            if (!(keys.length > 0)) {
                AddText();
            } else {
                AddText();
                AddData();
            }
            snappydb.close();
        } catch (SnappydbException e) {
        }


        btnaddsnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FoodActivity.class);
                startActivity(i);

            }
        });
        snackslistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                DeleteData(pos);
                return true;
            }
        });


        snackslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i=new Intent(getApplicationContext(),ShowDetail.class);
                i.putExtra("pos",position);
                i.putExtra("activityid",4);
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            String[] keys = snappydb.findKeys("snacksfood");
            if (snappydb.exists("tempdataname")) {
                AddCalories();
                return;
            }
            if (keys.length > 0) {
                AddData();
            }
            snappydb.close();
        } catch (SnappydbException e) {
        }

    }

    void DeleteData(final int pos) {
        new AlertDialog.Builder(SnacksDetail.this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        try {
                            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
                            String[] keys = snappydb.findKeys("snacksfood");
                            String[] keyscal = snappydb.findKeys("snackscalorie");
                            String[] keysdetail = snappydb.findKeys("snacksdetail");
                            float totcal=snappydb.getFloat("totalcalories");
                            totcal=totcal- snappydb.getFloat(keyscal[pos]);
                            snappydb.putFloat("totalcalories",totcal);
                            snappydb.del(keys[pos]);
                            snappydb.del(keyscal[pos]);
                            snappydb.del(keysdetail[pos]);
                            keys = snappydb.findKeys("snacksfood");
                            if (keys.length <= 0) {

                                snacksarrayList.clear();
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


    void AddData() {
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            String[] keys = snappydb.findKeys("snacksfood");
            if (keys.length > 0) {
                snacksarrayList.clear();
                tv1.setVisibility(View.GONE);
                for (int i = 0; i < keys.length; i++) {
                    snacksarrayList.add(snappydb.get(keys[i]));
                }
                adapter = new ArrayAdapter<String>(SnacksDetail.this, android.R.layout.simple_list_item_1, snacksarrayList);
                snackslistview.setAdapter(adapter);
            } else {
                AddText();
            }
            snappydb.close();
        } catch (SnappydbException e) {
        }

    }

    void AddText() {
        tv1 = new TextView(this);
        tv1.setText("No food record available");
        tv1.setTextSize(18);
        tv1.setGravity(Gravity.CENTER);
        RelativeLayout ll = (RelativeLayout) findViewById(R.id.content_snacks_detail);
        ll.addView(tv1);

    }


    void AddCalories() {
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            if (snappydb.exists("tempdataname")) {
                cnt = snappydb.getInt("snackscount");
                String strcnt = Integer.toString(cnt);
                String foodlist = "snacksfood" + strcnt;
                String foodcalorie = "snackscalorie" + strcnt;
                String fooddetail="snacksdetail"+ strcnt;
                snappydb.put(foodlist, snappydb.get("tempdataname"));
                snappydb.putFloat(foodcalorie, snappydb.getFloat("tempdatacalories"));
//                Toast.makeText(this, "Calories added: "+Float.toString(snappydb.getFloat("tempdatacalories")), Toast.LENGTH_SHORT).show();
                String [] detail=snappydb.getArray("tempdatadetail",String.class);
                snappydb.put(fooddetail,new String[]{detail[0],detail[1],
                        detail[2],detail[3],
                        detail[4],detail[5],
                        detail[6],detail[7],
                        detail[8],detail[9]});
                cnt++;
                snappydb.putInt("snackscount", cnt);
                snappydb.del("tempdataname");
                snappydb.del("tempdatadetail");
            }
            snappydb.close();
            AddData();
        } catch (SnappydbException e) {
        }
    }
}
