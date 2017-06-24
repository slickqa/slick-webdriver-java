package com.slickqa.webdriver.example;

import com.slickqa.webdriver.*;

/**
 * Search results
 */
public class GoogleResultsPage implements SelfAwarePage, PageWithActions {

    public static PageElement textResultStats = new PageElement("Results Statistics", FindBy.id("resultStats"));

    private WebDriverWrapper browserWrapper;

    @Override
    public void initializePage(WebDriverWrapper browser) {
        this.browserWrapper = browser;
    }

    @Override
    public boolean isCurrentPage(WebDriverWrapper browser) {
        return browser.exists(textResultStats) && browser.getText(textResultStats).contains("results");
    }

    @Override
    public void handlePage(WebDriverWrapper browser, Object context) throws Exception {
        // no default handling of this page
    }

    public PageElement searchResult(String url) {
        return new PageElement("Search Result for URL " + url, FindBy.xpath("//cite[contains(.,\"" + url + "\")]"));
    }

    public boolean searchResultExistsOnPage(String url) {
        try {
            browserWrapper.waitFor(searchResult(url));
            return true;
        } catch (TimeoutError e) {
            return false;
        }
    }
}
