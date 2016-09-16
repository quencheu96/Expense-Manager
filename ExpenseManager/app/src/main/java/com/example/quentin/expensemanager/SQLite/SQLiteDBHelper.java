package com.example.quentin.expensemanager.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quentin.expensemanager.R;

/**
 * Created by Quentin on 2016-09-15.
 */
public class SQLiteDBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String EXPENSES_TABLE_NAME = "expenses";
    private static final String EXPENSES_COLUMN_ID_ = "_id";
    private static final String EXPENSES__BUDGET_CYCLE = "cycle";
    private static final String EXPENSES__COLUMN_DATE = "date";
    private static final String EXPENSES_COLUMN_AMOUNT = "amount";
    private static final String EXPENSES_COLUMN_BALANCE = "balance";

    private SQLiteDatabase mExpensesDB;
    private Context mContext;
    public SQLiteDBHelper(Context context){
        super(context, context.getString(R.string.db_name), null, DATABASE_VERSION);
        mContext = context;
        if (!checkDataBase()) {
            getWritableDatabase();
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createSQLTable(db);
    }

    public void createSQLTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + EXPENSES_TABLE_NAME + "(" +
                EXPENSES_COLUMN_ID_ + " INTEGER PRIMARY KEY, " +
                EXPENSES__BUDGET_CYCLE + " TEXT, " +
                EXPENSES__COLUMN_DATE + " TEXT, " +
                EXPENSES_COLUMN_AMOUNT + " INTEGER, " +
                EXPENSES_COLUMN_BALANCE + " INTEGER)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Actually migrate database data over
    }


    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(mContext.getString(R.string.db_path), null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }
}
