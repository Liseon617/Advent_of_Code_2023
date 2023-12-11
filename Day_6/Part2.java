import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {
    // Calculate the distance covered based on time and button hold duration
    private static long calculate(long time, long hold) {
        return (time - hold) * hold;
    }

    public static void main(String[] args) throws IOException {
        // Open a BufferedReader to read input from the file
        try (BufferedReader br = new BufferedReader(new FileReader("Day_6\\day6.txt"))) {

            // Read the time and distance information from the file
            String tstr = br.readLine().substring("time: ".length());
            String dstr = br.readLine().substring("distance: ".length());

            // Parse the time and distance strings into lists of integers
            List<Integer> times = parseStringToList(tstr);
            List<Integer> records = parseStringToList(dstr);

            // Calculate and print the number of ways to beat the record in Part 2
            System.out.println((calculateLastFirstDifference(times, records)));
        }
    }

    // Parse a space-separated string of integers into a list of integers
    private static List<Integer> parseStringToList(String str) {
        List<Integer> list = new ArrayList<>();
        for (String s : str.split("\\s+")) {
            if (!s.isEmpty())
                list.add(Integer.parseInt(s));
        }
        return list;
    }

    // Calculate the difference between the last and first winning durations
    private static long calculateLastFirstDifference(List<Integer> times, List<Integer> records) {
        // Concatenate the integers in the lists into a single string
        String timeStr = times.stream().map(Object::toString).collect(Collectors.joining());
        String recordStr = records.stream().map(Object::toString).collect(Collectors.joining());

        // Parse the concatenated strings into long values
        long time = Long.parseLong(timeStr);
        long record = Long.parseLong(recordStr);

        // Calculate the midpoint for binary search
        long over = time / 2;

        // Find the first duration that beats the record
        long first = binarySearchFirstWinningDuration(time, record, 1, over);

        // Find the last duration that beats the record
        long last = binarySearchLastWinningDuration(time, record, over, time);

        // Calculate the difference
        return last - first + 1;
    }

    // Binary search to find the first duration that beats the record
    private static long binarySearchFirstWinningDuration(long time, long record, long left, long right) {
        while (left + 1 != right) {
            long mid = (right + left) / 2;
            long val = calculate(time, mid);
            if (val <= record) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return right;
    }

    // Binary search to find the last duration that beats the record
    private static long binarySearchLastWinningDuration(long time, long record, long left, long right) {
        while (left + 1 != right) {
            long mid = (right + left) / 2;
            long val = calculate(time, mid);
            if (val <= record) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return left;
    }
}
