package com.example.roomlibrarydailyexpense;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtAmount;
    Button btnAdd, btnOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtName = findViewById(R.id.edtName);
        edtAmount = findViewById(R.id.edtAmount);
        btnAdd = findViewById(R.id.btnAdd);
        btnOpen = findViewById(R.id.btnOpen);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ExpenseList.class);
                startActivity(intent);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String Item = edtName.getText().toString();
                    String Amount = edtAmount.getText().toString();

                    databaseHelper.expenseDao().addTx(new Expense(Item, Amount));
                    Toast.makeText(MainActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();

                    //To show the list in the log
                    ArrayList<Expense> expenses = (ArrayList<Expense>) databaseHelper.expenseDao().getAllExpenses();
                    for(int i = 0; i<expenses.size(); i++){
                        Log.d("Data" , "Item: " + expenses.get(i).getItem() + ", Amount: " + expenses.get(i).getAmount());
                    }
            }
        });

    }
}