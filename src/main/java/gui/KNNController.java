package gui;

import core.Instance;
import evaluation.Accuracy;
import evaluation.ConfusionMatrix;
import evaluation.Precision;
import evaluation.Recall;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.KNN;
import models.LogisticRegression;

import java.util.ArrayList;
import java.util.List;

public class KNNController {
    List<Instance<Double, String>> trainData ;
    List<Instance<Double, String>> testData;
    List<String> label ;
    String positiveLabel;

    KNNController(List<Instance<Double, String>> train, List<Instance<Double, String >> test, List<String> label, String truePositive){
        this.trainData = train;
        this.testData = test;
        this.label = label;
        this.positiveLabel = truePositive;
    }

    @FXML
    private TextField getK;

    @FXML
    private Label accuracy;

    @FXML
    private Label precision;

    @FXML
    private Label recall;

    @FXML
    private Label truePositive;

    @FXML
    private Label falseNegative;

    @FXML
    private Label falsePositive;

    @FXML
    private Label trueNegative;

    @FXML
    public void trainTestRunKNN(ActionEvent event){
        try{
            int k = Integer.parseInt(getK.getText());
            KNN model = new KNN(k);
            model.train(this.trainData);
            List<String> predictions = model.test(this.testData);
            Accuracy<Double, String> accuracy1 = new Accuracy<Double, String>();
            Precision<Double, String> precision1 = new Precision<Double, String>(this.positiveLabel);
            Recall<Double, String> recall1 = new Recall<Double, String>(this.positiveLabel);
            ConfusionMatrix confusionMatrix = new ConfusionMatrix(this.positiveLabel);
            confusionMatrix.calculate(this.label, predictions);
            this.accuracy.setText(accuracy1.evaluate(this.testData, predictions)*100 + "%");
            this.precision.setText(precision1.evaluate(this.testData, predictions)*100 + "%");
            this.recall.setText(recall1.evaluate(this.testData, predictions)*100 + "%");
            this.truePositive.setText(confusionMatrix.getTP()+"");
            this.falseNegative.setText(confusionMatrix.getFN()+"");
            this.falsePositive.setText(confusionMatrix.getFP()+"");
            this.trueNegative.setText(confusionMatrix.getTN()+"");
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }
}
