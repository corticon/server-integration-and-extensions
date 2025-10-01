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
}
