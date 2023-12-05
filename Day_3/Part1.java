import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Part1 {

    // go through the matrix and check every character to determine if it has a
    // surrounding gear part
    public static int matrixify(List<List<Character>> charMatrix) {
        ArrayList<String> validIntegers = new ArrayList<>();

        // determine if the current digit is valid
        boolean isValidFlag = false;

        // loop through a string in a row
        for (int i = 0; i < charMatrix.size(); i++) {
            String currentInt = "";

            // look at each character in the row
            for (int j = 0; j < charMatrix.get(i).size(); j++) {
                // if the character is a digit
                if (Character.isDigit(charMatrix.get(i).get(j))) {

                    // checking surrounding

                    // check the line above
                    if (i - 1 >= 0) {
                        // check diagonal left
                        if (j - 1 >= 0 && ("" + charMatrix.get(i - 1).get(j - 1)).matches("[^\\dA-Za-z. ]")) {
                            isValidFlag = true;
                        }

                        // check top
                        if (("" + charMatrix.get(i - 1).get(j)).matches("[^\\dA-Za-z. ]")) {
                            isValidFlag = true;
                        }

                        // check diagonal right
                        if (j + 1 < charMatrix.get(i).size()
                                && ("" + charMatrix.get(i - 1).get(j + 1)).matches("[^\\dA-Za-z. ]")) {
                            isValidFlag = true;
                        }
                    }

                    // check the line below
                    if (i + 1 < charMatrix.size()) {
                        // check diagonal left
                        if (j - 1 >= 0 && ("" + charMatrix.get(i + 1).get(j - 1)).matches("[^\\dA-Za-z. ]")) {
                            isValidFlag = true;
                        }

                        // check top
                        if (("" + charMatrix.get(i + 1).get(j)).matches("[^\\dA-Za-z. ]")) {
                            isValidFlag = true;
                        }

                        // check diagonal right
                        if (j + 1 < charMatrix.get(i).size()
                                && ("" + charMatrix.get(i + 1).get(j + 1)).matches("[^\\dA-Za-z. ]")) {
                            isValidFlag = true;
                        }
                    }

                    // determine the left of the current position
                    if (j - 1 >= 0) {
                        if (("" + charMatrix.get(i).get(j - 1)).matches("[^\\dA-Za-z. ]")) {
                            isValidFlag = true;
                        }
                    }

                    // determine the right of the current position
                    if (j + 1 < charMatrix.get(i).size()) {
                        if (("" + charMatrix.get(i).get(j + 1)).matches("[^\\dA-Za-z. ]")) {
                            isValidFlag = true;
                        }
                    }

                    // Check if EOL reach and flag is true
                    if (isValidFlag && j == charMatrix.get(i).size() - 1) {
                        currentInt = findConsecutiveIntegers(charMatrix.get(i), j);
                        isValidFlag = false;
                        validIntegers.add(currentInt);
                    }

                } else {
                    // determine if the digit was valid then find for the entire string of the input
                    if (isValidFlag) {
                        currentInt = findConsecutiveIntegers(charMatrix.get(i), j - 1);
                        isValidFlag = false;
                        validIntegers.add(currentInt);
                    }
                }
            }
        }

        int output = 0;

        for (String string : validIntegers) {
            output += Integer.parseInt(string);
        }
        return output;
    }

    public static String findConsecutiveIntegers(List<Character> currentLine, int currentIndex) {
        int leftIndex = currentIndex;
        String output = "";
        while (leftIndex >= 0 && Character.isDigit(currentLine.get(leftIndex))) {
            output = "" + currentLine.get(leftIndex) + output;
            leftIndex--;
        }

        return output;
    }

    public static void main(String[] args) {
        String filepath = "Day_3\\day3.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;

            List<List<Character>> charMatrix = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                List<Character> currentLine = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    currentLine.add(line.charAt(i));
                }
                charMatrix.add(currentLine);
            }

            int output = matrixify(charMatrix);
            System.err.println(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
