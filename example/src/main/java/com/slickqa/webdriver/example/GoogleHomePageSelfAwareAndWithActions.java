package com.slickqa.webdriver.example;

/**
 * Google's Home Page
 * Created by jcorbett on 6/24/17.
 */
public class GoogleHomePageSelfAwareAndWithActions implements SelfAwarePage, PageWithActions {
    public static String URL = "https://www.google.com";

    // NOTE: by prefixing the elements with their type, it can help find the elements in code completion
    public static PageElement inputQueryBox = new PageElement("Google Query Box", FindBy.name("q"));
    public static PageElement buttonGoogleSearch = new PageElement("Google Search Button", FindBy.value("Google Search"));

    private WebDriverWrapper browserWrapper;

    @Override
    public boolean isCurrentPage(WebDriverWrapper browser) {
        return browser.exists(inputQueryBox) && browser.exists(buttonGoogleSearch);
    }

    @Override
    public void handlePage(WebDriverWrapper browser, Object context) throws Exception {
        // no default handling of page
    }

    @Override
    public void initializePage(WebDriverWrapper browser) {
        this.browserWrapper = browser;
    }

    public void searchFor(String whatToSearchFor) {
        browserWrapper.type(inputQueryBox, whatToSearchFor);
        browserWrapper.click(buttonGoogleSearch);
    }
}
