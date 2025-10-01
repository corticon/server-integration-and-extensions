# How to create extensions

To create extensions, [import the Corticon APIs into the Java project](https://docs.progress.com/bundle/corticon-extensions/page/Import-the-Corticon-APIs-into-the-Java-project.html) so that you can create either [custom extended operators](https://docs.progress.com/bundle/corticon-extensions/page/How-to-create-custom-extended-operators.html) or , and then [add the compiled Java classes to the Rules Project](https://docs.progress.com/bundle/corticon-extensions/page/Add-the-compiled-Java-classes-to-the-Rules-Project.html).

## Import the Corticon APIs into the Java project

The Extended Operator and Service Callout samples contain Java projects demonstrating how to create a Java extension. These are standard Java projects. Each references the Corticon JAR file that defines its API, `CcExtensionApi.jar`, located in a Studio installation at `[CORTICON_HOME]/Studio/lib/`. The JAR is added to the project from its Corticon installed location to the project's build path using the predefined Eclipse variable `CORTICON_HOME`. For example:

![](https://docs-be.progress.com/bundle/corticon-extensions/page/wjm1539037290071.png?_LANG=enus)

In your Java project, import the Corticon APIs as described, then create your Java source files. Build the Java project by right-clicking on the project name, and then choosing **Export**. In the Export Dialog, choose **Java > Jar file**. Enter a destination location for the JAR file, then choose appropriate options, and then click **Finish**.