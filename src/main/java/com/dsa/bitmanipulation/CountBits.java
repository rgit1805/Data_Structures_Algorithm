package com.dsa.bitmanipulation;

/**
 * LeetCode 191 - Number of 1 Bits
 * Time: O(1) (32-bit int) | Space: O(1)
 */
public class CountBits {

    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= (n - 1);
            count++;
        }
        return count;
    }
}
