package arrays;

/**
 * Day 10 - Binary Search.
 *
 * Binary search repeatedly cuts a sorted search space in half.
 */
public class Day10BinarySearch {

    public static void main(String[] args) {
        int[] numbers = {1, 3, 5, 7, 9, 11, 15};
        int target = 9;

        System.out.println("Index (brute force): "
                + linearSearch(numbers, target));
        System.out.println("Index (binary search): "
                + binarySearch(numbers, target));
    }

    /**
     * Linear search for comparison.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    static int linearSearch(int[] numbers, int target) {
        validate(numbers);

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Iterative binary search on a sorted array.
     *
     * Each iteration eliminates half of the remaining search space.
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    static int binarySearch(int[] numbers, int target) {
        validate(numbers);

        int left = 0;
        int right = numbers.length - 1;

        while (left <= right) {
            // Avoid (left + right) overflow.
            int mid = left + (right - left) / 2;

            if (numbers[mid] == target) {
                return mid;
            }

            if (numbers[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    private static void validate(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < numbers[i - 1]) {
                throw new IllegalArgumentException("Array must be sorted in ascending order");
            }
        }
    }
}
