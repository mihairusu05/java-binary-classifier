package main;

import data.CSVReader;
import data.DataPreprocessor;
import data.DataSplitter;

public class Main {
    public static void main(String[] args){
        CSVReader reader = new CSVReader("D:\\Info\\School\\Java\\java-binary-classifier\\data\\cancer.csv");
        System.out.println(reader.getData());
        System.out.println("**********************");
        DataPreprocessor preProcessor = new DataPreprocessor(reader.getData());
        preProcessor.process();
        System.out.println(preProcessor.getData());
        System.out.println("**********************");
        DataSplitter dataSplitter = new DataSplitter(preProcessor.getData());
        dataSplitter.splitData(0.8);
        System.out.println(dataSplitter.getTrainData());
        System.out.println("**********************");
        System.out.println(dataSplitter.getTestData());
        System.out.println("**********************");
    }
}
