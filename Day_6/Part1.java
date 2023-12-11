import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Part1 {
    // Calculate the distance covered based on time and button hold duration
    private static long calculate(long time, long hold) {
        return (time - hold) * hold;
    }

    public static void main(String[] args) throws IOException {
        // Open a BufferedReader to read input from the file
        BufferedReader br = new BufferedReader(new FileReader("Day_6\\day6.txt"));

        // Read the time and distance information from the file
        String tstr = br.readLine().substring("time: ".length());
        String dstr = br.readLine().substring("distance: ".length());

        // Parse the time and distance strings into lists of integers
        List<Integer> ts = new ArrayList<>();
        for (String s : tstr.split(" +")) {
            if (!s.isEmpty())
                ts.add(Integer.parseInt(s));
        }
        List<Integer> ds = new ArrayList<>();
        for (String s : dstr.split(" +")) {
            if (!s.isEmpty())
                ds.add(Integer.parseInt(s));
        }

        // Calculate the product of the number of ways to beat the record in each race
        long p1 = 1;
        for (int i = 0; i < ts.size(); i++) {
            int sum = 0;
            Integer time = ts.get(i);
            Integer record = ds.get(i);
            // Check different durations to find ways to beat the record
            for (int hold = 1; hold < time; hold++) {
                // Calculate the distance covered by holding the button for different durations
                long dist = calculate(time, hold);
                if (dist > record) {
                    sum++;
                }
            }
            // Multiply the result by p1
            p1 *= sum;
        }
        // Print the result for Part 1
        System.out.println(p1);
        br.close();
    }
}
