package com.corticon.samples.extensions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Normalizer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.codec.language.DoubleMetaphone;

import com.corticon.services.extensions.ArgumentName;
import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcStandAloneExtension;
import com.corticon.services.extensions.OperatorFolder;
import com.corticon.services.extensions.TopLevelFolder;

@TopLevelFolder("Sample Extended Operators")
public class SimilarityAnalysisOperators implements ICcStandAloneExtension {

    @OperatorFolder(lang = { "en" }, values = { "Similarity text compare functions" })
    @Description(lang = { "en" }, values = {
        "Calculates Double Metaphone (phonetic) similarity between two strings.\n" +
        "Uses dynamic maxCodeLength based on input string lengths to better handle long names.\n" +
        "Applies Unicode normalization (NFD) and specific transliterations for German characters.\n" +
        "Returns: \n" +
        "1.00 = Primary & alternate codes match AND spelling identical.\n" +
        "0.95 = Primary & alternate codes match but spelling differs.\n" +
        "0.90 = Primary codes match only.\n" +
        "0.80 = Cross-match between primary/alternate codes.\n" +
        "0.00 = No match"
    })
    public static BigDecimal compareWithDoubleMetaphone(
            @ArgumentName(lang = { "en" }, values = { "string 1" }) String str1,
            @ArgumentName(lang = { "en" }, values = { "string 2" }) String str2) {

        if (str1 == null || str2 == null) {
            return null;
        }

        // Normalize and transliterate before comparison
        String input1 = preprocess(str1);
        String input2 = preprocess(str2);

        // Determine dynamic max length for codes (cap at 12)
        int dynamicMaxLen = Math.min(Math.max(input1.length(), input2.length()), 12);

        DoubleMetaphone dm = new DoubleMetaphone();
        dm.setMaxCodeLen(dynamicMaxLen);

        // Primary & Alternate Codes
        String p1 = dm.doubleMetaphone(input1);
        String a1 = dm.doubleMetaphone(input1, true);
        String p2 = dm.doubleMetaphone(input2);
        String a2 = dm.doubleMetaphone(input2, true);

        // CASE 1: Primary & alternate match
        if (p1.equals(p2) && a1.equals(a2)) {
            if (input1.equalsIgnoreCase(input2)) {
                return BigDecimal.ONE; // exact spelling & sound
            } else {
                return new BigDecimal("0.95"); // same sound, different spelling
            }
        }
        // CASE 2: Primary codes match only
        if (p1.equals(p2)) {
            return new BigDecimal("0.9");
        }
        // CASE 3: Cross matches between primary & alternate
        if (p1.equals(a2) || p2.equals(a1) || a1.equals(a2)) {
            return new BigDecimal("0.8");
        }
        // CASE 4: No match
        return BigDecimal.ZERO;
    }

    private static String preprocess(String input) {
        if (input == null) return null;

        // Normalize to decomposed form
        String normalized = Normalizer.normalize(input.trim(), Normalizer.Form.NFD);

        // Specific German handling before stripping marks
        normalized = normalized
                .replaceAll("Ü", "UE")
                .replaceAll("ü", "ue")
                .replaceAll("Ö", "OE")
                .replaceAll("ö", "oe")
                .replaceAll("Ä", "AE")
                .replaceAll("ä", "ae")
                .replaceAll("ß", "ss");

        // Remove any remaining diacritics
        normalized = normalized.replaceAll("\\p{M}", "");

        return normalized;
    }

    @OperatorFolder(lang = { "en" }, values = { "Similarity text compare functions" })
    @Description(lang = { "en" }, values = { 
        "Calculates Levenshtein similarity between two strings.\n" +
        "Returns normalized score (0.0-1.0) where 1.0 = identical strings.\n" +
        "Based on minimum edit distance (insertions, deletions, substitutions).\n" +
        "Uses case-insensitive comparison."
    })
    public static BigDecimal compareWithLevenshtein(
            @ArgumentName(lang = { "en" }, values = { "string 1" }) String str1,
            @ArgumentName(lang = { "en" }, values = { "string 2" }) String str2) {
        
        // Null handling: return null if any input is null
        if ((str1 == null) || (str2 == null))
            return null;
        
        // Handle identical strings early
        if (str1.equals(str2))
            return BigDecimal.ONE;
        
        // Convert to lowercase for case-insensitive comparison
        String s1 = str1.toLowerCase();
        String s2 = str2.toLowerCase();
        
        // Early check after case normalization
        if (s1.equals(s2))
            return BigDecimal.ONE;
        
        int len1 = s1.length();
        int len2 = s2.length();
        
        // Handle empty strings
        if (len1 == 0 && len2 == 0)
            return BigDecimal.ONE;
        if (len1 == 0 || len2 == 0)
            return BigDecimal.ZERO;
        
        // Calculate edit distance between s1 and s2
        int distance = calculateLevenshteinDistance(s1, s2, len1, len2);
        
        // Normalize similarity score: 1 - (distance / max length)
        int maxLen = Math.max(len1, len2);
        double similarity = 1.0 - ((double) distance / maxLen);
        similarity = Math.max(0.0, similarity); // safeguard
        
        // Return BigDecimal score
        return new BigDecimal(similarity);
    }
    
