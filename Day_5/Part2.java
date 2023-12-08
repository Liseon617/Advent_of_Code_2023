import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Part2 {
    // function to get the destination of where the current seed goes to
    public static List<Long> getDestination(Long currentSeed, Long range, Map<List<Long>, Long> currentMap) {

        Long currentEnd = currentSeed + range - 1;
        // loop through every entry in the current map
        for (Map.Entry<List<Long>, Long> entry : currentMap.entrySet()) {

            // get the key
            List<Long> key = entry.getKey();

            Long startingRangeInclusive = key.get(0);
            Long endingRangeExclusive = key.get(1) + startingRangeInclusive - 1;

            if (isOverLapping(currentSeed, currentEnd, startingRangeInclusive, endingRangeExclusive)) {
                List<Long> overlappedRange = getOverlap(currentSeed, currentEnd, startingRangeInclusive, endingRangeExclusive);

                // determine what is the difference
                Long difference = overlappedRange.get(0) - startingRangeInclusive;
                System.out.println(difference);
                System.out.println(overlappedRange.get(1));
                // determine what is the mapping of the first number in the particular range
                Long destination = entry.getValue() + difference;
                List<Long> output = new ArrayList<>();
                output.add(destination);
                output.add(overlappedRange.get(1));
                return output; 
            }

        }
        List<Long> defaultOutput = new ArrayList<>();
        defaultOutput.add(currentSeed);
        defaultOutput.add(range);
        return defaultOutput;
    }

    public static boolean isOverLapping(Long currentStart, Long currentEnd, Long mapStart, Long mapEnd) {
        return currentStart <= mapEnd && currentEnd >= mapStart;
    }

    public static List<Long> getOverlap(Long currentStart, Long currentEnd, Long mapStart, Long mapEnd) {
        long overlapStart = Math.max(currentStart, mapStart);
        long overlapEnd = Math.min(currentEnd, mapEnd);
        long overlapCount = overlapEnd - overlapStart + 1;
        List<Long> output = new ArrayList<>();
        output.add(overlapStart);
        output.add(overlapCount);
        return output;
    }

    // public static List<List<Long>> findOverlappingRanges(List<List<Long>> ranges)
    // {
    // if (ranges == null || ranges.isEmpty()) {
    // return Collections.emptyList();
    // }

    // List<List<Long>> overlappingRanges = new ArrayList<>();

    // for (int i = 0; i < ranges.size() - 1; i++) {
    // List<Long> currentRange = ranges.get(i);
    // Long currentStart = currentRange.get(0);
    // Long currentEnd = currentRange.get(0) + currentRange.get(1) - 1;

    // for (int j = i + 1; j < ranges.size(); j++) {
    // List<Long> nextRange = ranges.get(j);
    // Long currentStart = currentRange.get(0);
    // Long currentEnd = currentRange.get(0) + currentRange.get(1) - 1;

    // if (currentRange.isOverLapping(currentRange.get(0), , nextRange)) {
    // List<Long> overlapRange = currentRange.getOverlap(nextRange);
    // overlappingRanges.add(overlapRange);
    // }
    // }
    // }

    // return overlappingRanges;
    // }

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
        String filepath = "Day_5\\test.txt";
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
            for (int i = 0; i < seeds.size(); i += 2) {
                List<Long> soil = getDestination(seeds.get(i), seeds.get(i + 1), seedToSoil);
                List<Long> fertilizer = getDestination(soil.get(0), soil.get(1), soilToFertilizer);
                List<Long> water = getDestination(fertilizer.get(0), fertilizer.get(1), fertilizerToWater);
                List<Long> light = getDestination(water.get(0), water.get(1), waterToLight);
                List<Long> temperature = getDestination(light.get(0), light.get(1), lightToTemperature);
                List<Long> humidity = getDestination(temperature.get(0), temperature.get(1), temperatureToHumidity);
                List<Long> location = getDestination(humidity.get(0), humidity.get(1), humidityToLocation);
                // System.out.println("seed: " + seed);
                System.out.println("soil: " + soil);
                System.out.println("fertilizer: " + fertilizer);
                System.out.println("water: " + water);
                System.out.println("light: " + light);
                System.out.println("temperature: " + temperature);
                System.out.println("humidity: " + humidity);
                System.out.println("location: " + location);
                System.out.println();
                if (location.get(0) < lowestLocation) {
                    lowestLocation = location.get(0);
                }
            }
            // for (Long seed : seeds) {

            // }
            System.err.println(lowestLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
