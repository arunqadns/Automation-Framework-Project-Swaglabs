package com.Test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.pages.HomePage;
import com.pages.LoginPage;
import com.utilities.DataProviders;
import com.utilities.ExtentManager;

public class HomePageTest extends BaseClass {
	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());

	}

	@Test(dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyOrangeHRMLogo(String username, String password) {
		// ExtentManager.startTest("Home Page Verify Logo Test"); //--This has been
		// implemented in TestListener
		ExtentManager.logStep("Trying to Navigating to Home Page by entering username and password");

		loginPage.login(username, password); // for Swaglabs from data provider Excel
		ExtentManager.logStep("Verifying Logo is visible or not");
		Assert.assertTrue(homePage.verifySwaglabslogo(), "Logo is not visible");
		ExtentManager.logStep("Validation Successful");

	}

}
