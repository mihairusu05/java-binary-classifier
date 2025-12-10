package main;

import core.Instance;
import data.CSVReader;
import data.DataPreprocessor;
import data.DataSplitter;
import models.KNN;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        CSVReader reader1 = new CSVReader("D:\\Info\\School\\Java\\java-binary-classifier\\data\\cancer.csv");
        CSVReader reader2 = new CSVReader("D:\\Info\\School\\Java\\java-binary-classifier\\data\\sonar data.csv");
        CSVReader reader3 = new CSVReader("D:\\Info\\School\\Java\\java-binary-classifier\\data\\telescope_data_no_id.csv");
        DataPreprocessor preProcessor = new DataPreprocessor(reader3.getData());
        preProcessor.process();
        DataSplitter dataSplitter = new DataSplitter(preProcessor.getData());
        dataSplitter.splitData(0.8);
        List<Instance<Double, String>> trainData = dataSplitter.getTrainData();
        List<Instance<Double, String>> testData = dataSplitter.getTestData();
        List<String> label = new ArrayList<>();
        testData.forEach(p -> label.add(p.getOutput()));

        KNN knnModel = new KNN(61);
        knnModel.train(trainData);
        List<String> predictions = knnModel.test(testData);
        double correct = 0;
        for (int m = 0; m< label.size() ; m++)
            if (predictions.get(m).equals(label.get(m))){
                correct++;
            }
        System.out.println("For k = " + 61 + "the accuracy is : " + correct*100/label.size() + " %");
       }

}
