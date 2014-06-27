package com.test.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CustomDatabaseOpenHelper extends SQLiteOpenHelper{
	
    private static final int DATABASE_VERSION = 1;
    private static final String CUSTOM_TABLE_NAME = CustomDatabaseConstants.TABLE_NAME;
    private static final String CUSTOM_TABLE_CREATE =
                "CREATE TABLE " + CUSTOM_TABLE_NAME + " (" +
                CustomDatabaseConstants.COLUNA_A + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                CustomDatabaseConstants.COLUNA_B + " TEXT, " +
                CustomDatabaseConstants.COLUNA_C + " TEXT);";
    private static CustomDatabaseOpenHelper mInstance;

    CustomDatabaseOpenHelper(Context context) {
        super(context, CustomDatabaseConstants.DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public static CustomDatabaseOpenHelper getInstance(Context context){
    	if(mInstance==null){
    		mInstance = new CustomDatabaseOpenHelper(context);
    	}
    	return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CUSTOM_TABLE_CREATE);
        Log.v("DB", "created");
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + CustomDatabaseConstants.TABLE_NAME);
		Log.v("DB", "upgraded");
		onCreate(db);
	}

}

