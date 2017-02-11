package com.android.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.test.interfaces.ICategoryTable;
import com.android.test.model.Category;

import java.util.ArrayList;

import static com.android.test.interfaces.ICategoryTable.COLUMN_CATEGORY_NAME;
import static com.android.test.interfaces.ICategoryTable.COLUMN_ID;
import static com.android.test.interfaces.ICategoryTable.SQL_SELECT_QUARY;

/**
 * Created by Manish on 9/2/17.
 */

public class CategoryHelper {

    private static Context context;
    ArrayList<Category> arrayList = new ArrayList();

    public CategoryHelper(Context context) {
        CategoryHelper.context = context;
        arrayList.add(new Category(1, "Theatre"));
        arrayList.add(new Category(2, "School"));
        arrayList.add(new Category(3, "Hospital"));
        arrayList.add(new Category(4, "Hotel"));
        arrayList.add(new Category(5, "Petrol Pump"));
        arrayList.add(new Category(6, "Govt Bldg"));
        arrayList.add(new Category(7, "Mall"));
        arrayList.add(new Category(8, "Park"));
        insertCategoryList(arrayList);
    }

    public long insertCategory(Category category) throws SQLException {
        SQLiteDatabase db = null;
        long rowid = -1;
        try {
            if (category != null) {
                if (db == null || !db.isOpen()) {
                    db = new DBHelper(context).writeDatabases();
                }

                ContentValues values = new ContentValues();
                values.put(COLUMN_CATEGORY_NAME, category.getName());


                rowid = db.insertWithOnConflict(ICategoryTable.TABLE, ICategoryTable.COLUMN_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
                return rowid;
            }

        } finally {
            if (db != null)
                db.close();
        }
        return rowid;
    }

    public void insertCategoryList(ArrayList<Category> list) {
        SQLiteDatabase db = null;
        if (db == null || !db.isOpen()) {
            db = new DBHelper(context).getWritableDatabase();
        }
        try {
            if (list.size() > 0) {
                for (Category category : list) {

                    ContentValues values = new ContentValues();
                    values.put(COLUMN_ID, category.getId());
                    values.put(COLUMN_CATEGORY_NAME, category.getName());
                    long result = db.insertWithOnConflict(ICategoryTable.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);

                    if (result != -1) {
                        Log.v("qqqq CategoryHelper ", "inserted " + " ");
                    } else {
                        Log.v("qqqq CategoryHelper ", "not inserted " + " ");
                    }
                }
            }
        } finally {
            if (db != null)
                db.close();
        }
    }


    public ArrayList<Category> getCategoryLists() {
        DBHelper mydb = new DBHelper(context);
        Cursor cursor = mydb.executeQuery(SQL_SELECT_QUARY);
        ArrayList<Category> list = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Category obj = new Category();
                    obj.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    obj.setName(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME)));

                    list.add(obj);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (mydb != null)
                mydb.close();
        }
        return list;
    }


}
