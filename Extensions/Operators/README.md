# Table of Contents

*   [How to create custom extended operators](#how-to-create-custom-extended-operators)
    *   [Add the compiled Java classes to the Rules Project](#add-the-compiled-java-classes-to-the-rules-project)
    *   [How to deploy Decision Services with extensions](#how-to-deploy-decision-services-with-extensions)
    *   [Deployment from Studio](#deployment-from-studio)
    *   [Deployment using the Server's command line interface](#deployment-using-the-servers-command-line-interface)
*   [Sample Operators](#available-operators)
    *   [AttributeOperators](#attributeoperators)
        *   [isLeapYear](#isleapyear)
        *   [replaceAll](#replaceall)
    *   [CollectionOperators](#collectionoperators)
        *   [containsDuplicates](#containsduplicates)
        *   [occurrenceCount](#occurrencecount)
    *   [DecimalOperators](#decimaloperators)
        *   [truncFloor](#truncfloor)
    *   [Finance](#finance)
        *   [getPresentValue](#getpresentvalue)
        *   [getFutureValue](#getfuturevalue)
    *   [JaroWinklerDistance](#jarowinklerdistance)
        *   [apply](#apply)
    *   [SimilarityAnalysisOperators](#similarityanalysisoperators)
        *   [compareWithDoubleMetaphone](#comparewithdoublemetaphone)
        *   [compareWithLevenshtein](#comparewithlevenshtein)
        *   [compareWithNgram](#comparewithngram)
        *   [compareWithTokenization](#comparewithtokenization)
    *   [PerformanceOperators](#performanceoperators)
        *   [getNanoTimeMillis](#getnanotimemillis)

# How to create custom extended operators

Note: Corticon Studio is built on Eclipse which provides a Java development environment you can use for creating Corticon extensions that you can use in current and future versions of Corticon. If you want to create extensions in a separate IDE, you must use Java 1.8 or higher.

Note: Compatibility of extensions created in an earlier release, any extension operators and service callouts that are in `extended.core.jar` are shared across all Rule Projects. As a result, such extensions are always in the **Rule Operator** tab in every editor. Then, you can add your extended operators and service callouts to specific Rule Projects using the new mechanism.

Note: You might want to simply copy the sample project **Extended Operator Java Project**, and then tweak a sample such as `AttributeOperators.java` by renaming the `TopLevelFolder` to `mySampleExtendedOperators` for your first run. You can then build the Java project.

For many developers, the quickest way to learn is by example. You might want to compare the three Java source files in the **Extended Operator Java Project** to see what is common and what changes. In this example, the `AttributeOperators.java` is presented.

1.  Specify the imports and interfaces you will need.

    ```
    package com.corticon.samples.extensions;

    import java.util.Calendar;
    import java.util.Date;

    import com.corticon.services.extensions.ArgumentName;
    import com.corticon.services.extensions.Description;
    import com.corticon.services.extensions.ICcDateTimeExtension;
    import com.corticon.services.extensions.ICcStringExtension;
    import com.corticon.services.extensions.OperatorFolder;
    import com.corticon.services.extensions.TopLevelFolder;
    ```

    This class imports the Corticon `ICcStringExtension` and `ICcDateTimeExtension` interfaces because it will implement extended operators for String and DateTime attributes. The other Corticon imports are for the annotations which will be used to describe the extensions.
2.  Enter your comments that describe the class.

    ```
    /**
     * This class provides sample Corticon stand-alone extended operators.
     * Extended operators are a means to add custom features to Corticon for
     * use in Corticon rules.
     *
     * The samples in this class provide simple operators for calculating the
     * present and future value of an investment for a number of years at a given
     * interest rate.
     */
    ```

    A general description of this source file is always good coding practice. It has no use outside of the source file.
3.  Specify the `TopLevelFolder` name.

    ```
    @TopLevelFolder("Sample Extended Operators")
    ```

    The `TopLevelFolder` annotation identifies the folder that will group the extended operators on the **Rule Operators** tab in Corticon Studio. You can name the folder to fit your needs, such as "My Operators", or "Financial Operators".
4.  Specify the class and its implementations.

    ```
    public class AttributeOperators implements ICcStringExtension, ICcDateTimeExtension {
    ```

5.  Name your operator folder, and use the locale parameters if appropriate.

    ```
    	@OperatorFolder(lang = { "en" }, values = { "Date" })
    ```

    The `OperatorFolder` defines the subfolder that will list an individual operator within the `TopLevelFolder` on the **Rule Operators** tab in Corticon Studio. You can organize and name folders to fit your needs.

6.  Add your description of the operator, and use the locale parameters if appropriate..

    ```
    	@Description(lang = { "en" }, values = { "Returns true if the date is in a leap year." })
    ```

    The `Description` annotation describes the specific operator. The hover help reveals what is passed, what is returned, and description text for the locale.

7.  Write your actual implementation of the extended operator. It is always `public static`.

    ```
    	public static Boolean isLeapYear(Date d) {
    		if (d == null)
    			return null;

    		Calendar c = Calendar.getInstance();
    		c.setTime(d);

    		int year = c.get(Calendar.YEAR);
    		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)))
    			return true;
    		else
    			return false;
    	}
    ```

    The example takes a date and returns a boolean. It is up to you to determine that this produces the result you want, and that you can verify it across a range of values and error conditions.

8.  The sample includes another operator definition using the same structure, this one for the **String** `OperatorFolder`. You can similarly change this section, or just cut the whole section out.

    ```
    	/**
    	 * Replaces all occurrences of a substring in a string with another string.
    	 *
    	 * @param s A string.
    	 * @param searchString The substring to look for in s.
    	 * @param replacement The string to replace it with.
    	 * @return The original string with all instances of searchString replace by
    	 * replacement.
    	 */
    	@OperatorFolder(lang = { "en" }, values = { "String" })
    	@Description(lang = { "en" }, values = { "Replace all occurrences of a substring within a string with another string." })
    	public static String replaceAll(String s,
    			@ArgumentName(lang = { "en" }, values = { "searchString" }) String searchString,
    			@ArgumentName(lang = { "en" }, values = { "replacement" }) String replacement) {
    		if (s == null)
    			return null;

    		if (searchString == null)
    			return s;

    		String r = s.replaceAll(searchString, replacement);
    		return r;
    	}
    	}
    ```

9.  Save your work, and then go ahead to build the Java project by right-clicking on the project name, and then choosing **Export**. In the Export Dialog, choose **Java > Jar file**. Enter a destination location for the JAR file, then choose appropriate options, and click **Finish**.
10.  In order for the extension to get referenced by a Rules project, and then packaged with a Decision Service, you must [Add the compiled Java classes to the Rules Project](https://docs.progress.com/bundle/corticon-extensions/page/Add-the-compiled-Java-classes-to-the-Rules-Project.html).
11.  Create and run Ruletests that evaluate the range of possible values that could be presented to the extension. Be sure to include blanks and nulls so that you get complete coverage.


## Add the compiled Java classes to the Rules Project

To add the Java project's compiled classes to a rules project:

1.  Right-click on a project name in the Studio's Project Explorer that requires additional JARs, and then choose **Properties**.
2.  Click **Corticon Extensions**.
3.  Navigate in the panel to locate and list all JAR files used by the project, as illustrated:

    ![](https://docs-be.progress.com/bundle/corticon-extensions/page/lwf1539037315778.png?_LANG=enus)

    All the listed JARs will be added to compiled EDS as *dependent* JARs, but only the ones that are checked will also be *included* in the compiled EDS file.
4.  Click **OK** to save the project properties.

When you use the Studio feature of **Package and Deploy > Save for later Deployment**, the JAR dependencies and inclusions will be added into the **.eds** file.

## How to deploy Decision Services with extensions

Once you have added extension JARs to your project, several deployment tools provide mechanisms to package the extension JARs into deployment. When you compile a project Ruleflow into an EDS file, the extension JARs are encapsulated within the encrypted `.eds`. That insures that regardless how you relocate or update a Decision Service, the extension JARs that are associated with it are consistent.

## Deployment from Studio

The three standard techniques in Studio that package and deploy Decision Services all incorporate the extension JARs that were associated with the project:

*   [Deploying directly to a server](https://docs.progress.com/bundle/corticon-deployment/page/Deploy-to-a-Corticon-Server.html)
*   [Deploying to a server through a Web Console application](https://docs.progress.com/bundle/corticon-deployment/page/Deploy-to-Corticon-Web-Console.html)
*   [Packaging the EDS file locally for access by other tools or the Web Console to complete the deployment.](https://docs.progress.com/bundle/corticon-deployment/page/Package-and-save-for-later-deployment.html)

## Deployment using the Server's command line interface

Note: The `corticonManagement` utilities are installed by the distinct installer `PROGRESS_CORTICON_7.1_UTILITIES_WIN_64.exe` that defaults to locating the utilities at `C:\Progress\Corticon 7.1\Utilities`. You need to install a license to enable it. To run the utility, choose **Start > Progress > Corticon Command Prompt,** and then navigate to the installation location's `bin` directory. Type `corticonManagement` to display its usage.

When you use the tool `CorticonManagement` at a server's `[CORTICON_HOME]\Server\bin` location, the `compile` command provides parameters that will declare dependent JARs and then include them. Both parameters take values separated by spaces and both parameters are required to achieve the packaging into EDS file.

```
  -dj,--dependentjars dependentJar  add jar files required for this decision service
  -ij,--includedjars includedJar    add jar files to include in the generated eds file
```

Note: Any values that contain spaces must be in quotes. For example:

```
-ij "C:\Program Files\myExtensions.jar" "C:\Program Files\myCallouts.jar"
```

A complete command might look like this:

```
corticonManagement
   --compile
   --input C:\myProject\myRuleflow.erf
   --output C:\myProject\Output
   --service MyDS
   --dependentjars C:\myProject\myExtensions.jar C:\myProject\myCallouts.jar
   --includedjars C:\myProject\myExtensions.jar C:\myProject\myCallouts.jar
   --toplevelentities "Entity1,Entity2"
```

With only required options specified, the result is `C:\myProject\Output\MyDS.eds`

**Additions to Ant macro compile arguments**

If you want to use Ant macros for the `corticonManagement` command line utilities that are in the file `[CORTICON_HOME]\Server\lib\corticonAntMacros.xml`, you can set the required extension JARs and top level entities in the arguments for the `compile` macro so that you can use them in the call:

```
<attribute name="input" default=""/>
   <attribute name="output" default="" />
   <attribute name="service" default="" />
   <attribute name="version" default="false" />
   <attribute name="edc" default="false" />
   <attribute name="failonerror" default="false" />
   <attribute name="dependentjars" default="" />
   <attribute name="includedjars" default="" />
   <attribute name="toplevelentities" default="" />
```

Example of a call to the compile macro:

```
<corticon-compile
   input="${project.home}/Order.erf"
   output="${project.home}"
   service="OrderProcessing"
   dependentjars="${project.home}/myExtensions.jar ${project.home}/myCallouts.jar"
   includedjars="${project.home}/myExtensions.jar ${project.home}/myCallouts.jar"
   toplevelentities="Item,Order"
/>
```

Note: **Deployment to a Corticon .NET Server** - Once a project that includes extension JARs is packaged into a Decision Service, it deploys and performs as expected on Corticon .NET Server.

# Available Operators

## AttributeOperators

### isLeapYear

**Description:** Determines if a date is a leap year.

**Code:**
```java
@OperatorFolder(lang = { "en" }, values = { "Date" })
@Description(lang = { "en" }, values = { "Returns true if the date is in a leap year." })
public static Boolean isLeapYear(Date d) {
	if (d == null)
		return null;

	Calendar c = Calendar.getInstance();
	c.setTime(d);

	int year = c.get(Calendar.YEAR);
	if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)))
		return true;
	else
		return false;
}
```

**Usage:**
```
Entity.attribute.isLeapYear()
```

### replaceAll

**Description:** Replaces all occurrences of a substring in a string with another string.

**Code:**
```java
@OperatorFolder(lang = { "en" }, values = { "String" })
@Description(lang = { "en" }, values = { "Replace all occurences of a substring with a string with another string." })
public static String replaceAll(String s, 
		@ArgumentName(lang = { "en" }, values = { "searchString" }) String searchString,
		@ArgumentName(lang = { "en" }, values = { "replacement" }) String replacement) {
	if (s == null)
		return null;

	if (searchString == null)
		return s;

	String r = s.replaceAll(searchString, replacement);
	return r;
}
```



## CollectionOperators

### containsDuplicates

**Description:** Determines if a collection of strings contains any duplicates.

**Code:**
```java
@OperatorFolder(lang = { "en" }, values = { "Collection" })
@Description(lang = { "en" }, values = { "Returns true if the collection of strings contains any duplicates." })
public static Boolean containsDuplicates(String[] collection) {
	if ((collection == null))
		return null;

	HashSet<String> set = new HashSet<String>();
	for (String s : collection) {
		if (!set.add(s)) {
			return true;
		}
	}

	return false;
}
```

**Usage:**
```
Entity.collection.containsDuplicates()
```

### occurrenceCount

**Description:** Determines the number of times a given string occurs in a collection of strings.

**Code:**
```java
@OperatorFolder(lang = { "en" }, values = { "Collection" })
@Description(lang = { "en" }, values = { "Returns the number of times the specified string occurs in the collection of strings." })
public static BigInteger occurrenceCount(String[] collection,
		@ArgumentName(lang = { "en" }, values = { "searchString" }) String searchString) {
	if ((collection == null) || (searchString == null))
		return null;

	int count = 0;
	for (String s : collection) {
		if (s.equals(searchString)) {
			count++;
		}
	}
	return BigInteger.valueOf(count);
}
```

**Usage:**
```


## DecimalOperators

### truncFloor

**Description:** Truncate and round down to a specified number of fractional digits.

**Code:**
```java
@OperatorFolder(lang = "en", values = "Decimal")
@Description(lang = "en", values = "Truncate and round down to the specified number of fractional digits.")
public static BigDecimal truncFloor(
        BigDecimal abdThis,
        @ArgumentName(lang = "en", values = "digits") BigInteger digits) {

    if (abdThis == null || digits == null) {
        return null;
    }
    int scale;
    try {
        scale = digits.intValueExact();
    } catch (ArithmeticException ex) {
        return null; // digits too large
    }
    if (scale < 0) {
        return null;
    }
    return abdThis.setScale(scale, RoundingMode.DOWN);
}
```

**Usage:**
```


## Finance

### getPresentValue

**Description:** Get the present value of an investment.

**Code:**
```java
@OperatorFolder(lang = { "en" }, values = { "Finance" })
@Description(lang = { "en" }, values = { "Returns the present value of an investment." })
public static BigDecimal getPresentValue(
		@ArgumentName(lang = { "en" }, values = { "futureValue" }) BigDecimal futureValue,
		@ArgumentName(lang = { "en" }, values = { "interestRate" }) BigDecimal interestRate, 
		@ArgumentName(lang = { "en" }, values = { "numberOfYears" }) BigInteger numberOfYears) {
	if ((futureValue == null) || (interestRate == null)
			|| (numberOfYears == null))
		return null;

	double depositRequired = futureValue.doubleValue()
			/ Math.pow((1 + interestRate.doubleValue()),
					numberOfYears.intValue());
	return new BigDecimal(depositRequired);
}
```

**Usage:**
```
getPresentValue(10000.0, 0.05, 10)
```

### getFutureValue

**Description:** Get the future value of an investment.

**Code:**
```java
@OperatorFolder(lang = { "en" }, values = { "Finance" })
@Description(lang = { "en" }, values = { "Returns the future value of an investment." })
public static BigDecimal getFutureValue(
		@ArgumentName(lang = { "en" }, values = { "presentValue" }) BigDecimal presentValue,
		@ArgumentName(lang = { "en" }, values = { "interestRate" }) BigDecimal interestRate, 
		@ArgumentName(lang = { "en" }, values = { "numberOfYears" }) BigInteger numberOfYears) {
	if ((presentValue == null) || (interestRate == null)
			|| (numberOfYears == null))
		return null;

	double futureValue = presentValue.doubleValue()
			* Math.pow((1 + interestRate.doubleValue()),
					numberOfYears.intValue());
	return new BigDecimal(futureValue);
}
```

**Usage:**
```
getFutureValue(10000.0, 0.05, 10)
```


## JaroWinklerDistance

### apply

**Description:** A similarity algorithm indicating the percentage of matched characters between two character sequences.

**Code:**
```java
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
```

**Usage:**
```
apply("frog", "fog")
```


## SimilarityAnalysisOperators

### compareWithDoubleMetaphone

**Description:** Calculates Double Metaphone (phonetic) similarity between two strings.

**Code:**
```java
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
```

**Usage:**
```
compareWithDoubleMetaphone("string1", "string2")
```

### compareWithLevenshtein

**Description:** Calculates Levenshtein similarity between two strings.

**Code:**
```java
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
```

**Usage:**
```
compareWithLevenshtein("string1", "string2")
```

### compareWithNgram

**Description:** Calculates N-gram similarity between two strings.

**Code:**
```java
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
```

**Usage:**
```
compareWithNgram("string1", "string2", 2)
```

### compareWithTokenization

**Description:** Calculates tokenization-based similarity between two strings.

**Code:**
```java
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
```

**Usage:**
```
compareWithTokenization("string1", "string2")
```

## PerformanceOperators

### getNanoTimeMillis

**Description:** Returns a timestamp in milliseconds allowing to gather performance metrics in a rulesheet.

**Code:**
```java
@OperatorFolder(lang = { "en" }, values = { "Performance Metrics" })
@Description(lang = { "en" }, values = { "Returns a timestamp in milliseconds allowing to gather performance metrics in a rulesheet" })
public static BigInteger getNanoTimeMillis() {
    try {
         long timeInMilliSeconds = ((System.nanoTime() / 1000000)); 
         return BigInteger.valueOf(timeInMilliSeconds);
    } catch (Exception e) {
        return BigInteger.ZERO;
    }
}
```

**Usage:**
```
getNanoTimeMillis()
```