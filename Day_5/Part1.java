import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part1 {

    // function to get the destination of where the current seed goes to
    public static Long getDestination(Long currentSeed, Map<List<Long>, Long> currentMap){

        // loop through every entry in the current map
        for (Map.Entry<List<Long>, Long> entry : currentMap.entrySet()) {

            // get the key 
            List<Long> key = entry.getKey();

            Long startingRangeInclusive = key.get(0);
            Long endingRangeExclusive = key.get(1) + startingRangeInclusive - 1;

            if (currentSeed >= startingRangeInclusive && currentSeed < endingRangeExclusive) {
                // determine what is the difference
                Long difference = currentSeed - startingRangeInclusive;

                // determine what is the mapping of the first number in the particular range
                Long destination = entry.getValue() + difference;
                return destination;
            }

            // if(currentSeed >= key.get(0) && currentSeed < key.get(0) + key.get(1)){
            //     return entry.getValue() + currentSeed - key.get(0);
            // }
        }
        return currentSeed;
    }

    public static List<Long> lineToList(String line) {
        List<Long> output = new ArrayList<>();
        String[] lineArray = line.split("\\s+");
        for (int i = 0; i < lineArray.length; i++) {
            if (lineArray[i].matches("\\d+")) {
                output.add(Long.parseLong(lineArray[i]));
                ;
            }
        }
        return output;
    }

    public static List<Long> convertToIntList(String[] lineArray) {
        List<Long> numberList = new ArrayList<>();

        for (String str : lineArray) {
            if (str.matches("\\d+")) {
                // Parse each string to a double and add to the list
                Long number = Long.parseLong(str);
                numberList.add(number);
            }
        }

        return numberList;
    }

    public static void main(String[] args) {
        String filepath = "Day_5\\day5.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            List<Long> seeds = new ArrayList<>();
            Map<List<Long>, Long> seedToSoil = new HashMap<>();
            Map<List<Long>, Long> soilToFertilizer = new HashMap<>();
            Map<List<Long>, Long> fertilizerToWater = new HashMap<>();
            Map<List<Long>, Long> waterToLight = new HashMap<>();
            Map<List<Long>, Long> lightToTemperature = new HashMap<>();
            Map<List<Long>, Long> temperatureToHumidity = new HashMap<>();
            Map<List<Long>, Long> humidityToLocation = new HashMap<>();

            List<String> stringLines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                stringLines.add(line);
            }

            String[] lineArray = stringLines.get(0).split("\\s+");
            seeds = convertToIntList(lineArray);
            Map<List<Long>, Long> currentMap = new HashMap<>();
            for (int i = 1; i < stringLines.size(); i++) {
                if (stringLines.get(i).equals("seed-to-soil map:")) {
                    currentMap = seedToSoil;
                } else if (stringLines.get(i).equals("soil-to-fertilizer map:")) {
                    currentMap = soilToFertilizer;
                } else if (stringLines.get(i).equals("fertilizer-to-water map:")) {
                    currentMap = fertilizerToWater;
                } else if (stringLines.get(i).equals("water-to-light map:")) {
                    currentMap = waterToLight;
                } else if (stringLines.get(i).equals("light-to-temperature map:")) {
                    currentMap = lightToTemperature;
                } else if (stringLines.get(i).equals("temperature-to-humidity map:")) {
                    currentMap = temperatureToHumidity;
                } else if (stringLines.get(i).equals("humidity-to-location map:")) {
                    currentMap = humidityToLocation;
                } else {
                    List<Long> lineIntList = lineToList(stringLines.get(i));
                    if (lineIntList.size() > 0) {
                        // System.out.println(lineIntList);
                        List<Long> keyList = new ArrayList<>(Arrays.asList(lineIntList.get(1), lineIntList.get(2)));
                        currentMap.put(keyList, lineIntList.get(0));
                    }
                }
            }
            long lowestLocation = Long.MAX_VALUE;
            for (Long seed : seeds) {
                long soil = getDestination(seed,seedToSoil);
                long fertilizer = getDestination(soil, soilToFertilizer);
                long water = getDestination(fertilizer, fertilizerToWater);
                long light = getDestination(water, waterToLight);
                long temperature = getDestination(light, lightToTemperature);
                long humidity = getDestination(temperature, temperatureToHumidity);
                long location = getDestination(humidity, humidityToLocation);

                System.out.println("seed: " + seed);
                System.out.println("soil: " + soil);
                System.out.println("fertilizer: " + fertilizer);
                System.out.println("water: " + water);
                System.out.println("light: " + light);
                System.out.println("temperature: " + temperature);
                System.out.println("humidity: " + humidity);
                System.out.println("location: " + location);
                System.out.println();
                if(location < lowestLocation){
                    lowestLocation = location;
                }
            }
            System.err.println(lowestLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
