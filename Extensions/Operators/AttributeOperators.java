/* 
 * Copyright (c) 2016 by Progress Software Corporation and/or one of its 
 * subsidiaries or affiliates. All rights reserved.
 */
package com.corticon.samples.extensions;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
}
