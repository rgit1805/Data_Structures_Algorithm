package arrays;

/**
 * Day 1 - Array fundamentals.
 *
 * Demonstrates traversal, sum, minimum, maximum, and linear search.
 */
public class Day01ArrayBasics {

    public static void main(String[] args) {
        int[] numbers = {12, 5, 8, 21, 3, 17};

        System.out.println("Array:");
        printArray(numbers);
        System.out.println("\nSum: " + sum(numbers));
        System.out.println("Minimum: " + min(numbers));
        System.out.println("Maximum: " + max(numbers));

        int target = 21;
        System.out.println("Index of " + target + ": " + linearSearch(numbers, target));
    }

    static void printArray(int[] numbers) {
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println();
    }

    static int sum(int[] numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        return total;
    }

    static int min(int[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        int minimum = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            minimum = Math.min(minimum, numbers[i]);
        }
        return minimum;
    }

    static int max(int[] numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        int maximum = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            maximum = Math.max(maximum, numbers[i]);
        }
        return maximum;
    }

    static int linearSearch(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
