package com.example.quentin.expensemanager.Realm;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Quentin on 2016-09-26.
 */

public class Transaction extends RealmObject {

    private String mCurrency;
    private String mNotes;
    private double mAmount;
    private Date mDate;
    private int mId;

    public Transaction(){

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }
    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        this.mCurrency = currency;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        this.mNotes = notes;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double amount) {
        this.mAmount = amount;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }
}
