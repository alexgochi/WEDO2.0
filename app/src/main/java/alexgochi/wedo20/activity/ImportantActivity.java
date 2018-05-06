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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ImportantActivity extends Counter implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    ImageView imageImportant;
    private SwipeMenuListView Limportant;
    private ArrayAdapter<String> mAdapter;
    TextView important;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_important);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_important);
        navigationView.setNavigationItemSelectedListener(this);

        imageImportant = (ImageView) findViewById(R.id.important);
        imageImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_list();
            }
        });

        Limportant = (SwipeMenuListView) findViewById(R.id.list_important);
//        Limportant.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
//
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0xCC, 0x00)));
//                // set item width
//                deleteItem.setWidth(80);
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_done);
//                // add to menu
//                menu.addMenuItem(deleteItem);
//            }
//        };
//        // set creator
//        Limportant.setMenuCreator(creator);
//
//        Limportant.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        deleteTask();
//                        Toast.makeText(getApplicationContext(), "List Completed", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                // false : close the menu; true : not close the menu
//                return false;
//            }
//        });
        updateUI();
    }

    protected void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE3,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE3},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE3);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.add_todo, R.id.task_title, taskList);
            Limportant.setAdapter(mAdapter);

        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        getCountImportant();
        important = (TextView) findViewById(R.id.total_important);
        important.setText("Total List : "+ mCountImportant);

        cursor.close();
        db.close();
    }

    public void add_list() {
        LayoutInflater inflater = ImportantActivity.this.getLayoutInflater();
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
                            values.put(TaskContract.TaskEntry.COL_TASK_TITLE3, mNoteTodo);
                            db.insertWithOnConflict(TaskContract.TaskEntry.TABLE3,
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

    public void deleteTask(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you done?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                View parent = (View) view.getParent();
                TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
                String task = String.valueOf(taskTextView.getText());
                SQLiteDatabase db = mHelper.getWritableDatabase();
                db.delete(TaskContract.TaskEntry.TABLE3,
                        TaskContract.TaskEntry.COL_TASK_TITLE3+ " = ?",
                        new String[]{task});
                db.close();
                updateUI();
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

    public void deleteAllTask() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + TaskContract.TaskEntry.TABLE3);
        db.close();
        updateUI();
    }

    @Override
    public void onBackPressed() {
        Intent intent_tomorrow = new Intent(ImportantActivity.this, MainActivity.class);
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
            Intent in_today = new Intent(ImportantActivity.this, TodayActivity.class);
            Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_SHORT).show();
            startActivity(in_today);
            finish();
        } else if (id == R.id.tomorrow_act) {
            Intent in_tomorrow = new Intent(ImportantActivity.this, TomorrowActivity.class);
            Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_SHORT).show();
            startActivity(in_tomorrow);
            finish();
        } else if (id == R.id.important_act) {
            Toast.makeText(getApplicationContext(), "You're in Important Activity", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.work_act) {
            Intent in_work = new Intent(ImportantActivity.this, WorkActivity.class);
            Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_SHORT).show();
            startActivity(in_work);
            finish();
        } else if (id == R.id.social_act) {
            Intent in_social = new Intent(ImportantActivity.this, SocialActivity.class);
            Toast.makeText(getApplicationContext(), "Swipe Right to Delete", Toast.LENGTH_SHORT).show();
            startActivity(in_social);
            finish();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_important);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
