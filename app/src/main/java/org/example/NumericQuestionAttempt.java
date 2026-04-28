package main.java.org.example;
public class NumericQuestionAttempt {
    private NumericQuestion question;
    private int givenAnswer;

    public NumericQuestionAttempt(NumericQuestion question, int givenAnswer) {
        this.question = question;
        this.givenAnswer = givenAnswer;
    }
     public NumericQuestion getQuestion() {
        return question;
    }

    public int getGivenAnswer() {
        return givenAnswer;
    }

    public boolean isCorrect() {
        return givenAnswer == question.getResult();
    }

    public String getResult() {
        return isCorrect() ? "Correct" : "Wrong";
    }

   
}