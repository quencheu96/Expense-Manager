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

    public RealmHelper(Context context){
        mContext = context;
        InitializeRealm();
    }

    public void InitializeRealm(){
        RealmConfiguration config = new RealmConfiguration.Builder(mContext)
                .name(mContext.getString(R.string.realm_name))
                .build();

        mRealm = Realm.getInstance(config);
    }

    public void AddAccount(String name){
        Account account = new Account();
        account.setName(name);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(account);
        mRealm.commitTransaction();
    }

    public void AddTransaction(String account, String note,String currency, double amount,Date date){
        RealmQuery<Account> query = mRealm.where(Account.class);
        query.equalTo(mContext.getString(R.string.account_field_name),account);
        RealmResults<Account> result = query.findAll();
        Transaction transaction = new Transaction();
        transaction.setNotes(note);
        transaction.setCurrency(currency);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setId(transactionID);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(transaction);
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

        int length = accountResult.first().GetTranaactions().size();

        for (int x = 0;x<length;x++){
            if (accountResult.first().GetTranaactions().get(x).getId() == id){
                mRealm.beginTransaction();
                accountResult.first().GetTranaactions().remove(x);
                mRealm.commitTransaction();
                break;
            }
        }
        result.first().deleteFromRealm();
        mRealm.commitTransaction();
    }

}
