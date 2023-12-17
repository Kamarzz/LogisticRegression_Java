package LogisticRegression;

public class AlgebraUtility {

    public static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] transposed = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    public static double[][] multiply(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        double[][] result = new double[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    public static double[] operate(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[] result = new double[rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    public static double[][] invert(double[][] matrix) {
        int n = matrix.length;
        double[][] identity = new double[n][n];
        double[][] augmented = new double[n][2 * n];

        // Создаем расширенную матрицу [A|I], где A - исходная матрица, I - единичная матрица
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = matrix[i][j];
                identity[i][j] = (i == j) ? 1 : 0; // создание единичной матрицы
                augmented[i][j + n] = identity[i][j];
            }
        }

        // Применяем метод Гаусса-Жордана для получения инвертированной матрицы
        for (int i = 0; i < n; i++) {
            double max = augmented[i][i];
            int row = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmented[k][i]) > max) {
                    max = Math.abs(augmented[k][i]);
                    row = k;
                }
            }

            double[] temp = augmented[i];
            augmented[i] = augmented[row];
            augmented[row] = temp;

            double pivot = augmented[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] = augmented[i][j] / pivot;
            }

            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmented[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmented[k][j] -= factor * augmented[i][j];
                    }
                }
            }
        }

        // Извлекаем инвертированную матрицу из расширенной матрицы
        double[][] inverted = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmented[i], n, inverted[i], 0, n);
        }

        return inverted;
    }

}

