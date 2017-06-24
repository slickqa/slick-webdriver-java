package com.slickqa.webdriver.example;

import com.slickqa.webdriver.DefaultWebDriverWrapper;
import com.slickqa.webdriver.WebDriverWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by jcorbett on 6/24/17.
 */
public class ExampleSearchTests {

    private WebDriverWrapper browser = null;


    @Before
    public void setUp() {
        browser = new DefaultWebDriverWrapper(DefaultWebDriverWrapper.getDriverFromCapabilities(DesiredCapabilities.phantomjs()), new DumbOutfileSupport());
    }

    @After
    public void cleanUp() {
        if(browser != null) {
            browser.closeWindow();
            browser.getDriver().quit();
        }
    }

    @Test
    public void searchForSlickqaContainsSlickqaDotCom() throws Exception {
        browser.goTo(GoogleHomePage.URL);
        browser.waitFor(GoogleHomePage.class);
        browser.on(GoogleHomePage.class).searchFor("slickqa");
        browser.waitFor(GoogleResultsPage.class, 2);
        assertTrue("www.slickqa.com should be one of the results when searching for slickqa",
                   browser.on(GoogleResultsPage.class).searchResultExistsOnPage("www.slickqa.com"));
    }
}
