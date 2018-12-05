import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Kellen Haynes
 * @version 1.0
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * <p>
     * Make sure to implement the failure table before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || comparator == null || text == null) {
            throw new IllegalArgumentException(
                    "the given paramaters are invalid"
            );
        }
        int j = 0;
        int k = 0;
        if (pattern.length() > text.length()) {
            return new ArrayList<>();
        }
        boolean match = false;
        List<Integer> returnList = new ArrayList<>();
        int[] failureTable = buildFailureTable(pattern, comparator);
        while (k <= text.length() - pattern.length()) {
            while (k < text.length() && !match) {
                if (comparator.compare(pattern.charAt(j),
                        text.charAt(k)) == 0) {
                    if (j == pattern.length() - 1) {
                        returnList.add(k - j);
                        match = true;
                        j = failureTable[j];
                        k++;
                    } else {
                        j++;
                        k++;
                    }
                } else {
                    if (j == 0) {
                        k++;
                    } else {
                        j = failureTable[j - 1];
                    }
                }
            }
            match = false;

        }
        return returnList;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * <p>
     * The table built should be the length of the input text.
     * <p>
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     * <p>
     * Ex. ababac
     * <p>
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     * <p>
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a {@code CharSequence} you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     * @throws IllegalArgumentException if the pattern or comparator is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException(
                    "the given paramaters are invalid"
            );
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException(
                    "the given paramaters are invalid"
            );
        }
        int[] returnarray = new int[pattern.length()];
        int j = 1;
        int i = 0;
        returnarray[0] = 0;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                returnarray[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    returnarray[j] = 0;
                    j++;
                } else {
                    i = returnarray[i - 1];
                }

            }
        }
        return returnarray;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * <p>
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || text == null || comparator == null) {
            throw new IllegalArgumentException(
                    "the given paramaters are invalid"
            );
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException(
                    "the given paramaters are invalid"
            );
        }
        int i = pattern.length() - 1;
        int j = pattern.length() - 1;
        List<Integer> returnList = new ArrayList<>();
        Map<Character, Integer> lastOccurenceTable = buildLastTable(pattern);
        int tempi = i;
        int tempj = j;
        while (i < text.length()) {
            if (comparator.compare(pattern.charAt(tempj),
                    text.charAt(tempi)) == 0) {

                if (tempj == 0) {
                    returnList.add(i - (pattern.length() - 1));
                    i++;
                    tempi = i;
                    tempj = j;
                } else {
                    tempi--;
                    tempj--;

                }

            } else {
                Integer mismatch = lastOccurenceTable.get(text.charAt(i));
                if (mismatch == null) {
                    i += pattern.length();
                    tempi = i;
                    tempj = j;
                } else if (mismatch >= 0) {
                    if (mismatch > tempj) {
                        i++;
                        tempi = i;
                        tempj = j;
                    } else {
                        i = i + (pattern.length() - 1) - mismatch;
                        tempi = i;
                        tempj = j;
                    }

                }

            }
        }
        return returnList;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * <p>
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     * <p>
     * Ex. octocat
     * <p>
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     * <p>
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException(
                    "the given paramaters are invalid"
            );
        }
        Map<Character, Integer> returnMap = new HashMap<>(pattern.length());
        for (int i = pattern.length() - 1; i >= 0; i--) {
            returnMap.putIfAbsent(pattern.charAt(i), i);
        }
        return returnMap;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 101;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     * <p>
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     * <p>
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     * <p>
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     * <p>
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow. However, you will not need to handle this case.
     * You may assume there will be no overflow.
     * <p>
     * For example: Hashing "bunn" as a substring of "bunny" with base 101 hash
     * = b * 101 ^ 3 + u * 101 ^ 2 + n * 101 ^ 1 + n * 101 ^ 0 = 98 * 101 ^ 3 +
     * 117 * 101 ^ 2 + 110 * 101 ^ 1 + 110 * 101 ^ 0 = 102174235
     * <p>
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     * <p>
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     * <p>
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 101
     * hash("unny") = (hash("bunn") - b * 101 ^ 3) * 101 + y =
     * (102174235 - 98 * 101 ^ 3) * 101 + 121 = 121678558
     * <p>
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     * <p>
     * Do NOT use Math.pow
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || text == null || comparator == null) {
            throw new IllegalArgumentException(
                    "the given paramaters are invalid"
            );
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException(
                    "the given paramaters are invalid"
            );
        }

        double hash = 0;
        double texthash = 0;
        double firstcharValue = 0;
        if (pattern.length() > text.length()) {
            return new ArrayList<>();
        }
        for (int i = 0; i < pattern.length(); i++) {
            hash += pattern.charAt(i) * pow(BASE, pattern.length() - 1 - i);
            texthash += text.charAt(i) * pow(BASE, pattern.length() - 1 - i);
            if (i == 0) {
                firstcharValue = texthash;
            }
        }

        int start = 0;
        int end = pattern.length() - 1;
        List<Integer> returnList = new ArrayList<>();

        while (end < text.length()) {
            if (hash == texthash) {
                boolean match = true;
                int j = end;
                int i = start;
                int k = 0;
                while (match && i <= j) {
                    match = comparator.compare(pattern.charAt(k),
                            text.charAt(i)) == 0;
                    if (i == j) {
                        returnList.add(j - (pattern.length() - 1));
                        end++;
                        if (end < text.length() - 1) {
                            double x = text.charAt(start)
                                    * pow(BASE, pattern.length() - 1);
                            texthash = (texthash - (x))
                                    * BASE + text.charAt(end);
                        }
                        start++;


                    }
                    i++;
                    k++;
                }
            } else {
                end++;

                if (end < text.length()) {
                    texthash = (texthash - (text.charAt(start)
                            * pow(BASE, pattern.length() - 1)))
                            * BASE + text.charAt(end);
                }
                start++;


            }
        }
        return returnList;
    }

    /**
     * computes the power of a function
     *
     * @param a int to be raised
     * @param b power to raise a to
     * @return a^b
     */
    private static double pow(int a, int b) {
        int returnValue = 1;
        for (int i = 0; i < b; i++) {
            if (i == 0) {
                returnValue = a;
            } else {
                returnValue *= a;
            }

        }
        return returnValue;
    }
}
