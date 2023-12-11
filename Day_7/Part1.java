import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {

    // Enum to represent the possible ranks of a hand
    private enum Rank {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD;

        // Determine the rank of a hand based on the given cards
        public static Rank determine(Card[] cards) {
            EnumMap<Card, Integer> counts = new EnumMap<>(Card.class);
            
            // Count occurrences of each card
            for (Card c : cards) {
                if (!counts.containsKey(c)) {
                    counts.put(c, 1);
                } else {
                    counts.put(c, counts.get(c) + 1);
                }
            }
            int two = 0;
            boolean three = false;
            //count repeated cards
            for (var v : counts.values()) {
                if (v == 5) {
                    return FIVE_OF_A_KIND;
                }
                if (v == 4) {
                    return FOUR_OF_A_KIND;
                }
                if (v == 3) {
                    three = true;
                }
                if (v == 2) {
                    two++;
                }
            }
            // Determine the final hand rank
            if (two == 1 && three) {
                return FULL_HOUSE;
            }
            if (three) {
                return THREE_OF_A_KIND;
            }
            if (two == 2) {
                return TWO_PAIR;
            }
            if (two == 1) {
                return ONE_PAIR;
            }
            System.out.println(HIGH_CARD);
            return HIGH_CARD;
        }
    }

    // Enum to represent individual cards
    private enum Card {
        A("A"), K("K"), Q("Q"), JACK("J"), TEN("T"), NINE("9"), EIGHT("8"), SEVEN("7"),
        SIX("6"), FIVE("5"), FOUR("4"), THREE("3"), TWO("2");

        private final String disp;

        // Constructor to associate a display string with each card
        Card(String disp) {
            this.disp = disp;
        }

        // Find a card by its display string
        public static Card findByCharJack(String in) {
            for (Card c : values()) {
                if (c.disp.equals(in)) {
                    return c;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return disp;
        }
    }

    // Hand class
    private static class Hand implements Comparable<Hand> {
        private long bid;
        private Rank rank;
        private Card[] cards;

        public Hand(String hand, String bid) {
            this.bid = Long.parseLong(bid);
            List<Card> cs = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                cs.add(Card.findByCharJack(hand.substring(i, i + 1)));
            }

            cards = cs.toArray(new Card[5]);
            rank = Rank.determine(cards);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Card c : cards) {
                sb.append(c);
            }
            sb.append(" ");
            sb.append(rank);
            sb.append(" ");
            sb.append(bid);
            return sb.toString();
        }

        @Override
        public int compareTo(Hand o) {
            // Compare hands based on rank and then individual card values
            int c = rank.compareTo(o.rank);
            if (c == 0) {
                for (int i = 0; i < 5; i++) {
                    c = cards[i].compareTo(o.cards[i]);
                    if (c != 0) {
                        return c;
                    }
                }
            }
            return c;
        }
    }

    private static void printSum(List<Hand> hands) {
        Collections.sort(hands);
        long sum = 0;
        long rank = hands.size();
        for (Hand h : hands) {
            sum += h.bid * rank--;
        }
        System.out.println(sum);
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("Day_7\\day7.txt"))) {
            Pattern pat = Pattern.compile("([^ ]*) (.*)");
            String s;
            List<Hand> hands = new ArrayList<>();
            while ((s = br.readLine()) != null) {
                Matcher mat = pat.matcher(s);
                // Create hand objects
                if (mat.matches()) {
                    hands.add(new Hand(mat.group(1), mat.group(2)));

                } else {
                    throw new IllegalArgumentException(s);
                }
            }

            printSum(hands);
        }
    }
}
