package LogisticRegression.metrics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class VisualizationUtility {

    public static void visualizeDataset(double[][] dataset) {
        for (int col = 0; col < dataset[0].length; col++) {
            double[] columnData = getColumn(dataset, col);

            // Визуализация
            showHistogram(columnData, "Гистограмма столбца " + (col + 1), "Значения", "Частота");

            // Вывод статистических данных
            printStatistics(columnData, "Статистика столбца " + (col + 1));
        }
    }

    private static double[] getColumn(double[][] array, int index) {
        return Arrays.stream(array).mapToDouble(row -> row[index]).toArray();
    }

    private static void showHistogram(double[] data, String title, String xAxisLabel, String yAxisLabel) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("Key", data, 50);

        JFreeChart histogram = ChartFactory.createHistogram(
            title, xAxisLabel, yAxisLabel, dataset,
            PlotOrientation.VERTICAL, false, true, false);

        displayChart(histogram, title);
    }

    private static void printStatistics(double[] data, String title) {
        System.out.println(title);
        System.out.println("Количество: " + data.length);
        System.out.println("Среднее: " + Statistics.calculateMean(data));
        System.out.println("Стандартное отклонение: " + Statistics.calculateStdDev(data));
        System.out.println("Минимум: " + Statistics.calculateMin(data));
        System.out.println("Максимум: " + Statistics.calculateMax(data));
        System.out.println("1-й квантиль: " + Statistics.calculateQuantile(data, 0.25));
        System.out.println("2-й квантиль: " + Statistics.calculateQuantile(data, 0.5));
        System.out.println("3-й квантиль: " + Statistics.calculateQuantile(data, 0.75));
        System.out.println();
    }

    private static void displayChart(JFreeChart chart, String title) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));
        JFrame frame = new JFrame(title);
        frame.getContentPane().add(chartPanel, BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
