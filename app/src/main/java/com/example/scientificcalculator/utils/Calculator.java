package com.example.scientificcalculator.utils;

import java.util.Stack;

public class Calculator {
    public static double evaluate(String expression) {
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == ' ') {
                continue;
            } else if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                // we need to decrement i because the outer loop will increment it
                i--;
                operandStack.push(Double.parseDouble(num.toString()));
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (operatorStack.peek() != '(') {
                    double result = performOperation(operatorStack.pop(), operandStack.pop(), operandStack.pop());
                    operandStack.push(result);
                }
                operatorStack.pop();
            } else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && precedence(c) <= precedence(operatorStack.peek())) {
                    double result = performOperation(operatorStack.pop(), operandStack.pop(), operandStack.pop());
                    operandStack.push(result);
                }
                operatorStack.push(c);
            } else if (Character.isLetter(c)) {
                StringBuilder functionName = new StringBuilder();
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    functionName.append(expression.charAt(i));
                    i++;
                }
                i--;
                if (isFunction(functionName.toString())) {
                    String expressionInsideFunction ="";
                    // here we need to skip the function name
                    int j = i+2;
                    int openBrackets = 0;
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
                        expressionInsideFunction += expression.charAt(j);
                        j++;
                    }
                    i = j;

                    double result = evaluateExpression(expressionInsideFunction);

                    operandStack.push(applyFunction(functionName.toString(), result));

                } else {
                    throw new IllegalArgumentException("Unknown function: " + functionName.toString());
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            double result = performOperation(operatorStack.pop(), operandStack.pop(), operandStack.pop());
            operandStack.push(result);
        }

        return operandStack.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char c) {
        if (c == '+' || c == '-') {
            return 1;
        } else if (c == '*' || c == '/') {
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
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    private static boolean isFunction(String functionName) {
        String[] functions = {"cos", "sin", "tan", "log", "exp"};
        for (String function : functions) {
            if (function.equals(functionName)) {
                return true;
            }
        }
        return false;
    }

    private static double evaluateExpression(String expression) {
        return evaluate(expression);
    }
    private static double applyFunction(String functionName, double value) {
        switch (functionName) {
            case "cos":
                return Math.cos(value);
            case "sin":
                return Math.sin(value);
            case "tan":
                return Math.tan(value);
            case "log":
                return Math.log(value);
            case "exp":
                return Math.exp(value);
            default:
                throw new IllegalArgumentException("Unknown function: " + functionName);
        }

    }
}

