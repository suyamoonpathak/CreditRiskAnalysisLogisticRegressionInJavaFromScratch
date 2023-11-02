import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DataLoader dataLoader = new DataLoader();
        try {
            // Load and preprocess data
            dataLoader.loadData("cs-training.csv");
            dataLoader.preprocessData();

            // Initialize logistic regression model
            LogisticRegressionModel model = new LogisticRegressionModel();

            int numEpochs = 100;
            double learningRate = 0.01;

            // Convert List<Double> to double[]
            double[] yTrainArray = dataLoader.getYTrain().stream()
                    .mapToDouble(Double::doubleValue)
                    .toArray();

            // Train the model
            double[][] xTrainArray = new double[dataLoader.getXTrain().size()][];
            for (int i = 0; i < dataLoader.getXTrain().size(); i++) {
                xTrainArray[i] = dataLoader.getXTrain().get(i);
            }

            model.train(xTrainArray, yTrainArray, numEpochs, learningRate);

            // Make predictions on the test data
            double[] trueLabels = dataLoader.getYTest().stream()
                    .mapToDouble(Double::doubleValue)
                    .toArray();
            List<double[]> xTestList = dataLoader.getXTest();
            double[][] testData = new double[xTestList.size()][];

            for (int i = 0; i < xTestList.size(); i++) {
                testData[i] = xTestList.get(i);
            }

            double threshold = 0.5; // Adjust threshold as needed
            double accuracy = model.getAccuracy(testData, trueLabels, threshold);

            // Print the accuracy
            System.out.println("Accuracy: " + accuracy);

            // Create a Testing object
            Testing tester = new Testing();

            // Predict and save results
            tester.predictAndSave("cs-test.csv", "results.csv", model);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
