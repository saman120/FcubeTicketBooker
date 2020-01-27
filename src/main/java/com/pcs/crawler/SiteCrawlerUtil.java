package com.pcs.crawler;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SiteCrawlerUtil {
	private WebDriver driver;
	
	public SiteCrawlerUtil(WebDriver driver) {
		this.driver = driver;
	}

    public boolean waitForClass(String className, int interval, int time, boolean flag) {
        int count = 0;
        while (count < time * 1000) {
            if (driver.findElement(By.className(className)).isDisplayed() == flag) {
                return true;
            }
            delay(interval);

            count += interval;
        }
        return false;
    }

    public boolean waitForId(String elementId, int interval, int time, boolean flag) {
        int count = 0;
        while (count < time * 1000) {
            WebElement loginModal = driver.findElement(By.id(elementId));

            if (loginModal.isDisplayed() == flag) {
                return true;
            }
            delay(interval);

            count += interval;
        }
        return false;
    }

    public boolean delay(int delay) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
            return true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
