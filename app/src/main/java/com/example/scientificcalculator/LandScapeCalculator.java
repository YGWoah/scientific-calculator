package com.example.scientificcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scientificcalculator.utils.Calculator;
import com.example.scientificcalculator.utils.ObservableValue;

public class LandScapeCalculator extends AppCompatActivity implements View.OnClickListener {

    private TextView resultDisplay;
    private TextView expressionDisplay;
    private ObservableValue<StringBuilder> expression = new ObservableValue<>(new StringBuilder(), this::updateExpression);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        verifyOrientation();
        setContentView(R.layout.activity_land_scape_view);

        initializeListeners();
    }

    public void verifyOrientation(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            navigateToPortrait();
        }
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            navigateToPortrait();
        }
    }
    public void navigateToPortrait(){
        Intent intent = new Intent(this, StandardCalculator.class);
        startActivity(intent);
        finish();
    }
    public void initializeListeners(){

        resultDisplay = findViewById(R.id.resultDisplay);
        expressionDisplay = findViewById(R.id.expressionDisplay);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);

        findViewById(R.id.btnPlus).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnMinus).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnMultiply).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnDevide).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnDot).setOnClickListener(this::handleOperation);

        findViewById(R.id.btnSin).setOnClickListener(this);
        findViewById(R.id.btnCos).setOnClickListener(this);
        findViewById(R.id.btnTan).setOnClickListener(this);
        findViewById(R.id.btnPi).setOnClickListener(this);

        findViewById(R.id.btnExp).setOnClickListener(this);
        findViewById(R.id.btnLn).setOnClickListener(this);
        findViewById(R.id.btnLog).setOnClickListener(this);
        findViewById(R.id.btnSqrt).setOnClickListener(this);
//        findViewById(R.id.btnSquare).setOnClickListener(this);
        findViewById(R.id.btnPower).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnLeftBracket).setOnClickListener(this::handleInput);
        findViewById(R.id.btnRightBracket).setOnClickListener(this::handleInput);

        findViewById(R.id.btnDel).setOnClickListener(this::handleSupp);
        findViewById(R.id.btnAC).setOnClickListener(this::handleClear);

        findViewById(R.id.btnAns).setOnClickListener(this::addAns);

        // Set OnClickListener for equal button
        findViewById(R.id.btnResult).setOnClickListener(this::evaluateExpression);

        // special buttons
//        findViewById(R.id.btnLeft).setOnClickListener(this::handleLeftDirection);
//        findViewById(R.id.btnRight).setOnClickListener(this::handleRightDirection);
    }

    @Override
    public void onClick(View v) {
        expression.setValue(expression.getValue().append(((TextView)v).getText()));
    }


    public void addAns(View view){
        expression.setValue(expression.getValue().append(resultDisplay.getText()));
    }
    public void evaluateExpression(View view){
        try {
            double result = Calculator.evaluate(expression.getValue().toString());
            resultDisplay.setText(String.valueOf(result));
        } catch (IllegalArgumentException e){
            resultDisplay.setText("Error");
        }
    }

    public void handleOperation(View view){
        String input = expressionDisplay.getText().toString();
        if(input.length() > 0 && Calculator.isOperator(input.charAt(input.length() - 1))){
            input = input.substring(0, input.length() - 1) ;
        }
        input += ((TextView)view).getText();
        expression.setValue(new StringBuilder(input));
    }


    public void handleInput(View view){
       expression.setValue(expression.getValue().append(((TextView)view).getText()));
    }

    public void handleSupp(View view){
        if(expression.getValue().length() > 0){
            expression.setValue(expression.getValue().deleteCharAt(expression.getValue().length() - 1));
        }
    }

    public void handleClear(View view){
        expression.setValue(new StringBuilder());
    }

    public void updateExpression(){
        expressionDisplay.setText(expression.getValue());
    }



}