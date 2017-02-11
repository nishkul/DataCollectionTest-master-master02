package com.android.test.interfaces;


public interface IEntryTable {
    String TABLE = "entry";
    String COLUMN_ID = "id";
    String COLUMN_TITLE = "title";
    String COLUMN_IMAGE = "image";
    String COLUMN_DESCRIPTION = "descrptn";
    String COLUMN_LAT = "lat";
    String COLUMN_LANG = "lang";
    String COLUMN_ADDRESS = "address";
    String COLUMN_CATEGORY_ID = "cateogroyid";
    String COLUMN_SENDTOSERVER_ID = "sendtoserver";
//    String COLUMN_ACKNOWDEGE_FROM_SERVER_ID = "acknowlegefromserver";


    String SQL_CREATE_ENTRY_TABLE =
            "CREATE TABLE  IF NOT EXISTS " + TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " Varchar(100), " +
                    COLUMN_IMAGE + " Varchar(200)  , " +
                    COLUMN_DESCRIPTION + " Varchar(200) , " +
                    COLUMN_LAT + " Varchar(50) , " +
                    COLUMN_LANG + " Varchar(50) ," +
                    COLUMN_CATEGORY_ID + " Varchar(100) ," +
                    COLUMN_ADDRESS + " Varchar(200) ," +
                    COLUMN_SENDTOSERVER_ID + " INTEGER(1) " +
                    " )";

    String SQL_DELETE_ENTRY_TABLE =
            "DROP TABLE IF EXISTS " + TABLE;

    String SQL_SELECT_QUARY =
            "SELECT * FROM  " + TABLE;
}
