package strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day61CountAndSay {

    /*
     * Problem:
     * Generate the nth term of the Count-and-Say sequence.
     *
     * 1
     * 11       -> one 1
     * 21       -> two 1s
     * 1211     -> one 2, one 1
     * 111221   -> one 1, one 2, two 1s
     *
     * Important:
     * The next term describes consecutive groups in the current term.
     */

    public static String countAndSay(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive.");
        }

        String current = "1";

        for (int term = 2; term <= n; term++) {
            current = describe(current);
        }

        return current;
    }

    /*
     * Run-length encoding style traversal.
     *
     * For each group of equal consecutive digits:
     * append(count) + digit
     */
    private static String describe(String current) {
        StringBuilder next = new StringBuilder();

        int i = 0;

        while (i < current.length()) {
            int j = i;

            while (j < current.length()
                    && current.charAt(j) == current.charAt(i)) {
                j++;
            }

            next.append(j - i);
            next.append(current.charAt(i));

            i = j;
        }

        return next.toString();
    }

    /*
     * Returns every term from 1 through n.
     * Useful for understanding the sequence and debugging.
     */
    public static List<String> generateSequence(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive.");
        }

        List<String> sequence = new ArrayList<>();
        String current = "1";

        sequence.add(current);

        for (int term = 2; term <= n; term++) {
            current = describe(current);
            sequence.add(current);
        }

        return sequence;
    }

    public static void main(String[] args) {
        System.out.println("n = 1: " + countAndSay(1)); // 1
        System.out.println("n = 2: " + countAndSay(2)); // 11
        System.out.println("n = 3: " + countAndSay(3)); // 21
        System.out.println("n = 4: " + countAndSay(4)); // 1211
        System.out.println("n = 5: " + countAndSay(5)); // 111221
        System.out.println("n = 6: " + countAndSay(6)); // 312211
        System.out.println("n = 7: " + countAndSay(7)); // 13112221

        System.out.println("Full sequence up to n = 6:");
        System.out.println(generateSequence(6));

        System.out.println("Example term: "
                + Arrays.toString(countAndSay(5).chars().toArray()));
    }
}
