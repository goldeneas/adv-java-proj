package org.example.formatter;

import javafx.scene.control.TextFormatter;

public class NameFormatter extends TextFormatter<String> {
    public NameFormatter() {
        super(s -> s.getControlNewText().matches("\\[a-zA-Z]{0, 10}") ? s : null);
    }
}
