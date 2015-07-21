package com.eliasnogueira.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobileCapabilityType;

/*
 * Classe de teste entre dispositivo e wearable
 */
public class TestSimpleNotification {

	private AndroidDriver<WebElement> device;
	private AndroidDriver<WebElement> wear;
	
	private DesiredCapabilities capacidadeDevice;
	private DesiredCapabilities capacidadeWear;
	
	private WebDriverWait waitWear;
	
	@Before
	public void setUp() throws Exception {
		
		// device
		capacidadeDevice = new DesiredCapabilities();
		capacidadeDevice.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
		capacidadeDevice.setCapability(MobileCapabilityType.APP_PACKAGE, "com.eliasnogueira.wearableexample");
		capacidadeDevice.setCapability(MobileCapabilityType.APP_ACTIVITY, "MainActivity");
		device = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4724/wd/hub"), capacidadeDevice);	// sessao
		
		// wear
		capacidadeWear = new DesiredCapabilities();
		capacidadeWear.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5556");
		capacidadeWear.setCapability(MobileCapabilityType.APP_PACKAGE, "com.eliasnogueira.wearableexample");
		capacidadeWear.setCapability(MobileCapabilityType.APP_ACTIVITY, "WearActivity");
		wear = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capacidadeWear);	// sessao
		
		// esperas
		waitWear = new WebDriverWait(wear, 15);
	}

	@After
	public void tearDown() throws Exception {
		device.closeApp();
		wear.closeApp();
	}

	@Test
	public void testSimpleNotification() {
		
		// espera pela tela no wear
		By text = By.id("com.eliasnogueira.wearableexample:id/text");
		waitWear.until(ExpectedConditions.presenceOfElementLocated(text));
		
		// valida a mensagem no wear
		assertEquals("Hello Round World!", wear.findElement(By.id("com.eliasnogueira.wearableexample:id/text")).getText());
		
		// valida que existe o botão de emitir notificação no device
		assertTrue(device.findElement(By.id("com.eliasnogueira.wearableexample:id/btn_notification")).isDisplayed());
	}
	
	@Test
	public void testSimpleNotificationWearExample() {
		
		// espera pelo wear
		By text = By.id("com.eliasnogueira.wearableexample:id/text");
		waitWear.until(ExpectedConditions.presenceOfElementLocated(text));
		
		// vai para a home do wear
		wear.sendKeyEvent(AndroidKeyCode.HOME);
		
		// emite a notificação pelo dispositivo
		device.findElement(By.id("com.eliasnogueira.wearableexample:id/btn_notification")).click();
		
		// espera pela notificacao no wear
		By title = By.id("com.google.android.wearable.app:id/title");
		waitWear.until(ExpectedConditions.presenceOfElementLocated(title));
		
		// valida a mensagem no wear
		assertEquals("New message", wear.findElement(title).getText());
		assertEquals("You received a new message!", wear.findElement(By.id("com.google.android.wearable.app:id/text")).getText());
	}

}
