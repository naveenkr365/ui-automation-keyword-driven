package com.uiauto.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.By;
import com.uiauto.test.app.DriverApp;
 
public class Locatorsparser extends DriverApp {
 
	private FileInputStream stream;
	private String RepositoryFile;
	private Properties propertyFile = new Properties();
	public Locatorsparser(String fileName) throws IOException
	{
		this.RepositoryFile = fileName;
		stream = new FileInputStream(RepositoryFile);
		propertyFile.load(stream);
	}
 
	public By getobjectLocator(String locatorName)
	{
		String locatorProperty = propertyFile.getProperty(locatorName);
		String locatorType = locatorProperty.split("%")[0];
		String locatorValue = locatorProperty.split("%")[1];

		By locator = null;
		switch(locatorType.toLowerCase())
		{
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "cssselector":
			locator = By.cssSelector(locatorValue);
			break;
		case "linktext":
			locator = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			locator = By.partialLinkText(locatorValue);
			break;
		case "tagname":
			locator = By.tagName(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		}
		return locator;
	}
	
	
	public String getobjectLocatorValue(String locatorName)
	{
	    String locatorProperty = propertyFile.getProperty(locatorName); 
	    String locatorValue = locatorProperty.split("%")[1];
	    return locatorValue;
	}
	
}