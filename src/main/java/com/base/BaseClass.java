package com.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;

import com.actiondriver.ActionDriver;
import com.beust.jcommander.Parameter;
import com.utilities.ExtentManager;
import com.utilities.LoggerManager;

public class BaseClass {

	protected static Properties prop;
	//protected static WebDriver driver;   //--Thread change
	//private static ActionDriver actionDriver;     //----Thread change
	
	//Above 2 lines changed in to below 2.

	private static ThreadLocal<WebDriver> driver=new ThreadLocal<>();//--Thread change
	private static ThreadLocal<ActionDriver> actionDriver=new ThreadLocal<>();//--Thread change
	public static final Logger Logger = LoggerManager.getLogger(BaseClass.class);

	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load configuration file.
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		Logger.info("Property file loaded");
		
		//Start the Extent Report
		//ExtentManager.getReporter();  //--This has been implemented in TestListener
	}

	@BeforeMethod
	//public void setup() throws IOException {   //Thread change this line to below added synchronized
		public synchronized void setup() throws IOException {
		System.out.println("Setting up WebDriver for:" + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(2);
		Logger.info("Webdriver Initialized and browser launched");
		Logger.trace("This is a Trace message");
		Logger.error("This is a error message");
		Logger.debug("This is a debug message");
		Logger.fatal("This is a fatal message");
		Logger.warn("This is a warn message");
		
		
/*		//initialize the action driver only once.
		if(actionDriver==null) {
			actionDriver=new ActionDriver(driver);
			System.out.println("action driver object instatiated");
		}*/  //Thread change above lines changed to below
		
//Initialize ActionDriver for the current Thread
		actionDriver.set(new ActionDriver (getDriver()));
		Logger.info("ActionDriver initlialized for thread: "+Thread.currentThread().getId()); 

	}

	@AfterMethod
	//public void teardown() {
	public synchronized void teardown() {	//Thread change this line to below added synchronized

		if (driver.get() != null) {
			try {
				driver.get().quit();
			} catch (Exception e) {
				System.out.println("Unable to Quite the browser:" + e.getMessage());
			}
		}
		Logger.info("Webdriver instance is closed");
		driver.remove();
		actionDriver.remove();
		//ExtentManager.endTest();    //--This has been implemented in TestListener

		
	}
		
/*	
	//Driver getter method/Now we get driver in all class/package.
	public WebDriver getDriver() {
		return driver;}
	//Driver setter method/Now we get driver in all class/package.
	public WebDriver getDriver(WebDriver driver) {
		return driver;}
*/
	
	// Getter Method for WebDriver
	public static WebDriver getDriver() {
	if (driver.get() == null) {
	System.out.println("WebDriver is not initialized");
	throw new IllegalStateException("WebDriver is not initialized");
	}
	return driver.get();
	}
	//Thread change-in above method it was driver changed to driver.get() 
	
	// Getter Method for ActionDriver
	public static ActionDriver getActionDriver() {
	if (actionDriver.get() == null) {
	System.out.println("actionDriver is not initialized");
	throw new IllegalStateException("actionDriver is not initialized");
	}
	return actionDriver.get();
	}
	
	
	//Getter method for prop
	public static Properties getProperties() {
		return prop;
	}
	
	// Driver setter method
	public void setDriver (ThreadLocal<WebDriver> driver) {
	this.driver = driver;
	}
	
	
	
	//Static wait for pause
	public void staticWait (int seconds) {
	LockSupport.parkNanos (TimeUnit.SECONDS. toNanos (seconds));
	}
	/*
	 * Initialize the WebDriver based on browser defined in config.properties file
	 */

	//private  void launchBrowser() {
	
	
	private synchronized  void launchBrowser() { //Thread change this line to below added synchronized

		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			//driver = new ChromeDriver();  //Thread change this line changed by below line
			
//Code for headless			
			ChromeOptions options=new ChromeOptions();
			options.addArguments("--headless=new"); // Run Chrome in headless mode
			options.addArguments("--disable-gpu"); // Disable GPU for headless mode
			options.addArguments("--window-size=1920,1080"); // Set window size
			options.addArguments("--disable-notifications"); // Disable browser notifications
			options.addArguments("--no-sandbox"); // Required for some CI environments like Jenkins
			options.addArguments("--disable-dev-shm-usage");
			driver.set(new ChromeDriver(options));
			
//--------------------------			
//			driver.set(new ChromeDriver());
			
			ExtentManager.registerDriver (getDriver());
			Logger.info("Chrome driver initialized");
		} else if (browser.equalsIgnoreCase("firefox")) {
			//driver = new FirefoxDriver();   //Thread change this line changed by below line
			driver.set(new FirefoxDriver());
			ExtentManager.registerDriver (getDriver());
			Logger.info("Firefox driver initialized");
		}

		else if (browser.equalsIgnoreCase("edge")) {
			//driver = new EdgeDriver();  //Thread change this line changed by below line
			driver.set(new EdgeDriver());
			ExtentManager.registerDriver (getDriver());
			Logger.info("Edge driver initialized");
		} else {
			System.out.println("invalid Browser given");
			throw new IllegalArgumentException("Browser Not Supported " + browser);
		}

	}

	/*
	 * Configure browser settings such as implicit wait, maximize the browser and
	 * navigate to the URL
	 */

	private void configureBrowser() {
		// Implicite wait
		String time = prop.getProperty("implicitWait");
		int waittime = Integer.parseInt(time);
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(waittime));

		// Maximize browser window
		driver.get().manage().window().maximize();

		// Navigate to URL
		String URL = prop.getProperty("url");

		try {
			driver.get().get(URL);
		} catch (Exception e) {
			System.out.println("Failed to Navigate to the URL:" + e.getMessage());

		}

	}

}
