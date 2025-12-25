package evaluation;

import core.EvaluationMeasure;
import core.Instance;

import java.util.List;

public class Precision<F, L> implements EvaluationMeasure<F, L> {
    private L positiveLabel;
    public Precision(L positiveLabel){
        this.positiveLabel = positiveLabel;
    }

    @Override
    public double evaluate(List<Instance<F, L>> instances, List<L> predictions) {
        int truePositives = 0;
        int falsePositives = 0;

        for (int i = 0; i < instances.size(); i++) {
            L actual = instances.get(i).getOutput();
            L predicted = predictions.get(i);
            if (predicted.equals(positiveLabel)) {
                if (actual.equals(positiveLabel)) {
                    truePositives++;
                } else {
                    falsePositives++;
                }
            }
        }

        if (truePositives + falsePositives == 0)
            return 0.0;

        return (double) truePositives / (truePositives + falsePositives);
    }
}
