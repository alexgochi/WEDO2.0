package alexgochi.wedo20.superb;

import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import alexgochi.wedo20.TaskContract;
import alexgochi.wedo20.TaskDBHelper;

public abstract class Counter extends AppCompatActivity {
    public TaskDBHelper mHelper = new TaskDBHelper(Counter.this);
    public int mCountToday = 0;
    public int mCountTomorrow = 0;
    public int mCountImportant = 0;
    public int mCountWork = 0;
    public int mCountSocial = 0;

    public void getCountToday() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE1;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCountToday = cursor.getInt(0);
        }
        cursor.close();
    }

    public void getCountTomorrow() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE2;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCountTomorrow = cursor.getInt(0);
        }
        cursor.close();
    }

    public void getCountImportant() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE3;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCountImportant = cursor.getInt(0);
        }
        cursor.close();
    }

    public void getCountWork() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE4;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCountWork = cursor.getInt(0);
        }
        cursor.close();
    }

    public void getCountSocial() {
        String sql = "SELECT COUNT(*) FROM " + TaskContract.TaskEntry.TABLE5;
        Cursor cursor = mHelper.getReadableDatabase().rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            mCountSocial = cursor.getInt(0);
        }
        cursor.close();
    }
}
