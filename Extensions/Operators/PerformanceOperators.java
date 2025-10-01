package com.corticon.samples.extensions;

import java.math.BigInteger;

import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcStandAloneExtension;
import com.corticon.services.extensions.OperatorFolder;
import com.corticon.services.extensions.TopLevelFolder;

@TopLevelFolder("Sample Extended Operators")
public class PerformanceOperators implements ICcStandAloneExtension {

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
}
