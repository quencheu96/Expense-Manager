package com.example.quentin.expensemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;

import com.example.quentin.expensemanager.CurrencyConverter.CurrencyConverter;
import com.example.quentin.expensemanager.SQLite.SQLiteDBHelper;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.account_analysis_button) Button accountAnalysisButton;
    @BindView(R.id.account_management_button) Button accountManagementButton;
    @BindView(R.id.transaction_overview_button) Button transactionOverviewButton;
    @BindView(R.id.add_transaction_fab_button) FloatingActionButton addTransactionFabButton;

    SQLiteDBHelper mDBHelper;
    CurrencyConverter mCurrencyConverter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        mCurrencyConverter= new CurrencyConverter(this);
        mDBHelper = new SQLiteDBHelper(getApplicationContext());

        accountAnalysisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountAnalysisActivity.class);
                startActivity(intent);
            }
        });

        accountManagementButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountManagementActivity.class);
                startActivity(intent);
            }
        });

        transactionOverviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionOverviewActivity.class);
                startActivity(intent);
            }
        });

        addTransactionFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                startActivity(intent);
            }
        });



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
