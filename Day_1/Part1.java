import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {

    public static int calibrate(String line) {
        String filteredString = line.replaceAll("[^\\d]", "");
        if (filteredString.length() == 1) {
            int intValue = (int) filteredString.charAt(0) - '0';
            return intValue * 10 + intValue;
        }
        int firstValue = (int) filteredString.charAt(0) - '0';
        int lastValue = (int) filteredString.charAt(filteredString.length() - 1) - '0';
        return firstValue * 10 + lastValue;
    }

    public static void main(String[] args) {
        String filepath = "Day_1\\day1.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int total_sum = 0;
            while ((line = br.readLine()) != null) {
                // Process each line as needed
                total_sum += calibrate(line);
                // System.out.println(line);
            }
            System.out.println(total_sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
