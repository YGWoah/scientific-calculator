package com.example.scientificcalculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.example.scientificcalculator.utils.Calculator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ExampleInstrumentedTest {
    private String expression;
    private double expected;

    public ExampleInstrumentedTest(String expression, double expected) {
        this.expression = expression;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"2 + 2", 4.0},
                {"5 - 2", 3.0},
                {"3 x 3", 9.0},
                {"6 / 2", 3.0},
                {"exp(0)", 1.0},
                {"exp(0 )+1", 2.0},
                {"sqrt(4)", 2.0},
                {"2^3", 8.0},
                {"log(1)", 0.0},
                {"sin(0)", 0.0},
                {"cos(0)", 1.0},
                {"tan(0)", 0.0},
                {"log(exp(0))",0.0},
                {"log(1)",0}
        });
    }

    @Test
    public void testEvaluate() {
        double result = Calculator.evaluate(expression);
        assertEquals(expected, result, 0.001);
    }
}
