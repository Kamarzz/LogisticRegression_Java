package LogisticRegression;


public class LogisticRegression {

    private double[] weights;

    private double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }

    private double[] gradientDescent(double[][] X, double[] y, double lr, int iterations) {
        int n = X.length;
        int p = X[0].length;
        double[] weights = new double[p];

        for (int iter = 0; iter < iterations; iter++) {
            double[] predictions = new double[n];
            for (int i = 0; i < n; i++) {
                double linearCombination = 0.0;
                for (int j = 0; j < p; j++) {
                    linearCombination += X[i][j] * weights[j];
                }
                predictions[i] = sigmoid(linearCombination);
            }

            for (int j = 0; j < p; j++) {
                double gradient = 0.0;
                for (int i = 0; i < n; i++) {
                    gradient += (predictions[i] - y[i]) * X[i][j];
                }
                weights[j] -= lr * gradient / n;
            }

        }

        return weights;
    }

    private double[] newtonMethod(double[][] X, double[] y, int iterations) {
        int m = X.length;
        int n = X[0].length;
        double[] weights = new double[n];

        for (int iter = 0; iter < iterations; iter++) {
            double[] predictions = new double[m];
            double[][] hessian = new double[n][n];

            // Вычисление предсказаний
            for (int i = 0; i < m; i++) {
                predictions[i] = sigmoid(dotProduct(X[i], weights));
            }

            // Вычисление градиента
            double[] gradient = new double[n];
            for (int j = 0; j < n; j++) {
                for (int i = 0; i < m; i++) {
                    gradient[j] += (predictions[i] - y[i]) * X[i][j];
                }
            }

            // Вычисление гессиана
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    for (int i = 0; i < m; i++) {
                        hessian[j][k] += predictions[i] * (1 - predictions[i]) * X[i][j] * X[i][k];
                    }
                }
            }

            // Обращение гессиана
            double[][] hessianInverse = AlgebraUtility.invert(hessian);

            // Обновление весов
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    weights[j] -= hessianInverse[j][k] * gradient[k];
                }
            }

            // Опционально, вы можете вывести log loss для мониторинга обучения
            // System.out.println("Iteration " + iter + ": Log Loss = " + logLoss(y, predictions));
        }

        return weights;
    }

    // Вспомогательный метод для вычисления скалярного произведения
    private double dotProduct(double[] x, double[] w) {
        double product = 0;
        for (int i = 0; i < x.length; i++) {
            product += x[i] * w[i];
        }
        return product;
    }

    public void fit(double[][] X, double[] y, double lr, int iterations, OptimizationMethods optimizationMethod) {
        if (optimizationMethod == OptimizationMethods.GRADIENT_DESCENT) {
            this.weights = gradientDescent(X, y, lr, iterations);
        } else if (optimizationMethod == OptimizationMethods.NEWTON) {
            this.weights = newtonMethod(X, y, iterations);
        }
    }

    public double[] predict(double[][] X) {
        double[] predictions = new double[X.length];
        for (int i = 0; i < X.length; i++) {
            double linearCombination = 0.0;
            for (int j = 0; j < weights.length; j++) {
                linearCombination += X[i][j] * weights[j];
            }
            predictions[i] = sigmoid(linearCombination);
        }
        return predictions;
    }
}


