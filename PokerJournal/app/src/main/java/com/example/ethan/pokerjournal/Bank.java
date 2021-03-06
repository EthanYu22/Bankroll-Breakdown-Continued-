package com.example.ethan.pokerjournal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Represents a Deposit or Withdraw Bank Transaction
public class Bank
{

    protected int id; // Transaction ID
    protected String type; // Transaction Type
    protected String date; // Transaction Computed Date YYYY-MM-DD
    protected int amount; // Transaction Amount

    public void setEntries(String type, String date, int amount)
    {
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    public void setAll(int id, String type, String date, int amount)
    {
        this.id = id;
        setEntries(type, date, amount);
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getDate() { return date; }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount) { this.amount = amount; }

    // Converts Date to Display Format
    public String getConvertedDateMMddyyyy()
    {
        String sessionDate = this.date;
        LocalDate localDate = LocalDate.parse(sessionDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return localDate.format(formatter);
    }

    // Used for Array Adapter to Display Transaction Details
    @Override
    public String toString()
    {
        return " " + getConvertedDateMMddyyyy() + "\n " + type + "\n $" + amount;
    }

    // Arbitrary For No Error Alerts
    public int compare(Bank bank, Bank t1)
    {
        return 0;
    }

}
