package org.example.formatter;

import javafx.scene.control.TextFormatter;

public class SurnameFormatter extends TextFormatter<String> {
    public SurnameFormatter() {
        super(s -> s.getControlNewText().matches("\\d{0, 10}") ? s : null);
    }
}
