package org.example;

import java.util.Random;

public class NumericQuestion {
    private final int num1;
    private final int num2;
    private final char operator;

    public NumericQuestion(int num1, int num2, Operation operator) {
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator.toChar();
    }

    public int getNum1() {
        return this.num1;
    }

    public int getNum2() {
        return this.num2;
    }

    public char getOperator() {
        return this.operator;
    }

    public static NumericQuestion randomInit() {
        Random rand = new Random();

        int n1 = rand.nextInt(51);
        int n2 = rand.nextInt(51);

        Operation[] operations = Operation.values();
        Operation op = operations[rand.nextInt(operations.length)];

        return new NumericQuestion(n1, n2, op);
    }

    public int getResult() throws ArithmeticException {
        if (this.operator == '+') {
            return this.num1 + this.num2;
        } else if (this.operator == '-') {
            return this.num1 - this.num2;
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return this.num1 + "" + this.operator + " " + this.num2 + " = " + this.getResult() + "\n";
    }
}
