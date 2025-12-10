package data;

import core.Instance;
import java.util.List;

public class DataPreprocessor {
    private List<Instance<Double, String>> data;

    public DataPreprocessor(List<Instance<Double, String>> data){
        this.data = data;
    }

    public void process(){
        int numFeatures = this.data.get(0).getInput().size();
        for (int i = 0; i < numFeatures; i++) {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            for (Instance<Double, String> instance : this.data) {
                double val = instance.getInput().get(i);
                if (val < min) min = val;
                if (val > max) max = val;
            }
            if (max - min == 0) {
                continue;
            }

            for (Instance<Double, String> instance : this.data) {
                double originalValue = instance.getInput().get(i);
                double normalizedValue = (originalValue - min) / (max - min);
                instance.getInput().set(i, normalizedValue);
            }
        }
    }

    public List<Instance<Double, String>> getData() {
        return data;
    }
}
