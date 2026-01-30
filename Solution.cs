
using System;
using System.Collections.Generic;

public class Solution
{
    private static readonly int FAMILY_SIZE = 4;
    private static readonly int SEATS_PER_ROW = 10;
    private static readonly int BITSTAMP_TWO_FAMILIES = GET_BITSTAMP_TWO_FAMILIES();
    private static readonly int BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE = GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE();
    private static readonly int BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE = GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE();
    private static readonly int BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES = GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES();

    public int MaxNumberOfFamilies(int totalRows, int[][] reservedSeats)
    {
        Dictionary<int, int> rowsToBitstampReservedSeats = [];
        foreach (int[] seat in reservedSeats)
        {
            int row = seat[0];
            int column = seat[1];
            rowsToBitstampReservedSeats.TryAdd(row, 0);
            int bitstampReservedSeats = rowsToBitstampReservedSeats[row] | (1 << column);
            rowsToBitstampReservedSeats[row] = bitstampReservedSeats;
        }

        int maxNumberOfSeatedFamilies = GetNumberOfSeatedFamiliesOnEmptyRows(totalRows, rowsToBitstampReservedSeats.Count);
        foreach (int bitstampReservedSeats in rowsToBitstampReservedSeats.Values)
        {
            if ((bitstampReservedSeats & BITSTAMP_TWO_FAMILIES) == 0)
            {
                maxNumberOfSeatedFamilies += 2;
                continue;
            }
            if (((bitstampReservedSeats & BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE) == 0)
                    || ((bitstampReservedSeats & BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE) == 0)
                    || ((bitstampReservedSeats & BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES) == 0))
            {
                ++maxNumberOfSeatedFamilies;
            }
        }

        return maxNumberOfSeatedFamilies;
    }

    private static int GetNumberOfSeatedFamiliesOnEmptyRows(int totalRows, int numberOfRowsWithReservedSeats)
    {
        return (totalRows - numberOfRowsWithReservedSeats) * (SEATS_PER_ROW / FAMILY_SIZE);
    }

    private static int GET_BITSTAMP_TWO_FAMILIES()
    {
        int bitstamp = 0;
        int startSeat = 2;
        for (int seat = startSeat; seat < startSeat + 2 * FAMILY_SIZE; ++seat)
        {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    private static int GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_FIRST_AISLE()
    {
        int bitstamp = 0;
        int startSeat = 2;
        for (int seat = startSeat; seat < startSeat + FAMILY_SIZE; ++seat)
        {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    private static int GET_BITSTAMP_ONE_FAMILY_SPLIT_BY_LAST_AISLE()
    {
        int bitstamp = 0;
        int startSeat = 6;
        for (int seat = startSeat; seat < startSeat + FAMILY_SIZE; ++seat)
        {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }

    private static int GET_BITSTAMP_ONE_FAMILY_BETWEEN_FIRST_AND_LAST_AISLES()
    {
        int bitstamp = 0;
        int startSeat = 4;
        for (int seat = startSeat; seat < startSeat + FAMILY_SIZE; ++seat)
        {
            bitstamp |= 1 << seat;
        }
        return bitstamp;
    }
}
