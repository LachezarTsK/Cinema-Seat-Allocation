
function maxNumberOfFamilies(totalRows: number, reservedSeats: number[][]): number {
    const rowsToBitstampReservedSeats = new CustomizedMap<number, number>();
    for (let [row, column] of reservedSeats) {
        const bitstampReservedSeats = rowsToBitstampReservedSeats.getOrDefault(row, 0) | (1 << column);
        rowsToBitstampReservedSeats.set(row, bitstampReservedSeats);
    }

    let maxNumberOfSeatedFamilies = getNumberOfSeatedFamiliesOnEmptyRows(totalRows, rowsToBitstampReservedSeats.size);
    for (let bitstampReservedSeats of rowsToBitstampReservedSeats.values()) {
        if ((bitstampReservedSeats & Util.BITSTAMP_TWO_FAMILIES) === 0) {
            maxNumberOfSeatedFamilies += 2;
            continue;
        }
        if (((bitstampReservedSeats & Util.BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE) === 0)
            || ((bitstampReservedSeats & Util.BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE) === 0)
            || ((bitstampReservedSeats & Util.BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES) === 0)) {
            ++maxNumberOfSeatedFamilies;
        }
    }

    return maxNumberOfSeatedFamilies;
};

function getNumberOfSeatedFamiliesOnEmptyRows(totalRows: number, numberOfRowsWithReservedSeats: number): number {
    return (totalRows - numberOfRowsWithReservedSeats) * Math.floor(Util.SEATS_PER_ROW / Util.FAMILY_SIZE);
}

class Util {

    static FAMILY_SIZE = 4;
    static SEATS_PER_ROW = 10;
    static BITSTAMP_TWO_FAMILIES = Util.GET_BITSTAMP_TWO_FAMILIES();
    static BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE = Util.GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE();
    static BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE = Util.GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE();
    static BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES = Util.GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES();

    static GET_BITSTAMP_TWO_FAMILIES(): number {
        let bitstamp = 0;
        let startSeat = 2;
        for (let seat = startSeat; seat < startSeat + 2 * Util.FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }


    static GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE(): number {
        let bitstamp = 0;
        let startSeat = 2;
        for (let seat = startSeat; seat < startSeat + Util.FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }


    static GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE(): number {
        let bitstamp = 0;
        let startSeat = 6;
        for (let seat = startSeat; seat < startSeat + Util.FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    static GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES(): number {
        let bitstamp = 0;
        let startSeat = 4;
        for (let seat = startSeat; seat < startSeat + Util.FAMILY_SIZE; ++seat) {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }
}

class CustomizedMap<Key, Value> extends Map {

    getOrDefault(key: Key, defaultValue: Value): Value {
        if (!this.has(key)) {
            this.set(key, defaultValue);
        }
        return this.get(key);
    }
}
