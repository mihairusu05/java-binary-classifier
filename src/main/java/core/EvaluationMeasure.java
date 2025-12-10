package core;

import java.util.List;

public interface EvaluationMeasure<F, L> {
    public void evaluate(List<Instance<F, L>> instances, List<L> predictions);
}
