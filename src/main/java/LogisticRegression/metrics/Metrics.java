package LogisticRegression.metrics;

public class Metrics {

    public static double calculateAccuracy(double[] yTrue, double[] yPred) {
        int correctCount = 0;
        for (int i = 0; i < yTrue.length; i++) {
            if ((yPred[i] >= 0.5 ? 1 : 0) == yTrue[i]) {
                correctCount++;
            }
        }
        return (double) correctCount / yTrue.length;
    }

    public static double calculatePrecision(double[] yTrue, double[] yPred) {
        int truePositives = 0;
        int falsePositives = 0;
        for (int i = 0; i < yTrue.length; i++) {
            int prediction = yPred[i] >= 0.5 ? 1 : 0;
            if (prediction == 1) {
                if (yTrue[i] == 1) {
                    truePositives++;
                } else {
                    falsePositives++;
                }
            }
        }
        return truePositives == 0 ? 0 : (double) truePositives / (truePositives + falsePositives);
    }

    public static double calculateRecall(double[] yTrue, double[] yPred) {
        int truePositives = 0;
        int falseNegatives = 0;
        for (int i = 0; i < yTrue.length; i++) {
            int prediction = yPred[i] >= 0.5 ? 1 : 0;
            if (yTrue[i] == 1) {
                if (prediction == 1) {
                    truePositives++;
                } else {
                    falseNegatives++;
                }
            }
        }
        return truePositives == 0 ? 0 : (double) truePositives / (truePositives + falseNegatives);
    }

    public static double calculateF1Score(double[] yTrue, double[] yPred) {
        double precision = calculatePrecision(yTrue, yPred);
        double recall = calculateRecall(yTrue, yPred);
        return (precision + recall) == 0 ? 0 : 2 * (precision * recall) / (precision + recall);
    }
}
