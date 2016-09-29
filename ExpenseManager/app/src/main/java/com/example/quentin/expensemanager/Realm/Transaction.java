package com.example.quentin.expensemanager.Realm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quentin on 2016-09-26.
 */

public class Transaction extends RealmObject {

    @PrimaryKey
    private int id;

    private String currency;
    private String notes;
    private double amount;
    private Date date;
    private String accountName;

    public Transaction(int id, String currency, String notes, double amount, Date date, String accountName){
        this.id = id;
        this.currency = currency;
        this.notes = notes;
        this.amount = amount;
        this.date = date;
        this.accountName = accountName;
    }

    public Transaction(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
