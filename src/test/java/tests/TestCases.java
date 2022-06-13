package tests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestCases {

	private WebDriver driver;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Test
	public void test_Login() {
		driver.get("http://localhost:8080");
		WebElement inputNick = driver.findElement(By.id("Nick"));
		WebElement inputPass = driver.findElement(By.id("Contra"));
		WebElement button = driver.findElement(By.id("boton"));
		
		inputNick.sendKeys("admin");
		inputPass.sendKeys("admin");
		button.click();
		
		String title = driver.getTitle();
		
		
		assertEquals("E-Market", title);
	}
	
	@Test
	public void test_NavigateToEmptyPedidos() {
		driver.get("http://localhost:8080");
		WebElement inputNick = driver.findElement(By.id("Nick"));
		WebElement inputPass = driver.findElement(By.id("Contra"));
		WebElement button = driver.findElement(By.id("boton"));
		
		inputNick.sendKeys("admin");
		inputPass.sendKeys("admin");
		button.click();
		
		WebElement buttonPedidos = driver.findElement(By.id("pedidos"));
		buttonPedidos.click();
		
		WebElement noPedidos = driver.findElement(By.id("noPedidos"));
		String noPedidosString = noPedidos.getText();
		
		
		assertEquals(noPedidosString, "Aun no has realizado ningún pedido");
	}
	
	@After
	public void shutdown() {
		driver.quit();
	}

}
