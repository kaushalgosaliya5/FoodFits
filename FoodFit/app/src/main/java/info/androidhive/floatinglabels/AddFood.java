package info.androidhive.floatinglabels;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by admin on 15/10/2016.
 */
public class AddFood extends Activity {

    TextView tvLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);


        tvLink = (TextView)findViewById(R.id.txtAddFood);
        tvLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AddFood.this, FoodDetail.class);
                startActivity(intent);
            }
        });}
}
