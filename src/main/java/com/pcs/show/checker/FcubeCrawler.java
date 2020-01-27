package com.pcs.show.checker;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.pcs.crawler.DefaultSiteCrawler;
import com.pcs.crawler.SiteCrawler;

public class FcubeCrawler extends DefaultSiteCrawler implements SiteCrawler {

	private static final String movieModalPrifix = "movieModal_";

	private String date;
	private String movieCode;
	private String time;
	private int delay = 10000;

	private int count = 0;

	public FcubeCrawler(String year, String month, String date, String movieCode, String time) {
		super();
		this.date = date;
		this.movieCode = movieCode + "_" + date + "-" + month + "-" + year;
		this.time = time;
		getDriver().get("http://fcubecinemas.com/");
	}

	private int increaseCount() {
		return ++count;
	}

	// crawls page and search if show is present on the provided date
	@Override
	public void crawl() {
		if(!searchMovie())  {
			reCrawl();
			return;
		}
		
		bookMovie();
	}

	// refresh and crawls page after 10 secounds delay
	@Override
	public void reCrawl() {
		System.out.print("\nReattempting... ");
		randomDelay();
		refresh();
		crawl();
	}

	@Override
	public boolean delay() {
		return siteCrawlerUtil.delay(delay);
	}

    // login to site
	@Override
    public boolean login() {
        getDriver().findElements(By.className("loginModal-trigger")).get(0).click();

        if (!siteCrawlerUtil.waitForClass("login-modal", 100, 20, true)) {
            return false;
        }
        if (!siteCrawlerUtil.waitForId("UserName", 100, 20, true)) {
            return false;
        }

        WebElement login = getDriver().findElement(By.id("UserName"));
        login.sendKeys("samanacharya@gmail.com");
        login = getDriver().findElement(By.id("Password"));
        login.sendKeys("fcube9849091777");
        login.submit();

        siteCrawlerUtil.waitForClass("login-modal", 100, 10, false);

        return true;
    }

    //search and opens movie
    public boolean searchMovie() {
		System.out.println("\nSearching element for date(" + date + ") .... \n\tattempt: " + increaseCount());
		WebElement thumbSelect = null;

		List<WebElement> elements = getDriver().findElements(By.className("ms-thumb"));

		// searching the tab button to match the date String
		for (WebElement element : elements) {
			if (element.findElement(By.className("date")).getText().trim().equals(date)) {
				thumbSelect = element;
				thumbSelect.click();
				break;
			}
		}

        System.out.println("Crawlering throught page to search show list...");
        
        WebElement slickElement = null;
		// when the tab button is found
		if (thumbSelect != null) {
			try {
				WebElement element = getDriver().findElement(By.id(thumbSelect.getAttribute("data-target")));

				if (element.findElements(By.className("no-shows")).size() > 0) {
					return false;
				} else {
					// JOptionPane.showMessageDialog(new JFrame(), "Show found!", "Found", 1);
					slickElement = element;
				}
			} catch (NoSuchElementException e) {
				this.reCrawl();
			}
		}

        if (slickElement == null) {
            return false;
        }
        System.out.println("Show list Elements Found");

        List<WebElement> showList = slickElement.findElements(By.className("shows-list"));

        System.out.println("Selecting the provided movie...");
        int count = 0;
        while (count++ < 20) {
            for (WebElement webElement : showList) {
                WebElement showElement = webElement.findElement(By.className("modal-trigger"));
                if (showElement.getAttribute("data-movie").equals(movieCode)) {
                    if (showElement.isDisplayed()) {
                        showElement.click();
                        count = 20;
                        break;
                    }
                }
            }
            siteCrawlerUtil.delay(2000);
        }

        if (!siteCrawlerUtil.waitForId(movieModalPrifix + movieCode, 100, 20, true)) {
            return false;
        }

        System.err.println("Movie Modal Found");
        WebElement movieModal = getDriver().findElement(By.id(movieModalPrifix + movieCode));

        if (movieModal != null) {
            System.err.println("Selecting Time Slot....");
            List<WebElement> timeElementList = movieModal.findElements(By.className("show-time-wrap")).get(0)
                    .findElements(By.className("show-time"));
            for (WebElement timeElement : timeElementList) {
                if (timeElement.getText().equals(time)) {
                    timeElement.click();
                    break;
                }
            }

        }
        
        return true;
    }

    public void bookMovie() {
        siteCrawlerUtil.waitForId("93", 100, 10, true);
        WebElement seatElement = getDriver().findElement(By.id("93"));
        seatElement.click();

        siteCrawlerUtil.waitForClass("seat-loader", 100, 10, true);
        siteCrawlerUtil.waitForClass("seat-loader", 100, 10, false);

        WebElement buttonElement = getDriver().findElement(By.id("btn-reserve"));
        buttonElement.click();

        if (siteCrawlerUtil.waitForId("confirmationMessage", 100, 20, true)) {
            System.err.println("Booking Successful!");
        }
    }

}
