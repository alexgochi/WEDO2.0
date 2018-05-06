package alexgochi.wedo20;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import alexgochi.wedo20.activity.ImportantActivity;
import alexgochi.wedo20.activity.SocialActivity;
import alexgochi.wedo20.activity.TodayActivity;
import alexgochi.wedo20.activity.TomorrowActivity;
import alexgochi.wedo20.activity.WorkActivity;
import alexgochi.wedo20.superb.Counter;

public class MainActivity extends Counter implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    TextView today_count, tomorrow_count, important_count, work_count, social_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_main);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void mPieChartLaunch(View view) {
        Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCountToday();
        today_count = (TextView) findViewById(R.id.main_today);
        today_count.setText(mCountToday + " List");

        getCountTomorrow();
        tomorrow_count = (TextView) findViewById(R.id.main_tomorrow);
        tomorrow_count.setText(mCountTomorrow + " List");

        getCountImportant();
        important_count = (TextView) findViewById(R.id.main_important);
        important_count.setText(mCountImportant + " List");

        getCountWork();
        work_count = (TextView) findViewById(R.id.main_work);
        work_count.setText(mCountWork + " List");

        getCountSocial();
        social_count = (TextView) findViewById(R.id.main_social);
        social_count.setText(mCountSocial + " List");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.today_act) {
            Intent in_today = new Intent(MainActivity.this, TodayActivity.class);
            startActivity(in_today);
            finish();
        } else if (id == R.id.tomorrow_act) {
            Intent in_tomorrow = new Intent(MainActivity.this, TomorrowActivity.class);
            startActivity(in_tomorrow);
            finish();
        } else if (id == R.id.important_act) {
            Intent in_important = new Intent(MainActivity.this, ImportantActivity.class);
            startActivity(in_important);
            finish();
        } else if (id == R.id.work_act) {
            Intent in_work = new Intent(MainActivity.this, WorkActivity.class);
            startActivity(in_work);
            finish();
        } else if (id == R.id.social_act) {
            Intent in_social = new Intent(MainActivity.this, SocialActivity.class);
            startActivity(in_social);
            finish();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void TodayActivity(View view) {
        Intent in_today = new Intent(MainActivity.this, TodayActivity.class);
        startActivity(in_today);
        finish();
    }

    public void TomorrowActivity(View view) {
        Intent in_tomorrow = new Intent(MainActivity.this, alexgochi.wedo20.activity.TomorrowActivity.class);
        startActivity(in_tomorrow);
        finish();
    }

    public void ImportantActivity(View view) {
        Intent in_important = new Intent(MainActivity.this, ImportantActivity.class);
        startActivity(in_important);
        finish();
    }

    public void WorkActivity(View view) {
        Intent in_work = new Intent(MainActivity.this, WorkActivity.class);
        startActivity(in_work);
        finish();
    }

    public void SocialActivity(View view) {
        Intent in_social = new Intent(MainActivity.this, SocialActivity.class);
        startActivity(in_social);
        finish();
    }
}