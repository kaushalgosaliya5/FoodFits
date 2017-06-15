package info.androidhive.floatinglabels;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;


public class FoodTracker extends AppCompatActivity
{
    CardView cardView1,cardView2,cardView3,cardView4;
    Toolbar toolbar;
    DonutProgress donutProgress;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment2);


        cardView1 = (CardView) findViewById(R.id.breakfast);
        cardView2 = (CardView) findViewById(R.id.lunch);
        cardView3 = (CardView) findViewById(R.id.dinner);
        cardView4 = (CardView) findViewById(R.id.snacks);

        toolbar = (Toolbar) findViewById(R.id.toolbar9);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Choose Your Category");
        //getSupportActionBar().setTitle("Lunch");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FoodTracker.this.finish();

            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FoodTracker.this,BreakfastDetail.class);
                startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FoodTracker.this,LunchDetail.class);
                startActivity(intent);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FoodTracker.this,DinnerDetail.class);
                startActivity(intent);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(FoodTracker.this,SnacksDetail.class);
                startActivity(intent);
            }
        });


    }
}
