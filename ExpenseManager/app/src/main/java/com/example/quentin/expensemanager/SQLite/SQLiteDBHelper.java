package com.example.quentin.expensemanager.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quentin.expensemanager.R;

import java.sql.Date;

/**
 * Created by Quentin on 2016-09-15.
 */
public class SQLiteDBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String EXPENSES_TABLE_NAME = "expenses";
    private static final String EXPENSES_COLUMN_ID_ = "_id";
    private static final String EXPENSES_COLUMN_DATE = "date";
    private static final String EXPENSES_COLUMN_AMOUNT = "amount";
    private static final String EXPENSES_COLUMN_NOTE = "note";

    private SQLiteDatabase mExpensesDB;
    private Context mContext;
    public SQLiteDBHelper(Context context){
        super(context, context.getString(R.string.db_name), null, DATABASE_VERSION);
//        mContext = context;
//        if (!checkDataBase()) {
//            createSQLTable();
//        }
//        mExpensesDB = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Leave this blank, tables will be created manually
        //jks
        createSQLTable(db);
    }

    public void createSQLTable(SQLiteDatabase db){
        System.out.println("CREATING SQL TABLE");
        db.execSQL("CREATE TABLE " + EXPENSES_TABLE_NAME + "(" +
                EXPENSES_COLUMN_ID_ + " INTEGER PRIMARY KEY, " +
                EXPENSES_COLUMN_DATE + " TEXT, " +
                EXPENSES_COLUMN_AMOUNT + " INTEGER, " +
                EXPENSES_COLUMN_NOTE + " TEXT)"
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
            System.out.println(e);
        }
        return checkDB != null;
    }

    public void addExpense(double amount, Date date, String note) {
        SQLiteDatabase db = getWritableDatabase();

        double formatAmount = Math.round(amount*100)/100.0;

        ContentValues values = new ContentValues();
        values.put(EXPENSES_COLUMN_AMOUNT, formatAmount);
        values.put(EXPENSES_COLUMN_DATE, date.toString());
        values.put(EXPENSES_COLUMN_NOTE, note);

        db.insert(EXPENSES_TABLE_NAME, null, values);
        System.out.println("Expense inserted");
    }

    public Cursor getExpenses() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
//                EXPENSES_COLUMN_ID_,
                EXPENSES_COLUMN_AMOUNT,
                EXPENSES_COLUMN_DATE,
                EXPENSES_COLUMN_NOTE
        };
        String selection = null;    // No filters just get everything
        String[] selectionArgs = new String[1];    // donno what this is
        
//        Cursor c = db.query(EXPENSES_TABLE_NAME, projection, null, selectionArgs, null, null, null, null );
        Cursor c = db.rawQuery("SELECT * FROM " + EXPENSES_TABLE_NAME, null);
        return c;
    }
}
