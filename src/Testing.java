import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Testing {
    public void predictAndSave(String testDataPath, String resultsPath, LogisticRegressionModel model) throws IOException {
        List<String> resultLines = new ArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(testDataPath))) {
            String line;
            // Skip the header line
            reader.readLine();

            int id = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    double[] features = new double[parts.length - 2]; // Remove the first and last columns

                    for (int i = 2; i < parts.length; i++) {
                        if (i == 8 && parts[i].equals("NA")) {
                            // Handle missing "MonthlyIncome" value (you can choose an imputation strategy)
                            // For simplicity, let's replace "NA" with 0.0
                            features[i - 2] = 0.0;
                        } else if (parts[i].equals("NA")) {
                            // Handle missing values for other columns (if any)
                            // For simplicity, let's replace with 0.0
                            features[i - 2] = 0.0;
                        } else {
                            features[i - 2] = Double.parseDouble(parts[i]);
                        }
                    }

                    double probability = model.predict(features);
                    resultLines.add(id + "," + probability);
                    id++;
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultsPath))) {
            writer.write("Id,Probability\n");
            for (String line : resultLines) {
                writer.write(line + "\n");
            }
        }
    }
}
