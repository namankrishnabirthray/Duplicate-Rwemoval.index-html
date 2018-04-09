package libraries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class Utility {
	
	public static String[][] getExcelData(String fileName, String sheetName) {
		String[][] arrayExcelData = null;
		System.out.println("Reading Excel for URLs");
		try {
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = WorkbookFactory.create(fs);
			Sheet sh = wb.getSheet(sheetName);
			int totalNoOfCols = sh.getRow(0).getLastCellNum();
			int totalNoOfRows = sh.getLastRowNum()+1;
			
			arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];
			
			for (int i= 1 ; i <totalNoOfRows; i++) {
				
				for (int j=0; j < totalNoOfCols; j++) {
					if(!sh.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase("") && !(sh.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(null)))
					{
						arrayExcelData[i-1][j] = sh.getRow(i).getCell(j).getStringCellValue();
					
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Reading from Excel is done.....");
		return arrayExcelData;
	}
	
	 public static void writeExcel(String fileName,String sheetName,ArrayList<String> dataToWrite)
	 {
		 try {
				FileInputStream fs = new FileInputStream(fileName);
				Workbook wb = WorkbookFactory.create(fs);
				Sheet sh = wb.getSheet(sheetName);
				int rowCount = sh.getLastRowNum()-sh.getFirstRowNum();
			    Row row = sh.getRow(0);
			    Row newRow = sh.createRow(rowCount+1);
			    for(int j = 0; j < dataToWrite.size(); j++){

			        Cell cell = newRow.createCell(j);
			        cell.setCellValue(dataToWrite.get(j));

			    }


			    fs.close();

			    //Create an object of FileOutputStream class to create write data in excel file

			    FileOutputStream outputStream = new FileOutputStream(fileName);

			    //write data in the excel file

			    wb.write(outputStream);

			    //close output stream

			    outputStream.close();
		 }
		 catch(Exception e)
		 {}
		 
	 }
	 
	 public static void upload(WebDriver driver)
		{
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().setPosition(new Point(-2000, 0));
			driver.get("https://github.com/Abdul-WTOracle/project/tree/master/TestReports");
			driver.findElement(By.linkText("Sign in")).click();
			driver.findElement(By.id("login_field")).sendKeys("Abdul-WTOracle");
			driver.findElement(By.id("password")).sendKeys("pwd4git");
			driver.findElement(By.name("commit")).click();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.findElement(By.xpath("//*[contains(text(),'Upload files')]")).click();
			File f=new File(System.getProperty("user.dir")+"/"+ExtentManager.Path.replace("./", ""));
			System.out.println(f.getAbsolutePath());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.findElement(By.xpath("//*[@type='file']")).sendKeys(f.getAbsolutePath());
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.findElement(By.xpath("//button[contains(text(),'Commit changes')]")).click();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.findElement(By.linkText("TestReports")).click();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driver.findElement(By.linkText(ExtentManager.Path.replace("./", ""))).click();
			String reportURL=driver.getCurrentUrl();
			String report=reportURL.replace("github", "rawgit").replace("/blob/", "/");
			/*driver.navigate().to("https://rawgit.com/");
			driver.findElement(By.id("url")).sendKeys(reportURL);
			String report=driver.findElement(By.id("url-dev")).getText();*/
			driver.navigate().to(report);
			try {
				Thread.sleep(13000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(report);
			driver.quit();
			
			File file1 = new File("./Automation_Test_Report_URL.txt");
			try{
			if (!file1.exists()) {
	            file1.createNewFile();
	        }
			FileWriter fw = new FileWriter(file1.getAbsoluteFile(),true);
			BufferedWriter b = new BufferedWriter(fw);
			PrintWriter bw = new PrintWriter(b);
			bw.append(report);
			bw.flush();
			fw.flush();
			bw.close();
			fw.close();
			
			}catch(Exception e){}
			
		}
	
	public static List<String> getExcelData(String fileName, String sheetName, int row) {
		List<String> excelData = new ArrayList<String>();
		System.out.println("Reading Excel for Tags");
		try {
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = WorkbookFactory.create(fs);
			Sheet sh = wb.getSheet(sheetName);
			int totalNoOfCols = sh.getRow(0).getLastCellNum();
			int totalNoOfRows = sh.getLastRowNum()+1;
						
			for (int i= 1 ; i <totalNoOfRows; i++) {
				
				for (int j=0; j < totalNoOfCols; j++) {
					if(!sh.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase("") && !(sh.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase(null)))
					{
						excelData.add( sh.getRow(i).getCell(j).getStringCellValue());
					
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Reading from Excel is done.....");
		return excelData;
	}
	
	public static void deleteRows(String fileName, String sheetName)
	{
		try {
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = WorkbookFactory.create(fs);
			Sheet sheet = wb.getSheet(sheetName);
			
			/*Iterator<Row> rowIte =  sheet.iterator();
			rowIte.next(); 
			while(rowIte.hasNext()){
			    rowIte.next();              
			    rowIte.remove();
			}*/
			int rowCount = sheet.getLastRowNum()-sheet.getFirstRowNum();
			System.out.println(rowCount);
			while(rowCount >0)
			{
				Row r = sheet.getRow(rowCount);
				sheet.removeRow(r);
				--rowCount;
				System.out.println(rowCount);
			}
			fs.close();

		    //Create an object of FileOutputStream class to create write data in excel file

		    FileOutputStream outputStream = new FileOutputStream(fileName);

		    //write data in the excel file

		    wb.write(outputStream);

		    //close output stream

		    outputStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String GetCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

}
