# How to create custom service callouts

Service Callout (SCO) extensions are user-written functions that can be invoked in a Ruleflow.

In a Ruleflow, the flow of control moves from Rulesheet to Rulesheet, with all Rulesheets operating on a common pool of facts. This common pool of facts is retained in the rule execution engine's working memory for the duration of the transaction. Connection arrows on the diagram specify the flow of control. Each Rulesheet in the flow may update the fact pool.

When you add a Service Callout (SCO) to a Ruleflow diagram, you effectively instruct the system to transfer control to your extension class at a specific point in the flow. Your extension can directly update the fact pool, and your updated facts are available to subsequent Rulesheets.

Your Service Callouts use the Progress Corticon Extension API to retrieve and update facts. The package `com.corticon.services.dataobject` contains two Java interfaces:

| Interface | Purpose |
| --- | --- |
| `com.corticon.services.dataobject.ICcDataObjectManager` | Provides access to the entire fact pool. Allows you to create, retrieve and remove entity instances. |
| `com.corticon.services.dataobject.ICcDataObject` | Provides access to a single entity instance. Allows you to update the entity instance, including setting attributes and creating and removing associations. |

Your Service Callout class must implement marker interface `ICcServiceCalloutExtension`.

Service Callout methods must be declared `public static`.

The system will recognize your Service Callout method if the method signature takes one parameter and that parameter is an instance of `ICcDataObjectManager`.

## Specify properties on a service callout instance

You can specify properties on a Service Callout (SCO) that can be set per instance. That means that a SCO that retrieves data from a web service could use multiple instances of it in a Ruleflow where each instance has different parameters. The nature of the parameters is unrestricted; they are simple name/value pairs that a SCO can interpret as needed.

When a SCO is added to a Ruleflow canvas, its **Properties (View) > Runtime Properties** let you set name/value parameter pairs on this SCO instance. These name/value pairs will be passed to the SCO when the SCO is executed.

To enable this functionality, the SCO's method must need to accept a `java.util.Properties` object in its method signature:

```
public static void processCreditReport(ICcDataObjectManager aDataObjectManager, Properties apropServiceCalloutProperties)
```

If the method does not accept a `Properties` object (as is the case for SCO's created before 5.6.1), the original method will still be called, providing both backward compatibility as well as an alternative approach to using parameters in SCOs.

## Selecting the Runtime Properties for a SCO

Often you will want to constrain the Property Names and their respective Values to ensure that only valid combinations are selected by the user from a drop-down list box. The Service Callout (SCO) must implement a specific Interface and the following methods for the Ruleflow to list the possible Property Names and their respective Values.

**Interface:** `com.corticon.services.extensions.ICcPropertyProvider`

**Static Methods:**
```
public List<String> getPropertyNameOptions()throws Exception;
public List<String> getProperyValueOptions(String astrPropertyName)throws Exception;
```

# Available Service Callouts

This document provides an overview of the available service callouts in this project.

## ServiceCallouts

### assignHub

**Description:** Assigns a random 5-digit hub zip to an order.

**Usage:**
This service callout iterates through all `Customer` entities and their associated `orders`, and assigns a random 5-digit number to the `hubZip` attribute of each order.

### setPolicyPrice

**Description:** Sets the premium for a policy based on its type.

**Usage:**
This service callout iterates through all `Policy` entities and sets the `premium` attribute to `100.0` if the `type` is "A", "B", or "C".

### setStockPrice

**Description:** Sets the price for a stock based on its symbol.

**Usage:**
This service callout iterates through all `Stock` entities and sets the `price` attribute based on the `symbol` attribute. It also posts a message to the Corticon log.

### Sleep

**Description:** Pauses the execution for a specified interval.

**Usage:**
This service callout reads a `sleepInterval` attribute from a `Config` entity and pauses the execution for that amount of time in milliseconds.

### getAppraisalValue

**Description:** Sets a hardcoded appraisal value on a property.

**Usage:**
This service callout iterates through all `Property` entities and sets the `appraisedValue` attribute to `250000`. It also posts a message to the Corticon log.

### getEnvironmentDetails

**Description:** Retrieves system environment details.

**Usage:**
This service callout creates a `SystemInfo` entity and populates it with various system properties like hostname, Java version, and OS information.
