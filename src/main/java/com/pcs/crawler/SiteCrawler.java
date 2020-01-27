package com.pcs.crawler;

public interface SiteCrawler {
	
	public void openUrl(String url);
    
    public boolean login();

    public void crawl();

    public void reCrawl();
    
    public void refresh();
	
	public boolean delay();
	
	public boolean randomDelay();
}
