package arrays;

/**
 * Day 2 - Fundamental array operations.
 *
 * Focus: insertion, deletion, reversing, and finding the second largest element.
 */
public class Day02ArrayOperations {

    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50};

        System.out.println("Original array:");
        printArray(numbers);

        System.out.println("\nAfter inserting 25 at index 2:");
        printArray(insert(numbers, 2, 25));

        System.out.println("\nAfter deleting index 2:");
        printArray(delete(numbers, 2));

        int[] reversed = numbers.clone();
        reverse(reversed);
        System.out.println("\nReversed array:");
        printArray(reversed);

        System.out.println("\nSecond largest: " + secondLargest(numbers));
    }

    static int[] insert(int[] numbers, int index, int value) {
        if (index < 0 || index > numbers.length) {
            throw new IndexOutOfBoundsException("Invalid insertion index");
        }

        int[] result = new int[numbers.length + 1];

        for (int i = 0; i < index; i++) {
            result[i] = numbers[i];
        }

        result[index] = value;

        for (int i = index; i < numbers.length; i++) {
            result[i + 1] = numbers[i];
        }

        return result;
    }

    static int[] delete(int[] numbers, int index) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        if (index < 0 || index >= numbers.length) {
            throw new IndexOutOfBoundsException("Invalid deletion index");
        }

        int[] result = new int[numbers.length - 1];

        for (int i = 0; i < index; i++) {
            result[i] = numbers[i];
        }

        for (int i = index + 1; i < numbers.length; i++) {
            result[i - 1] = numbers[i];
        }

        return result;
    }

    static void reverse(int[] numbers) {
        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {
            int temp = numbers[left];
            numbers[left] = numbers[right];
            numbers[right] = temp;

            left++;
            right--;
        }
    }

    static int secondLargest(int[] numbers) {
        if (numbers.length < 2) {
            throw new IllegalArgumentException("At least two elements are required");
        }

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int number : numbers) {
            if (number > largest) {
                secondLargest = largest;
                largest = number;
            } else if (number > secondLargest && number < largest) {
                secondLargest = number;
            }
        }

        if (secondLargest == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("A distinct second largest element does not exist");
        }

        return secondLargest;
    }

    static void printArray(int[] numbers) {
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println();
    }
}
