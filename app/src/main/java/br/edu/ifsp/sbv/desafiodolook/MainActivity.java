package br.edu.ifsp.sbv.desafiodolook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 1;
    public static JSONObject dataResult = new JSONObject();

    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("mYpREFERENCES_DDL",0);
        boolean isLogged = preferences.getBoolean("isLogged",false);

        if (isLogged) {
            setContentView(R.layout.activity_main);
            Log.d(TAG,"onCreate: starting in Main.");

            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
            TextView txtTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            txtTitle.setText("Desafios");
            txtTitle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/sweetsensations.ttf"));

            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);

            DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            setupFooterNavigationView();
        } else {
            Intent intent = new Intent(mContext, HomeActivity.class);
            mContext.startActivity(intent);
            Log.d(TAG, "onCreate: starting in Home.");
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences preferences = getSharedPreferences("mYpREFERENCES_DDL",0);
        boolean isLogged = preferences.getBoolean("isLogged",false);

        if (isLogged) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_green, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_looks) {
            Intent intent1 = new Intent(mContext, ProfileActivity.class);
            mContext.startActivity(intent1);
        } else if (id == R.id.nav_about) {
            Intent intent1 = new Intent(mContext, AboutActivity.class);
            mContext.startActivity(intent1);
        } else if (id == R.id.nav_logoff) {
            SharedPreferences preferences = getSharedPreferences("mYpREFERENCES_DDL", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isLogged", false);
            editor.commit();
            Intent intent1 = new Intent(mContext, HomeActivity.class);
            mContext.startActivity(intent1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupFooterNavigationView(){
        Log.d(TAG,"setupFooterNavigationView: setting up FooterNavigationView.");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.footerNavigation);
        FooterNavigationViewHelper.setupFooterNavigationView(bottomNavigationViewEx);
        FooterNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences preferences = getSharedPreferences("mYpREFERENCES_DDL",0);
        boolean isLogged = preferences.getBoolean("isLogged",false);

        if (!isLogged) {
            finish();
        }
    }
}