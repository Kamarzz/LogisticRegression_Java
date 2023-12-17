package LogisticRegression.metrics;

import java.util.Arrays;

class Statistics {

    static double calculateMean(double[] data) {
        return Arrays.stream(data).average().orElse(Double.NaN);
    }

    static double calculateMin(double[] data) {
        return Arrays.stream(data).min().orElse(Double.NaN);
    }

    static double calculateMax(double[] data) {
        return Arrays.stream(data).max().orElse(Double.NaN);
    }

    static double calculateStdDev(double[] data) {
        double mean = calculateMean(data);
        return Math.sqrt(Arrays.stream(data).map(x -> Math.pow(x - mean, 2)).sum() / data.length);
    }

    static double calculateQuantile(double[] data, double quantile) {
        double[] sortedData = Arrays.copyOf(data, data.length);
        Arrays.sort(sortedData);
        double pos = quantile * (sortedData.length + 1);
        int index = (int) pos;
        if (index < 1) {
            return sortedData[0];
        } else if (index >= sortedData.length) {
            return sortedData[sortedData.length - 1];
        } else {
            double diff = pos - index;
            return sortedData[index - 1] + diff * (sortedData[index] - sortedData[index - 1]);
        }
    }

}

