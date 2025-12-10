package models;

import core.Instance;
import core.Model;

import java.util.*;
import java.util.stream.Collectors;

public class KNN implements Model<Double, String> {
    /// For Knn training the best k should be between 1 and rad(number of training points)

    private Integer k;
    private List<Instance<Double, String>> trainingData;

    public KNN(int k){
        if ( k < 1){
            throw new IllegalArgumentException("K parameter should be bigger than 0");
        }
        this.k = k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    @Override
    public void train(List<Instance<Double, String>> trainingData){
        this.trainingData = trainingData;
    }

    private double distanceEuclidean(List<Double> e1, List<Double> e2){
        double sum = 0.0;
        int size = e1.size();
        for (int i = 0; i < size; i++){
            double diff = e1.get(i) - e2.get(i);
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }

    @Override
    public List<String> test(List<Instance<Double, String>> testData){
        return testData.stream()
                .map(this::findLabel)
                .collect(Collectors.toList());
    }

    public String findLabel(Instance<Double, String> instance){
        class NeighborPair implements Comparable<NeighborPair> {
            Double distance;
            String label;

            public NeighborPair(Double d, String l) {
                this.distance = d;
                this.label = l; }

            @Override
            public int compareTo(NeighborPair other) {
                return this.distance.compareTo(other.distance);
            }
        }

        List<NeighborPair> neighbors = new ArrayList<>();
        for (Instance<Double, String> trainInstance : this.trainingData) {
            double dist = distanceEuclidean(trainInstance.getInput(), instance.getInput());
            neighbors.add(new NeighborPair(dist, trainInstance.getOutput()));
        }
        Collections.sort(neighbors);
        List<String> topKLabels = new ArrayList<>();
        for (int i = 0; i < Math.min(this.k, neighbors.size()); i++) {
            topKLabels.add(neighbors.get(i).label);
        }

        Map<String, Integer> counts = new HashMap<>();
        for (String label : topKLabels) {
            counts.put(label, counts.getOrDefault(label, 0) + 1);
        }

        String bestLabel = "";
        int maxCount = -1;

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                bestLabel = entry.getKey();
            }
        }

        return bestLabel;
    }
}