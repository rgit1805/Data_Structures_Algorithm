# 🧠 DSA Pattern-Wise Learning in Java

> A structured, pattern-first approach to mastering Data Structures & Algorithms using Java.

## 🎯 Goal

The goal of this repository is **not to memorize DSA solutions**.

The goal is to develop the ability to:

- Understand an unfamiliar problem
- Identify the underlying pattern
- Choose the right data structure
- Develop a brute-force solution
- Identify the bottleneck
- Optimize the approach
- Implement it cleanly in Java
- Analyze Time & Space Complexity
- Handle edge cases
- Explain the solution like an interviewer

### End Goal

> **Understand → Recognize Pattern → Choose Data Structure → Optimize → Implement → Explain**

---

# 🗺️ Learning Roadmap

## 01. Arrays

**Core Concepts**
- Traversal
- Searching
- In-place modification
- Frequency counting
- Prefix concepts

**Important Problems**
- Two Sum
- Best Time to Buy and Sell Stock
- Move Zeroes
- Remove Duplicates
- Maximum Subarray

---

## 02. Hashing

**Core Idea**

Use `HashMap` / `HashSet` to store information for fast lookup.

**Recognition Signals**
- Find duplicates
- Frequency counting
- "Have we seen this before?"
- Find a pair
- Find a complement

**Important Problems**
- Two Sum
- Contains Duplicate
- Valid Anagram
- Group Anagrams
- Longest Consecutive Sequence
- Subarray Sum Equals K

---

## 03. Two Pointers

**Core Idea**

Use two indices to process data efficiently instead of repeatedly scanning it.

**Recognition Signals**
- Sorted array
- Pair problems
- Opposite ends
- Removing duplicates
- Comparing elements

**Important Problems**
- Two Sum II
- Valid Palindrome
- Container With Most Water
- 3Sum
- Remove Duplicates from Sorted Array

---

## 04. Sliding Window

**Core Idea**

Maintain a dynamic window over a contiguous part of an array or string.

**Recognition Signals**
- Subarray
- Substring
- Contiguous elements
- Longest / shortest
- At most `K`
- Exactly `K`

**Important Problems**
- Longest Substring Without Repeating Characters
- Maximum Average Subarray
- Minimum Size Subarray Sum
- Longest Repeating Character Replacement
- Minimum Window Substring

---

## 05. Prefix Sum

**Core Idea**

Precompute cumulative information to answer range-based problems efficiently.

**Recognition Signals**
- Range sum
- Subarray sum
- Multiple range queries
- Cumulative values

**Important Problems**
- Range Sum Query
- Subarray Sum Equals K
- Find Pivot Index
- Product Except Self

---

## 06. Binary Search

**Core Idea**

Repeatedly eliminate half of the search space.

**Recognition Signals**
- Sorted data
- Search problems
- Minimum possible answer
- Maximum possible answer
- Monotonic condition

**Important Problems**
- Binary Search
- Search Insert Position
- First and Last Position
- Search in Rotated Sorted Array
- Find Minimum in Rotated Sorted Array
- Koko Eating Bananas

---

## 07. Linked List

**Core Concepts**
- Nodes
- References
- Traversal
- Insertion
- Deletion
- Reversal

**Important Patterns**
- Fast & Slow Pointers
- Dummy Node
- In-place Reversal

**Important Problems**
- Reverse Linked List
- Merge Two Sorted Lists
- Linked List Cycle
- Remove Nth Node From End
- Reorder List

---

## 08. Stack

**Core Idea**

Use **LIFO** behavior to process the most recently added element first.

**Recognition Signals**
- Matching parentheses
- Undo operations
- Nested structures
- Previous / next elements

**Important Problems**
- Valid Parentheses
- Min Stack
- Evaluate Reverse Polish Notation
- Next Greater Element
- Daily Temperatures

---

## 09. Monotonic Stack

**Core Idea**

Maintain a stack whose elements follow an increasing or decreasing order.

**Recognition Signals**
- Next greater element
- Next smaller element
- Previous greater element
- Previous smaller element
- Histogram problems

**Important Problems**
- Daily Temperatures
- Next Greater Element
- Largest Rectangle in Histogram
- Trapping Rain Water

---

## 10. Recursion

**Core Idea**

Solve a problem by solving a smaller version of the same problem.

Every recursive solution needs:

```text
Base Case
+
Recursive Case
 
