package com.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.pages.HomePage;
import com.pages.LoginPage;
import com.utilities.DataProviders;
import com.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());

	}

	@Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyValidLoginTest(String username, String password) {

		// TestListener
		loginPage.login(username, password); // for Swaglabs from data provider Excel
		ExtentManager.logStep("Trying to Navigating to Home Page by entering username and password");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should visible after successful login");
		ExtentManager.logStep("Validation Successfull");
//		homePage.logout();
//		ExtentManager.logStep("Logged out Successfully!");
		staticWait(2);
	}

	@Test(dataProvider = "inValidLoginData", dataProviderClass = DataProviders.class)
	public void inValidLoginTest(String username, String password) {

		// in TestListener
		System.out.println("Running testMethod2 on thread: " + Thread.currentThread().getId());
		ExtentManager.logStep("Trying to Navigating to Home Page by entering username and password");
		loginPage.login(username, password); // for Swaglabs from data provider Excel
		String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service"; // for
																													// Swaglabs
		staticWait(2);
		Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage), "Test Failed: Invalid Error message");
		ExtentManager.logStep("Validation Successful");
	}

}
