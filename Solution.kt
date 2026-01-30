
class Solution {

    private companion object {

        const val FAMILY_SIZE = 4
        const val SEATS_PER_ROW = 10
        val BITSTAMP_TWO_FAMILIES = GET_BITSTAMP_TWO_FAMILIES()
        val BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE = GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE()
        val BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE = GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE()
        val BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES = GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES()

        fun GET_BITSTAMP_TWO_FAMILIES(): Int {
            var bitstamp = 0
            val startSeat = 2
            for (seat in startSeat..<startSeat + 2 * FAMILY_SIZE) {
                bitstamp = bitstamp or (1 shl seat)
            }
            return bitstamp
        }

        fun GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE(): Int {
            var bitstamp = 0
            val startSeat = 2
            for (seat in startSeat..<startSeat + FAMILY_SIZE) {
                bitstamp = bitstamp or (1 shl seat)
            }
            return bitstamp
        }

        fun GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE(): Int {
            var bitstamp = 0
            val startSeat = 6
            for (seat in startSeat..<startSeat + FAMILY_SIZE) {
                bitstamp = bitstamp or (1 shl seat)
            }
            return bitstamp
        }

        fun GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES(): Int {
            var bitstamp = 0
            val startSeat = 4
            for (seat in startSeat..<startSeat + FAMILY_SIZE) {
                bitstamp = bitstamp or (1 shl seat)
            }
            return bitstamp
        }
    }

    fun maxNumberOfFamilies(totalRows: Int, reservedSeats: Array<IntArray>): Int {
        val rowsToBitstampReservedSeats = mutableMapOf<Int, Int>()
        for ((row, column) in reservedSeats) {
            val bitstampReservedSeats = rowsToBitstampReservedSeats.getOrDefault(row, 0) or (1 shl column)
            rowsToBitstampReservedSeats[row] = bitstampReservedSeats
        }

        var maxNumberOfSeatedFamilies = getNumberOfSeatedFamiliesOnEmptyRows(totalRows, rowsToBitstampReservedSeats.size)
        for (bitstampReservedSeats in rowsToBitstampReservedSeats.values) {
            if ((bitstampReservedSeats and BITSTAMP_TWO_FAMILIES) == 0) {
                maxNumberOfSeatedFamilies += 2
                continue
            }
            if (((bitstampReservedSeats and BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE) == 0)
                || ((bitstampReservedSeats and BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE) == 0)
                || ((bitstampReservedSeats and BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES) == 0)) {
                ++maxNumberOfSeatedFamilies
            }
        }

        return maxNumberOfSeatedFamilies
    }

    private fun getNumberOfSeatedFamiliesOnEmptyRows(totalRows: Int, numberOfRowsWithReservedSeats: Int): Int {
        return (totalRows - numberOfRowsWithReservedSeats) * (SEATS_PER_ROW / FAMILY_SIZE)
    }
}
