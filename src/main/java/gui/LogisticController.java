package gui;

import core.Instance;
import evaluation.Accuracy;
import evaluation.Precision;
import evaluation.Recall;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.KNN;
import models.LogisticRegression;

import java.util.List;

public class LogisticController {
    List<Instance<Double, String>> trainData ;
    List<Instance<Double, String>> testData;
    List<String> label ;
    String positiveLabel;

    LogisticController(List<Instance<Double, String>> train, List<Instance<Double, String >> test, List<String> label, String positiveLabel){
        this.trainData = train;
        this.testData = test;
        this.label = label;
        this.positiveLabel = positiveLabel;
    }

    @FXML
    private Label accuracy;

    @FXML
    private Label precision;

    @FXML
    private Label recall;

    @FXML
    private TextField epochs;

    @FXML
    private TextField learningRate;

    @FXML
    public void trainTestRunLogistic(ActionEvent event){
        try{
            int epochs = Integer.parseInt(this.epochs.getText());
            double learningRate = Double.parseDouble(this.learningRate.getText());
            LogisticRegression model = new LogisticRegression(learningRate, epochs);
            model.train(this.trainData);
            List<String> predictions = model.test(this.testData);
            Accuracy<Double, String> accuracy1 = new Accuracy<Double, String>();
            Precision<Double, String> precision1 = new Precision<Double, String>(this.positiveLabel);
            Recall<Double, String> recall1 = new Recall<Double, String>(this.positiveLabel);
            this.accuracy.setText(accuracy1.evaluate(this.testData, predictions)*100 + "%");
            this.precision.setText(precision1.evaluate(this.testData, predictions)*100 + "%");
            this.recall.setText(recall1.evaluate(this.testData, predictions)*100 + "%");
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }
}
