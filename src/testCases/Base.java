package testCases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.gargoylesoftware.htmlunit.WebConsole.Logger;

import libraries.ExtentManager;
import libraries.Utility;


public class Base 
{     public static  ExtentHtmlReporter htmlReporter;
	  public static ExtentReports extent;
	  public static ExtentTest logger;
	  public static  WebDriver driver =null ;
	  List<WebElement> header=null;
	  int  linkcounts =0;
	 
	@BeforeSuite
	public void preRequisite()
	{
		  extent= ExtentManager.Instance();
		 
		System.setProperty("webdriver.chrome.driver","./Resources/chromedriver.exe");
		
	}
	@BeforeMethod
	public void setup()
	{
		
			
	}
	
	protected void validateTest(String URL) 
	{
		       setupDriver(URL);
         String url=driver.getCurrentUrl();
         logger = extent.createTest(url,"Pass");
             
        
         logger.log(Status.INFO, MarkupHelper.createLabel(" Main URL-navigation result listed below   ", ExtentColor.TEAL));
         logger.assignCategory(url);
         String[][] data1 = new String[1][3];
         String[][] data = new String[1][3];
         data1[0][0]="status"; 
         data1[0][1]="Page Title";
         data1[0][2]="URL";
         logger.log(Status.INFO,MarkupHelper.createTable(data1));
         List<WebElement> linksize =  driver.findElements(By.tagName("a")); 
         header=driver.findElements(By.xpath("//nav[contains(@class,'u02nav')]//a"));
                             int headerSize=header.size();
                             int end=(headerSize+10);
                             linkcounts = linksize.size();
                             System.out.println(linkcounts);
                             String []links=new String[linkcounts];
                             for(int i=0;i< 10;i++)
                               {
                              links[i] = linksize.get(i).getAttribute("href");
                                } 
                              for(int i=0;i<10;i++) { 
                       
                            	   try{
                            		   driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                                         driver.navigate().to(links[i]); 
                                         WebDriverWait wait=new WebDriverWait(driver,5);
                                         wait.until(ExpectedConditions.alertIsPresent());
                                     try {
                                         Alert alert=driver.switchTo().alert();
                                                     alert.accept();
                                          }
                                         catch(NoAlertPresentException a) {
                                    	   
                                                                           }
                                                 
                            	       }
                                    catch(Exception e){
                             		 
                             	                      }
                        	          String currentUrl=driver.getCurrentUrl();
                        	          String title= driver.getTitle();
                        	          String  title1=title.replace("| Oracle", "").replace("Oracle |", "");   		
                        	          data[0][1]=title1;
                        	          data[0][2]=currentUrl;
                      
                        	          if(currentUrl.contains("index.html")) {
                        	        	 data[0][0]="301Fail";
                        	        	 logger.assignCategory(url);
                	                 logger.log(Status.FAIL,( MarkupHelper.createTable(data)));
 
                	                
                        	         }
                        	          else {
                        	        	 data[0][0]="301Pass";
                        	        	 logger.assignCategory(url);
                        	        	 logger.log(Status.PASS, MarkupHelper.createTable(data));
                        	        	
                        	        	 
                        	        	 
                        	             }
                            }
                          }
	private void setupDriver(String URL) 
	{
		try 
		{
			if(driver==null)
			{
				driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				driver.get(URL);
			}
		Thread.sleep(2000);
		} catch(Exception ie) {
		ie.printStackTrace();
		}
		
		
	}
	
	@DataProvider(name="URLs")
	public Object[][] loginData() 
	{
		Object[][] arrayObject = Utility.getExcelData("./TestData/TestURLs.xlsx","URLs");
		return arrayObject;
	}
	@AfterMethod
	public void updateResults()
	{
		driver.close();
		driver=null;
	}
	
	@AfterSuite
	 public void tear()
	 {
		//  extent.endTest(logger);
		  extent.flush();  
		  driver=new ChromeDriver();
		  driver.get("file:///"+System.getProperty("user.dir")+"/"+"/Report/WebTechExtentReport.html".replace("./", ""));
		  driver.manage().window().maximize();
		 // driver.quit();
	 }
	
}
