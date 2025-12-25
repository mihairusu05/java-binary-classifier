package evaluation;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ConfusionMatrix {

    private final String positiveLabel;
    private int TP = 0;
    private int FP = 0;
    private int TN = 0;
    private int FN = 0;

    public ConfusionMatrix(String positiveLabel) {
        this.positiveLabel = positiveLabel;
    }

    public void calculate(List<String> groundTruth, List<String> predictions) {
        if (groundTruth.size() != predictions.size()) {
            throw new IllegalArgumentException("Lists must be of the same size.");
        }
        TP = 0; FP = 0; TN = 0; FN = 0;

        for (int i = 0; i < groundTruth.size(); i++) {
            String actual = groundTruth.get(i);
            String predicted = predictions.get(i);

            if (actual.equals(positiveLabel)) {
                if (predicted.equals(positiveLabel)) {
                    TP++;
                } else {
                    FN++;
                }
            } else {
                if (predicted.equals(positiveLabel)) {
                    FP++;
                } else {
                    TN++;
                }
            }
        }
    }

    // Getters
    public int getTP() { return TP; }
    public int getFP() { return FP; }
    public int getTN() { return TN; }
    public int getFN() { return FN; }
}