package com.android.test.interfaces;

/**
 * Created by sumit on 15/9/16.
 */

public interface ICategoryTable {
    String TABLE = "category";
    String COLUMN_ID = "id";// varchar 255

    String COLUMN_CATEGORY_NAME = "categoryName";


    String SQL_CREATE_CATEGORY_TABLE =
            "CREATE TABLE  IF NOT EXISTS " + TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                    COLUMN_CATEGORY_NAME + " CHAR(255) " +
                    " )";

    String SQL_DELETE_CATEGORY_TABLE =
            "DROP TABLE IF EXISTS " + TABLE;
    String SQL_SELECT_QUARY =
            "SELECT * FROM  " + TABLE;
}
