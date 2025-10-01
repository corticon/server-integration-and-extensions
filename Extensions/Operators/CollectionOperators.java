/* 
 * Copyright (c) 2016 by Progress Software Corporation and/or one of its 
 * subsidiaries or affiliates. All rights reserved.
 */
package com.corticon.samples.extensions;

import java.math.BigInteger;
import java.util.HashSet;

import com.corticon.services.extensions.ArgumentName;
import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcCollectionExtension;
import com.corticon.services.extensions.OperatorFolder;
import com.corticon.services.extensions.TopLevelFolder;

/**
 * This class provides sample Corticon collection extended operators.
 * Extended operators are a means to add custom features to Corticon for
 * use in Corticon rules.
 * 
 * The samples in this class provide simple operators for calculating the
 * present and future value of an investment for a number of years at a given
 * interest rate.
 */
@TopLevelFolder("Sample Extended Operators")
public class CollectionOperators implements ICcCollectionExtension {

	/**
	 * Determine if a collection of strings contains any duplicates.
	 * 
	 * @param collection The collection of strings.
	 * 
	 * @return true if the collection contains any duplicates, false otherwise.
	 */
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

	/**
	 * Determine the number of times a given string occurs in a collection of
	 * strings.
	 * 
	 * @param collection The collection of strings.
	 * 
	 * @return true if the collection contains any duplicates, false otherwise.
	 */
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

}
