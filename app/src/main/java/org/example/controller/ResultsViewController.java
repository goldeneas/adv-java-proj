package org.example.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.example.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultsViewController implements Initializable {

    @FXML
    private TableView<NumericQuestionAttempt> resultsTable;

    @FXML
    private TableColumn<NumericQuestionAttempt, String> attemptColumn;

    @FXML
    private TableColumn<NumericQuestionAttempt, String> outcomeColumn;

    @FXML
    private Label messageLabel;

    @FXML
    private Button exportButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.attemptColumn.setCellValueFactory(new PropertyValueFactory<>("attempt"));
        this.outcomeColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        List<NumericQuestionAttempt> attempts = ;
        this.resultsTable.setItems(FXCollections.observableArrayList(attempts));

        this.messageLabel.setText("Gentile " + /* getUserName() */ + ", grazie per aver completato il quiz. " + "Esporta i tuoi risultati su file.");
    }

    @FXML
    private void exportFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salva i risultati del quiz");
        fileChooser.setInitialFileName("risultati.txt");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File di testo (*.txt)", "*.txt"));

        File file = fileChooser.showSaveDialog(exportButton.getScene().getWindow());

        if (file != null) {
            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
                pw.println("TENTATIVO; RISULTATO CORRETTO; ESITO");

                for (NumericQuestionAttempt attempt : resultsTable.getItems()) {
                    String line = String.format("%s=%d; %d; %s",
                            attempt.getQuestion(),
                            attempt.getGivenAnswer(),
                            attempt.getQuestion().getResult(),
                            attempt.getResult()
                    );
                    pw.println(line);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}