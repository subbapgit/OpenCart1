package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import TestBase.BaseClass;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC002_LoginTest extends BaseClass{

	@Test(groups={"Regression","Master"})
	public void verifyLogin() 
	{
	
		try 
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
				
		MyAccountPage myacc = new MyAccountPage(driver);
		boolean targetPage= myacc.isMyAccountsPageExists();
		
		//Assert.assertEquals(targetPage, true, "LoginFailed"); //If condition fails "Login Failed" msg will be displayed
		
		Assert.assertTrue(targetPage);
		}
		catch(Exception e)
		{
			Assert.fail();
		}
	}
	
	
}
