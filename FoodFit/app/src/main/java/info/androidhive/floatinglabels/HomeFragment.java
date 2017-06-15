package info.androidhive.floatinglabels;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import info.androidhive.floatinglabels.SensorListener;
import info.androidhive.floatinglabels.ui.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private CardView pedometer_card , food_card , rest;
    private ArcProgress arcProgress1,arcProgress2;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        pedometer_card = (CardView) view.findViewById(R.id.card_view);
        food_card = (CardView) view.findViewById(R.id.card_view1);
        rest = (CardView) view.findViewById(R.id.card_view2);

        arcProgress1 = (ArcProgress)view.findViewById(R.id.arc_progress1);
        arcProgress2 = (ArcProgress)view.findViewById(R.id.arc_progress2);

        pedometer_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),Activity_Main.class);
                startActivity(intent);
            }
        });

        food_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent_it = new Intent(getActivity(),FoodTracker.class);
                startActivity(intent_it);
            }
        });

        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent_it = new Intent(getActivity(),MainRestaur.class);
                startActivity(intent_it);
            }
        });

        ProgressBar1();
        ProgressBar2();
        return view;
    }
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        ProgressBar1();
        ProgressBar2();
    }
    void ProgressBar1(){
        int steps = 0;
        try {
            DB snappydb = DBFactory.open(getActivity(), "caloriesDB");
            steps = snappydb.getInt("stepsuser");
            snappydb.close();
        } catch (SnappydbException e) {
        }
        arcProgress1.setBottomText("Steps");
        arcProgress1.setBottomTextSize(50);
        arcProgress1.setMax(10000);
        arcProgress1.setProgress(steps);
        arcProgress1.setTextColor(Color.parseColor("#FF5722"));
        arcProgress1.setFinishedStrokeColor(Color.parseColor("#FF5722"));
        arcProgress1.setUnfinishedStrokeColor(Color.parseColor("#FFCCBC"));
        arcProgress1.setArcAngle(280);
        arcProgress1.setSuffixText("");

        ObjectAnimator animation = ObjectAnimator.ofInt (arcProgress1, "progress", 0, steps); // see this max value coming back here, we animale towards that value
        animation.setDuration (500); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());
        animation.start ();

    }

    void ProgressBar2(){
        int calorie = 0;
        int consumecal = 180;
        String consume;
        try {
            DB snappydb = DBFactory.open(getActivity(), "caloriesDB");
            float temp = snappydb.getFloat("totalcalories");
            calorie=(int) temp;
            snappydb.close();
        } catch (SnappydbException e) {
        }

//        try {
//            DB snappydb = DBFactory.open(getActivity(),"user");
//            String[] key=snappydb.findKeys("USER");
//            String [] details=snappydb.getArray(key[0],String.class);
//            consume = details[7];
//            consumecal=Integer.parseInt(consume);
//            snappydb.close();
//        } catch (SnappydbException e) {
//        }
//if (consumecal==calorie) {
//    NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(getActivity()).setSmallIcon(R.drawable.ic_launcher)
//            .setContentTitle("Food Fit").setContentText("You have reached you calorie limit");
//    Intent notificationIntent = new Intent(getActivity(), MainActivity.class);
//    PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//    builder.setContentIntent(contentIntent);
//}
        arcProgress2.setBottomText("Calories");
        arcProgress2.setBottomTextSize(50);
        arcProgress2.setMax(10000);
        arcProgress2.setProgress((int) calorie);
        arcProgress2.setTextColor(Color.parseColor("#FF5722"));
        arcProgress2.setFinishedStrokeColor(Color.parseColor("#FF5722"));
        arcProgress2.setUnfinishedStrokeColor(Color.parseColor("#FFCCBC"));
        arcProgress2.setArcAngle(280);
        arcProgress2.setSuffixText("");

        ObjectAnimator animation = ObjectAnimator.ofInt (arcProgress2, "progress", 0, calorie); // see this max value coming back here, we animale towards that value
        animation.setDuration (500); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());
        animation.start ();
    }


    }
