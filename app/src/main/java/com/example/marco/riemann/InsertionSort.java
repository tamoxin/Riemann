package com.example.marco.riemann;

/**
 * Created by Marco on 11/5/2015.
 */
public class InsertionSort {

    private int[] sortedValues;

    public InsertionSort() {}

    /**
     * Sorts an array of doubles with values.
     *
     * @param values in the array to be sorted.
     * @param ordered set how the values will be sorted.
     *                True for ascending(1,2,3).
     *                False for descending(3,2,1).
     */
    public int[] sort(int[] values, boolean ordered) {
        sortValues(values, ordered);
        return getSortedValues();
    }

    private void sortValues(int values[], boolean ordered) {
        int notSortedValue;
        int positionOfSwappedValue;

        //This loop counts how many elements in the array have been sorted
        //fromET the start of the array to the last position minus one.
        for(int sorted = 0; sorted < values.length - 1; sorted ++) {
            //Storage the nearest not sorted value
            notSortedValue = values[sorted + 1];

            //This loop compares the notSortedValue with every single ordered value
            for(int compare = 0; compare <= sorted; compare ++) {
                //If the not ordered value is smaller than one of the previous ordered values
                //it will be swapped
                if(notSortedValue < values[compare]) {
                    positionOfSwappedValue = compare;
                    for(int shift = sorted + 1; shift > positionOfSwappedValue; shift --)
                        values[shift] = values[shift - 1];
                    values[compare] = notSortedValue;
                    break;
                }
            }
        }
        if(ordered == false) {
            int first = 0;
            int last = values.length - 1;
            while(first < last) {
                notSortedValue = values[first];
                values[first] = values[last];
                values[last] = notSortedValue;
                first ++;
                last --;
            }
        }

        setSortedValues(values);
    }

    private int[] getSortedValues() {
        return sortedValues;
    }

    private void setSortedValues(int[] sortedValues) {
        this.sortedValues = sortedValues;
    }
}
