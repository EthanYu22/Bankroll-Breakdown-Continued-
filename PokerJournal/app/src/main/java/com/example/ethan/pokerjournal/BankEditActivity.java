package com.example.ethan.pokerjournal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.MonthDisplayHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

// Edits Bank Transaction Entries
public class BankEditActivity extends AppCompatActivity {

    DatabaseHelper db;
    Bank bank;
    int bankId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_form);
        db = new DatabaseHelper(this);
        bankId = MainActivity.bankId;
        bank = db.getBank(bankId);

        final TextView displayAmount = (TextView) findViewById(R.id.editAmount);
        displayAmount.setText(Double.toString(bank.getAmount()));
    }

    // Action When Off Bank Form Page
    public void onPause() {
        super.onPause();
        finish();
    }

    // Submit Edited Bank Transaction
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
        int intDay = Integer.parseInt(day);
        switch(intDay){
            case 1: day = "01";
                break;
            case 2: day = "02";
                break;
            case 3: day = "03";
                break;
            case 4: day = "04";
                break;
            case 5: day = "05";
                break;
            case 6: day = "06";
                break;
            case 7: day = "07";
                break;
            case 8: day = "08";
                break;
            case 9: day = "09";
        }
        String date = month + "/" + day + "/" + year;
        String date2 = year + "/" + month + "/" + day;

        // Get Amount and Make Sure it's Valid
        EditText editAmount = (EditText) findViewById(R.id.editAmount);
        String Amount = editAmount.getText().toString();
        if(Amount.isEmpty()) {
            toast.show();
            return;
        }

        // Amount Transaction as a Double
        double amountMoney = Double.parseDouble(editAmount.getText().toString());

        // Set Entries into DB
        bank.setAll(bankId, type, date, date2, amountMoney);

        db.editBank(bank);

        finish();
    }
}
