public class LogisticRegressionModel {
    private double[] weights;

    public LogisticRegressionModel() {
        // Initialize model weights as needed
    }

    public double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }

    public double predict(double[] features) {
        double z = 0.0;
        for (int i = 0; i < features.length; i++) {
            z += features[i] * weights[i];
        }
        return sigmoid(z);
    }

    public double calculateCost(double[] features, double label) {
        double predicted = predict(features);
        return -label * Math.log(predicted) - (1.0 - label) * Math.log(1.0 - predicted);
    }

    public void gradientDescent(double[] features, double label, double learningRate) {
        double predicted = predict(features);
        double error = predicted - label;

        for (int i = 0; i < features.length; i++) {
            weights[i] -= learningRate * error * features[i];
        }
    }

    public void train(double[][] trainingData, double[] labels, int numEpochs, double learningRate) {
        int numFeatures = trainingData[0].length;
        weights = new double[numFeatures]; // Initialize weights

        for (int epoch = 0; epoch < numEpochs; epoch++) {
            for (int i = 0; i < trainingData.length; i++) {
                gradientDescent(trainingData[i], labels[i], learningRate);
            }
        }
    }

    public double getAccuracy(double[][] testData, double[] trueLabels, double threshold) {
        int correct = 0;
        for (int i = 0; i < testData.length; i++) {
            double prediction = predict(testData[i]);
            int predictedLabel = (prediction >= threshold) ? 1 : 0;
            if (predictedLabel == trueLabels[i]) {
                correct++;
            }
        }
        return (double) correct / testData.length;
    }
}
