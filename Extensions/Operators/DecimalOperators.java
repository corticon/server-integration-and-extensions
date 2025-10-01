package com.corticon.samples.extensions;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import com.corticon.services.extensions.ArgumentName;
import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcDecimalExtension;
import com.corticon.services.extensions.ICcIntegerExtension;
import com.corticon.services.extensions.OperatorFolder;
import com.corticon.services.extensions.TopLevelFolder;

/**
 * Decimal operators including truncFloor for Corticon.
 */
@TopLevelFolder("Sample Extended Operators")
public class DecimalOperators implements ICcDecimalExtension, ICcIntegerExtension {

    /**
     * Truncate and round down to a specified number of fractional digits.
     *
     * Usage in a Rulesheet action cell:
     *   Entity_1.attribute_1 = Entity_1.attribute_1.truncFloor(1)
     *
     * @param abdThis  attribute value to operate on (BigDecimal)
     * @param digits   number of fractional digits to keep
     * @return truncated value rounded down, or null for invalid input
     */
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
}
