package LogisticRegression;


import LogisticRegression.data.DataProcessor;
import LogisticRegression.metrics.Metrics;
import LogisticRegression.metrics.VisualizationUtility;

import static LogisticRegression.data.DataProcessor.extractFeatures;
import static LogisticRegression.data.DataProcessor.extractLabels;

public class Main {

    public static void main(String[] args) {
        DataProcessor data = new DataProcessor("src/main/java/LogisticRegression/data/dataStorage/diabetes.csv");
        double[][] normalizedData = data.getNormalizedData();

        VisualizationUtility.visualizeDataset(normalizedData);

        double[][][] splitData = DataProcessor.splitData(normalizedData, 0.8);

        double[][] trainData = splitData[0];
        double[][] testData = splitData[1];

        double[][] XTrain = extractFeatures(trainData);
        double[] YTrain = extractLabels(trainData);
        double[][] XTest = extractFeatures(testData);
        double[] YTest = extractLabels(testData);

        LogisticRegression logisticRegression = new LogisticRegression();
        logisticRegression.fit(XTrain, YTrain, 0.05, 100, OptimizationMethods.NEWTON);

        double[] predicted = logisticRegression.predict(XTest);

        System.out.println("Accuracy: " + Metrics.calculateAccuracy(YTest, predicted));
        System.out.println("Precision: " + Metrics.calculatePrecision(YTest, predicted));
        System.out.println("Recall: " + Metrics.calculateRecall(YTest, predicted));
        System.out.println("F1Score: " + Metrics.calculateF1Score(YTest, predicted));

    }

}
