package com.codeclan.housework4.data;

import android.provider.BaseColumns;

/**
 * Created by David Hale on 20/03/2017.
 */

public final class TaskListContract {

    private TaskListContract() {}

    public static final class TasksEntry implements BaseColumns {

        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IS_COMLETED = "is_completed";

    }

    public static final class DaysEntry implements BaseColumns {

        public static final String TABLE_NAME = "days";
        public static final String COLUMN_DAY_NAME = "day_name";

    }

    public static final class DayTasksEntry implements BaseColumns {

        public static final String TABLE_NAME = "day_tasks";
        public static final String COLUMN_DAY_ID = "day_id";
        public static final String COLUMN_TASK_ID = "task_id";

    }
}
