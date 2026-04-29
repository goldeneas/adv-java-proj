package org.example.controller;

import org.example.formatter.NameFormatter;
import org.example.formatter.QuestionLenFormatter;
import org.example.formatter.SurnameFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HomeViewController {
    @FXML
    public TextField nameField;

    @FXML
    public TextField surnameField;

    @FXML
    public TextField questionLenField;

    @FXML
    public Button startButton;

    @FXML
    public void initialize() {
        this.startButton.disableProperty()
                .bind(this.nameField.textProperty().isEmpty()
                        .or(this.surnameField.textProperty().isEmpty())
                        .or(this.questionLenField.textProperty().isEmpty()));

        this.nameField.setTextFormatter(new NameFormatter());
        this.surnameField.setTextFormatter(new SurnameFormatter());
        this.questionLenField.setTextFormatter(new QuestionLenFormatter());
    }

    @FXML
    public void onButtonStart(ActionEvent e) {
        String questionsLenStr = this.questionLenField.textProperty().get();
        int questionsLen = Integer.parseInt(questionsLenStr);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("questions.fxml"));
        Parent root = (Parent) loader.load();

        QuestionsViewController controller = loader.getController();
        controller.setQuestionsLen(questionsLen);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public String getUsername() {
        String name = this.nameField.getText();
        String username = this.surnameField.getText();
        return String.format("%s %s", name, username);
    }
}
