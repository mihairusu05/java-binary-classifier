package evaluation;

import core.EvaluationMeasure;
import core.Instance;

import java.util.List;

public class Accuracy<F, L> implements EvaluationMeasure<F, L> {
    @Override
    public double evaluate(List<Instance<F, L>> instances, List<L> predictions){
        int correct = 0;
        int total = instances.size();

        for (int i = 0; i < total; i++){
            L actual = instances.get(i).getOutput();
            L prediction = predictions.get(i);

            if (actual.equals(prediction)){
                correct++;
            }
        }

        return (double) correct/ total;
    }
}
