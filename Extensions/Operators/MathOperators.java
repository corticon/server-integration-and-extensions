package com.corticon.samples.extensions;

import java.math.BigDecimal;

import com.corticon.services.extensions.ArgumentName;
import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcDecimalExtension;
import com.corticon.services.extensions.OperatorFolder;
import com.corticon.services.extensions.TopLevelFolder;

@TopLevelFolder("Sample Extended Operators")
public class MathOperators implements ICcDecimalExtension {

    @OperatorFolder(lang = { "en" }, values = { "Math" })
    @Description(lang = { "en" }, values = { "Convert a caller-supplied radius to circumference." })
    public static BigDecimal getCircumference(@ArgumentName(lang = { "en" }, values = { "radius" }) BigDecimal abdRadius) {
        BigDecimal lbdBigDecimal = abdRadius.multiply(new BigDecimal(2.0));
        lbdBigDecimal = lbdBigDecimal.multiply(new BigDecimal(Math.PI));
        return lbdBigDecimal;
    }

    @OperatorFolder(lang = { "en" }, values = { "Math" })
    @Description(lang = { "en" }, values = { "Convert a caller-supplied Centigrade temperature to Farenheight." })
    public static BigDecimal getFarenheight(@ArgumentName(lang = { "en" }, values = { "centigrade" }) BigDecimal abdCentigrade) {
        BigDecimal lbdBigDecimal = abdCentigrade.multiply(new BigDecimal(9.0));
        lbdBigDecimal = (BigDecimal) lbdBigDecimal.divide(new BigDecimal(5.0), BigDecimal.ROUND_HALF_UP);
        lbdBigDecimal = (BigDecimal) lbdBigDecimal.add(new BigDecimal(32.0));
        return lbdBigDecimal;
    }

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the absolute value of a decimal value." })
	public static BigDecimal abs(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		return n.abs();
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the arc cosine of a value; the returned angle is in the range 0.0 through pi." })
	public static BigDecimal acos(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.acos(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the arc sine of a value; the returned angle is in the range -pi/2 through pi/2." })
	public static BigDecimal asin(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.asin(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the arc tangent of a value; the returned angle is in the range -pi/2 through pi/2." })
	public static BigDecimal atan(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.atan(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the cube root of a double value." })
	public static BigDecimal cbrt(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.cbrt(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the smallest (closest to negative infinity) double value that is greater than or equal to the argument and is equal to a mathematical integer." })
	public static BigDecimal ceil(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.ceil(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the trigonometric cosine of an angle." })
	public static BigDecimal cos(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.cos(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the hyperbolic cosine of a double value." })
	public static BigDecimal cosh(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.cosh(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns Euler's number e raised to the power of a double value." })
	public static BigDecimal exp(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.exp(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns ex -1." })
	public static BigDecimal expm1(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.expm1(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the largest (closest to positive infinity) double value that is less than or equal to the argument and is equal to a mathematical integer." })
	public static BigDecimal floor(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.floor(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the unbiased exponent used in the representation of a double." })
	public static BigDecimal exponent(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.getExponent(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the natural logarithm (base e) of a double value." })
	public static BigDecimal loge(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.log(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the base 10 logarithm of a double value." })
	public static BigDecimal log10(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.log10(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}

	@OperatorFolder(lang = { "en" }, values = { "Math" })
	@Description(lang = { "en" }, values = { "Returns the natural logarithm of the sum of the argument and 1." })
	public static BigDecimal log1p(
			@ArgumentName(lang = { "en" }, values = { "n" }) BigDecimal n) {

		if (n == null)
			return null;
		double v = Math.log1p(n.doubleValue());
		return Double.isNaN(v) || Double.isInfinite(v) ? null : new BigDecimal(v);
	}
}
