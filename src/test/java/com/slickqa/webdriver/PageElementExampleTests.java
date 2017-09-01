package com.slickqa.webdriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.ChromeDriverManager;

/**
 * Created by slambson on 8/29/17.
 */
public class PageElementExampleTests {

    private SoftAssert softAssert = new SoftAssert();
    protected DefaultWebDriverWrapper browserWrapper;

    @BeforeClass
    public void setupBrowser() {
        ChromeDriverManager.getInstance().setup();
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        browserWrapper = new DefaultWebDriverWrapper(DefaultWebDriverWrapper.getDriverFromCapabilities(capability), new TestOutputFileSupport());
    }

    /**
     * The list of page elements returned should be 8
     */
    @Test
    public void findListOfPageElementsTest() {
        browserWrapper.goTo("https://www.google.com/");
        ExamplePage examplePage = new ExamplePage(browserWrapper);
        int numberOfElementsFound = examplePage.listInputElements();
        softAssert.assertEquals(numberOfElementsFound, 8, "Incorrect number of input elements found on page");
        softAssert.assertAll();
    }

//    @Test
//    public void clickElementByAttributeValue() {
//        browserWrapper.goTo("https://github.com/slickqa/slick-webdriver-java");
//        GitHubPage gitHubPage = new GitHubPage(browserWrapper);
//        gitHubPage.clickElementByAttributeValue();
//
//        softAssert.assertTrue(browserWrapper.getPageUrl().equals("https://github.com/slickqa"));
//        softAssert.assertAll();
//    }

            // in iframe
    /**
     * Test that clicking on an element within an iFrame element works.
     *
     * There is no assertion because if the In.Frame does not work then we will not be able to locate the element to click on it
     */
    @Test
    public void clickElementInIFrameElement() {
        browserWrapper.goTo("http://www.dwuser.com/education/content/the-magical-iframe-tag-an-introduction/");
        DemoiFramePage demoiFramePage = new DemoiFramePage(browserWrapper);
        demoiFramePage.clickForecastLink();
    }

        // ****** NOT LIMITING TO THE IFRAME
//    @Test
//    public void findListOfPageElementsInIFrameElement() {
//        browserWrapper.goTo("http://www.dwuser.com/education/content/the-magical-iframe-tag-an-introduction/");
//        DemoiFramePage demoiFramePage = new DemoiFramePage(browserWrapper);
//        demoiFramePage.listInputElementsInIFrameElement();
//    }

            // in parent
    /**
     * Test that clicking on an element within a Parent element works
     *
     * If we don't click on the correct a tag within the specified parent element then we will not end up on the correct page
     */
    @Test
    public void clickElementInParentElement() {
        browserWrapper.goTo("https://github.com/slickqa/slick-webdriver-java");
        GitHubPage gitHubPage = new GitHubPage(browserWrapper);
        gitHubPage.clickExamplesDirectoryLink();

        softAssert.assertTrue(browserWrapper.getPageUrl().equals("https://github.com/slickqa/slick-webdriver-java/tree/master/example"));
        softAssert.assertAll();
    }

    /**
     * The list of page elements returned should be 3.  If it doesn't look specifically in the Parent Element it will return more than 3.
     *
     */
    @Test
    public void findListOfPageElementsInAParentElementTest() {
        browserWrapper.goTo("http://www.dwuser.com/education/content/the-magical-iframe-tag-an-introduction//");
        DemoiFramePage demoiFramePage = new DemoiFramePage(browserWrapper);
        int numberOfElementsFound = demoiFramePage.listInputElementsInParentElement();

        softAssert.assertEquals(numberOfElementsFound, 3, "Incorrect number of input elements found on page");
        softAssert.assertAll();
    }
}