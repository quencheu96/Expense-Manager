package com.example.quentin.expensemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.quentin.expensemanager.SQLite.SQLiteDBHelper;

import java.sql.Date;

public class AddTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
    }
}
