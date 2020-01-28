package com.pcs.show.checker;

import com.pcs.crawler.SiteCrawler;

public class Launcher {

    public static void main(String[] args) {
        SiteCrawler siteCrawler = new FcubeCrawler("2020", "01", "30", "1039", "12:30 PM");
        siteCrawler.login();
        siteCrawler.crawl();
    }
}
