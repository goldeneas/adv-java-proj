package org.example.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.NumericQuestion;
import org.example.NumericQuestionAttempt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class QuizViewController implements Initializable {

    @FXML
    private Label questionLabel;

    @FXML
    private Label progressLabel;

    @FXML
    private TextField answerField;

    @FXML
    private Button nextButton;

    @FXML
    private Label timerLabel;

    private int totalQuestions;
    private String username;
    private int currentQuestionIndex = 1;
    private int secondsRemaining = 30;

    private Timeline timeline;
    private NumericQuestion currentQuestion;
    private final List<NumericQuestionAttempt> attemptsList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?|0|-?")) {
                return change;
            }
            return null;
        };
        answerField.setTextFormatter(new TextFormatter<>(integerFilter));
    }

    public void setQuestionsLen(int questionsLen) {
        this.totalQuestions = questionsLen;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void startQuiz() {
        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestionIndex > totalQuestions) {
            goToResults();
            return;
        }
        currentQuestion = NumericQuestion.randomInit();

        questionLabel.setText(currentQuestion + " = ?");
        progressLabel.setText(currentQuestionIndex + "/" + totalQuestions);
        answerField.clear();

        startTimer();
    }

    private void startTimer(){
        if (timeline != null) {
            timeline.stop();
        }
        secondsRemaining = 30;
        updateTimerLabel();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            secondsRemaining--;
            updateTimerLabel();
            if (secondsRemaining <= 0) {
                timeline.stop();
                recordAttemptAndProceed(Integer.MIN_VALUE);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTimerLabel() {
        timerLabel.setText(String.format("00:%02d", secondsRemaining));
    }

    @FXML
    void handleNextAction(ActionEvent event) {
        timeline.stop();

        int givenAnswer;
        try {
            String text = answerField.getText();
            if (text.isEmpty() || text.equals("-")) {
                givenAnswer = Integer.MIN_VALUE;
            } else {
                givenAnswer = Integer.parseInt(text);
            }
        } catch (NumberFormatException e) {
            givenAnswer = Integer.MIN_VALUE;
        }

        recordAttemptAndProceed(givenAnswer);
    }


    private void recordAttemptAndProceed(int givenAnswer) {
        NumericQuestionAttempt attempt = new NumericQuestionAttempt(currentQuestion, givenAnswer);
        attemptsList.add(attempt);
        currentQuestionIndex++;
        showNextQuestion();
    }

    private void goToResults() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultsView.fxml"));
            Parent root = loader.load();

            ResultsViewController controller = loader.getController();
            controller.setQuizData(attemptsList, username);

            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}