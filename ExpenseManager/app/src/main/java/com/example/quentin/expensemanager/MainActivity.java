package com.example.quentin.expensemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.PopupWindow;

import com.example.quentin.expensemanager.SQLite.SQLiteDBHelper;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Date;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    SQLiteDBHelper mDBHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        mDBHelper = new SQLiteDBHelper(getApplicationContext());

        new Handler(  ).postDelayed(new Runnable() {
            @Override
            public void run() {
                showPopup(MainActivity.this);
            }
        }, 1000 );

        SharedPreferences sharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefsEditor = sharedPrefs.edit();
        sharedPrefsEditor.apply();
        if (sharedPrefs.contains(getString(R.string.shared_preferences_cycle_start_date)) && sharedPrefs.contains(getString(R.string.shared_preferences_cycle_end_date))){
            Date currentDate = new Date();
            Date startDate = new Date(sharedPrefs.getLong(getString(R.string.shared_preferences_cycle_start_date),0));
            Date endDate = new Date(sharedPrefs.getLong(getString(R.string.shared_preferences_cycle_end_date),0));

            if (currentDate.before(startDate) || currentDate.after(endDate)){
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.alert_title))
                        .setMessage(getString(R.string.alert_message))
                        .setNeutralButton(getString(R.string.alert_button_message), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                showPopup(MainActivity.this);
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        }
    }



    private void showPopup(Activity context) {

        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.calendar_view_layout, null,false);
        // Creating the PopupWindow
        final PopupWindow popupWindow = new PopupWindow(
                layout,context.getWindow().getDecorView().getWidth(),context.getWindow().getDecorView().getHeight()/2);

        popupWindow.setContentView(layout);
        popupWindow.setOutsideTouchable(false);
        // Clear the default translucent background
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        CalendarView cv = (CalendarView) layout.findViewById(R.id.calendarView);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Implement logic


            }
        });
        popupWindow.showAtLocation(layout, Gravity.CENTER,0,0);
    }

}
