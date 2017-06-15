package info.androidhive.floatinglabels;

import info.androidhive.floatinglabels.ui.Activity_Main;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import info.androidhive.floatinglabels.ui.Activity_Main;

public class    MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private UserSessionManager manager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = new UserSessionManager(getApplicationContext());


        if(displaycalories.getInstance()==null)
        {
         //do  nothing
        }
        else {
            displaycalories.getInstance().finish();
        }
        if(LoginPage.getInstance()==null)
        {
            //do  nothing
        }
        else {
            LoginPage.getInstance().finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.Home);
        onNavigationItemSelected(menuItem);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.Home:
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relative_layoutfrgmnt, homeFragment, homeFragment.getTag()).commit();
                break;
        }

        switch (id) {
            case R.id.fitness_tracker:
                Intent intent =new Intent(MainActivity.this, Activity_Main.class);
                startActivity(intent);
                break;

            case R.id.food_tracker:
                Intent foodintent =new Intent(MainActivity.this, FoodTracker.class);
                startActivity(foodintent);
                break;

            case R.id.restaurant:
                Intent resintent =new Intent(MainActivity.this, MainRestaur.class);
                startActivity(resintent);

                break;

            case R.id.report:
                Intent reportintent =new Intent(MainActivity.this, ReportActivity.class);
                startActivity(reportintent);
                break;

            case R.id.about:
                AboutFragment aboutFragment = new AboutFragment();
                FragmentManager aboutmanager = getSupportFragmentManager();
                aboutmanager.beginTransaction().replace(R.id.relative_layoutfrgmnt, aboutFragment, aboutFragment.getTag()).commit();
                break;

            case R.id.editPro:
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                FragmentManager editmanager = getSupportFragmentManager();
                editmanager.beginTransaction().replace(R.id.relative_layoutfrgmnt, editProfileFragment, editProfileFragment.getTag()).commit();
                break;



            case R.id.logout:
                manager.setPreferences(MainActivity.this, "status", "0");
                manager.setPreferences(MainActivity.this,"email","");
                manager.setPreferences(MainActivity.this,"password","");
                String status=manager.getPreferences(MainActivity.this,"status");
                Log.d("status", status);
                Intent loginintent = new Intent(MainActivity.this, LoginPage.class);
                startActivity(loginintent);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}






