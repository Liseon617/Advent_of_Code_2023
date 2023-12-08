import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

    public static class Range {
        int start;
        int count;

        public Range(int start, int count) {
            this.start = start;
            this.count = count;
        }

        public int getEnd() {
            return start + count - 1;
        }

        public boolean overlapsWith(Range other) {
            return start <= other.getEnd() && getEnd() >= other.start;
        }

        public Range getOverlap(Range other) {
            int overlapStart = Math.max(start, other.start);
            int overlapEnd = Math.min(getEnd(), other.getEnd());
            int overlapCount = overlapEnd - overlapStart + 1;
            return new Range(overlapStart, overlapCount);
        }
    }

    public static List<Range> findOverlappingRanges(List<Range> ranges) {
        if (ranges == null || ranges.isEmpty()) {
            return Collections.emptyList();
        }

        List<Range> overlappingRanges = new ArrayList<>();

        for (int i = 0; i < ranges.size() - 1; i++) {
            Range currentRange = ranges.get(i);

            for (int j = i + 1; j < ranges.size(); j++) {
                Range nextRange = ranges.get(j);

                if (currentRange.overlapsWith(nextRange)) {
                    Range overlapRange = currentRange.getOverlap(nextRange);
                    overlappingRanges.add(overlapRange);
                }
            }
        }

        return overlappingRanges;
    }

    public static void main(String[] args) {
        List<Range> ranges = new ArrayList<>();
        ranges.add(new Range(1, 6)); //1 - 6
        ranges.add(new Range(7, 7)); //7 - 13
        ranges.add(new Range(1, 10)); // 10, 11, 12
        ranges.add(new Range(11, 3)); // 15, 16, 17, 18, 19, 20

        List<Range> overlappingRanges = findOverlappingRanges(ranges);

        // Print the overlapping ranges
        for (Range range : overlappingRanges) {
            System.out.println("Overlapping Range: [" + range.start + ", " + range.count + "]");
        }
    }
}
