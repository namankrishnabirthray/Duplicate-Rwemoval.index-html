package testCases;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class TestCaseName extends Base
{
	
	@Test(dataProvider="URLs")
	public void TC_ID(String URL)
	{
		validateTest(URL);
	}
  /* @Test(dataProvider="URLs")
   public void  TC_BC(String URL)
   {   
	 validateBreadCrumb(URL);
	 
   } */
	
}
