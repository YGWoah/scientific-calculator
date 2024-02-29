package com.example.scientificcalculator.utils;

import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;

import com.example.scientificcalculator.utils.Pair;

public class Calculator {
    private static final ArrayList<String> operators = new ArrayList<String>() {{
        add("+");
        add("-");
        add("x");
        add("/");
        add("^");
    }};
    private static final Map<String, Function<Double, Double>> functions = new HashMap<String, Function<Double, Double>>() {{
        put("sin", Math::sin);
        put("cos", Math::cos);
        put("tan", Math::tan);
        put("log", Math::log);
        put("exp", Math::exp);
        put("sqrt", Math::sqrt);
        put("abs", Math::abs);
    }};


    public static double evaluate(String expression) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                Pair<Double, Integer> result = parseNumber(expression, i);
                operandStack.push(result.first);
                i = result.second;
            } else if (c == 'P') {
                operandStack.push(Math.PI);
                i++;
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (operatorStack.peek() != '(') {
                    double result = performOperation(operatorStack.pop(), operandStack.pop(), operandStack.pop());
                    operandStack.push(result);
                }
                operatorStack.pop();
            } else if (isOperator(c)) {
                handleOperator(operatorStack, operandStack, c);
                operatorStack.push(c);
            } else if (Character.isLetter(c)) {
                String functionName = getFunction(expression, i);
                i += functionName.length();
                Pair<Double, Integer> result = evaluateFunction(functionName, expression, i);
                operandStack.push(result.first);
                i += result.second;
            }
        }

        while (!operatorStack.isEmpty()) {
            double result = performOperation(operatorStack.pop(), operandStack.pop(), operandStack.pop());
            operandStack.push(result);
        }
        return operandStack.pop();
    }

    private static void handleOperator(Stack<Character> operatorStack, Stack<Double> operandStack, char operator) {
        while (!operatorStack.isEmpty() && precedence(operator) <= precedence(operatorStack.peek())) {
            double result = performOperation(operatorStack.pop(), operandStack.pop(), operandStack.pop());
            operandStack.push(result);
        }
    }

    private static Pair<Double, Integer> evaluateFunction(String functionName, String expression, int startIndex) {
        validateFunction(functionName);

        Pair<String, Integer> extractedExpression = extractExpressionInsideFunction(expression, startIndex);
        startIndex = extractedExpression.second;

        double evaluatedResult = evaluateExpression(extractedExpression.first);

        return new Pair<>(evaluatedResult, startIndex);
    }

    private static void validateFunction(String functionName) {
        if (!isFunction(functionName)) {
            throw new IllegalArgumentException("Function " + functionName + " is not supported. Please use a valid function.");
        }
    }

    private static Pair<String, Integer> extractExpressionInsideFunction(String expression, int startIndex) {
        StringBuilder expressionInsideFunction = new StringBuilder();
        int openBrackets = 0;
        int j = startIndex;

        while (j < expression.length()) {
            if (expression.charAt(j) == '(') {
                openBrackets++;
            } else if (expression.charAt(j) == ')') {
                if (openBrackets == 0) {
                    break;
                } else {
                    openBrackets--;
                }
            }
            expressionInsideFunction.append(expression.charAt(j));
            j++;
        }

        return new Pair<>(expressionInsideFunction.toString(), j);
    }

    private static String getFunction(String expression, int startIndex) {
        StringBuilder functionName = new StringBuilder();
        int i = startIndex;
        while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
            functionName.append(expression.charAt(i));
            i++;
        }
        return functionName.toString();
    }

    private static Pair<Double, Integer> parseNumber(String expression, int startIndex) {
        StringBuilder num = new StringBuilder();
        int i = startIndex;
        while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
            num.append(expression.charAt(i));
            i++;
        }
        i--;
        double number = Double.parseDouble(num.toString());
        return new Pair<>(number, i);
    }

    private static boolean isOperator(char c) {
        return operators.contains(String.valueOf(c));
    }

    private static int precedence(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == 'x' || c == '/') {
            return 2;
        }
        return -1;
    }

    private static double performOperation(char operation, double b, double a) {
        switch (operation) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case 'x':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Cannot divide by zero");
                return a / b;
            case '^':
                return Math.pow(a, b);
        }
        return 0;
    }

    private static boolean isFunction(String functionName) {
        for (String function : functions.keySet()) {
            if (function.equals(functionName)) {
                return true;
            }
        }
        return false;
    }

    private static double evaluateExpression(String expression) {
        return evaluate(expression);
    }

}
