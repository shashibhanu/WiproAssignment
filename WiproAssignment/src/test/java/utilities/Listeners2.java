package utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class Listeners2 implements ITestListener {
	public ExtentSparkReporter htmlReporter;//from v 4.1.3 ExtentHtmlReporter has been deprecated so use ExtentSparkReporter 
	public ExtentReports extent;
	public ExtentTest test;
	
	

    		
    public void onStart(ITestContext result) {					
       				
     System.out.println("Test Listening Started");
     htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/Reports/RestAssuredExtentReport.html");
     extent = new ExtentReports();
     extent.attachReporter(htmlReporter);
     extent.setSystemInfo("Host Name", "Shashi local host");
     extent.setSystemInfo("Environment", "shashi QA");
     extent.setSystemInfo("User Name", "Shashi");
     
     htmlReporter.config().setDocumentTitle("Rest Assured Report"); 
     htmlReporter.config().setReportName("Demoreport"); 
     htmlReporter.config().setTheme(Theme.STANDARD); 
    }
    
    		
    public void onTestStart(ITestResult result) {					
    	{		
    		test = extent.createTest(result.getName());
    		System.out.println(test+" test case started");
    	    //System.out.println(result.getName()+" test case started");					
    	}	
        		
    }	

    		
    public void onTestSuccess(ITestResult result) {					
    	{		
    		test = extent.createTest(result.getName());
    		test.log(Status.PASS, "TestCase Passed is"+result.getName());
    	    //System.out.println("The name of the testcase passed is :"+test);					
    	}
        		
    }	
    
    		
    public void onTestFailure(ITestResult result) {					
    	{		
    		test = extent.createTest(result.getName());
    		test.log(Status.FAIL, "TestCase Failed is"+result.getName());
    		test.log(Status.FAIL, "TestCase Failed is"+result.getThrowable());
    		//System.out.println("The name of the testcase failed is :"+result.getName());					
    	}
        		
    }
    
    		
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {					
        			
    	System.out.println("Debug the failure test cases");
    }	
    
    		
    public void onTestSkipped(ITestResult result) {					
        			
    	 {		
    		 test = extent.createTest(result.getName());
     		test.log(Status.SKIP, "TestCase Skipped is"+result.getName());					
         }
    }
    
			
    public void onFinish(ITestContext result) {					
				
		extent.flush();
    }

}
