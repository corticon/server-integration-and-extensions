package com.corticon.samples.extensions;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.corticon.services.dataobject.ICcDataObjectManager;
import com.corticon.services.extensions.ArgumentName;
import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcStandAloneExtension;
import com.corticon.services.extensions.OperatorFolder;
import com.corticon.services.extensions.TopLevelFolder;
import com.corticon.services.metadata.decisionservice.IDecisionServiceMetadata;

@TopLevelFolder("Sample Extended Operators")
public class UtilityOperators implements ICcStandAloneExtension {

    @OperatorFolder(lang = { "en" }, values = { "Utility" })
    @Description(lang = { "en" }, values = { "Returns the current time value in millisenconds." })
    public static BigInteger getMillis() {
        try {
            return BigInteger.valueOf(System.currentTimeMillis());
        } catch (Exception e) {
            return BigInteger.ZERO;
        }
    }

    @OperatorFolder(lang = { "en" }, values = { "Utility" })
    @Description(lang = { "en" }, values = { "Calculate this great circle distance between to points measure in longitude and latitude, measured in decimal formation." })
    public static BigDecimal calcDistance(@ArgumentName(lang = { "en" }, values = { "Latitude A" }) BigDecimal latA,
            @ArgumentName(lang = { "en" }, values = { "Longitude A" }) BigDecimal longA,
            @ArgumentName(lang = { "en" }, values = { "Latitude B" }) BigDecimal latB,
            @ArgumentName(lang = { "en" }, values = { "Longitude B" }) BigDecimal longB) {
        double theDistance = (Math.sin(Math.toRadians(latA.longValue())) *
                Math.sin(Math.toRadians(latB.longValue())) +
                Math.cos(Math.toRadians(latA.longValue())) *
                        Math.cos(Math.toRadians(latB.longValue())) *
                        Math.cos(Math.toRadians(longA.longValue() - longB.longValue())));
        double dVal = (Math.toDegrees(Math.acos(theDistance))) * 69.09;
        return BigDecimal.valueOf(dVal);
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns the name of the Decision Service." })
    public static String getName(ICcDataObjectManager dom) {
        String strServiceName = null;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        strServiceName = dsm.getDecisionServiceName();
        return strServiceName;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns true if the service contains SCOs." })
    public static Boolean hasServiceCallOut(ICcDataObjectManager dom) {
        Boolean blnSCO = false;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        blnSCO = dsm.getContainsServiceCallouts();
        return blnSCO;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns the service version display of the Decision Service." })
    public static String getVersionDisplay(ICcDataObjectManager dom) {
        String strServiceVerDisplay = null;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        strServiceVerDisplay = dsm.getDecisionServiceVersionDisplay();
        return strServiceVerDisplay;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns the effective date start of the Decision Service." })
    public static String getEffectiveStart(ICcDataObjectManager dom) {
        String strEffStart = null;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        strEffStart = dsm.getEffectiveDateStart();
        return strEffStart;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns the effective date stop of the Decision Service." })
    public static String getEffectiveStop(ICcDataObjectManager dom) {
        String strEffStop = null;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        strEffStop = dsm.getEffectiveDateStop();
        return strEffStop;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns the major version of the Decision Service." })
    public static BigInteger getMajVersion(ICcDataObjectManager dom) {
        BigInteger intMajVer = BigInteger.ONE;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        intMajVer = BigInteger.valueOf(dsm.getRuleAssetMajorVersionNumber());
        return intMajVer;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns the minor version of the Decision Service." })
    public static BigInteger getMinVersion(ICcDataObjectManager dom) {
        BigInteger intMinVer = BigInteger.ONE;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        intMinVer = BigInteger.valueOf(dsm.getRuleAssetMinorVersionNumber());
        return intMinVer;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns the rule count of the Decision Service." })
    public static BigInteger getRuleCount(ICcDataObjectManager dom) {
        BigInteger intRuleCnt = BigInteger.ONE;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        intRuleCnt = BigInteger.valueOf(dsm.getRuleCount());
        return intRuleCnt;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns true if the service auto reloads." })
    public static Boolean isAutoReload(ICcDataObjectManager dom) {
        Boolean blnReload = false;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        blnReload = dsm.isAutoReloadRules();
        return blnReload;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns true if the service uses EDC." })
    public static Boolean hasEDC(ICcDataObjectManager dom) {
        Boolean blnEDC = false;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        blnEDC = dsm.isConfiguredForDatabaseAccess();
        return blnEDC;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns true if the service uses object execution." })
    public static Boolean hasObjectExecution(ICcDataObjectManager dom) {
        Boolean blnObject = false;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        blnObject = dsm.isConfiguredForObjectExecution();
        return blnObject;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns true if the service is deployed as EDS." })
    public static Boolean deployedEDS(ICcDataObjectManager dom) {
        Boolean blnEDS = false;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        blnEDS = dsm.isDeployedAsEds();
        return blnEDS;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns true if the service is deployed as ERF." })
    public static Boolean deployedERF(ICcDataObjectManager dom) {
        Boolean blnERF = false;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        blnERF = dsm.isDeployedAsRuleflow();
        return blnERF;
    }

    @OperatorFolder(lang = { "en" }, values = { "ServiceInfo" })
    @Description(lang = { "en" }, values = { "Returns true if the service is deployed as Studio Tester." })
    public static Boolean deployedStudioTester(ICcDataObjectManager dom) {
        Boolean blnStudio = false;
        IDecisionServiceMetadata dsm = dom.getDecisionServiceMetadata();
        blnStudio = dsm.isDeployedThroughStudioTester();
        return blnStudio;
    }
}
