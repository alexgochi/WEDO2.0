package alexgochi.wedo20;

import android.provider.BaseColumns;

/**
 * Created by Alex Gochi on 07/03/2018.
 */

public class TaskContract {
    public static final String DB_NAME = "alexgochi.wedo.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        //Today
        public static final String TABLE1 = "today";
        public static final String COL_TASK_TITLE1 ="col_today";

        //Tomorrow
        public static final String TABLE2 = "tomorrow";
        public static final String COL_TASK_TITLE2 ="col_tomorrow";

        //Important
        public static final String TABLE3 = "important";
        public static final String COL_TASK_TITLE3 ="col_important";

        //Work
        public static final String TABLE4 = "work";
        public static final String COL_TASK_TITLE4 ="col_work";

        //Social
        public static final String TABLE5 = "social";
        public static final String COL_TASK_TITLE5 ="col_social";
    }
}
