package alexgochi.wedo20.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.Objects;

import alexgochi.wedo20.MainActivity;
import alexgochi.wedo20.R;
import alexgochi.wedo20.TaskContract;
import alexgochi.wedo20.superb.Counter;

public class TomorrowActivity extends Counter implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    ImageView imageTomorrow;
    private SwipeMenuListView LTomorrow;
    private ArrayAdapter<String> mAdapter;
    TextView tomorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomorrow);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_tomorrow);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_tomorrow);
        navigationView.setNavigationItemSelectedListener(this);

        imageTomorrow = (ImageView) findViewById(R.id.tomorrow);
        imageTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_list();
            }
        });

        LTomorrow = (SwipeMenuListView) findViewById(R.id.list_tomorrow);
        LTomorrow.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0xCC, 0x00)));
                // set item width
                deleteItem.setWidth(80);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_done);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        LTomorrow.setMenuCreator(creator);

        LTomorrow.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deleteTask();
                        Toast.makeText(getApplicationContext(), "List Completed", Toast.LENGTH_SHORT).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        updateUI();
    }

    protected void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE2,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE2},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE2);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.add_todo, R.id.task_title, taskList);
            LTomorrow.setAdapter(mAdapter);

        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        getCountTomorrow();
        tomorrow = (TextView) findViewById(R.id.total_tomorrow);
        tomorrow.setText("Total List : " + mCountTomorrow);

        cursor.close();
        db.close();
    }

    public void add_list() {
        LayoutInflater inflater = TomorrowActivity.this.getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.add_activity, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogLayout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText mNote = dialogLayout.findViewById(R.id.note);
                        if (mNote.getText().toString().equals("")) {
                            showToast();
                        } else {
                            String mNoteTodo = mNote.getText().toString();
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(TaskContract.TaskEntry.COL_TASK_TITLE2, mNoteTodo);
                            db.insertWithOnConflict(TaskContract.TaskEntry.TABLE2,
                                    null, values, SQLiteDatabase.CONFLICT_REPLACE);
                            db.close();
                            updateUI();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void showToast() {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.input_todo, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void deleteTask() {
        TextView taskTextView = (TextView) findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE2,
                TaskContract.TaskEntry.COL_TASK_TITLE2+ " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    public void deleteAllTask() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + TaskContract.TaskEntry.TABLE2);
        db.close();
        updateUI();
    }

    @Override
    public void onBackPressed() {
        Intent intent_tomorrow = new Intent(TomorrowActivity.this, MainActivity.class);
        startActivity(intent_tomorrow);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_clear) {
            deleteAllTask();
            Toast.makeText(getApplicationContext(), "All List deleted", Toast.LENGTH_SHORT).show();
            return true;
        }

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.today_act) {
            Intent intent_today = new Intent(TomorrowActivity.this, TodayActivity.class);
            Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_SHORT).show();
            startActivity(intent_today);
            finish();
        } else if (id == R.id.tomorrow_act) {
            Toast.makeText(getApplicationContext(), "You're in Tomorrow Activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.important_act) {
            Intent in_important = new Intent(TomorrowActivity.this, ImportantActivity.class);
            Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_SHORT).show();
            startActivity(in_important);
            finish();
        } else if (id == R.id.work_act) {
            Intent in_work = new Intent(TomorrowActivity.this, WorkActivity.class);
            Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_SHORT).show();
            startActivity(in_work);
            finish();
        } else if (id == R.id.social_act) {
            Intent in_social = new Intent(TomorrowActivity.this, SocialActivity.class);
            Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_SHORT).show();
            startActivity(in_social);
            finish();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_tomorrow);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
