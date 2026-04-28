package org.example;

public enum Operation {
    ADD('+'),
    SUB('-');

    private final char operation;

    Operation(char operation) {
        this.operation = operation;
    }

    public char toChar() {
        return this.operation;
    }
}
