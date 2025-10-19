package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import TestBase.BaseClass;
import pageObjects.AccountsRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups={"Sanity","Master"})
	public void verify_account_registration() {
		
		//logger.info("*** Starting Tc001****");
		try
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		//logger.info("Clicked my account");
		
		hp.clickRegister();
		//logger.info("Clicked register");
		
		AccountsRegistrationPage regpage = new AccountsRegistrationPage(driver);
		
		//logger.info("Providing customer details");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString() + "@gmail.com");
		regpage.setTelephone(randomNumber());
		
		String password = randomAplhaNumeric();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		//logger.info("Validating expected message");
		String confmsg = regpage.getConfirmationMsg();
		
		Assert.assertEquals(confmsg, "Your Account Has Been Created!");		
		
	}
	
	catch (Exception e) 
		{
		//logger.error("Test failed");
		Assert.fail();
	}
		
		//logger.info("Finished");
	}
}
