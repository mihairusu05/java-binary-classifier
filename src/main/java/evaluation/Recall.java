package evaluation;

import core.EvaluationMeasure;
import core.Instance;

import java.util.List;

public class Recall<F, L> implements EvaluationMeasure<F, L> {
    private L positiveLabel;

    public Recall(L positiveLabel) {
        this.positiveLabel = positiveLabel;
    }

    @Override
    public double evaluate(List<Instance<F, L>> instances, List<L> predictions) {
        int truePositives = 0;
        int falseNegatives = 0;

        for (int i = 0; i < instances.size(); i++) {
            L actual = instances.get(i).getOutput();
            L predicted = predictions.get(i);

            if (actual.equals(positiveLabel)) {
                if (predicted.equals(positiveLabel)) {
                    truePositives++;
                } else {
                    falseNegatives++;
                }
            }
        }

        if (truePositives + falseNegatives == 0) return 0.0;
        return (double) truePositives / (truePositives + falseNegatives);
    }
}