package com.example.quentin.expensemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddTransactionActivity extends AppCompatActivity {

    //TODO: Change this activity into a dialog fragment so we can add to every activity instead
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
    }
}
