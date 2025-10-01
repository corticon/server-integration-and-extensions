/* 
 * Copyright (c) 2016 by Progress Software Corporation and/or one of its 
 * subsidiaries or affiliates. All rights reserved.
 */
package com.corticon.samples.extensions;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigInteger;

import com.corticon.services.extensions.ArgumentName;
import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcDateTimeExtension;
import com.corticon.services.extensions.ICcStringExtension;
import com.corticon.services.extensions.OperatorFolder;
import com.corticon.services.extensions.TopLevelFolder;

/**
 * This class provides sample Corticon stand-alone extended operators.
 * Extended operators are a means to add custom features to Corticon for
 * use in Corticon rules.
 * 
 * The samples in this class provide simple operators for calculating the
 * present and future value of an investment for a number of years at a given
 * interest rate.
 */
@TopLevelFolder("Sample Extended Operators")
public class AttributeOperators implements ICcStringExtension, ICcDateTimeExtension {

	/**
	 * Determine if a date is a leap year.
	 * 
	 * @param d A date.
	 * 
	 * @return true if the date is a leap year.
	 */
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

	@OperatorFolder(lang = { "en" }, values = { "String" })
	@Description(lang = { "en" }, values = { "Returns true if the input string matches the supplied regular expression." })
	public static Boolean regex(String strInput, @ArgumentName(lang = { "en" }, values = { "Regular expression" }) String strPattern){
		String patternStr = strPattern; 
		Pattern pattern = Pattern.compile(patternStr); 
		Matcher matcher = pattern.matcher(strInput); 
		return new Boolean(matcher.matches());
	}

	@OperatorFolder(lang = { "en" }, values = { "String" })
    @Description(lang = { "en" }, values = { "Determine whether the specified string contains any blanks." })
    public static Boolean containsBlanks(String astrThis) 
    {
       if (astrThis.indexOf(" ") >= 0) {
          return Boolean.TRUE;
       }
 
       return Boolean.FALSE;
    }
 
    @OperatorFolder(lang = { "en" }, values = { "String" })
    @Description(lang = { "en" }, values = { "Returns the character at the specified index (one-based) in the String." })
    public static String characterAt(String astrThis, @ArgumentName(lang = { "en" }, values = { "index" }) BigInteger abiIndex) {
    
       if (abiIndex == null) {
          return null;
       }
 
       int liIndex = abiIndex.intValue() - 1;
 
       if (liIndex < 0 || liIndex >= astrThis.length()) {
          return null;
       }
 
       return astrThis.substring(liIndex, liIndex + 1);
    }
 
    @OperatorFolder(lang = { "en" }, values = { "String" })
    @Description(lang = { "en" }, values = { "Determines whether the specified String contains nothing but integer digits." })
    public static Boolean isInteger(String astrThis) {
 
       if (astrThis == null || astrThis.length() == 0) {
          return Boolean.FALSE;
       }
       
       for (int liIndex=0; liIndex< astrThis.length(); liIndex++) {
          if (!Character.isDigit(astrThis.charAt(liIndex))) {
             return Boolean.FALSE;
          }
       }
 
       return Boolean.TRUE;
    }
    
    @OperatorFolder(lang = { "en" }, values = { "String" })
    @Description(lang = { "en" }, values = { "Trim the leading and trailing whitespace from the specified string." })
    public static String trimSpaces(String astrThis)   {
    
       if (astrThis == null) {
          return null;
       }
       return astrThis.trim();
    }
 
    @OperatorFolder(lang = { "en" }, values = { "String" })
    @Description(lang = { "en" }, values = { "Determine whether all the characters in the specified are contained in the passed in String" })
    public static Boolean charsIn(String astrThis, @ArgumentName(lang = { "en" }, values = { "characterSet" }) String astrCharcterSet) 
    {
       if (astrThis == null || astrCharcterSet == null || astrCharcterSet.length() == 0) 
       {
          return Boolean.FALSE;
       }
 
       char[] lchArrayThis = astrThis.toCharArray();
       
       for (int liIndexThis = 0; liIndexThis < lchArrayThis.length; liIndexThis++) 
       {
          String lstrThisCharacter = String.valueOf(lchArrayThis[liIndexThis]);
          
          boolean lbFoundMatch = false;
          for (int liIndexCharacterSet = 0; liIndexCharacterSet < astrCharcterSet.length(); liIndexCharacterSet++)
          {
             char[] lchArrayCharacterSet = astrCharcterSet.toCharArray();
             
             String lstrCharacterSetValue = String.valueOf(lchArrayCharacterSet[liIndexCharacterSet]);
 
             //  Check to see if they are equal
             if (lstrThisCharacter.equals(lstrCharacterSetValue))
             {
                lbFoundMatch = true;
                break;
             }
          }
 
          //  If there was no match found for the ThisCharacter, return false
          if (lbFoundMatch == false)
          {
             return Boolean.FALSE;
          }
       }
 
       //  If the method has not kicked out from the above loop, then everything did match up.
       return Boolean.TRUE;
    }
}
