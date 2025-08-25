package fetchLinks;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class GetLinks {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		WebDriver driver =new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://sitechecker.pro/");
		
		//Login
		driver.findElement(By.xpath("//button[@class=\"signin__btn btn-app\"]")).click();
		driver.findElement(By.xpath("//input[@id=\"loginFormEmail\"]")).sendKeys("jay990@yopmail.com");
		driver.findElement(By.xpath("//input[@id=\"loginFormPass\"]")).sendKeys("Admin@123");
		driver.findElement(By.xpath("//input[@value=\"Log in\"]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@class=\"mat-button-wrapper\"]/mat-icon[@data-mat-icon-type=\"font\" and text()='close']")).click();
		
		driver.findElement(By.xpath("//div[@class=\"summary-view-cards-content__body-item\"][1]")).click();
		
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class=\"mat-tooltip-trigger primary__value value-crawledpages cursor-pointer\"]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class=\"audit-issues__container app-cell ng-tns-c230-32 ng-star-inserted\"]")).click();
		
		Actions myAction = new Actions(driver);
		//getAll links
		List<WebElement> myList;
		for(int i=1;i<=5;i++)
		{
			Thread.sleep(3000);
			//WebElement ele=driver.findElement(By.xpath("//button[@class='mat-tooltip-trigger mat-ripple pagination-button cursor-pointer ng-star-inserted']"));
			//System.out.println(ele.getText());
			myList = driver.findElements(By.xpath("//span[@class='ng-star-inserted' and contains(.,'http')]"));
			System.out.println(myList.size());
			Thread.sleep(2000);
			System.out.println(i);
			for( WebElement finalList:myList)
			{
				myAction.moveToElement(finalList).build().perform();
				System.out.println(finalList.getText());
			}
			WebElement nextBtn = driver.findElement(By.xpath("//button/mat-icon[@role='img' and contains(.,'keyboard_arrow_right')]"));
			myAction.moveToElement(nextBtn).click().build().perform();
					}
		
		
	}
}
