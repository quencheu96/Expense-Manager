package com.example.quentin.expensemanager;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.currency_spinner) Spinner mSpinner;

    String[] mCurrencyArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        SharedPreferences sharedPrefs = this
                .getSharedPreferences(this.getString(R.string.shared_preferences_currency),this.MODE_PRIVATE);
        mCurrencyArray = getResources().getStringArray(R.array.currency_array);
        int index = sharedPrefs.getInt(getString(R.string.default_currency_index),0);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
        mSpinner.setSelection(index);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences sharedPrefs = this
                .getSharedPreferences(this.getString(R.string.shared_preferences_currency),this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(getString(R.string.default_currency),mCurrencyArray[(int)id]);
        editor.putInt(getString(R.string.default_currency_index),(int)id);
        editor.apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
