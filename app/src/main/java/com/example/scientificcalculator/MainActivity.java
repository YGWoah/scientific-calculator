package com.example.scientificcalculator;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.scientificcalculator.utils.Calculator;
import com.example.scientificcalculator.utils.ObservableValue;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultDisplay;
    private TextView expressionDisplay;
    private StringBuilder input = new StringBuilder();
    private ObservableValue<Integer> cursorPosition = new ObservableValue<>(0, this::updateCursorValue);
    private TextView cursorPositionDisplay;
    private Button scientificButton;
    private Button standardButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cursorPositionDisplay = findViewById(R.id.cursorPositionDisplay);
        scientificButton = findViewById(R.id.btnScientific);
        standardButton = findViewById(R.id.btnStandard);

        // Initialize TextView for display
        resultDisplay = findViewById(R.id.resultDisplay);
        expressionDisplay = findViewById(R.id.expressionDisplay);

        // Set OnClickListener for number buttons
        findViewById(R.id.btn0).setOnClickListener(this::handleNumber);
        findViewById(R.id.btn1).setOnClickListener(this::handleNumber);
        findViewById(R.id.btn2).setOnClickListener(this::handleNumber);
        findViewById(R.id.btn3).setOnClickListener(this::handleNumber);
        findViewById(R.id.btn4).setOnClickListener(this::handleNumber);
        findViewById(R.id.btn5).setOnClickListener(this::handleNumber);
        findViewById(R.id.btn6).setOnClickListener(this::handleNumber);
        findViewById(R.id.btn7).setOnClickListener(this::handleNumber);
        findViewById(R.id.btn8).setOnClickListener(this::handleNumber);
        findViewById(R.id.btn9).setOnClickListener(this::handleNumber);


//         Set OnClickListener for operation buttons
        findViewById(R.id.btnPlus).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnMinus).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnMultiply).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnDevide).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnDot).setOnClickListener(this::handleOperation);

        // Set OnClickListener for trigonometric function buttons
        findViewById(R.id.btnSin).setOnClickListener(this);
        findViewById(R.id.btnCos).setOnClickListener(this);
        findViewById(R.id.btnTan).setOnClickListener(this);
        findViewById(R.id.btnPi).setOnClickListener(this);

        // Set OnClickListener for exponential function buttons
        findViewById(R.id.btnExp).setOnClickListener(this);
        findViewById(R.id.btnLn).setOnClickListener(this);
        findViewById(R.id.btnLog).setOnClickListener(this);
        findViewById(R.id.btnSqrt).setOnClickListener(this);
//        findViewById(R.id.btnSquare).setOnClickListener(this);

        // parathanses button
        findViewById(R.id.btnLeftBracket).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnRightBracket).setOnClickListener(this::handleOperation);

//        //controle button
        findViewById(R.id.btnDel).setOnClickListener(this::handleSupp);
        findViewById(R.id.btnAC).setOnClickListener(this::handleClear);
//
        // Set OnClickListener for equal button
        findViewById(R.id.btnEqual).setOnClickListener(this::evaluateExpression);

        // special buttons
        findViewById(R.id.btnLeft).setOnClickListener(this::handleLeftDirection);
        findViewById(R.id.btnRight).setOnClickListener(this::handleRightDirection);

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        insertIntoInput(button.getText().toString());
        present();
    }

    public void evaluateExpression(View v){
        try {
            double result = Calculator.evaluate(input.toString());
            setResultDisplayText(String.valueOf(result));
        }
        catch (Exception e){
            e.printStackTrace();
            setResultDisplayText("Error");
        }
    }

    public void handleNumber(View v ){
        Button button = (Button) v;
        insertIntoInput(button.getText().toString());
        present();
    }

    public void handleOperation(View v){
        Button button = (Button) v;
        System.out.println(cursorPosition);
        // check if the last character is an operator
        if(input.length() > 0 && isOperator(input.charAt(cursorPosition.getValue()-1))){
            input.deleteCharAt(cursorPosition.getValue()-1);
            cursorPosition.setValue(cursorPosition.getValue()-1);
        }
        insertIntoInput(button.getText().toString());
        present();
    }

    public void updateCursorValue(){
        cursorPositionDisplay.setText(String.valueOf(cursorPosition.getValue()));
    }



    public void handleSupp(View v){
        if(input.length() > 0){
            input.deleteCharAt(input.length() - 1);
            cursorPosition.setValue(cursorPosition.getValue()-1);
            present();
        }
    }

    public void handleClear(View v){
        input = new StringBuilder();
        cursorPosition.setValue(0);
        present();
    }

    private boolean isOperator(char c){
        return c == '+' || c == '-' || c == '*' || c == '/'|| c=='x';
    }

    private void setResultDisplayText(String text){
        resultDisplay.setText(text);
    }

    private void present(){
        SpannableString spannableString = new SpannableString(input.toString());
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.RED);
        if (cursorPosition.getValue() >= 0 && cursorPosition.getValue() < input.length()) {
            spannableString.setSpan(backgroundColorSpan, cursorPosition.getValue(), cursorPosition.getValue() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        expressionDisplay.setText(spannableString);
    }

    private void insertIntoInput(String text){
        input.insert(cursorPosition.getValue(), text);
        cursorPosition.setValue(cursorPosition.getValue()+text.length());
        present();
    }


    public void handleLeftDirection(View v){
        if(cursorPosition.getValue() > 0){
            cursorPosition.setValue(cursorPosition.getValue()-1);
            present();
        }
    }

    public void handleRightDirection(View v){
        if(cursorPosition.getValue() < input.length()){
            cursorPosition.setValue(cursorPosition.getValue()+1);
            present();
        }
    }

}
