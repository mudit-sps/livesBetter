package seleniumLightHouse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LightHouse2 {

	static String trim;
	static WebDriver driver;
	static int row, cell;
	static int srlNo=0;
	static List<String> allUrl=new ArrayList<String>();
	public static void main(String[] args) throws IOException {
	        // Automatically download and setup the correct version of ChromeDriver
	    	        // Start Chrome browser with Selenium
		 
		 FileInputStream myFile=new FileInputStream(System.getProperty("user.dir")+"\\currentURLs\\LiveBetterExistingURL.xlsx");
  	   	 XSSFWorkbook wb=new XSSFWorkbook(myFile);
  	   	 XSSFSheet sh=wb.getSheet("Sheet1");
  	   	 row=sh.getLastRowNum(); 	//read number of rows
  	   	 cell=sh.getRow(1).getLastCellNum();
  	   	 System.out.println(row+" & "+cell);
  	   	 for(int r=0;r<=row;r++)
  	   	 {
  	   		 XSSFRow rw = sh.getRow(r);
  	   		 for(int c=0;c<cell;c++)
	   	   	 {
	   	   		 XSSFCell cl = rw.getCell(c);
	   	   		 String st=cl.getStringCellValue();
	   	   		 allUrl.add(st);
	   	   	 }
  	   	 }
			/*
			 * for(String currentURL:allUrl ) { System.out.println(currentURL); }
			 */
		 System.out.println("Total Number of URL's to be tested: "+allUrl.size());
  	   	 wb.close();
		 myFile.close();
  	   	 driver=new ChromeDriver();
  	   	 driver.manage().window().maximize();
	        try {
	            // Navigate to the page 
		        	for(String currentURL:allUrl)
		        	{
		        		driver.get(currentURL);
		        		
				   	   	trim=currentURL.replaceAll("https?://", "").replaceAll("[^a-zA-Z0-9-_\\.]", "_");
				        // Wait for page to load fully (or simulate login, etc.)
				         Thread.sleep(3000);
				         // Run Lighthouse via command line
				         srlNo++;
				          runLighthouse(currentURL, srlNo);
					 }
	        }	 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        } 
	        finally 
	        {
	          	driver.quit();
	        	System.out.println("FAILED: "+srlNo);
	        }
	   
	    }

	    private static void runLighthouse(String url, int rs) {
	        try {
	            // Lighthouse CLI command
	        
	        	
	        	String lighthousePath = "C:\\Users\\user\\AppData\\Roaming\\npm\\lighthouse.cmd";
	        	String command = String.format("\"%s\" %s --output html --output-path=C:\\Users\\user\\eclipse-workspace\\com.LivesBetter\\reports\\"+rs+"_lighthouse-report"+trim+".html --quiet --chrome-flags=--headless",
	        	                               lighthousePath, url);

	        	@SuppressWarnings("deprecation")
				Process process = Runtime.getRuntime().exec(command);

	            // Print CLI output
	            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            String line;
	            while ((line = reader.readLine()) != null) {
	                System.out.println(line);
	            }

	            process.waitFor();
	            System.out.println("Lighthouse audit completed. Report saved as "+trim);

	        } catch (Exception e) {
	            System.err.println("❌ Failed to run Lighthouse:");
	            e.printStackTrace();
	        }
	        
	        
	      
	    }
}
