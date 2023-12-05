import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Part2 {

    public static int evalScore(String line) {
        List<List<Integer>> cleanedTickets = cleanTicket(line);

        List<Integer> winningList = cleanedTickets.get(0);
        List<Integer> ticketValueList = cleanedTickets.get(1);
        int matches = 0;
        for (Integer integer : winningList) {
            if (ticketValueList.indexOf(integer) >= 0) {
                matches++;
            }
        }
        return matches;
    }

    public static List<List<Integer>> cleanTicket(String line) {
        List<String> firstSplit = new ArrayList<>(Arrays.asList(line.split("[|]")));

        int titleStop = firstSplit.get(0).indexOf(":");

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
            int counter = 0;
            int totalScore = 0;
            List<Integer> scoreList = new ArrayList<>();
            Map<Integer, Integer> copiesMap = new HashMap<>();
            while ((line = br.readLine()) != null) {
                int score = evalScore(line);
                scoreList.add(score);
                copiesMap.put(counter++, 1);
            }

            for (int i = 0; i < scoreList.size(); i++) {
                // get the number of matches per scratch ticket
                Integer currentScore = scoreList.get(i);
                Integer currentCopy = copiesMap.get(i);

                while (currentScore > 0) {
                    // get the current amount of each scratch ticket
                    int copiesOfNextCard = copiesMap.get(i + currentScore);

                    // add to the current amount
                    copiesMap.put(i + currentScore, copiesOfNextCard + currentCopy);

                    
                    currentScore--;

                }
            }

            for (Entry<Integer, Integer> entry : copiesMap.entrySet()) {

                totalScore += entry.getValue();
            }
  
            System.err.println(totalScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
