package fetchLinks;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Deleteone {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver= new ChromeDriver();
		driver.get("https://tutorialsninja.com/demo/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		boolean pageLoadStatus=driver.findElement(By.xpath("//a[text()='Qafox.com']")).isDisplayed();
		if (pageLoadStatus==true && driver.getTitle().equals("Your Store")) {
			System.out.println("Test Passed");
		}
		else
			System.out.println("test failed");
	}

}
