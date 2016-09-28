package com.example.quentin.expensemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.quentin.expensemanager.SQLite.SQLiteDBHelper;

import java.sql.Date;

public class AddTransactionActivity extends AppCompatActivity {

    //TODO: Change this activity into a dialog fragment so we can add to every activity instead
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
    }
}
