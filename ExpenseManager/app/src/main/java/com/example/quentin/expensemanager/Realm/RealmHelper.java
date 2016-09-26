package com.example.quentin.expensemanager.Realm;

import android.content.Context;

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
    private int transactionID = 0;

    public RealmHelper(){

    }

    public void InitializeRealm(Context context){
        mContext = context;
        RealmConfiguration config = new RealmConfiguration.Builder(context)
                .name(context.getString(R.string.realm_name))
                .build();

        mRealm = Realm.getInstance(config);
    }

    public void AddAccount(String name){
        mRealm.beginTransaction();
        Account account = mRealm.createObject(Account.class);
        account.setName(name);
        mRealm.commitTransaction();
    }

    public void AddTransaction(String account, String note,String currency, double amount,Date date){
        RealmQuery<Account> query = mRealm.where(Account.class);
        query.equalTo(mContext.getString(R.string.account_field_name),account);
        RealmResults<Account> result = query.findAll();
        mRealm.beginTransaction();
        Transaction transaction = mRealm.createObject(Transaction.class);
        transaction.setNotes(note);
        transaction.setCurrency(currency);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setId(transactionID);
        result.first().AddTransaction(transaction,mContext,new CurrencyConverter(mContext));
        mRealm.commitTransaction();
        transactionID++;
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
        result.first().deleteFromRealm();
    }

    public void DeleteTransaction(String account){
        RealmQuery<Transaction> query = mRealm.where(Transaction.class);
        RealmResults<Transaction> result = query.findAll();
        result.first().deleteFromRealm();

    }

}
