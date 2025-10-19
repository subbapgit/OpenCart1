package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestBase.BaseClass;

public class ExtentReportManager implements ITestListener

{

	public ExtentSparkReporter sparkReporter ;
	public ExtentReports extent ;
	public ExtentTest test ;
	
	String repName;
	
	public void onStart(ITestContext context) 
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //Times stamp
		
		repName = "Test-report" + timeStamp +".html"; // name of the report
		
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // Specify report Location
		
		sparkReporter.config().setDocumentTitle("OpenCart Automation Report"); //Title of report
		sparkReporter.config().setReportName("OpenCart Functional Testing"); // name of report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application", "Open Cart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os = context.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser = context.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) 
		{
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
		
	}
	
	public void onTestSuccess(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName()) ; // create a new test
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS,"Test case passed is :" + result.getName()); //update test status p/f/s
	}
	
	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName()) ; // create a new test
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName() + "Got Failed"); //update test status p/f/s
		test.log(Status.INFO, result.getThrowable().getMessage()); 
	
		try 
		{
		String imgPath = new BaseClass().captureScreen(result.getName());
		test.addScreenCaptureFromPath(imgPath);
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName()) ; // create a new test
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.SKIP, result.getName()+" got Skipped"); //update test status p/f/s
		test.log(Status.INFO, result.getThrowable().getMessage()); 

	}
	
	public void onFinish(ITestContext context) {
		
		extent.flush();
		
		String pathofExtentReport = System.getProperty("user.dir")+"\\reports\\"+ repName;
		File extentReport =new File(pathofExtentReport);
	
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
