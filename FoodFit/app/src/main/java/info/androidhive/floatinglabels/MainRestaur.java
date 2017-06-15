package info.androidhive.floatinglabels;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aarti Radadiya on 23/11/2016.
 */

public class MainRestaur extends AppCompatActivity
{
    private String TAG = MainRestaur.class.getSimpleName();
    private ListView lv;
    private Restaurant[] r;
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurnt);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Restaurants");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainRestaur.this.finish();
            }
        });



        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] array1={r[i].getRid(),r[i].getRname(),r[i].getAddress(),r[i].getEmail(),r[i].getZipcode(),r[i].getCity(),r[i].getPhone()};
                Intent j = new Intent(getApplication(),RestaurantDetails.class);
                j.putExtra("key",array1);
                j.putExtra("key2",r[i].getFood_name());
                j.putExtra("key3",r[i].getFood_price());
                startActivity(j);
            }
        });

        new GetContacts().execute();
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainRestaur.this,"Data is downloading",Toast.LENGTH_LONG).show();
        }


        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://sen6.herokuapp.com/admin/rest/getrest";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {

                    // Getting JSON Array node
                    JSONArray contacts = new JSONArray(jsonStr);

                    r = new Restaurant[contacts.length()];

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {

                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("_id");
                        String name = c.getString("rest_name");
                        String email = c.getString("rest_email");
                        String address = c.getString("rest_address");
                        String phoneno = c.getString("phoneno");
                        //String website = c.getString("restraunt_website") + "www.google.com";
                        String city = c.getString("rest_city");
                        String zipcode = c.getString("zipcode");

                        JSONArray a = c.getJSONArray("rest_menu");
                        String food_name[] = new String[a.length()];
                        String food_price[] = new String[a.length()];

                        for(int p=0;p<a.length();p++)
                        {
                            JSONObject b = a.getJSONObject(p);
                            food_name[p] = b.getString("food_name");
                            food_price[p] = b.getString("food_price");
                        }

                        r[i] = new Restaurant();
                        r[i].setRid(id);
                        r[i].setZipcode(zipcode);
                        r[i].setEmail(email);
                        r[i].setRname(name);
                        r[i].setAddress(address);
                        r[i].setPhone(phoneno);
                        r[i].setCity(city);
                        r[i].setRest_menu(a.length(),food_name,food_price);

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        //                  contact.put("_id", id);
                        //                contact.put("name", name);
                        contact.put("rest_name", name);
                        contact.put("rest_address", address);

                        // adding contact to contact list
                        contactList.add(contact);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainRestaur.this, contactList,
                    R.layout.list_item, new String[]{ "rest_name","rest_address"},
                    new int[]{R.id.email, R.id.mobile});
            lv.setAdapter(adapter);
        }
    }

}
