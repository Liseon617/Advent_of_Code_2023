import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part2 {
    public static int calibrate(String line) {
        // substring and find position of substring
        List<String> intList = new ArrayList<>(
                Arrays.asList("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                integerList.add((int) line.charAt(i) - '0');
            } else {
                String substring = line.substring(i, line.length());
                for (int j = 0; j < intList.size(); j++) {
                    if(substring.indexOf(intList.get(j)) == 0){
                        integerList.add(j);
                    }
                }
            }
        }

        if (integerList.size() == 1) {
            int intValue = integerList.get(0);
            return intValue * 10 + intValue;
        }
        int firstValue = integerList.get(0);
        int lastValue = integerList.get(integerList.size() - 1);
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
            }
            System.out.println(total_sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
