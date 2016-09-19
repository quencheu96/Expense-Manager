package com.example.quentin.expensemanager.CurrencyConverter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.quentin.expensemanager.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Quentin on 2016-09-18.
 */
public class CurrencyConverter {

    private String URL = "http://api.fixer.io/latest?base=";
    private  OkHttpClient client;
    private String mCurrencyTables;
    private Context mContext;
    private String DEFAULT_CURRENCY = "CAD";
    private String RATES = "rates";

    public CurrencyConverter(Context context){
        client = new OkHttpClient();
        mContext = context;
        SharedPreferences sharedPrefs = mContext
                .getSharedPreferences(mContext.getString(R.string.shared_preferences_currency_conversion_table),mContext.MODE_PRIVATE);
        mCurrencyTables = sharedPrefs.getString(mContext.getString(R.string.currency_tables),"");
        saveCurrencyConversionTables();

    }

    private void saveCurrencyConversionTables() {
        Request request = new Request.Builder()
                .url(URL + mContext.getString(R.string.currency_CAD))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                mCurrencyTables = response.body().string();

                SharedPreferences sharedPrefs = mContext
                        .getSharedPreferences(mContext.getString(R.string.shared_preferences_currency_conversion_table),mContext.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(mContext.getString(R.string.currency_tables), mCurrencyTables);
                editor.apply();
            }
        });
    }

    public double convertCurrencies(String base, String target, double amount) {
        if (mCurrencyTables==null){
            return -1;
        }
        try {
            JSONObject jsonObject = new JSONObject(mCurrencyTables);
            if (base.equals(DEFAULT_CURRENCY)){
                double conversionRate = jsonObject.getJSONObject(RATES).getDouble(target);
                return amount * conversionRate;
            }
            else{
                double baseRate = jsonObject.getJSONObject(RATES).getDouble(base);
                double targetRate = jsonObject.getJSONObject(RATES).getDouble(target);
                return (amount / baseRate) * targetRate;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
