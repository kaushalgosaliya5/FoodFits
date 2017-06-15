package info.androidhive.floatinglabels;

//import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import info.androidhive.floatinglabels.ui.Activity_Main;

public class AppActivity extends Activity {

    Button edit_button , calorie_button , register , login,stepcounter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;


        edit_button = (Button) findViewById(R.id.button1);
        calorie_button = (Button) findViewById(R.id.button2);
        register = (Button) findViewById(R.id.button3);
        login = (Button) findViewById(R.id.button4);
        stepcounter = (Button) findViewById(R.id.button5);

        edit_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, UserProfile1.class);
                startActivity(intent);

            }

        });

        calorie_button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(context, caloriecounter.class);
                 startActivity(i);

            }

        });

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(context, Register.class);
                startActivity(i);

            }

        });

        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(context, LoginPage.class);
                startActivity(i);

            }

        });
        stepcounter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(context,Activity_Main.class);
                startActivity(i);

            }

        });

    }

}
