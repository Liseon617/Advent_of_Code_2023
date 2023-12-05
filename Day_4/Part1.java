import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Part1 {

    public static int evalScore(String line) {
        List<List<Integer>> cleanedTickets = cleanTicket(line);

        List<Integer> winningList = cleanedTickets.get(0);
        List<Integer> ticketValueList = cleanedTickets.get(1);
        int score = 0;
        for (Integer integer : winningList) {
            if(ticketValueList.indexOf(integer) >= 0){
                score = increaseScore(score);
            }
        }
        return score;
    }

    public static int increaseScore(int score) {
        if (score == 0) {
            return 1;
        } else {
            return  score*= 2;
        }
    }

    public static List<List<Integer>> cleanTicket(String line) {
        List<String> firstSplit = new ArrayList<>(Arrays.asList(line.split("[|]")));

        int titleStop = firstSplit.get(0).indexOf(":");
        System.out.println(firstSplit);

        String winning = firstSplit.get(0).substring(titleStop + 1).trim();
        String ticketVal = firstSplit.get(1).substring(0).trim();

        List<Integer> winningList = Arrays.stream(winning.split("\\s+"))
                .map((Function<String, Integer>) Integer::parseInt)
                .collect(Collectors.toList());
        List<Integer> ticketValueList = Arrays.stream(ticketVal.split("\\s+"))
                .map((Function<String, Integer>) Integer::parseInt)
                .collect(Collectors.toList());

        List<List<Integer>> output = new ArrayList<>();

        output.add(winningList);
        output.add(ticketValueList);

        return output;
    }

    public static void main(String[] args) {
        String filepath = "Day_4\\day4.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int totalScore = 0;
            while ((line = br.readLine()) != null) {

                int score = evalScore(line);
                System.out.println(score);
                totalScore += score;
            }

            System.err.println(totalScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
