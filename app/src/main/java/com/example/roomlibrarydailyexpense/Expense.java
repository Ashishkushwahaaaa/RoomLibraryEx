package com.example.roomlibrarydailyexpense;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Item")
    private String item;

    @ColumnInfo(name = "Amount")
    private String amount;

    Expense(int id, String item, String amount){
        this.id = id;
        this.item = item;
        this.amount = amount;
    }

    @Ignore
    Expense(String item, String amount){
        this.item = item;
        this.amount = amount;
    }
    Expense(){
        this.id = 1;
        this.item = "someThing";
        this.amount = "100";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    // to set the list on the list
    public String toString(){
        return  id +")  "+ item + "            " + amount;
    }
}
