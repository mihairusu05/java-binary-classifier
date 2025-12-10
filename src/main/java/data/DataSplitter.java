package data;

import core.Instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataSplitter {
    private List<Instance<Double, String>> data;
    private List<Instance<Double, String>> trainData = new ArrayList<>();
    private List<Instance<Double, String>> testData = new ArrayList<>();

    public DataSplitter(List<Instance<Double, String>> data){
        this.data = data;
    }

    public List<Instance<Double, String>> getData() {
        return data;
    }

    public List<Instance<Double, String>> getTrainData() {
        return trainData;
    }

    public List<Instance<Double, String>> getTestData() {
        return testData;
    }

    public void splitData(Double percentage){
        List<Instance<Double, String>> shuffled = new ArrayList<>(this.data);
        Collections.shuffle(shuffled);

        int splitIndex = (int) (shuffled.size() * percentage);
        this.trainData = new ArrayList<>(shuffled.subList(0, splitIndex));
        this.testData = new ArrayList<>(shuffled.subList(splitIndex, shuffled.size()));
    }


}
