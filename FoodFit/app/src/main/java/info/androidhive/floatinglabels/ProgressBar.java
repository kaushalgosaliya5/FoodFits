package info.androidhive.floatinglabels;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import com.github.lzyzsd.circleprogress.ArcProgress;

/**
 * Created by admin on 28/10/2016.
 */
public class ProgressBar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);


        ProgressBar1();
        ProgressBar2();

    }

    void ProgressBar1(){

        ArcProgress arcProgress = (ArcProgress) findViewById(R.id.arc_progress);

        arcProgress.setBottomText("Daily Steps");
        arcProgress.setBottomTextSize(20);
        arcProgress.setMax(10000);
        arcProgress.setProgress(6000);
        arcProgress.setTextColor(Color.parseColor("#FF5722"));
        arcProgress.setFinishedStrokeColor(Color.parseColor("#FF5722"));
        arcProgress.setUnfinishedStrokeColor(Color.parseColor("#FFCCBC"));
        arcProgress.setArcAngle(280);

        ObjectAnimator animation = ObjectAnimator.ofInt (arcProgress, "progress", 100, 6000); // see this max value coming back here, we animale towards that value
        animation.setDuration (5000); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());
        animation.start ();

    }

    void ProgressBar2(){

        ArcProgress arcProgress2 = (ArcProgress) findViewById(R.id.arc_progress2);

        arcProgress2.setBottomText("Daily Steps");
        arcProgress2.setBottomTextSize(20);
        arcProgress2.setMax(10000);
        arcProgress2.setProgress(6000);
        arcProgress2.setTextColor(Color.parseColor("#FF5722"));
        arcProgress2.setFinishedStrokeColor(Color.parseColor("#FF5722"));
        arcProgress2.setUnfinishedStrokeColor(Color.parseColor("#FFCCBC"));
        arcProgress2.setArcAngle(280);

        ObjectAnimator animation = ObjectAnimator.ofInt (arcProgress2, "progress", 100, 6000); // see this max value coming back here, we animale towards that value
        animation.setDuration (5000); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());
        animation.start ();
    }
}
