package com.example.roomlibrarydailyexpense;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ExpenseList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ListView listView = findViewById(R.id.listView);
        Button btnDelete = findViewById(R.id.btnDelete);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        // Retrieve all expenses from the database
        ArrayList<Expense> expenses = (ArrayList<Expense>) databaseHelper.expenseDao().getAllExpenses();

        // Create an ArrayAdapter to display the expenses
        ArrayAdapter<Expense> arrayAdapter = new ArrayAdapter<>(ExpenseList.this, android.R.layout.simple_list_item_1, expenses);
        listView.setAdapter(arrayAdapter);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(ExpenseList.this);
                dialog.setContentView(R.layout.activity_expense_row_delete);
                EditText edtRowNumber = dialog.findViewById(R.id.edtRowNumber);
                AppCompatButton btnConfirm = dialog.findViewById(R.id.btnConfirm);
                AppCompatButton btnCancel = dialog.findViewById(R.id.btnCancel);

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!edtRowNumber.getText().toString().isEmpty()){
                            int rowNumber = Integer.parseInt(edtRowNumber.getText().toString());
                            if(rowNumber > expenses.size() || rowNumber < 1){
                                edtRowNumber.setError("Invalid row number");
                                Toast.makeText(ExpenseList.this, "Invalid row number", Toast.LENGTH_SHORT).show();
                            }else{
                                Expense expense = expenses.get(rowNumber - 1);
                                databaseHelper.expenseDao().deleteTx(expense);
                                expenses.remove(rowNumber - 1);
                                arrayAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }else{
                            Toast.makeText(ExpenseList.this, "Enter the row number", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}