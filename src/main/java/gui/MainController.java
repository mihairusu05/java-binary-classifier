package gui;

import config.Config;
import core.Instance;
import data.CSVReader;
import data.DataPreprocessor;
import data.DataSplitter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {

    private CSVReader csvReader;
    private DataPreprocessor dataPreprocessor;
    private DataSplitter dataSplitter;
    List<Instance<Double, String>> trainData ;
    List<Instance<Double, String>> testData;
    List<String> label = new ArrayList<>();

    @FXML
    private ComboBox<String> chooseData;

    @FXML
    private TextField percentage;

    private Map<String, String> dataset = new HashMap<>();

    @FXML
    public void initialize() {
        this.populate();
    }

    public void populate(){
        try{
            String path1 = Config.get("CANCER_PATH");
            String path2  = Config.get("SONAR_PATH");
            String path3 = Config.get("TELESCOPE_PATH");
            dataset.put("Breast Cancer(Malign or Benign)", path1);
            dataset.put("Sonar (Mines or Rocks)", path2);
            dataset.put("Telescope data(gamma or hadron)", path3);
            chooseData.getItems().addAll(dataset.keySet());
            chooseData.setOnAction(event -> chooseDataset());
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void handleKNN(ActionEvent event){
        try{
            this.chooseDataset();
            FXMLLoader knnLoader = new FXMLLoader(getClass().getResource("KNN.fxml"));
            KNNController knnController = new KNNController(this.trainData, this.testData, this.label);
            knnLoader.setController(knnController);
            Stage knnStage = new Stage();
            Scene scene = new Scene(knnLoader.load());
            knnStage.setScene(scene);
            knnStage.setTitle("KNN Classification");
            knnStage.setMinWidth(400);
            knnStage.setMinHeight(300);
            knnStage.show();

        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    public void chooseDataset() {

        String selectedName = chooseData.getValue();

        if (selectedName != null) {
            String actualPath = dataset.get(selectedName);
            this.csvReader = new CSVReader(actualPath);
            this.dataPreprocessor = new DataPreprocessor(csvReader.getData());
            this.dataPreprocessor.process();
            this.dataSplitter = new DataSplitter(this.dataPreprocessor.getData());
            dataSplitter.splitData(Double.parseDouble(this.percentage.getText()));
            this.trainData = dataSplitter.getTrainData();
            this.testData = dataSplitter.getTestData();
            this.label = new ArrayList<>();
            this.testData.forEach(p -> this.label.add(p.getOutput()));
        }
    }
}
