package com.example.quentin.expensemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String DB_FULL_PATH = this.getString(R.string.db_path);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!checkDataBase()){
            final String reg = "^[0-9]*$";
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.alert_title));
            alert.setMessage(getString(R.string.alert_message));
            final EditText input = new EditText(this);

            input.setText(getString(R.string.alert_input_message));

            alert.setView(input);
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String cycleLength = input.getText().toString();
                    if(cycleLength.matches(reg)&&!cycleLength.isEmpty()){
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(MainActivity.this, getString(R.string.alert_invalid_input), Toast.LENGTH_LONG).show();
                    }

                }

            });
            alert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(MainActivity.this, getString(R.string.alert_invalid_input) , Toast.LENGTH_LONG).show();
                        }
                    });
            alert.create().show();
        }

    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }
}
