package com.example.ethan.pokerjournal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

// Bank Transaction Entry Form
public class BankFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_form);
    }

    // Action When Off Bank Form Page
    public void onPause() {
        super.onPause();
        finish();
    }

    // Submit Bank Transaction
    public void onClickBankButton(View v) {
        DatabaseHelper db = new DatabaseHelper(this);
        Bank bank = new Bank();
        Toast toast = Toast.makeText(getApplication(), "Please fill all fields", Toast.LENGTH_SHORT);

        // ~ Get Entries and Validate ~
        // Get Input of Deposit or Withdraw
        Spinner spinType = (Spinner) findViewById(R.id.spinnerType);
        String type = spinType.getSelectedItem().toString();

        // Get Input of Date and Format into String
        Spinner spinMonth = (Spinner) findViewById(R.id.spinnerBankMonth);
        String month = spinMonth.getSelectedItem().toString();
        Spinner spinDay = (Spinner) findViewById(R.id.spinnerBankDay);
        String day = spinDay.getSelectedItem().toString();
        Spinner spinYear = (Spinner) findViewById(R.id.spinnerBankYear);
        String year = spinYear.getSelectedItem().toString();
        if(month.equals("January")){month = "01";}
        else if(month.equals("February")){month = "02";}
        else if(month.equals("March")){month = "03";}
        else if(month.equals("April")){month = "04";}
        else if(month.equals("May")){month = "05";}
        else if(month.equals("June")){month = "06";}
        else if(month.equals("July")){month = "07";}
        else if(month.equals("August")){month = "08";}
        else if(month.equals("September")){month = "09";}
        else if(month.equals("October")){month = "10";}
        else if(month.equals("November")){month = "11";}
        else{month = "12";}
        if(day.equals("1")){day = "01";}
        else if(day.equals("2")){day = "02";}
        else if(day.equals("3")){day = "03";}
        else if(day.equals("4")){day = "04";}
        else if(day.equals("5")){day = "05";}
        else if(day.equals("6")){day = "06";}
        else if(day.equals("7")){day = "07";}
        else if(day.equals("8")){day = "08";}
        else if(day.equals("9")){day = "09";}
        String date = month + "/" + day + "/" + year;

        // Get Amount and Make Sure it's Valid
        EditText editAmount = (EditText) findViewById(R.id.editAmount);
        String Amount = editAmount.getText().toString();
        if(Amount.isEmpty()) {
            toast.show();
            return;
        }

        // Amount Transaction
        double amountMoney = Double.parseDouble(editAmount.getText().toString());

        // Set Entries into DB
        bank.setEntries(type, date, amountMoney);

        db.createBank(bank);

        finish();
    }
}