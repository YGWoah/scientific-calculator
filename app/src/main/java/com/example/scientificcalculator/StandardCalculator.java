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

    TextView expressionDisplay, resultDisplay;
    ObservableValue<StringBuilder> expression = new ObservableValue<>(new StringBuilder(), this::updateDisplay);

    String result = "";
    int paranthesesCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyOrientation();
        setContentView(R.layout.activity_standard_calculator);
        initializeListeners();

    }

    public void verifyOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            navigateToLandscape();
        }
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            navigateToLandscape();
        }
    }

    public void navigateToLandscape() {
        Intent intent = new Intent(this, LandScapeCalculator.class);
        startActivity(intent);
        finish();
    }

    public void initializeListeners() {
        findViewById(R.id.btn0).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btn1).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btn2).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btn3).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btn4).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btn5).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btn6).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btn7).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btn8).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btn9).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btnPlus).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btnMinus).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btnDivide).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btnDot).setOnClickListener(v -> handleInput((Button) v));
        findViewById(R.id.btnResult).setOnClickListener(v -> handleResult());
        findViewById(R.id.btnAC).setOnClickListener(v -> handleClear());
        findViewById(R.id.btnAns).setOnClickListener(v -> handleAns());
        findViewById(R.id.btnParantheses).setOnClickListener(v -> handleParentheses());
        expressionDisplay = findViewById(R.id.expressionDisplay);
        resultDisplay = findViewById(R.id.resultDisplay);
    }

    public void handleParentheses() {
        if (paranthesesCount % 2 == 0) {
            expression.setValue(expression.getValue().append("("));
        } else {
            expression.setValue(expression.getValue().append(")"));
        }
        paranthesesCount++;
    }

    public void handleInput(Button btn) {
        expression.setValue(expression.getValue().append(btn.getText()));
        System.out.println(expression.getValue());
    }

    public void handleClear() {
        expression.setValue(new StringBuilder());
    }

    public void handleAns() {
        expression.setValue(expression.getValue().append(result));
    }

    public void handleResult() {
        try {
            result = String.valueOf(Calculator.evaluate(expression.getValue().toString()));
            resultDisplay.setText(result);
        } catch (Exception e) {
            resultDisplay.setText("Invalid Expression");
        }
    }

    public void updateDisplay() {
        expressionDisplay.setText(expression.getValue().toString());
    }

}