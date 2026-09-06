package arrays;

/**
 * Day 11 - Binary Search Variations.
 *
 * Finds the first and last occurrence of a target in a sorted array.
 * The key idea is to continue searching after finding the target instead
 * of immediately returning.
 */
public class Day11BinarySearchOccurrences {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 2, 2, 4, 5, 5, 7};
        int target = 2;

        System.out.println("First occurrence: "
                + firstOccurrence(numbers, target));
        System.out.println("Last occurrence: "
                + lastOccurrence(numbers, target));
        System.out.println("Occurrence count: "
                + countOccurrences(numbers, target));
    }

    /**
     * Finds the first (leftmost) occurrence of target.
     * Time: O(log n), Space: O(1)
     */
    static int firstOccurrence(int[] numbers, int target) {
        validate(numbers);

        int left = 0;
        int right = numbers.length - 1;
        int answer = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (numbers[mid] == target) {
                answer = mid;
                // A target may exist further to the left.
                right = mid - 1;
            } else if (numbers[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

    /**
     * Finds the last (rightmost) occurrence of target.
     * Time: O(log n), Space: O(1)
     */
    static int lastOccurrence(int[] numbers, int target) {
        validate(numbers);

        int left = 0;
        int right = numbers.length - 1;
        int answer = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (numbers[mid] == target) {
                answer = mid;
                // A target may exist further to the right.
                left = mid + 1;
            } else if (numbers[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

    /**
     * Counts target occurrences using the first and last positions.
     * Time: O(log n), Space: O(1)
     */
    static int countOccurrences(int[] numbers, int target) {
        int first = firstOccurrence(numbers, target);

        if (first == -1) {
            return 0;
        }

        int last = lastOccurrence(numbers, target);
        return last - first + 1;
    }

    private static void validate(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < numbers[i - 1]) {
                throw new IllegalArgumentException(
                        "Array must be sorted in ascending order");
            }
        }
    }
}
