import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Part1 {

    private final static int RED_CUBES = 12;
    private final static int GREEN_CUBES = 13;
    private final static int BLUE_CUBES = 14;

    public static boolean possible(String line){
        int red = -1;
        int green = -1;
        int blue = -1;

        int startingPos = line.indexOf(":");
        String subLine = line.substring(startingPos, line.length());
        String[] possibilities = subLine.split(";");
        for (int i = 0; i < possibilities.length; i++) {
            int indexOfRed = possibilities[i].indexOf("red");
            int indexOfGreen = possibilities[i].indexOf("green");
            int indexOfBlue = possibilities[i].indexOf("blue");
            // System.out.println(indexOfRed);
            // System.out.println(indexOfGreen);
            // System.out.println(indexOfBlue);
            if(indexOfRed >= 0){
                int currentRed = -1;
                if(Character.isDigit(possibilities[i].charAt(indexOfRed - 3))){
                    currentRed = Integer.parseInt(possibilities[i].substring(indexOfRed - 3, indexOfRed - 1));
                } else{
                    currentRed = (int)(possibilities[i].charAt(indexOfRed - 2) - '0');
                }
                
                // System.out.println(currentRed);
                if(red < currentRed) red = currentRed;
            }
            if(indexOfGreen >= 0){
                int currentGreen = -1;
                if(Character.isDigit(possibilities[i].charAt(indexOfGreen - 3))){
                    currentGreen = Integer.parseInt(possibilities[i].substring(indexOfGreen - 3, indexOfGreen - 1));
                } else{
                    currentGreen = (int)(possibilities[i].charAt(indexOfGreen - 2) - '0');
                }

                if(green < currentGreen) green = currentGreen;
            }
            if(indexOfBlue >= 0){
                int currentBlue = -1;
                if(Character.isDigit(possibilities[i].charAt(indexOfBlue - 3))){
                    currentBlue = Integer.parseInt(possibilities[i].substring(indexOfBlue - 3, indexOfBlue - 1));
                } else{
                    currentBlue = (int)(possibilities[i].charAt(indexOfBlue - 2) - '0');
                }

                if(blue < currentBlue) blue = currentBlue;
            }
        }
        // System.out.println(red);
        // System.out.println(green);
        // System.out.println(blue);
        return red <= RED_CUBES && green <= GREEN_CUBES && blue <= BLUE_CUBES;
    }


    public static void main(String[] args) {
        String filepath = "Day_2\\day2.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int current_game = 1;
            int total_sum = 0;
            while ((line = br.readLine()) != null) {
                
                System.out.println(line);
                
                if(possible(line)){
                    total_sum += current_game;
                }
                current_game++;
            }
            System.out.println(total_sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
