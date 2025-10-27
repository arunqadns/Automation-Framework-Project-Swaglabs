package com.actiondriver;



import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.BaseClass;
import com.utilities.ExtentManager;

public class ActionDriver {

	
	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger=BaseClass.Logger;


	
	
	public ActionDriver (WebDriver driver) {
	this.driver=driver;
	//this.prop=prop;
	
	String Explicittime = BaseClass.getProperties().getProperty("ExlicitWait");
	int waittime = Integer.parseInt(Explicittime);
	this.wait = new WebDriverWait (driver, Duration.ofSeconds(waittime));
	logger.info("Webdriver instance created");

	}
	
	
	//Explicite Wait for element to be clickable
	private void waitForElementToBeClickable (By by) {
	try {
	wait.until(ExpectedConditions.elementToBeClickable(by));
	} catch (Exception e) {
	logger.error("element is not clickable: "+e.getMessage());
	}
	}
	
	//Explicite Wait for Element to be visible
	private void waitForElementToBeVisible(By by) {
	try {
	wait.until(ExpectedConditions.visibilityOfElementLocated (by));
	} catch (Exception e) {
	logger.error("Element is not visible: "+e.getMessage());
	}
	}
	
	//Method to click an element
	public void click (By by) {
		
	String elementDescription= getElementDescription(by);
	
	try {
	waitForElementToBeClickable (by);
	applyBorder(by,"green");
	driver.findElement (by).click();
	ExtentManager.logStep("Clicked an Element--> "+elementDescription);
	logger.info("Clicked an Element--> "+elementDescription);
	} catch (Exception e) {
		applyBorder(by,"red");
	ExtentManager.logFailure(BaseClass.getDriver(), "Unable to click element: ", elementDescription+"unable to click");
	logger.error("Unable to click element: "+e.getMessage());
	}
	}
	
	//Method to enter text into an input field
	public void enterText (By by, String value) {
	try {
		waitForElementToBeVisible(by);
		WebElement element= driver.findElement (by);
		element.clear();
		element.sendKeys(value);
		applyBorder(by,"green");
		ExtentManager.logStep("Entered text on: "+getElementDescription(by)+"--> "+value);
		logger.info("Entered text on: "+getElementDescription(by)+"--> "+value);
	} catch (Exception e) {
		applyBorder(by,"red");
		ExtentManager.logFailure(BaseClass.getDriver(), "Unable to enter the value:"+ value,"Unable to enter the value on:"+getElementDescription(by));
		logger.error("Unable to enter the value:"+e.getMessage());
		logger.info("Unable to enter the value");
	}
	}
	
	//Method to get text from an input field
	public String getText(By by) {
	try {
		waitForElementToBeVisible(by);
		applyBorder(by,"green");
		return driver.findElement(by).getText();
	} catch (Exception e) {
		applyBorder(by,"red");
		logger.error("Unable to get Text:"+e.getMessage());
		return " ";

	}
	}
	
	//Method to compare Two text
	public boolean compareText(By by, String expectedText) {
	try {
		waitForElementToBeVisible(by);
		String actualText = driver.findElement(by).getText();
		if(expectedText.equals(actualText)) {
			applyBorder(by,"green");
		ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Texts Comparison Success! ", "Texts are matched! "+actualText+ " equals "+expectedText);
		logger.info("Text are Matching: "+actualText+" equals "+expectedText);
		
		return true;
		}
		else{
			applyBorder(by,"red");
		ExtentManager.logFailure(BaseClass.getDriver(), "Texts Comparison failed! ", "Texts are not matched! "+actualText+ " not equals "+expectedText);
		logger.error("Text Comparison failed: "+actualText+" not equals"+expectedText);
		return false;
		}
	} catch (Exception e) {
		logger.error("Unable to compare text:"+e.getMessage());
	}
	return false;
	}
	
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			applyBorder(by,"green");
			ExtentManager.logStep("Element is displayed: "+getElementDescription(by));
			logger.info("Element is displayed: "+getElementDescription(by));
			return driver.findElement(by).isDisplayed();
			} catch (Exception e) {
				applyBorder(by,"red");
			ExtentManager.logFailure(BaseClass.getDriver(),"Element is not displayed: ", "Element is not displayed: "+getElementDescription(by));	
			logger.error("Element is not displayed: "+e.getMessage());
			return false;
			}
		
	}
	
	
	//Scroll to an element
	public void scrollToElement(By by) {
	try {
	JavascriptExecutor js = (JavascriptExecutor) driver;
	WebElement element= driver.findElement(by);
	js.executeScript("arguments[0], scrollIntoView(true);", element);
	} catch (Exception e) {
	logger.error("Unable to locate element:"+e.getMessage());
	}
	}
	
	// Wait for the page to load
	public void waitForPageLoad(int timeoutInSec) {
	try {
	wait.withTimeout(Duration.ofSeconds(timeoutInSec)).until(WebDriver ->((JavascriptExecutor)WebDriver)
	.executeScript("return document.readyState").equals("complete"));
	logger.info("Page loaded successfully.");
	} catch (Exception e) {
	logger.error("Page did not load within "+ timeoutInSec +" seconds.Exception:"+e.getMessage());
	}
	
	}
	
	
	// Method to get the description of an element using By locator
	public String getElementDescription (By locator) {
	// Check for null driver or locator to avoid NUllPointer Exception
	if (driver == null)
	return "driver is null";
	if (locator == null)
	return "Locator is null";
	try {
		// find the element using the locator
		WebElement element = driver.findElement(locator);
		//Get Element Attributes
		String name= element.getDomAttribute("name");
		String id= element.getDomAttribute("id");
		String text = element.getText();
		String className= element.getDomAttribute("class");
		String placeHolder= element.getDomAttribute("placeholder");
		
		// Return the description based on element attributes
		if (isNotEmpty(name)) {
		return "Element with name:" + name;
		} else if (isNotEmpty(id)) {
		return "Element with id:" + id;
		} else if (isNotEmpty(text)) {
		return "Element with text:" + truncate (text, 50);
		}
		else if(isNotEmpty (className)) {
		return "Element with class:" +className;
		}
		else if(isNotEmpty (placeHolder)) {
		return "Element with placeholder:" +placeHolder;
		}
	} catch (Exception e) {
		
		logger.error("Unable to describe the element"+e.getMessage());
	}
	return "Unable to describe the element";
	
	
	
	}
	
	//Utility method to check a String is not NULL or empty
	private boolean isNotEmpty (String value) {
	return value!=null && ! value.isEmpty();
	}
	
	// Utility Method to truncate long String
	private String truncate (String value, int maxLength) {
	if(value==null || value.length()<=maxLength) {
	return value;
	}
	return value.substring(0,maxLength)+"...";
	}
	
	
	//Utility Method to Border an element
	public void applyBorder (By by, String color) {
	try {
	//Locate the element
	WebElement element = driver.findElement(by);
	//Apply the border
	String script = "arguments[0].style.border='3px solid "+color+"'";
	JavascriptExecutor js = (JavascriptExecutor) driver;
	js.executeScript (script, element);
	logger.info("Applied the border with color "+color+" to element: "+getElementDescription(by));
	}
	catch (Exception e) {
    logger.warn("Failed to apply the border to an element: "+getElementDescription(by),e);
	
	}
	}
	

}

