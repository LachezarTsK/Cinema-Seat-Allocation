
import java.util.HashMap;
import java.util.Map;

/*
The number of seats per row is always 10, hence, (1 << 10) < INTEGER_MAX_VALUE.
Thus, the position of each seat can easily be stored as a bitstamp.
 */
public class Solution {

    private static final int FAMILY_SIZE = 4;
    private static final int SEATS_PER_ROW = 10;
    private static final int BITSTAMP_TWO_FAMILIES = GET_BITSTAMP_TWO_FAMILIES();
    private static final int BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE = GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE();
    private static final int BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE = GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE();
    private static final int BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES = GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES();

    public int maxNumberOfFamilies(int totalRows, int[][] reservedSeats) {
        Map<Integer, Integer> rowsToBitstampReservedSeats = new HashMap<>();
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int column = seat[1];
            int bitstampReservedSeats = rowsToBitstampReservedSeats.getOrDefault(row, 0) | (1 << column);
            rowsToBitstampReservedSeats.put(row, bitstampReservedSeats);
        }

        int maxNumberOfSeatedFamilies = getNumberOfSeatedFamiliesOnEmptyRows(totalRows, rowsToBitstampReservedSeats.size());
        for (int bitstampReservedSeats : rowsToBitstampReservedSeats.values()) {
            if ((bitstampReservedSeats & BITSTAMP_TWO_FAMILIES) == 0) {
                maxNumberOfSeatedFamilies += 2;
                continue;
            }
            if (((bitstampReservedSeats & BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE) == 0)
                    || ((bitstampReservedSeats & BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE) == 0)
                    || ((bitstampReservedSeats & BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES) == 0)) {
                ++maxNumberOfSeatedFamilies;
            }
        }

        return maxNumberOfSeatedFamilies;
    }

    private static int getNumberOfSeatedFamiliesOnEmptyRows(int totalRows, int numberOfRowsWithReservedSeats) {
        return (totalRows - numberOfRowsWithReservedSeats) * (SEATS_PER_ROW / FAMILY_SIZE);
    }

    private static int GET_BITSTAMP_TWO_FAMILIES() {
        int bitstamp = 0;
        int startSeat = 2;
        for (int seat = startSeat; seat < startSeat + 2 * FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    private static int GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE() {
        int bitstamp = 0;
        int startSeat = 2;
        for (int seat = startSeat; seat < startSeat + FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    private static int GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE() {
        int bitstamp = 0;
        int startSeat = 6;
        for (int seat = startSeat; seat < startSeat + FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    private static int GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES() {
        int bitstamp = 0;
        int startSeat = 4;
        for (int seat = startSeat; seat < startSeat + FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }
}
