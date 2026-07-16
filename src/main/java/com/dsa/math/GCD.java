package com.dsa.math;

/**
 * Euclidean algorithm for Greatest Common Divisor.
 * Time: O(log(min(a, b))) | Space: O(1)
 */
public class GCD {

    public int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
