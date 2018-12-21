package com.surya432.kamusque.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    static String TABLE_INDONESIA = "indonesia";
    static String TABLE_ENGLISH = "english";
    static String DATABASE_NAME = "dbmahasiswa";
    static String FIELD_ID = "id";
    static String FIELD_KEY = "keyword";
    static String FIELD_VALUES = "terjemahan";
    public static String CREATE_TABLE_ENGLISH = "create table " + TABLE_ENGLISH +
            " (" + FIELD_ID + " integer primary key autoincrement, " +
            FIELD_KEY + " text not null, " +
            FIELD_VALUES + " text not null);";
    public static String CREATE_TABLE_INDONESIA = "create table " + TABLE_INDONESIA + " (" +
            FIELD_ID + " integer primary key autoincrement, " +
            FIELD_KEY + " text not null, " +
            FIELD_VALUES + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISH);
        db.execSQL(CREATE_TABLE_INDONESIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGLISH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDONESIA);
        onCreate(db);
    }
}
