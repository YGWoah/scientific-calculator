package com.example.scientificcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.scientificcalculator.utils.Calculator;
import com.example.scientificcalculator.utils.ObservableValue;


public class StandardCalculator extends AppCompatActivity {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnAdd, btnSub, btnMul, btnDiv, btnDot, btnResult, btnClear, btnDel,btnAns;
    TextView expressionDisplay, resultDisplay;
    ObservableValue<StringBuilder> expression = new ObservableValue<>(new StringBuilder(), this::updateDisplay);

    String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_calculator);

        initializeViews();
        initializeListeners();

    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Intent intent = new Intent(this, LandScapeCalculator.class);
            startActivity(intent);
        }
    }
    public void initializeViews(){
        // Initialize the buttons
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnAdd = findViewById(R.id.btnPlus);
        btnSub = findViewById(R.id.btnMinus);
        btnMul = findViewById(R.id.btnMultiply);
        btnDiv = findViewById(R.id.btnDivide);
        btnDot = findViewById(R.id.btnDot);
        btnResult = findViewById(R.id.btnResult);
        btnClear = findViewById(R.id.btnAC);
        btnAns = findViewById(R.id.btnAns);
        expressionDisplay = findViewById(R.id.expressionDisplay);
        resultDisplay = findViewById(R.id.resultDisplay);
    }

    public void initializeListeners(){
        // Set the listeners for the buttons
        btn0.setOnClickListener(v -> handleInput(btn0));
        btn1.setOnClickListener(v -> handleInput(btn1));
        btn2.setOnClickListener(v -> handleInput(btn2));
        btn3.setOnClickListener(v -> handleInput(btn3));
        btn4.setOnClickListener(v -> handleInput(btn4));
        btn5.setOnClickListener(v -> handleInput(btn5));
        btn6.setOnClickListener(v -> handleInput(btn6));
        btn7.setOnClickListener(v -> handleInput(btn7));
        btn8.setOnClickListener(v -> handleInput(btn8));
        btn9.setOnClickListener(v -> handleInput(btn9));
        btnAdd.setOnClickListener(v -> handleInput(btnAdd));
        btnSub.setOnClickListener(v -> handleInput(btnSub));
        btnMul.setOnClickListener(v -> handleInput(btnMul));
        btnDiv.setOnClickListener(v -> handleInput(btnDiv));
        btnDot.setOnClickListener(v -> handleInput(btnDot));
        btnResult.setOnClickListener(v -> handleResult());
        btnClear.setOnClickListener(v -> handleClear());
        btnAns.setOnClickListener(v -> handleAns());
    }

    // Function to handle the expression of the user
    public void handleInput(Button btn) {
        expression.setValue(expression.getValue().append(btn.getText()));
        System.out.println(expression.getValue());
    }

    // Function to handle the clear button
    public void handleClear() {
        expression.setValue(new StringBuilder());
    }

    public void handleAns(){
        expression.setValue(expression.getValue().append(result));
    }

    public void handleResult(){
        try {


            result = String.valueOf(Calculator.evaluate(expression.getValue().toString()));
            resultDisplay.setText(result);
        } catch (Exception e) {
            resultDisplay.setText("Invalid Expression");
        }
    }

    public void updateDisplay(){
        expressionDisplay.setText(expression.getValue().toString());
    }

}