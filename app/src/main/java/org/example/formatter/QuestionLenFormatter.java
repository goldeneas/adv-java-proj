package org.example.formatter;

import javafx.scene.control.TextFormatter;

public class QuestionLenFormatter extends TextFormatter<String> {
    public QuestionLenFormatter() {
        super(s -> s.getControlNewText().matches("\\d{0, 10}") ? s : null);
    }
}
