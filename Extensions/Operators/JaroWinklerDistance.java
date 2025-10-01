/* 
 * Copyright (c) 2016 by Progress Software Corporation and/or one of its 
 * subsidiaries or affiliates. All rights reserved.
 */
package myExtendedOperators;
import java.util.Arrays;

import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcCollectionExtension;
import com.corticon.services.extensions.ICcStringExtension;
import com.corticon.services.extensions.OperatorFolder;
import com.corticon.services.extensions.TopLevelFolder;

/**
 * A similarity algorithm indicating the percentage of matched characters between two character sequences.
 * 
The Jaro measure is the weighted sum of percentage of matched characters
from each file and transposed characters. Winkler increased this measure
 * for matching initial characters.
 */
@TopLevelFolder("myExtendedOperators")
public class JaroWinklerDistance implements ICcCollectionExtension, ICcStringExtension {

	/**
     * <pre>
     * distance.apply(null, null)          = IllegalArgumentException
     * distance.apply("","")               = 0.0
     * distance.apply("","a")              = 0.0
     * distance.apply("aaapppp", "")       = 0.0
     * distance.apply("frog", "fog")       = 0.93
     * distance.apply("fly", "ant")        = 0.0
     * distance.apply("elephant", "hippo") = 0.44
     * distance.apply("hippo", "elephant") = 0.44
     * distance.apply("hippo", "zzzzzzzz") = 0.0
     * distance.apply("hello", "hallo")    = 0.88
     * distance.apply("ABC Corporation", "ABC Corp") = 0.93
     * distance.apply("D N H Enterprises Inc", "D &amp; H Enterprises, Inc.") = 0.95
     * distance.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness") = 0.92
     * distance.apply("PENNSYLVANIA", "PENNCISYLVNIA")    = 0.88
     * </pre>
     *
     * @param left the first String, must not be null
     * @param right the second String, must not be null
     * @return result distance
     * @throws IllegalArgumentException if either String input {@code null}
	 */
	@OperatorFolder(lang = { "en" }, values = { "Collection" })
	@Description(lang = { "en" }, values = { "A similarity algorithm indicating the percentage of matched characters between two character sequences." })
    public Double apply(final CharSequence left, final CharSequence right) {
        final double defaultScalingFactor = 0.1;
        final double percentageRoundValue = 100.0;

        if (left == null || right == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int[] mtp = matches(left, right);
        double m = mtp[0];
        if (m == 0) {
            return 0D;
        }
        double j = ((m / left.length() + m / right.length() + (m - mtp[1]) / m)) / 3;
        double jw = j < 0.7D ? j : j + Math.min(defaultScalingFactor, 1D / mtp[3]) * mtp[2] * (1D - j);
        return Math.round(jw * percentageRoundValue) / percentageRoundValue;
    }


	/**
	 * This method returns the Jaro-Winkler string matches, transpositions, prefix, max array.
	 * 	 * 
     * @param first the first string to be matched
     * @param second the second string to be matched
     * @return mtp array containing: matches, transpositions, prefix, and max length
	 */
	@OperatorFolder(lang = { "en" }, values = { "Collection" })
	@Description(lang = { "en" }, values = { "Returns the number of times the specified string occurs in the collection of strings." })
    protected static int[] matches(final CharSequence first, final CharSequence second) {
        CharSequence max, min;
        if (first.length() > second.length()) {
            max = first;
            min = second;
        } else {
            max = second;
            min = first;
        }
        int range = Math.max(max.length() / 2 - 1, 0);
        int[] matchIndexes = new int[min.length()];
        Arrays.fill(matchIndexes, -1);
        boolean[] matchFlags = new boolean[max.length()];
        int matches = 0;
        for (int mi = 0; mi < min.length(); mi++) {
            char c1 = min.charAt(mi);
            for (int xi = Math.max(mi - range, 0), xn = Math.min(mi + range + 1, max.length()); xi < xn; xi++) {
                if (!matchFlags[xi] && c1 == max.charAt(xi)) {
                    matchIndexes[mi] = xi;
                    matchFlags[xi] = true;
                    matches++;
                    break;
                }
            }
        }
        char[] ms1 = new char[matches];
        char[] ms2 = new char[matches];
        for (int i = 0, si = 0; i < min.length(); i++) {
            if (matchIndexes[i] != -1) {
                ms1[si] = min.charAt(i);
                si++;
            }
        }
        for (int i = 0, si = 0; i < max.length(); i++) {
            if (matchFlags[i]) {
                ms2[si] = max.charAt(i);
                si++;
            }
        }
        int transpositions = 0;
        for (int mi = 0; mi < ms1.length; mi++) {
            if (ms1[mi] != ms2[mi]) {
                transpositions++;
            }
        }
        int prefix = 0;
        for (int mi = 0; mi < min.length(); mi++) {
            if (first.charAt(mi) == second.charAt(mi)) {
                prefix++;
            } else {
                break;
            }
        }
        return new int[] { matches, transpositions / 2, prefix, max.length() };
    }
}