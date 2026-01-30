
package main

const FAMILY_SIZE = 4
const SEATS_PER_ROW = 10

var BITSTAMP_TWO_FAMILIES = GET_BITSTAMP_TWO_FAMILIES()
var BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE = GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE()
var BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE = GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE()
var BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES = GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES()

func GET_BITSTAMP_TWO_FAMILIES() int {
    bitstamp := 0
    startSeat := 2
    for seat := startSeat; seat < startSeat + 2 * FAMILY_SIZE; seat++ {
        bitstamp |= (1 << seat)
    }
    return bitstamp
}

func GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE() int {
    bitstamp := 0
    startSeat := 2
    for seat := startSeat; seat < startSeat + FAMILY_SIZE; seat++ {
        bitstamp |= (1 << seat)
    }
    return bitstamp
}

func GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE() int {
    bitstamp := 0
    startSeat := 6
    for seat := startSeat; seat < startSeat + FAMILY_SIZE; seat++ {
        bitstamp |= (1 << seat)
    }
    return bitstamp
}

func GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES() int {
    bitstamp := 0
    startSeat := 4
    for seat := startSeat; seat < startSeat + FAMILY_SIZE; seat++ {
        bitstamp |= (1 << seat)
    }
    return bitstamp
}

func maxNumberOfFamilies(totalRows int, reservedSeats [][]int) int {
    rowsToBitstampReservedSeats := map[int]int{}
    for _, seat := range reservedSeats {
        row := seat[0]
        column := seat[1]
        bitstampReservedSeats := getOrDefault(rowsToBitstampReservedSeats, row, 0) | (1 << column)
        rowsToBitstampReservedSeats[row] = bitstampReservedSeats
    }

    maxNumberOfSeatedFamilies := getNumberOfSeatedFamiliesOnEmptyRows(totalRows, len(rowsToBitstampReservedSeats))
    for _, bitstampReservedSeats := range rowsToBitstampReservedSeats {
        if (bitstampReservedSeats & BITSTAMP_TWO_FAMILIES) == 0 {
            maxNumberOfSeatedFamilies += 2
            continue
        }
        if ((bitstampReservedSeats & BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE) == 0) ||
            ((bitstampReservedSeats & BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE) == 0) ||
            ((bitstampReservedSeats & BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES) == 0) {
            maxNumberOfSeatedFamilies++
        }
    }

    return maxNumberOfSeatedFamilies
}

func getNumberOfSeatedFamiliesOnEmptyRows(totalRows int, numberOfRowsWithReservedSeats int) int {
    return (totalRows - numberOfRowsWithReservedSeats) * (SEATS_PER_ROW / FAMILY_SIZE)
}

func getOrDefault[Key comparable, Value any](toCheck map[Key]Value, key Key, defaultValue Value) Value {
    if value, has := toCheck[key]; has {
        return value
    }
    return defaultValue
}
