package com.pcs.show.checker;

import com.pcs.crawler.SiteCrawler;

public class Launcher {

    public static void main(String[] args) {
        SiteCrawler siteCrawler = new FcubeCrawler("2018", "05", "01", "752", "11:30 AM");
        siteCrawler.login();
        siteCrawler.crawl();
    }
}
