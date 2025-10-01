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
        *   [containsBlanks](#containsblanks)
        *   [characterAt](#characterat)
        *   [isInteger](#isinteger)
        *   [trimSpaces](#trimspaces)
        *   [charsIn](#charsin)
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
    *   [SolarEnergyOperators](#solarenergyoperators)
        *   [sinDegrees](#sindegrees)
        *   [cosDegrees](#cosdegrees)
        *   [calculateElevationAngle](#calculateelevationangle)
        *   [calculateSolarDeclination](#calculatesolardeclination)
        *   [calculateHourAngle](#calculatehourangle)
        *   [calculateAirMass](#calculateairmass)
    *   [MathOperators](#mathoperators)
        *   [getCircumference](#getcircumference)
        *   [getFarenheight](#getfarenheight)

# How to create custom extended operators

Note: Corticon Studio is built on Eclipse which provides a Java development environment you can use for creating Corticon extensions that you can use in current and future versions of Corticon. If you want to create extensions in a separate IDE, you must use Java 1.8 or higher.

Note: Compatibility of extensions created in an earlier release, any extension operators and service callouts that are in `extended.core.jar` are shared across all Rule Projects. As a result, such extensions are always in the **Rule Operator** tab in every editor. Then, you can add your extended operators and service callouts to specific Rule Projects using the new mechanism.

Note: You might want to simply copy the sample project **Extended Operator Java Project**, and then tweak a sample such as `AttributeOperators.java` by renaming the `TopLevelFolder` to `mySampleExtendedOperators` for your first run. You can then build the Java project.

For many developers, the quickest way to learn is by example. You might want to compare the three Java source files in the **Extended Operator Java Project** to see what is common and what changes. In this example, the `AttributeOperators.java` is presented.

# Available Operators

## AttributeOperators

### isLeapYear

**Description:** Determines if a date is a leap year.

**Source:** [AttributeOperators.java](AttributeOperators.java)

**Usage:**
```
Entity.attribute.isLeapYear()
```

### replaceAll

**Description:** Replaces all occurrences of a substring in a string with another string.

**Source:** [AttributeOperators.java](AttributeOperators.java)

**Usage:**
```
replaceAll(string, searchString, replacement)
```

### containsBlanks

**Description:** Determine whether the specified string contains any blanks.

**Source:** [AttributeOperators.java](AttributeOperators.java)

**Usage:**
```
containsBlanks(string)
```

### characterAt

**Description:** Returns the character at the specified index (one-based) in the String.

**Source:** [AttributeOperators.java](AttributeOperators.java)

**Usage:**
```
characterAt(string, index)
```

### isInteger

**Description:** Determines whether the specified String contains nothing but integer digits.

**Source:** [AttributeOperators.java](AttributeOperators.java)

**Usage:**
```
isInteger(string)
```

### trimSpaces

**Description:** Trim the leading and trailing whitespace from the specified string.

**Source:** [AttributeOperators.java](AttributeOperators.java)

**Usage:**
```
trimSpaces(string)
```

### charsIn

**Description:** Determine whether all the characters in the specified are contained in the passed in String

**Source:** [AttributeOperators.java](AttributeOperators.java)

**Usage:**
```
charsIn(string, characterSet)
```

## CollectionOperators

### containsDuplicates

**Description:** Determines if a collection of strings contains any duplicates.

**Source:** [CollectionOperators.java](CollectionOperators.java)

**Usage:**
```
Entity.collection.containsDuplicates()
```

### occurrenceCount

**Description:** Determines the number of times a given string occurs in a collection of strings.

**Source:** [CollectionOperators.java](CollectionOperators.java)

**Usage:**
```
occurrenceCount(collection, searchString)
```

## DecimalOperators

### truncFloor

**Description:** Truncate and round down to a specified number of fractional digits.

**Source:** [DecimalOperators.java](DecimalOperators.java)

**Usage:**
```
truncFloor(decimal, digits)
```

## Finance

### getPresentValue

**Description:** Get the present value of an investment.

**Source:** [Finance.java](Finance.java)

**Usage:**
```
getPresentValue(futureValue, interestRate, numberOfYears)
```

### getFutureValue

**Description:** Get the future value of an investment.

**Source:** [Finance.java](Finance.java)

**Usage:**
```
getFutureValue(presentValue, interestRate, numberOfYears)
```

## JaroWinklerDistance

### apply

**Description:** A similarity algorithm indicating the percentage of matched characters between two character sequences.

**Source:** [JaroWinklerDistance.java](JaroWinklerDistance.java)

**Usage:**
```
apply(string1, string2)
```

## SimilarityAnalysisOperators

### compareWithDoubleMetaphone

**Description:** Calculates Double Metaphone (phonetic) similarity between two strings.

**Source:** [SimilarityAnalysisOperators.java](SimilarityAnalysisOperators.java)

**Usage:**
```
compareWithDoubleMetaphone(string1, string2)
```

### compareWithLevenshtein

**Description:** Calculates Levenshtein similarity between two strings.

**Source:** [SimilarityAnalysisOperators.java](SimilarityAnalysisOperators.java)

**Usage:**
```
compareWithLevenshtein(string1, string2)
```

### compareWithNgram

**Description:** Calculates N-gram similarity between two strings.

**Source:** [SimilarityAnalysisOperators.java](SimilarityAnalysisOperators.java)

**Usage:**
```
compareWithNgram(string1, string2, n-gram size)
```

### compareWithTokenization

**Description:** Calculates tokenization-based similarity between two strings.

**Source:** [SimilarityAnalysisOperators.java](SimilarityAnalysisOperators.java)

**Usage:**
```
compareWithTokenization(string1, string2)
```

## PerformanceOperators

### getNanoTimeMillis

**Description:** Returns a timestamp in milliseconds allowing to gather performance metrics in a rulesheet.

**Source:** [PerformanceOperators.java](PerformanceOperators.java)

**Usage:**
```
getNanoTimeMillis()
```

## SolarEnergyOperators

### sinDegrees

**Description:** Returns the sine of the angle in degrees.

**Source:** [SolarEnergyOperators.java](SolarEnergyOperators.java)

**Usage:**
```
sinDegrees(angle)
```

### cosDegrees

**Description:** Returns the cosine of the angle in degrees.

**Source:** [SolarEnergyOperators.java](SolarEnergyOperators.java)

**Usage:**
```
cosDegrees(angle)
```

### calculateElevationAngle

**Description:** Returns the elevation angle for solar panel installation.

**Source:** [SolarEnergyOperators.java](SolarEnergyOperators.java)

**Usage:**
```
calculateElevationAngle(declination, latitude, hourAngle)
```

### calculateSolarDeclination

**Description:** Returns the solar declination angle for a given day of the year.

**Source:** [SolarEnergyOperators.java](SolarEnergyOperators.java)

**Usage:**
```
calculateSolarDeclination(dayOfYear)
```

### calculateHourAngle

**Description:** Returns the solar hour angle based on solar time.

**Source:** [SolarEnergyOperators.java](SolarEnergyOperators.java)

**Usage:**
```
calculateHourAngle(solarTime)
```

### calculateAirMass

**Description:** Returns the air mass based on the zenith angle.

**Source:** [SolarEnergyOperators.java](SolarEnergyOperators.java)

**Usage:**
```
calculateAirMass(zenithAngle)
```

## MathOperators

### getCircumference

**Description:** Convert a caller-supplied radius to circumference.

**Source:** [MathOperators.java](MathOperators.java)

**Usage:**
```
getCircumference(radius)
```

### getFarenheight

**Description:** Convert a caller-supplied Centigrade temperature to Farenheight.

**Source:** [MathOperators.java](MathOperators.java)

**Usage:**
```
getFarenheight(centigrade)
```
