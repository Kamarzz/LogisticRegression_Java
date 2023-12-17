package LogisticRegression.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class DataProcessor {

    private double[][] data;

    public double[][] getNormalizedData() {
        return normalizedData;
    }

    private double[][] normalizedData;
    private String[] columnNames;

    public DataProcessor(String filePath) {
        loadData(filePath);
        preprocessData();
        normalizeData();
    }

    private void loadData(String filePath) {
        try (Reader in = new FileReader(filePath)) {
            CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            columnNames = parser.getHeaderMap().keySet().toArray(new String[0]);

            int numRows = records.size();
            int numCols = columnNames.length;
            data = new double[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                CSVRecord record = records.get(i);
                for (int j = 0; j < numCols; j++) {
                    String value = record.get(columnNames[j]);
                    data[i][j] = value.matches("-?\\d+(\\.\\d+)?") ? Double.parseDouble(value) : value.equals("Yes") ? 1.0 : 0.0;
                }
            }
            normalizedData = new double[numRows][numCols];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void preprocessData() {
        // Преобразование выполнено в loadData
    }

    private void normalizeData() {
        for (int col = 0; col < data[0].length; col++) {
            double[] columnData = getColumn(data, col);
            if (Arrays.asList("Glucose", "BloodPressure", "BMI").contains(columnNames[col])) {
                columnData = standardScale(columnData);
            } else if (!columnNames[col].equals("Outcome")) {
                columnData = minMaxScale(logarithmicTransform(columnData));
            }
            setColumn(normalizedData, col, columnData);
        }
    }

    private static double[] getColumn(double[][] array, int index) {
        return Arrays.stream(array).mapToDouble(row -> row[index]).toArray();
    }

    private static void setColumn(double[][] array, int index, double[] columnData) {
        for (int i = 0; i < array.length; i++) {
            array[i][index] = columnData[i];
        }
    }

    private static double[] standardScale(double[] data) {
        double mean = Arrays.stream(data).average().orElse(0);
        double stdDev = Math.sqrt(Arrays.stream(data).map(val -> Math.pow(val - mean, 2)).sum() / data.length);
        return Arrays.stream(data).map(val -> (val - mean) / stdDev).toArray();
    }

    private static double[] minMaxScale(double[] data) {
        double min = Arrays.stream(data).min().orElse(0);
        double max = Arrays.stream(data).max().orElse(1);
        return Arrays.stream(data).map(val -> (val - min) / (max - min)).toArray();
    }

    private static double[] logarithmicTransform(double[] data) {
        return Arrays.stream(data).map(val -> Math.log(val + 1)).toArray();
    }

    public static double[][][] splitData(double[][] data, double proportion) {
        int totalRows = data.length;
        int trainSize = (int) (totalRows * proportion);

        double[][] trainData = Arrays.copyOfRange(data, 0, trainSize);
        double[][] testData = Arrays.copyOfRange(data, trainSize, totalRows);

        return new double[][][]{trainData, testData};
    }


    public static double[][] extractFeatures(double[][] data) {
        // Извлекаем все столбцы, кроме последнего
        double[][] features = new double[data.length][data[0].length - 1];
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, features[i], 0, data[i].length - 1);
        }
        return features;
    }

    public static double[] extractLabels(double[][] data) {
        // Извлекаем последний столбец
        double[] labels = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            labels[i] = data[i][data[i].length - 1];
        }
        return labels;
    }

}
