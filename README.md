# DSA in Java

Personal repository of Data Structures & Algorithms solutions in Java, organized by topic.

## 📁 Structure

```
src/main/java/com/dsa/
├── arrays/
├── strings/
├── linkedlist/
├── stacks_queues/
├── trees/
├── graphs/
├── dp/                     (Dynamic Programming)
├── sorting/
├── searching/
├── recursion_backtracking/
├── greedy/
├── math/
├── bitmanipulation/
└── heaps/
```

Each topic folder holds one class per problem. Every class:
- Names the source problem (e.g. "LeetCode 1 - Two Sum") when applicable
- States time and space complexity in a comment
- Includes a `main` method or a matching test for quick manual runs

## 🚀 Getting Started

**Requirements:** Java 17+, Maven

```bash
# Compile
mvn compile

# Run tests
mvn test

# Run a specific class directly
mvn compile exec:java -Dexec.mainClass="com.dsa.arrays.TwoSum"
```

## ✅ Progress Tracker

| Topic | Problems Solved |
|---|---|
| Arrays | 1 |
| Strings | 1 |
| Linked List | 1 |
| Stacks & Queues | 1 |
| Trees | 1 |
| Graphs | 1 |
| Dynamic Programming | 1 |
| Sorting | 1 |
| Searching | 1 |
| Recursion & Backtracking | 1 |
| Greedy | 1 |
| Math | 1 |
| Bit Manipulation | 1 |
| Heaps | 1 |

Update this table as you add solutions.

## 📝 Adding a New Solution

1. Drop a new `.java` file in the relevant topic package.
2. Follow the naming convention: `ProblemName.java` (e.g. `MaxSubarray.java`).
3. Add a short doc comment with source, approach, and complexity.
4. (Optional) Add a corresponding test in `src/test/java/com/dsa/`.

## 📌 Notes

Feel free to adapt the folder set (e.g. add `tries/`, `union_find/`, `sliding_window/`) as your practice grows.
