package com.pcs.crawler;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class DefaultSiteCrawler implements SiteCrawler {
	protected SiteCrawlerUtil siteCrawlerUtil;
	private WebDriver driver;

	public DefaultSiteCrawler() {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		driver = (WebDriver) new ChromeDriver();
		siteCrawlerUtil = new SiteCrawlerUtil(driver);
	}

	@Override
	public void refresh() {
		getDriver().navigate().refresh();
	}

	@Override
	public void openUrl(String url) {
		getDriver().get(url);
	}

	@Override
	public boolean randomDelay() {
		int time = new Random().nextInt(500) + 100;
		System.out.print("\n" + time + " secounds Delay");
		try {
			TimeUnit.SECONDS.sleep(time);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public SiteCrawlerUtil getSiteCrawlerUtil() {
		return siteCrawlerUtil;
	}

	public void setSiteCrawlerUtil(SiteCrawlerUtil siteCrawlerUtil) {
		this.siteCrawlerUtil = siteCrawlerUtil;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

}
