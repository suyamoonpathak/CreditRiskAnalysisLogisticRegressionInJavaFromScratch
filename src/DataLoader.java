import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private List<double[]> trainingData;
    private List<Double> labels;

    private List<double[]> xTrain;
    private List<Double> yTrain;
    private List<double[]> xTest;
    private List<Double> yTest;

    public DataLoader() {
        trainingData = new ArrayList<>();
        labels = new ArrayList<>();
        xTrain = new ArrayList<>();
        yTrain = new ArrayList<>();
        xTest = new ArrayList<>();
        yTest = new ArrayList<>();
    }

    public void loadData(String csvFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
        String line;

        // Skip the header line
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length > 0) {
                double[] features = new double[parts.length - 2]; // Remove the first (index) and last (label) columns
                boolean validData = true;

                for (int i = 2; i < parts.length; i++) {
                    if (i == 8 && parts[i].equals("NA")) {
                        // Handle missing "MonthlyIncome" value, e.g., replace with the mean
                        // You can choose your imputation strategy here
                        // For simplicity, let's replace "NA" with 0.0
                        features[i - 2] = 0.0;
                    } else if (parts[i].equals("NA")) {
                        // Handle missing values for other columns (if any)
                        validData = false;
                        break;
                    } else {
                        features[i - 2] = Double.parseDouble(parts[i]);
                    }
                }

                if (validData) {
                    double label = Double.parseDouble(parts[1]); // The label is in the second column

                    trainingData.add(features);
                    labels.add(label);
                }
            }
        }

        reader.close();
    }




    public void preprocessData() {
        // Handle missing values if needed
        // Split data into training and testing sets
        int splitIndex = (int) (0.7 * trainingData.size());

        for (int i = 0; i < trainingData.size(); i++) {
            if (i < splitIndex) {
                xTrain.add(trainingData.get(i));
                yTrain.add(labels.get(i));
            } else {
                xTest.add(trainingData.get(i));
                yTest.add(labels.get(i));
            }
        }
    }

    public List<double[]> getXTrain() {
        return xTrain;
    }

    public List<Double> getYTrain() {
        return yTrain;
    }

    public List<double[]> getXTest() {
        return xTest;
    }

    public List<Double> getYTest() {
        return yTest;
    }
}
