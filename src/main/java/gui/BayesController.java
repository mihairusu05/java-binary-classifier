package gui;

import core.Instance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.GaussianNaiveBayes;
import models.KNN;

import java.util.ArrayList;
import java.util.List;

public class BayesController {
    List<Instance<Double, String>> trainData ;
    List<Instance<Double, String>> testData;
    List<String> label ;

    BayesController(List<Instance<Double, String>> train, List<Instance<Double, String >> test, List<String> label){
        this.trainData = train;
        this.testData = test;
        this.label = label;
    }


    @FXML
    private Label showAccuracy;

    @FXML
    public void trainTestRun(ActionEvent event){
        try{
            GaussianNaiveBayes model = new GaussianNaiveBayes();
            model.train(this.trainData);
            List<String> predictions = model.test(this.testData);

            double correct = 0;
            for (int m = 0; m< label.size() ; m++)
                if (predictions.get(m).equals(label.get(m))){
                    correct++;
                }

            this.showAccuracy.setText(correct*100/label.size() + " %");
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }
}
