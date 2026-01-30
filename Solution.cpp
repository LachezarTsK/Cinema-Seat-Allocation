
#include <vector>
#include <unordered_map>
using namespace std;

class Solution {

    inline static int GET_BITSTAMP_TWO_FAMILIES() {
        int bitstamp = 0;
        int startSeat = 2;
        for (int seat = startSeat; seat < startSeat + 2 * FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    inline static int GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE() {
        int bitstamp = 0;
        int startSeat = 2;
        for (int seat = startSeat; seat < startSeat + FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    inline static int GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE() {
        int bitstamp = 0;
        int startSeat = 6;
        for (int seat = startSeat; seat < startSeat + FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    inline static int GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES() {
        int bitstamp = 0;
        int startSeat = 4;
        for (int seat = startSeat; seat < startSeat + FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    inline static const int FAMILY_SIZE = 4;
    inline static const int SEATS_PER_ROW = 10;
    inline static const int BITSTAMP_TWO_FAMILIES = GET_BITSTAMP_TWO_FAMILIES();
    inline static const int BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE = GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE();
    inline static const int BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE = GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE();
    inline static const int BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES = GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES();

public:
    int maxNumberOfFamilies(int totalRows, vector<vector<int>>& reservedSeats) const {
        unordered_map<int, int> rowsToBitstampReservedSeats;
        for (const auto seat : reservedSeats) {
            int row = seat[0];
            int column = seat[1];
            int bitstampReservedSeats = rowsToBitstampReservedSeats[row] | (1 << column);
            rowsToBitstampReservedSeats[row] = bitstampReservedSeats;
        }

        int maxNumberOfSeatedFamilies = getNumberOfSeatedFamiliesOnEmptyRows(totalRows, rowsToBitstampReservedSeats.size());
        for (const auto& [row, bitstampReservedSeats] : rowsToBitstampReservedSeats) {
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

private:
    int getNumberOfSeatedFamiliesOnEmptyRows(int totalRows, int numberOfRowsWithReservedSeats) const {
        return (totalRows - numberOfRowsWithReservedSeats) * (SEATS_PER_ROW / FAMILY_SIZE);
    }
};
