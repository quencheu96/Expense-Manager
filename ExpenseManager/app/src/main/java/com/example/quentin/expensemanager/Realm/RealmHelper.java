package com.example.quentin.expensemanager.Realm;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.quentin.expensemanager.CurrencyConverter.CurrencyConverter;
import com.example.quentin.expensemanager.R;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Quentin on 2016-09-26.
 */

public class RealmHelper {

    private Realm mRealm;
    private Context mContext;
    private int transactionID;

    public RealmHelper(Context context){
        mContext = context;

        SharedPreferences sharedPrefs = mContext
                .getSharedPreferences(mContext.getString(R.string.latest_transaction_id),mContext.MODE_PRIVATE);
        transactionID = sharedPrefs.getInt(mContext.getString(R.string.latest_transaction_id),0);

        InitializeRealm();

    }

    public void InitializeRealm(){
        RealmConfiguration config = new RealmConfiguration.Builder(mContext)
                .name(mContext.getString(R.string.realm_name))
                .build();

        mRealm = Realm.getInstance(config);
    }

    public void AddAccount(String name){
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(new Account(name));
        mRealm.commitTransaction();
    }

    public void AddTransaction(String account, String note,String currency, double amount,Date date){
        RealmQuery<Account> query = mRealm.where(Account.class);
        query.equalTo(mContext.getString(R.string.account_field_name),account);
        RealmResults<Account> result = query.findAll();
        mRealm.beginTransaction();
        Transaction transaction =  mRealm.copyToRealmOrUpdate(new Transaction(transactionID,currency,note,amount,date,account));
        result.first().getTransactions().add(transaction);
        result.first().setBalance(result.first().getBalance()+amount);
        if (amount > 0) {
            result.first().setOutgoing(result.first().getOutgoing() + amount);
        }
        else{
            result.first().setIncoming(result.first().getIncoming() + amount);
        }
        mRealm.commitTransaction();
        transactionID++;

        SharedPreferences sharedPrefs = mContext
                .getSharedPreferences(mContext.getString(R.string.latest_transaction_id),mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(mContext.getString(R.string.latest_transaction_id),transactionID);
        editor.apply();
    }

    public Account GetAccount(String account){
        RealmQuery<Account> query = mRealm.where(Account.class);
        query.equalTo(mContext.getString(R.string.account_field_name),account);
        RealmResults<Account> result = query.findAll();
        return result.first();
    }

    public void DeleteAccount(String account){
        RealmQuery<Account> query = mRealm.where(Account.class);
        query.equalTo(mContext.getString(R.string.account_field_name),account);
        RealmResults<Account> result = query.findAll();
        mRealm.beginTransaction();
        result.first().deleteFromRealm();
        mRealm.commitTransaction();
    }

    public void DeleteTransaction(int id){
        mRealm.beginTransaction();
        RealmQuery<Transaction> query = mRealm.where(Transaction.class);
        RealmResults<Transaction> result = query.findAll();
        RealmQuery<Account> accountQuery = mRealm.where(Account.class);
        accountQuery.equalTo(mContext.getString(R.string.account_field_name),result.first().getAccountName());

        RealmResults<Account> accountResult = accountQuery.findAll();

        int length = accountResult.first().getTransactions().size();

        for (int x = 0;x<length;x++){
            if (accountResult.first().getTransactions().get(x).getId() == id){
                mRealm.beginTransaction();
                accountResult.first().getTransactions().remove(x);
                mRealm.commitTransaction();
                break;
            }
        }
        result.first().deleteFromRealm();
        mRealm.commitTransaction();
    }

}
