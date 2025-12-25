package models;

import core.Instance;
import core.Model;

import java.util.*;

public class LogisticRegression implements Model<Double, String> {
    private List<Double> weights;
    private Double bias;
    private Integer epochs;
    private Double learningRate;
    private String positiveLabel;
    private String negativeLabel;

    public LogisticRegression(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
        this.weights = new ArrayList<>();
        this.bias = 0.0;
    }

    private double sigmoid(Double z){
        return 1.0/ (1.0 + Math.exp(-z));
    }

    private double getLabelAsNumber(String label){
        if (label.equals(this.positiveLabel)){
            return 1.0;
        }
        else if(label.equals(this.negativeLabel)){
            return 0.0;
        }

        else
            throw new IllegalArgumentException("Error in converting label as number.");
    }

    @Override
    public void train(List<Instance<Double , String>> instances){
        Map<String, List<Instance<Double, String>>> sortedData = new HashMap<>();

        for (Instance<Double, String> inst : instances) {
            String label = inst.getOutput();
            sortedData.putIfAbsent(label, new ArrayList<>());
            sortedData.get(label).add(inst);
        }

        List<String> labels = new ArrayList<>(sortedData.keySet());
        this.positiveLabel = labels.getFirst();
        this.negativeLabel = labels.getLast();

        Integer nrFeatures = instances.getFirst().getInput().size();
        this.bias = 0.0;
        /// Make the weights 0 initially
        this.weights = new ArrayList<>(Collections.nCopies(nrFeatures, 0.0));

        for (int epoch = 0; epoch < this.epochs; epoch++){
            for(Instance<Double, String> inst : instances){
                List<Double> input = inst.getInput();

                Double z = 0.0;
                for (int j = 0; j< nrFeatures ;j++){
                    z+=input.get(j)*weights.get(j);
                }
                z+=this.bias;

                double prediction = this.sigmoid(z);
                double actual = this.getLabelAsNumber(inst.getOutput());
                double error = prediction - actual;

                for(int j = 0; j < nrFeatures; j++){
                    double currentVal = this.weights.get(j);
                    double inputVal = input.get(j);
                    double newVal = currentVal - (inputVal*learningRate*error);
                    this.weights.set(j, newVal);
                }

                this.bias = this.bias - (this.learningRate * error);
            }
        }

    }

    public List<String> test(List<Instance<Double, String>> instances){
        return instances.stream()
                .map(this::processInstance)
                .toList();
    }

    public String processInstance(Instance<Double, String> instance){
        Double z = 0.0;
        int nrFeatures = instance.getInput().size();
        for (int i = 0 ; i < nrFeatures; i ++){
            z+=this.weights.get(i)*instance.getInput().get(i);
        }
        z+=this.bias;

        Double prob = this.sigmoid(z);
        if (prob > 0.5)
            return this.positiveLabel;

        else
            return this.negativeLabel;
    }
}
