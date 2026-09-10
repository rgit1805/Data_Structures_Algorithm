package arrays;

public class Day23AllocateMinimumPages {

    /**
     * Checks whether books can be allocated to at most k students
     * while keeping each student's pages <= maxPages.
     * Books must be allocated contiguously and in order.
     *
     * Time: O(n), Space: O(1)
     */
    public static boolean canAllocate(int[] pages, int k, long maxPages) {
        validateInput(pages, k);

        int students = 1;
        long currentPages = 0;

        for (int pageCount : pages) {
            if (currentPages + pageCount <= maxPages) {
                currentPages += pageCount;
            } else {
                students++;
                currentPages = pageCount;
            }
        }

        return students <= k;
    }

    /**
     * Finds the minimum possible maximum number of pages assigned
     * to any student.
     *
     * Search space:
     * left  = maximum single book
     * right = total pages
     *
     * Time: O(n log(sum(pages)))
     * Space: O(1)
     */
    public static long allocatePages(int[] pages, int k) {
        validateInput(pages, k);

        long left = 0;
        long right = 0;

        for (int pageCount : pages) {
            left = Math.max(left, pageCount);
            right += pageCount;
        }

        long answer = right;

        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (canAllocate(pages, k, mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return answer;
    }

    private static void validateInput(int[] pages, int k) {
        if (pages == null || pages.length == 0) {
            throw new IllegalArgumentException("Pages must not be null or empty");
        }
        if (k <= 0 || k > pages.length) {
            throw new IllegalArgumentException(
                    "Students must be between 1 and the number of books");
        }
        for (int pageCount : pages) {
            if (pageCount < 0) {
                throw new IllegalArgumentException("Page counts must be non-negative");
            }
        }
    }

    public static void main(String[] args) {
        int[] pages = {12, 34, 67, 90};
        int students = 2;

        System.out.println("Minimum maximum pages: "
                + allocatePages(pages, students));
        // Expected: 113
        // Allocation: [12, 34, 67] and [90]

        int[] secondExample = {10, 20, 30, 40};
        System.out.println("Second example: "
                + allocatePages(secondExample, 2));
        // Expected: 60
        // Allocation: [10, 20, 30] and [40]
    }
}
