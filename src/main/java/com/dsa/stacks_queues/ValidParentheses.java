package com.dsa.stacks_queues;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * LeetCode 20 - Valid Parentheses
 * Time: O(n) | Space: O(n)
 */
public class ValidParentheses {

    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Character> pairs = Map.of(')', '(', ']', '[', '}', '{');

        for (char c : s.toCharArray()) {
            if (pairs.containsKey(c)) {
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) return false;
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
}
