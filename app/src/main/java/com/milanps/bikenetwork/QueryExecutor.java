package com.milanps.bikenetwork;

import android.database.sqlite.SQLiteDatabase;

public interface QueryExecutor {
    void run(SQLiteDatabase database);
}
