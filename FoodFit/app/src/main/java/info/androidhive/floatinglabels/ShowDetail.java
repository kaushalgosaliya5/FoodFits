package info.androidhive.floatinglabels;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class ShowDetail extends AppCompatActivity {

    int pos,actiid;
    TextView foodname,protein,fat,calories,carbo,vita,vitc,fiber,iron,serve;
    private String[] details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        } else {
            pos = extras.getInt("pos");
            actiid=extras.getInt("activityid");
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


switch (actiid)
{
    case 1:
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            String[] keys = snappydb.findKeys("breakdetail");

            details=snappydb.getArray(keys[pos],String.class);

            snappydb.close();
        } catch (SnappydbException e) {
        }
        break;

    case 2:
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            String[] keys = snappydb.findKeys("lunchdetail");

            details=snappydb.getArray(keys[pos],String.class);

            snappydb.close();
        } catch (SnappydbException e) {
        }
        break;

    case 3:
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            String[] keys = snappydb.findKeys("dinnerdetail");
            details=snappydb.getArray(keys[pos],String.class);
            snappydb.close();
        } catch (SnappydbException e) {
        }
        break;

    case 4:
        try {
            DB snappydb = DBFactory.open(getApplicationContext(), "caloriesDB");
            String[] keys = snappydb.findKeys("snacksdetail");
            details=snappydb.getArray(keys[pos],String.class);
            snappydb.close();
        } catch (SnappydbException e) {
        }
        break;
}

        calories.setText(details[0]);
        fat.setText(details[1]);
        foodname.setText(details[9]);
        protein.setText(details[2]);
        carbo.setText(details[3]);
        vita.setText(details[4]);
        vitc.setText(details[5]);
        fiber.setText(details[6]);
        iron.setText(details[7]);
        serve.setText(details[8]);

    }
}
