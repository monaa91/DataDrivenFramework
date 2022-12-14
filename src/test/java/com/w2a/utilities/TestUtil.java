package com.w2a.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import java.lang.reflect.Method;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.mysql.cj.exceptions.DataConversionException;
import com.w2a.base.TestBase;

public class TestUtil extends TestBase{

	public static String screenshotPath;
	public static String screenshotName;
	
	public static void captureScreenshot() throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));
		FileUtils.copyFile(scrFile,
				new File(".\\reports\\" + screenshotName));

	}
	
	
	@DataProvider(name="dp")
	public Object[][] getData(Method m) {

		String sheetName = m.getName();
		//sheetName = sheetName.toUpperCase();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		System.out.println("rows size is "+rows);
		System.out.println("sheetname is "+sheetName);

		Object[][] data = new Object[rows - 1][1];
		
		Hashtable<String,String> table = null;

		for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2

			table = new Hashtable<String,String>();
			
			for (int colNum = 0; colNum < cols; colNum++) {

				// data[0][0]
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}

		}

		return data;

	}
	
	public static boolean isTestRunnable(String testName, ExcelReader excel) {
		
		
		String sheetName="test_suite";
		int rows = excel.getRowCount(sheetName);
		
		for(int rNum=2; rNum<=rows; rNum++) {
			String testCase = excel.getCellData(sheetName, "TCID", rNum);
			
			if(testCase.equalsIgnoreCase(testName)) {
				
				String runmode = excel.getCellData(sheetName, "Runmode", rNum);
				
				if(runmode.equalsIgnoreCase("Y")) {
					return true;
				}
					else {
						return false;
					}}
						return false;
				}
		return false;
			}
		}
		
		
	
