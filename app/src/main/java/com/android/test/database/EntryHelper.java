package com.android.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.android.test.interfaces.IEntryTable;
import com.android.test.model.Entry;

import java.util.ArrayList;

import static com.android.test.interfaces.IEntryTable.COLUMN_ADDRESS;
import static com.android.test.interfaces.IEntryTable.COLUMN_CATEGORY_ID;
import static com.android.test.interfaces.IEntryTable.COLUMN_DESCRIPTION;
import static com.android.test.interfaces.IEntryTable.COLUMN_ID;
import static com.android.test.interfaces.IEntryTable.COLUMN_IMAGE;
import static com.android.test.interfaces.IEntryTable.COLUMN_LANG;
import static com.android.test.interfaces.IEntryTable.COLUMN_LAT;
import static com.android.test.interfaces.IEntryTable.COLUMN_SENDTOSERVER_ID;
import static com.android.test.interfaces.IEntryTable.COLUMN_TITLE;
import static com.android.test.interfaces.IEntryTable.SQL_SELECT_QUARY;


public class EntryHelper {

    private static Context context;

    public EntryHelper(Context context) {
        EntryHelper.context = context;
    }

    public Entry getEntry(String id) {
        SQLiteDatabase db = new DBHelper(context).readDatabases();
        Entry entry = new Entry();
        Cursor cursor = db.rawQuery(SQL_SELECT_QUARY + " WHERE " + COLUMN_ID + " = '" + id + "'", null);
        try {
            if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
                //cursor is empty
                return null;
            }
            //cursor.moveToFirst();

            entry.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            entry.setCategory(cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID)));
            entry.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            entry.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
            entry.setLang(cursor.getString(cursor.getColumnIndex(COLUMN_LANG)));
            entry.setLat(cursor.getString(cursor.getColumnIndex(COLUMN_LAT)));
            return entry;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }


        return null;


    }

    public long getEntrySendToServer(String id) {
        SQLiteDatabase db = null;
        long rowid = -1;
        try {
            if (id != null) {
                if (db == null || !db.isOpen()) {
                    db = new DBHelper(context).writeDatabases();
                }

                ContentValues values = new ContentValues();
                values.put(COLUMN_SENDTOSERVER_ID, 1);
                rowid = db.update(IEntryTable.TABLE, values, COLUMN_ID + " = ?", new String[]{id});
                db.close();
                return rowid;
            }

        } finally {
            if (db != null)
                db.close();
        }
        return rowid;


    }
    public ArrayList<Entry> getAllEntry() {
        SQLiteDatabase db = new DBHelper(context).readDatabases();
        Cursor cursor = db.rawQuery(SQL_SELECT_QUARY, null);

        ArrayList<Entry> list = new ArrayList<>();
        try {
            if ((cursor.moveToFirst())) {
                do {
                    Entry entry = new Entry();
                    entry.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    entry.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                    entry.setCategory(cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID)));
                    entry.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                    entry.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
                    entry.setLang(cursor.getString(cursor.getColumnIndex(COLUMN_LANG)));
                    entry.setLat(cursor.getString(cursor.getColumnIndex(COLUMN_LAT)));
                    entry.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));


                    list.add(entry);
                } while (cursor.moveToNext());
            }
            //cursor.moveToFirst();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return null;
    }

    public ArrayList<Entry> getAllEntryToServerSend() {
        SQLiteDatabase db = new DBHelper(context).readDatabases();

        Cursor cursor = db.rawQuery(SQL_SELECT_QUARY + " WHERE " + COLUMN_SENDTOSERVER_ID + " = '" + 0 + "'", null);
        ArrayList<Entry> list = new ArrayList<>();
        try {
            if ((cursor.moveToFirst())) {
                do {
                    Entry entry = new Entry();
                    entry.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    entry.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                    entry.setCategory(cursor.getInt(cursor.getColumnIndex(COLUMN_CATEGORY_ID)));
                    entry.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                    entry.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
                    entry.setLang(cursor.getString(cursor.getColumnIndex(COLUMN_LANG)));
                    entry.setLat(cursor.getString(cursor.getColumnIndex(COLUMN_LAT)));
                    entry.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));


                    list.add(entry);
                } while (cursor.moveToNext());
            }
            //cursor.moveToFirst();
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return null;
    }
    public long insertEntry(Entry entry) throws SQLException {
        SQLiteDatabase db = null;
        long rowid = -1;
        try {
            if (entry != null) {
                if (db == null || !db.isOpen()) {
                    db = new DBHelper(context).writeDatabases();
                }

                ContentValues values = new ContentValues();

//                values.put(COLUMN_ID, entry.getId());
                values.put(COLUMN_TITLE, entry.getTitle());
                values.put(COLUMN_DESCRIPTION, entry.getDescription());
                values.put(COLUMN_IMAGE, entry.getImage());
                values.put(COLUMN_LANG, entry.getLang());
                values.put(COLUMN_LAT, entry.getLat());
                values.put(COLUMN_CATEGORY_ID, entry.getCategory());
                values.put(COLUMN_ADDRESS, entry.getAddress());
                values.put(COLUMN_SENDTOSERVER_ID, 0);
                rowid = db.insertWithOnConflict(IEntryTable.TABLE, COLUMN_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
                db.close();
                return rowid;
            }

        } finally {
            if (db != null)
                db.close();
        }
        return rowid;
    }

    public long deleteEntry(String id) {
        SQLiteDatabase db = null;
        try {
            if (db == null || !db.isOpen()) {
                db = new DBHelper(context).writeDatabases();
            }
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, id);

            long rowid = db.delete(IEntryTable.TABLE, COLUMN_ID + " = " + id, null);

            return rowid;

        } finally {
            if (db != null)
                db.close();
        }

    }
}
