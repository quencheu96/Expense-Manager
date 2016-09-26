package com.example.quentin.expensemanager.Realm;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quentin.expensemanager.CurrencyConverter.CurrencyConverter;
import com.example.quentin.expensemanager.R;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quentin on 2016-09-26.
 */

public class Account extends RealmObject {

    @PrimaryKey
    private String mName;
    private RealmList<Transaction> mTransactions;
    private double mBalance;
    private double mOutgoing;
    private double mIncoming;

    public Account(){

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void AddTransaction(Transaction transaction, Context context, CurrencyConverter converter){
        mTransactions.add(transaction);
        UpdateAccount(context,converter);
    }

    public void UpdateAccount(Context context, CurrencyConverter converter){
        double balance=0;
        double outgoing=0;
        double incoming =0;

        String defaultCurrency = context.getString(R.string.default_currency);
        for (Transaction transaction : mTransactions){
            balance += converter.convertCurrencies(transaction.getCurrency(),defaultCurrency,transaction.getAmount());
            if (transaction.getAmount()>0){
                incoming += converter.convertCurrencies(transaction.getCurrency(),defaultCurrency,transaction.getAmount());
            }
            else{
                outgoing += converter.convertCurrencies(transaction.getCurrency(),defaultCurrency,transaction.getAmount());
            }
        }
    }

    public RealmList<Transaction> GetTranaactions(){
        return mTransactions;
    }
}
