package info.androidhive.floatinglabels;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class splashActivity extends AppCompatActivity {
    UserSessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        manager=new UserSessionManager(getApplicationContext());

        /****** Create Thread that will sleep for 5 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 3 seconds
                    sleep(5*1000);

                    // After 5 seconds redirect to another intent
                    String status=manager.getPreferences(splashActivity.this,"status");
                    Log.d("status",status);
                    if (status.equals("1")){
                        Intent i=new Intent(splashActivity.this,MainActivity.class);
                        startActivity(i);
                    }else{
                        Intent i=new Intent(splashActivity.this,LoginPage.class);
                        startActivity(i);
                    }
                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();


    }

    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
    }

}
