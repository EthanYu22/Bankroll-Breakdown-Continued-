package com.example.ethan.pokerjournal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

// Shows Bank Transaction Details
public class BankDetailActivity extends AppCompatActivity {

    DatabaseHelper db;
    Bank bank;
    int bankId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detail); // Runs layout.activity_bank_detail
        bankId = MainActivity.bankId; // Refers to Bank Listing ID
        db = new DatabaseHelper(this);
        bank = db.getBank(bankId);
    }

    // Action When On Game Detail Page
    public void onResume() {
        super.onResume();
        displayDetails();
    }

    // Action When Off Game Detail Page
    public void onPause() {
        super.onPause();
        finish();
    }

    // Displays Bank Details
    public void displayDetails() {
        TextView date = (TextView) findViewById(R.id.textBankDate);
        TextView type = (TextView) findViewById(R.id.textBankType);
        TextView amount = (TextView) findViewById(R.id.textBankAmount);

        String amt = String.format("%.2f", bank.getAmount());

        date.setText("Date: " + bank.getDate());
        type.setText("Deposit/Withdraw: " + bank.getType());
        amount.setText("Amount: $" + amt);
    }

    public void onClickEditBank(View v) {
        bankId = v.getId();
        Intent intent = new Intent(BankDetailActivity.this, BankEditActivity.class);
        startActivity(intent);
    }

    // Deletes Bank Transaction
    public void onClickDeleteBank(View v) {
        db.deleteBank(bankId);
        Toast toast = Toast.makeText(getApplication(), "Bank Transaction Deleted", Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}
