package info.androidhive.floatinglabels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_search extends Fragment {
    String search_food;
    ArrayList<String> FoodList = new ArrayList< String>();
    ArrayList<String> FoodIdList=new ArrayList<String>();
    String id;
    CircularProgressView progressView;

    public Fragment_search() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        final ListView listView = (ListView) getActivity().findViewById(R.id.foodlist);
        Button btn = (Button) getActivity().findViewById(R.id.btnsearch);
        final EditText ed = (EditText) getActivity().findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                search_food=ed.getText().toString();

                if (search_food.length()<=0)
                {
                    Toast.makeText(getActivity(),"Please enter search text",Toast.LENGTH_LONG).show();
                    return;
                }

                search_food=search_food.replace(" ","");
                FoodList.clear();
                FoodIdList.clear();

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                progressView = (CircularProgressView) getActivity().findViewById(R.id.progress_view);
                progressView.setVisibility(View.VISIBLE);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                progressView.startAnimation();

                String url = "http://sen6.herokuapp.com/user/food/searchfood/"+search_food;

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            progressView.stopAnimation();
                            progressView.setVisibility(View.INVISIBLE);

                            JSONArray fullarr = response.getJSONArray("arry");
                            for(int i=0;i<fullarr.length();i++){
                                JSONObject field = fullarr.getJSONObject(i);
                                JSONObject items = field.getJSONObject("fields");
                                FoodList.add(items.getString("item_name"));
                                FoodIdList.add(items.getString("item_id"));
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

                    }

                });

                // Adding request to request queue
                queue.add(jsonObjReq);
            }



        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i=new Intent(getActivity(),FoodDetail.class);
                i.putExtra("name",FoodList.get(position));
                i.putExtra("id",FoodIdList.get(position));
                i.putExtra("spaceid","1");
                startActivity(i);

            }
        });
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
