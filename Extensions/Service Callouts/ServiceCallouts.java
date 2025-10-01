package com.corticon.samples.extensions;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

import com.corticon.services.dataobject.ICcDataObject;
import com.corticon.services.dataobject.ICcDataObjectManager;
import com.corticon.services.extensions.Description;
import com.corticon.services.extensions.ICcServiceCalloutExtension;
import com.corticon.services.extensions.OperatorFolder;
import com.corticon.services.extensions.TopLevelFolder;

@TopLevelFolder("Sample Service Callouts")
public class ServiceCallouts implements ICcServiceCalloutExtension {

    @OperatorFolder(lang = { "en" }, values = { "ERP" })
    @Description(lang = { "en" }, values = { "Assigns a random 5-digit hub zip to an order." })
    public static void assignHub(ICcDataObjectManager aDataObjectManager) {
        for (ICcDataObject aCustomer : aDataObjectManager.getEntitiesByName("Customer")) {
            for (ICcDataObject aOrder : aCustomer.getAssociations("orders")) {
                Random rand = new Random();
                int i = rand.nextInt(90000) + 10000;
                String strRand = String.valueOf(i);
                aOrder.setAttributeValue("hubZip", strRand);
            }
        }
    }

    @OperatorFolder(lang = { "en" }, values = { "Policy" })
    @Description(lang = { "en" }, values = { "Sets the premium for a policy based on its type." })
    public static void setPolicyPrice(ICcDataObjectManager aDataObjectManager) {
        for (ICcDataObject aPolicy : aDataObjectManager.getEntitiesByName("Policy")) {
            String lstrPolicyType = (String) aPolicy.getAttributeValue("type");
            if (lstrPolicyType.equalsIgnoreCase("A")) {
                aPolicy.setAttributeValue("premium", 100d);
            }
            if (lstrPolicyType.equalsIgnoreCase("B")) {
                aPolicy.setAttributeValue("premium", 100d);
            }
            if (lstrPolicyType.equalsIgnoreCase("C")) {
                aPolicy.setAttributeValue("premium", 100d);
            }
        }
    }

    @OperatorFolder(lang = { "en" }, values = { "Data" })
    @Description(lang = { "en" }, values = { "Sets the price for a stock based on its symbol." })
    public static void setStockPrice(ICcDataObjectManager aDataObjectManager) {
        for (ICcDataObject aStock : aDataObjectManager.getEntitiesByName("Stock")) {
            String lstrStockSymbol = (String) aStock.getAttributeValue("symbol");
            if (lstrStockSymbol.equals("MSFT")) {
                aStock.setAttributeValue("price", BigDecimal.valueOf(100));
                aDataObjectManager.postMessage("Info", "Microsoft set to 100 - (Service call out)", aStock);
            } else if (lstrStockSymbol.equals("ORCL")) {
                aStock.setAttributeValue("price", BigDecimal.valueOf(200));
                aDataObjectManager.postMessage("Info", "Oracle set to 200 - (Service call out)", aStock);
            } else if (lstrStockSymbol.equals("IBM")) {
                aStock.setAttributeValue("price", BigDecimal.valueOf(300));
                aDataObjectManager.postMessage("Info", "IBM set to 300 - (Service call out)", aStock);
            } else if (lstrStockSymbol.equals("HP")) {
                aStock.setAttributeValue("price", BigDecimal.valueOf(350));
                aDataObjectManager.postMessage("Info", "Hewlett Packard set to 350 - (Service call out)", aStock);
            } else if (lstrStockSymbol.equals("GOOG")) {
                aStock.setAttributeValue("price", BigDecimal.valueOf(400));
                aDataObjectManager.postMessage("Info", "Google set to 400 - (Service call out)", aStock);
            } else {
                aStock.setAttributeValue("price", BigDecimal.valueOf(0));
                aDataObjectManager.postMessage("Warning", "No data found for " + lstrStockSymbol + " - (Service call out)", aStock);
            }
        }
    }

    @OperatorFolder(lang = { "en" }, values = { "Utility" })
    @Description(lang = { "en" }, values = { "Pauses the execution for a specified interval." })
    public static void Sleep(ICcDataObjectManager aDataObjectManager) {
        Set<ICcDataObject> setConfig = aDataObjectManager.getEntitiesByName("Config");
        ICcDataObject Config = (ICcDataObject) setConfig.iterator().next();
        long interval = (Long) Config.getAttributeValue("sleepInterval");
        try {
            Thread.sleep(interval);
        } catch (Exception e) {
        }
    }

    @OperatorFolder(lang = { "en" }, values = { "Property" })
    @Description(lang = { "en" }, values = { "Sets a hardcoded appraisal value on a property." })
    public static void getAppraisalValue(ICcDataObjectManager aDataObjectManager) {
        for (ICcDataObject aProperty : aDataObjectManager.getEntitiesByName("Property")) {
            aProperty.setAttributeValue("appraisedValue", BigDecimal.valueOf(250000));
            aDataObjectManager.postMessage("Info", "Property has appraisal value of $250,000 - (Service call out)", aProperty);
        }
    }

    @OperatorFolder(lang = { "en" }, values = { "System" })
    @Description(lang = { "en" }, values = { "Retrieves system environment details." })
    public static void getEnvironmentDetails(ICcDataObjectManager aDataObjectManager) {
        try {
            ICcDataObject sysObjDetail = aDataObjectManager.createEntity("SystemInfo");
            sysObjDetail.setAttributeValue("hostName", java.net.InetAddress.getLocalHost().getHostName());
            sysObjDetail.setAttributeValue("javaHome", System.getProperty("java.home"));
            sysObjDetail.setAttributeValue("javaVendor", System.getProperty("java.vendor"));
            sysObjDetail.setAttributeValue("javaVersion", System.getProperty("java.version"));
            sysObjDetail.setAttributeValue("osArch", System.getProperty("os.arch"));
            sysObjDetail.setAttributeValue("osName", System.getProperty("os.name"));
            sysObjDetail.setAttributeValue("osVersion", System.getProperty("os.version"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