    private static int calculateLevenshteinDistance(String s1, String s2, int len1, int len2) {
        int[][] dp = new int[len1 + 1][len2 + 1];

        // Initialize first row and column
        for (int i = 0; i <= len1; i++)
            dp[i][0] = i;
        for (int j = 0; j <= len2; j++)
            dp[0][j] = j;

        // Fill the DP table
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // chars match
                } else {
                    dp[i][j] = 1 + Math.min(
                        Math.min(dp[i - 1][j],    // delete
                                 dp[i][j - 1]),   // insert
                        dp[i - 1][j - 1]            // substitute
                    );
                }
            }
        }
        return dp[len1][len2];
    }

    @OperatorFolder(lang = { "en" }, values = { "Similarity text compare functions" })
    @Description(lang = { "en" }, 
        values = { "Calculates N-gram similarity between two strings (case-insensitive, accent-normalized).\n"
                 + "Strips diacritics, removes all non-alphanumeric characters.\n"
                 + "Uses Jaccard coefficient on unique N-grams." })
    public static BigDecimal compareWithNgram(
            @ArgumentName(lang = { "en" }, values = { "string 1" }) String str1,
            @ArgumentName(lang = { "en" }, values = { "string 2" }) String str2,
            @ArgumentName(lang = { "en" }, values = { "n-gram size" }) BigInteger ngramSize) {

        if (str1 == null || str2 == null || ngramSize == null)
            return null;

        int n = ngramSize.intValue();
        if (n <= 0)
            return null;

        // Normalize inputs
        String s1 = normalize(str1);
        String s2 = normalize(str2);

        // Identical after normalization
        if (s1.equals(s2))
            return BigDecimal.ONE;

        Set<String> ngrams1 = generateNgrams(s1, n);
        Set<String> ngrams2 = generateNgrams(s2, n);

        if (ngrams1.isEmpty() && ngrams2.isEmpty())
            return BigDecimal.ONE;
        if (ngrams1.isEmpty() || ngrams2.isEmpty())
            return BigDecimal.ZERO;

        Set<String> intersection = new HashSet<>(ngrams1);
        intersection.retainAll(ngrams2);

        Set<String> union = new HashSet<>(ngrams1);
        union.addAll(ngrams2);

        double similarity = (double) intersection.size() / union.size();
        return new BigDecimal(similarity);
    }

    private static Set<String> generateNgrams(String text, int n) {
        Set<String> ngrams = new HashSet<>();
        if (text.length() < n) {
            if (!text.isEmpty())
                ngrams.add(text);
            return ngrams;
        }
        for (int i = 0; i <= text.length() - n; i++) {
            ngrams.add(text.substring(i, i + n));
        }
        return ngrams;
    }

    private static String normalize(String input) {
        if (input == null) return "";
        String lower = input.toLowerCase();
        String norm = Normalizer.normalize(lower, Normalizer.Form.NFD);
        norm = norm.replaceAll("\\p{M}+", "");                 // strip accents/diacritics
        norm = norm.replaceAll("[^\\p{L}\\p{N}]", "");          // strip spaces, punctuation
        return norm;
    }

    private static final Pattern SPLIT_PATTERN = Pattern.compile("[^\\p{L}\\p{N}]+");

    @OperatorFolder(lang = { "en" }, values = { "Similarity text compare functions" })
    @Description(lang = { "en" }, values = {
        "Calculates tokenization-based similarity between two strings.\n" +
        "Applies Unicode NFD normalization and transliterates German characters (Ü->UE, Ö->OE, Ä->AE, ß->SS),\n" +
        "supporting both precomposed and decomposed forms.\n" +
        "Splits both into tokens using any non-alphanumeric Unicode character.\n" +
        "Compares tokens in order, case-insensitively. Match=1.0, mismatch/missing=0.0.\n" +
        "Final score is the average over the maximum number of token positions."
    })
    public static BigDecimal compareWithTokenization(
            @ArgumentName(lang = { "en" }, values = { "string 1" }) String str1,
            @ArgumentName(lang = { "en" }, values = { "string 2" }) String str2) {
        if ((str1 == null) || (str2 == null))
            return null;

        String norm1 = preprocess(str1);
        String norm2 = preprocess(str2);

        List<String> tokens1 = tokenize(norm1);
        List<String> tokens2 = tokenize(norm2);
        int maxTokens = Math.max(tokens1.size(), tokens2.size());
        if (maxTokens == 0)
            return BigDecimal.ONE; // both empty → identical

        double totalScore = 0.0;
        for (int i = 0; i < maxTokens; i++) {
            String t1 = (i < tokens1.size()) ? tokens1.get(i) : null;
            String t2 = (i < tokens2.size()) ? tokens2.get(i) : null;
            if (t1 != null && t2 != null && t1.equalsIgnoreCase(t2)) {
                totalScore += 1.0;
            }
            // Mismatch or missing token: +0.0
        }
        double similarity = totalScore / maxTokens;
        return new BigDecimal(similarity);
    }

    public static List<String> tokenize(String input) {
        return SPLIT_PATTERN.splitAsStream(input == null ? "" : input)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public static String preprocessToken(String input) {
        if (input == null) return null;
        // NFD normalize (decompose characters + diacritics)
        String normalized = Normalizer.normalize(input.trim(), Normalizer.Form.NFD);

        // Replace all precomposed forms
        normalized = normalized
                .replace("Ä", "AE").replace("ä", "ae")
                .replace("Ö", "OE").replace("ö", "oe")
                .replace("Ü", "UE").replace("ü", "ue")
                .replace("ß", "ss");

        // Replace decomposed forms (base char + combining diaeresis)
        normalized = normalized
                .replaceAll("A\\u0308", "AE") // A + diaeresis
                .replaceAll("a\\u0308", "ae")
                .replaceAll("O\\u0308", "OE")
                .replaceAll("o\\u0308", "oe")
                .replaceAll("U\\u0308", "UE")
                .replaceAll("u\\u0308", "ue");

        // Remove remaining combining marks (diacritics)
        normalized = normalized.replaceAll("\\p{M}", "");
        return normalized;
    }
}
