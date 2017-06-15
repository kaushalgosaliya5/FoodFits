package info.androidhive.floatinglabels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;


public class Fragment_recom extends Fragment {

    ArrayList<String> FoodList = new ArrayList< String>();
    ArrayList<String> FoodIdList=new ArrayList<String>();
    String id;
    ArrayAdapter adapter;
    ListView listView;
    float consumecal,totalcal;
    String cal;
    CircularProgressView progressView;

    public Fragment_recom() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        try {
//            DB snappydb = DBFactory.open(getActivity(),"user");
//            String[] key=snappydb.findKeys("USER");
//            String [] details=snappydb.getArray(key[0],String.class);
//            String consume = details[7];
//            consumecal=Float.parseFloat(consume);
//            snappydb.close();

//        } catch (SnappydbException e) {
//        }

        try {
            DB snappydb = DBFactory.open(getActivity(),"caloriesDB");
            totalcal=snappydb.getFloat("totalcalories");
            consumecal=consumecal-totalcal;
            cal=Float.toString(consumecal);
            snappydb.close();

        } catch (SnappydbException e) {
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_fragment_recom, container, false);
        FoodList.clear();
        FoodIdList.clear();
        listView = (ListView) view.findViewById(R.id.foodlist2);



//        progressView = (CircularProgressView) getActivity().findViewById(R.id.progress_view);
//        progressView.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

//        progressView.startAnimation();

        String url = "http://sen6.herokuapp.com/user/recommend/3/200/1000";

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
//                    progressView.stopAnimation();
//                    progressView.setVisibility(View.INVISIBLE);
//

                   JSONArray cast = new JSONArray(response.toString());
                for (int i=0; i<cast.length(); i++) {
                    JSONObject actor = cast.getJSONObject(i);
                    FoodList.add(actor.getString("item_name"));
                    FoodIdList.add(actor.getString("_id"));
                }
                    ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, FoodList);
                    listView.setAdapter(adapter);
                }  catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText (getActivity(),"hello",Toast.LENGTH_LONG).show();
            }

        });

        // Adding request to request queue
        queue.add(jsonObjReq);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i=new Intent(getActivity(),FoodDetail.class);
                i.putExtra("name",FoodList.get(position));
                i.putExtra("id",FoodIdList.get(position));
                i.putExtra("spaceid","0");
                startActivity(i);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
