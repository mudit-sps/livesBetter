package seleniumLightHouse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LightHouse {

	static String trim;
	static WebDriver driver;
	
	public static void main(String[] args) throws IOException {
	        // Automatically download and setup the correct version of ChromeDriver
	     //   WebDriverManager.chromedriver().setup();

	        // Start Chrome browser with Selenium
	     
	   	 {
	        try {
	            // Navigate to the page
	        	
	        	FileInputStream myFile=new FileInputStream(System.getProperty("user.dir")+"\\currentURLs\\LiveBetterExistingURL.xlsx");
		   	   	 XSSFWorkbook wb=new XSSFWorkbook(myFile);
		   	   	 XSSFSheet sh=wb.getSheet("Sheet1");
		   	   	 int row=sh.getLastRowNum(); 	//read number of rows
		   	   	 int cell=sh.getRow(1).getLastCellNum();
		   	   	 System.out.println(row+" & "+cell);
		   	   	 for(int r=0;r<=row;r++)
		   	   	 {
		   	   		driver= new ChromeDriver();
		   	   		 driver.manage().window().maximize();
		   	   		 XSSFRow rw = sh.getRow(r);
		   	   		 for(int c=0;c<cell;c++)
			   	   	 {
			   	   		 XSSFCell cl = rw.getCell(c);
			   	   		 System.out.println(cl);
			   	   		 String url = cl.getStringCellValue();
			   	   		 driver.get(url);
			   	   		 trim=url.replaceAll("https?://", "").replaceAll("[^a-zA-Z0-9-_\\.]", "_");
			            // Wait for page to load fully (or simulate login, etc.)
			            Thread.sleep(3000);
		
			            // Run Lighthouse via command line
			           runLighthouse(url, r);
			           
					  }
		   	   		 driver.close();	
		   	   	 }	
		   	  wb.close();
 	   		 	myFile.close();
	        }	 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        } 
	        finally 
	        {
	          	driver.quit();
	        	System.out.println("PASS ");
	        }
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
