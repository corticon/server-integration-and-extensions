/* 
 * Copyright (c) 2016 by Progress Software Corporation and/or one of its 
 * subsidiaries or affiliates. All rights reserved.
 */
package com.corticon.samples.extensions;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.corticon.services.extensions.ArgumentName;
import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcStandAloneExtension;
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
public class Finance implements ICcStandAloneExtension {

	/**
	 * Get the present value of an investment.
	 * 
	 * @param futureValue Target future investment amount.
	 * @param interestRate Annual interest rate.
	 * @param numberOfYears Number of years.
	 * @return The amount which would need to be deposited to achieve the target
	 * futureValue at the specified interest rate for the numberOfYears.
	 */
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

	/**
	 * Get the future value of an investment.
	 * 
	 * @param presentValue Present investment amount.
	 * @param interestRate Annual interest rate.
	 * @param numberOfYears Number of years.
	 * @return The future value of the investment given the presentValue, the
	 * interestRate and numberOfYears.
	 */
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

}
