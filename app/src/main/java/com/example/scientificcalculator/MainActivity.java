package com.example.scientificcalculator;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

import com.example.scientificcalculator.utils.Calculator;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultDisplay;
    private TextView expressionDisplay;
    private StringBuilder input = new StringBuilder();
    private int cursorPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//
//        // Set OnClickListener for exponential function buttons
//        findViewById(R.id.btnE).setOnClickListener(this);
//        findViewById(R.id.btnLn).setOnClickListener(this);
//        findViewById(R.id.btnLog).setOnClickListener(this);
//        findViewById(R.id.btnSquare).setOnClickListener(this);
//
//        // parathanses button
//        findViewById(R.id.btnRightParenthesis).setOnClickListener(this::handleOperation);
//        findViewById(R.id.btnLeftParenthesis).setOnClickListener(this::handleOperation);
//
//        //controle button
//        findViewById(R.id.btnSupp).setOnClickListener(this::handleSupp);
//        findViewById(R.id.btnClear).setOnClickListener(this::handleClear);
//
        // Set OnClickListener for equal button
        findViewById(R.id.btnEqual).setOnClickListener(this::evaluateExpression);

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        input.append(button.getText());
        setResultDisplayText(input.toString());
    }

    public void evaluateExpression(View v){
        double result = Calculator.evaluate(input.toString());
        setResultDisplayText(String.valueOf(result));
    }

    public void handleNumber(View v ){
        Button button = (Button) v;
        input.append(button.getText());
      setExpressionDisplayText(input.toString());
        cursorPosition++;
    }

    public void handleOperation(View v){
        Button button = (Button) v;
        // check if the last character is an operator
        if(input.length() > 0 && isOperator(input.charAt(input.length() - 1))){
            input.deleteCharAt(input.length() - 1);
        }
        input.append(button.getText());
        setExpressionDisplayText(input.toString());
        cursorPosition++;
    }


    public void handleSupp(View v){
        if(input.length() > 0){
            input.deleteCharAt(input.length() - 1);
            setExpressionDisplayText(input.toString());
            cursorPosition--;
        }
    }

    public void handleClear(View v){
        input = new StringBuilder();
        setExpressionDisplayText("");
        cursorPosition = 0;
    }

    private boolean isOperator(char c){
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private void setResultDisplayText(String text){
        resultDisplay.setText(text);
    }

    private void setExpressionDisplayText(String text){
        expressionDisplay.setText(text);
    }

}
