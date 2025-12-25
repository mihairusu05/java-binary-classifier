package models;

import core.Instance;
import core.Model;

import java.util.*;

public class GaussianNaiveBayes implements Model<Double, String> {
    private Map<String, List<Double>> meansMap = new HashMap<>();
    private Map<String, List<Double>> variancesMap = new HashMap<>();
    int totalNumber;
    int label1NrFeatures;
    int label2NrFeatures;


    public Double getMean(List<Double> featureValues){
        double sum = featureValues.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        return sum / featureValues.size();
    }

    public Double getSampleVariance(List<Double> featureValues){
        double mean = this.getMean(featureValues);
        double sum = featureValues.stream()
                .mapToDouble(feature -> Math.pow(feature - mean, 2))
                .sum();

        return sum / (featureValues.size() - 1);
    }

    @Override
    public void train(List<Instance<Double, String>> instances) {
        this.totalNumber = instances.size();
        Map<String, List<Instance<Double, String>>> sortedData = new HashMap<>();

        for (Instance<Double, String> inst : instances) {
            String label = inst.getOutput();
            sortedData.putIfAbsent(label, new ArrayList<>());
            sortedData.get(label).add(inst);
        }

        List<String> labels = new ArrayList<>(sortedData.keySet());
        String label1Name = labels.getFirst();
        String label2Name = labels.getLast();
        this.label1NrFeatures = sortedData.get(label1Name).size();
        this.label2NrFeatures = sortedData.get(label2Name).size();

        for (Map.Entry<String, List<Instance<Double, String>>> entry : sortedData.entrySet()) {
            String className = entry.getKey();
            List<Instance<Double, String>> classRows = entry.getValue();
            List<Double> currentClassMeans = new ArrayList<>();
            List<Double> currentClassVariances = new ArrayList<>();

            int i = 0;
            int nrOfFeatures = classRows.get(0).getInput().size();

            while(i < nrOfFeatures){
                List<Double> featureValues = new ArrayList<>();

                for (Instance<Double, String> in : classRows){
                    double feature = in.getInput().get(i);
                    featureValues.add(feature);
                }
                currentClassMeans.add(this.getMean(featureValues));
                currentClassVariances.add(this.getSampleVariance(featureValues));

                i++;
            }
            meansMap.put(className, currentClassMeans);
            variancesMap.put(className, currentClassVariances);
        }
    }

    @Override
    public List<String> test(List<Instance<Double, String>> instances){
        return instances.stream()
                .map(this::processInstance)
                .toList();
    }

    public String processInstance(Instance<Double, String> instance) {
//        Here we compute P(label|features) = P(features|label) * P(label) / P(features)
//        Since P(features) is for both labels the same we save time but not computing it
//        Also we use the mathematical logarithm trick to avoid multiplying by very small numbers,
//        therefore making the score 0 either way.

        List<String> labels = new ArrayList<>(this.meansMap.keySet());
        String label1 = labels.get(0);
        String label2 = labels.get(1);
        double label1Score = Math.log((double) this.label1NrFeatures / this.totalNumber);
        double label2Score = Math.log((double) this.label2NrFeatures / this.totalNumber);

        int index = 0;
        for (Double feature : instance.getInput()) {
            double mean1 = this.meansMap.get(label1).get(index);
            double variance1 = this.variancesMap.get(label1).get(index);
            double mean2 = this.meansMap.get(label2).get(index);
            double variance2 = this.variancesMap.get(label2).get(index);

            double prob1 = (1 / Math.sqrt(2 * Math.PI * variance1)) * Math.exp(-Math.pow((feature - mean1), 2) / (2 * variance1));
            double prob2 = (1 / Math.sqrt(2 * Math.PI * variance2)) * Math.exp(-Math.pow((feature - mean2), 2) / (2 * variance2));

            label1Score += (prob1 > 1e-15) ? Math.log(prob1) : -100.0;
            label2Score += (prob2 > 1e-15) ? Math.log(prob2) : -100.0;

            index++;
        }

        if (label1Score > label2Score) {
            return label1;
        } else {
            return label2;
        }
    }
}
