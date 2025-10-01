/* 
 * Copyright (c) 2025 by Progress Software Corporation and/or one of its 
 * subsidiaries or affiliates. All rights reserved.
 */
package com.corticon.samples.extensions;

import com.corticon.services.extensions.ArgumentName;
import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcDecimalExtension;
import com.corticon.services.extensions.OperatorFolder;
import com.corticon.services.extensions.TopLevelFolder;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class provides extended operators for solar energy calculations,
 * including trigonometric functions for calculating solar panel energy
 * generation potential.
 */
@TopLevelFolder("Sample Extended Operators")
public class SolarEnergyOperators implements ICcDecimalExtension {

    private static final int SCALE = 10;

    /**
     * Calculates the sine of an angle in degrees.
     * 
     * @param angle The angle in degrees.
     * @return The sine of the angle.
     */
    @OperatorFolder(lang = { "en" }, values = { "Trigonometry" })
    @Description(lang = { "en" }, values = { "Returns the sine of the angle in degrees." })
    public static BigDecimal sinDegrees(@ArgumentName(lang = { "en" }, values = { "angle" }) BigDecimal angle) {
        if (angle == null) return null;
        double radians = Math.toRadians(angle.doubleValue());
        return BigDecimal.valueOf(Math.sin(radians)).setScale(SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the cosine of an angle in degrees.
     * 
     * @param angle The angle in degrees.
     * @return The cosine of the angle.
     */
    @OperatorFolder(lang = { "en" }, values = { "Trigonometry" })
    @Description(lang = { "en" }, values = { "Returns the cosine of the angle in degrees." })
    public static BigDecimal cosDegrees(@ArgumentName(lang = { "en" }, values = { "angle" }) BigDecimal angle) {
        if (angle == null) return null;
        double radians = Math.toRadians(angle.doubleValue());
        return BigDecimal.valueOf(Math.cos(radians)).setScale(SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the elevation angle for solar panel installation.
     * 
     * @param declination The solar declination angle (in degrees).
     * @param latitude The latitude of the installation site (in degrees).
     * @param hourAngle The hour angle (in degrees).
     * @return The elevation angle in degrees.
     */
    @OperatorFolder(lang = { "en" }, values = { "Solar Calculations" })
    @Description(lang = { "en" }, values = { "Returns the elevation angle for solar panel installation." })
    public static BigDecimal calculateElevationAngle(
        @ArgumentName(lang = { "en" }, values = { "declination" }) BigDecimal declination,
        @ArgumentName(lang = { "en" }, values = { "latitude" }) BigDecimal latitude,
        @ArgumentName(lang = { "en" }, values = { "hourAngle" }) BigDecimal hourAngle) {

        if (declination == null || latitude == null || hourAngle == null) return null;

        double sinElevation = Math.sin(Math.toRadians(declination.doubleValue())) * Math.sin(Math.toRadians(latitude.doubleValue())) +
                               Math.cos(Math.toRadians(declination.doubleValue())) * Math.cos(Math.toRadians(latitude.doubleValue())) *
                               Math.cos(Math.toRadians(hourAngle.doubleValue()));

        return BigDecimal.valueOf(Math.toDegrees(Math.asin(sinElevation))).setScale(SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the solar declination angle for a given day of the year.
     * 
     * @param dayOfYear The day of the year (1 to 365).
     * @return The solar declination angle in degrees.
     */
    @OperatorFolder(lang = { "en" }, values = { "Solar Calculations" })
    @Description(lang = { "en" }, values = { "Returns the solar declination angle for a given day of the year." })
    public static BigDecimal calculateSolarDeclination(@ArgumentName(lang = { "en" }, values = { "dayOfYear" }) Integer dayOfYear) {
        if (dayOfYear == null || dayOfYear < 1 || dayOfYear > 365) return null;
        return BigDecimal.valueOf(23.45 * Math.sin(Math.toRadians((360.0 / 365.0) * (dayOfYear - 81)))).setScale(SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the solar hour angle.
     * 
     * @param solarTime The solar time in hours.
     * @return The hour angle in degrees.
     */
    @OperatorFolder(lang = { "en" }, values = { "Solar Calculations" })
    @Description(lang = { "en" }, values = { "Returns the solar hour angle based on solar time." })
    public static BigDecimal calculateHourAngle(@ArgumentName(lang = { "en" }, values = { "solarTime" }) BigDecimal solarTime) {
        if (solarTime == null) return null;
        return BigDecimal.valueOf((solarTime.doubleValue() - 12) * 15).setScale(SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the air mass based on the zenith angle.
     * 
     * @param zenithAngle The zenith angle in degrees.
     * @return The air mass.
     */
    @OperatorFolder(lang = { "en" }, values = { "Solar Calculations" })
    @Description(lang = { "en" }, values = { "Returns the air mass based on the zenith angle." })
    public static BigDecimal calculateAirMass(@ArgumentName(lang = { "en" }, values = { "zenithAngle" }) BigDecimal zenithAngle) {
        if (zenithAngle == null || zenithAngle.compareTo(BigDecimal.valueOf(90)) >= 0) return null;
        return BigDecimal.valueOf(1 / Math.cos(Math.toRadians(zenithAngle.doubleValue()))).setScale(SCALE, RoundingMode.HALF_UP);
    }
}
