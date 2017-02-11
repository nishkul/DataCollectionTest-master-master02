package com.android.test.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.test.interfaces.ICategoryTable;
import com.android.test.interfaces.IDatabase;
import com.android.test.interfaces.IEntryTable;

/**
 * Created by root on 9/2/17.
 */

public class DBHelper extends SQLiteOpenHelper implements IDatabase, ICategoryTable, IEntryTable {
    private static DBHelper databaseHelper;
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, COLUNM_VERSION);
        databaseHelper = this;
        this.mContext = context;


    }

    //database get lock that why we addedssynchronized
    public static synchronized DBHelper getInstance() {
        return databaseHelper;
    }

    public SQLiteDatabase writeDatabases() {
        return this.getWritableDatabase();
    }


    public SQLiteDatabase readDatabases() {
        return this.getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CATEGORY_TABLE);

        db.execSQL(SQL_CREATE_ENTRY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CATEGORY_TABLE);
        db.execSQL(SQL_DELETE_ENTRY_TABLE);
        onCreate(db);
    }
    public Cursor executeQuery(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(query, null);
        return res;
    }
}
