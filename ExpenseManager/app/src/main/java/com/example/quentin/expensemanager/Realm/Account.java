package com.example.quentin.expensemanager.Realm;

import android.content.Context;

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
    private String name;
    private RealmList<Transaction> transactions = new RealmList<Transaction>();
    private double balance;
    private double outgoing;
    private double incoming;

    public Account(String accountName){
        name = accountName;
    }

    public Account(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Transaction> getTransactions(){
        return transactions;
    }

    public double getBalance() {
        return balance;
    }

    public double getOutgoing() {
        return outgoing;
    }

    public double getIncoming() {
        return incoming;
    }

    public void setIncoming(double incoming) {
        this.incoming = incoming;
    }

    public void setOutgoing(double outgoing) {
        this.outgoing = outgoing;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
