package info.androidhive.floatinglabels;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodDetail extends AppCompatActivity {

    String name, id,spaceid;
    TextView foodname,protein,fat,calories,carbo,vita,vitc,fiber,iron,serve;
    Button btnadd;
    float totalcalories=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodDetail.this.finish();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
           spaceid= name = id = null;
        } else {
            name = extras.getString("name");
            id = extras.getString("id");
            spaceid=extras.getString("spaceid");
        }


        foodname=(TextView) findViewById(R.id.foodname);
        protein=(TextView) findViewById(R.id.proteintext);
        fat=(TextView) findViewById(R.id.fattext);
        calories=(TextView) findViewById(R.id.caloriestext);
        carbo=(TextView) findViewById(R.id.carbotext);
        vita=(TextView) findViewById(R.id.vitatext);
        vitc=(TextView) findViewById(R.id.vitctext);
        fiber=(TextView) findViewById(R.id.fibertext);
        iron=(TextView) findViewById(R.id.irontext);
        serve=(TextView) findViewById(R.id.servetext);
        btnadd=(Button) findViewById(R.id.btnaddd22);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCalories();
            }
        });
        try {
            DB snappydb = DBFactory.open(getApplicationContext(),"caloriesDB");

            if (!snappydb.exists("totalcalories")) {
                snappydb.putFloat("totalcalories", 0);
            }
            else
            {
                totalcalories=snappydb.getFloat("totalcalories");
            }
            snappydb.close();

        } catch (SnappydbException e) {
        }

        Details();

    }

    void AddCalories() {
        totalcalories=Float.parseFloat(calories.getText().toString())+totalcalories;
        Toast.makeText(this,Float.toString(totalcalories), Toast.LENGTH_SHORT).show();
        try {
            DB snappydb = DBFactory.open(getApplicationContext(),"caloriesDB");
            snappydb.putFloat("totalcalories", totalcalories);
            snappydb.putFloat("tempdatacalories",Float.parseFloat(calories.getText().toString()));
            snappydb.put("tempdataname",name);
            Toast.makeText(this, "Food Added", Toast.LENGTH_SHORT).show();

            snappydb.put("tempdatadetail", new String[]{calories.getText().toString(),fat.getText().toString(),
                                                        protein.getText().toString(),carbo.getText().toString(),
                                                        vita.getText().toString(),vitc.getText().toString(),
                                                        fiber.getText().toString(),iron.getText().toString(),
                                                        serve.getText().toString(),name});
            snappydb.close();
        } catch (SnappydbException e) {
        }

        finish();
        FoodActivity.getInstance().finish();
    }

    void Details() {

        String tmpname;
        if(spaceid.equals("1"))
        {
            tmpname=name.replace(" ","");
//            Toast.makeText (FoodDetail.this,spaceid,Toast.LENGTH_LONG).show();


            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://sen6.herokuapp.com/user/food/getFoodInfo/" + tmpname + "/" + id;

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        calories.setText(response.getInt("nf_calories")+"");
                        fat.setText(response.getDouble("nf_total_fat")+"");
                        foodname.setText(name);
                        protein.setText(response.getDouble("nf_protein")+"");
                        carbo.setText(response.getDouble("nf_total_carbohydrate")+"");
                        vita.setText(response.getDouble("nf_vitamin_a_dv")+"");
                        vitc.setText(response.getDouble("nf_vitamin_c_dv")+"");
                        fiber.setText(response.getDouble("nf_dietary_fiber")+"");
                        iron.setText(response.getDouble("nf_iron_dv")+"");
                        serve.setText(response.getDouble("nf_serving_weight_grams")+"");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }

            });

            queue.add(jsonObjReq);

        }
        else {
            tmpname=name;
//            Toast.makeText (FoodDetail.this,spaceid,Toast.LENGTH_LONG).show();

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://sen6.herokuapp.com/user/food/getFoodInfo1/"+id+"/"+ id;

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        calories.setText(response.getInt("nf_calories")+"");
                        fat.setText(response.getDouble("nf_total_fat")+"");
                        foodname.setText(name);
                        protein.setText(response.getDouble("nf_protein")+"");
                        carbo.setText(response.getDouble("nf_total_carbohydrate")+"");
                        vita.setText(response.getDouble("nf_vitamin_a_dv")+"");
                        vitc.setText(response.getDouble("nf_vitamin_c_dv")+"");
                        fiber.setText(response.getDouble("nf_dietary_fiber")+"");
                        iron.setText(response.getDouble("nf_iron_dv")+"");
                        serve.setText(response.getDouble("nf_serving_weight_grams")+"");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }

            });

            queue.add(jsonObjReq);




        }


    }
}