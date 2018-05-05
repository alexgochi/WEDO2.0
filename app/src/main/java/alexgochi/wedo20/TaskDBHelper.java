package alexgochi.wedo20;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alex Gochi on 07/03/2018.
 */

public class TaskDBHelper extends SQLiteOpenHelper {
    //Today
    private String createTableToday = "CREATE TABLE "+ TaskContract.TaskEntry.TABLE1 +" (" +
            TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TaskContract.TaskEntry.COL_TASK_TITLE1 + " TEXT NOT NULL);";

    //Tomorrow
    private String createTableTomorrow = "CREATE TABLE "+ TaskContract.TaskEntry.TABLE2 +" (" +
            TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TaskContract.TaskEntry.COL_TASK_TITLE2 + " TEXT NOT NULL);";

    //Important
    private String createTableImportant = "CREATE TABLE "+ TaskContract.TaskEntry.TABLE3 +" (" +
            TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TaskContract.TaskEntry.COL_TASK_TITLE3 + " TEXT NOT NULL);";

    //Work
    private String createTableWork = "CREATE TABLE "+ TaskContract.TaskEntry.TABLE4 +" (" +
            TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TaskContract.TaskEntry.COL_TASK_TITLE4 + " TEXT NOT NULL);";

    //Social
    private String createTableSocial = "CREATE TABLE "+ TaskContract.TaskEntry.TABLE5 +" (" +
            TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TaskContract.TaskEntry.COL_TASK_TITLE5 + " TEXT NOT NULL);";

    public TaskDBHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableToday);
        db.execSQL(createTableTomorrow);
        db.execSQL(createTableImportant);
        db.execSQL(createTableWork);
        db.execSQL(createTableSocial);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE1);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE2);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE3);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE4);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE5);
        onCreate(db);
    }
}
