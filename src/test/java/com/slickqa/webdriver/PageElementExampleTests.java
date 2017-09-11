package com.slickqa.webdriver;

import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by slambson on 8/29/17.
 */
public class PageElementExampleTests {

    protected SoftAssert softAssert;
    protected DefaultWebDriverWrapper browserWrapper;
    private String testPage = "file:///Users/slambson/slick-webdriver-java/src/test/java/com/slickqa/webdriver/examplePage.html";

    @BeforeClass
    public void setupBrowser() {
        DesiredCapabilities capability;
        if (System.getProperty("headlessBrowser", "false").equals("true")) {
            PhantomJsDriverManager.getInstance().setup();
            capability = DesiredCapabilities.phantomjs();
        }
        else {
            FirefoxDriverManager.getInstance().setup();
            capability = DesiredCapabilities.firefox();
        }
        browserWrapper = new DefaultWebDriverWrapper(DefaultWebDriverWrapper.getDriverFromCapabilities(capability), new TestOutputFileSupport());
    }

    @BeforeMethod
    public void setupMethod() {
        softAssert = new SoftAssert();
    }

    /**
     * The list of input page elements returned should be 4
     */
    @Test
    public void findListOfPageElementsTest() {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        int numberOfElementsFound = slickWebDriverExamplePage.listInputElements();
        softAssert.assertEquals(numberOfElementsFound, 4, "Incorrect number of input elements found on page");
        softAssert.assertAll();
    }

    @Test
    public void getElementByAltValue() {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        String imageSrc = slickWebDriverExamplePage.getElementSrcByAlt();
        softAssert.assertTrue(imageSrc.endsWith("slickFancy.gif"));
        softAssert.assertAll();
    }

    @Test
    public void getElementByAttributeValue() {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        String imageSrc = slickWebDriverExamplePage.getElementSrcByAttributeValue();
        softAssert.assertTrue(imageSrc.endsWith("slickFancy.gif"));
        softAssert.assertAll();
    }

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
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        slickWebDriverExamplePage.clickOnElementInParentElement();
        softAssert.assertEquals(browserWrapper.getPageUrl(), "http://www.slickqa.com/features.html", "We did not end up on the correct end page so we must have clicked on the wrong link");
        softAssert.assertAll();
    }

    /**
     * Test that clicking on an element within a Parent where the Parent is defined with a FindBy works
     *
     * If we don't click on the correct a tag within the specified parent element then we will not end up on the correct page
     */
    @Test
    public void clickElementInParentElementWithFindBy() {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        slickWebDriverExamplePage.clickOnElementInParentElementWithFindBy();
        softAssert.assertEquals(browserWrapper.getPageUrl(), "http://www.slickqa.com/features.html", "We did not end up on the correct end page so we must have clicked on the wrong link");
        softAssert.assertAll();
    }

    /**
     * The list of page elements returned should be 10.  If it doesn't look specifically in the Parent Element it will return more than 10.
     *
     */
    @Test
    public void findListOfPageElementsInAParentElementTest() {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        int numberOfElementsFound = slickWebDriverExamplePage.listInputElementsInParentElement();
        softAssert.assertEquals(numberOfElementsFound, 10, "Incorrect number of input elements found on page");
        softAssert.assertAll();
    }

    /**
     * The list of page elements returned should be 10.  If it doesn't look specifically in the Parent Element it will return more than 10.
     *
     */
    @Test
    public void getPageElementByIndexFromListOfPageElementsInAParentElementTest() {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        String linkText = slickWebDriverExamplePage.getElementByIndexFromParentElement();
        softAssert.assertEquals(linkText, "Slick Webdriver Project", "Incorrect element by index found in parent element");
        softAssert.assertAll();
    }

    /**
     * The list of page elements returned should be 10.  If it doesn't look specifically in the Parent Element it will return more than 10.
     *
     */
    @Test
    public void findListOfPageElementsInAParentElementFindByTest() {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        int numberOfElementsFound = slickWebDriverExamplePage.listInputElementsInParentElementFindBy();
        softAssert.assertEquals(numberOfElementsFound, 10, "Incorrect number of input elements found on page");
        softAssert.assertAll();
    }

    /**
     * Test sliding a range input element
     */
    @Test
    public void slideRangeInputElement() throws Exception {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        int value1 = slickWebDriverExamplePage.getRangeInputValue();
        System.out.println("value1: " + value1);
        slickWebDriverExamplePage.slideRangeInputElement(5);
        int value2 = slickWebDriverExamplePage.getRangeInputValue();
        System.out.println("value2: " + value2);
        softAssert.assertTrue(value2 > value1, "Value did not increase sliding to the right");

        slickWebDriverExamplePage.slideRangeInputElement(-10);
        int value3 = slickWebDriverExamplePage.getRangeInputValue();
        System.out.println("value3: " + value3);
        softAssert.assertTrue(value3 < value2, "Value did not decrease sliding to the right");

        slickWebDriverExamplePage.clickRangeInputElement(20, 0);
        int value4 = slickWebDriverExamplePage.getRangeInputValue();
        System.out.println("value4: " + value4);
        softAssert.assertTrue(value4 > value3, "Value did not increase clicking to the right");

        slickWebDriverExamplePage.clickRangeInputElement(-40, 0);
        int value5 = slickWebDriverExamplePage.getRangeInputValue();
        System.out.println("value5: " + value5);
        softAssert.assertTrue(value5 < value4, "Value did not decrease clicking to the right");

        softAssert.assertAll();
    }

    /**
     * Test wait for element
     */
    @Test
    public void testWaitForElement() {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        slickWebDriverExamplePage.waitDefaultTimeForElementThatExists();
        slickWebDriverExamplePage.waitPassedInTimeForElementThatExists(5);
        boolean errorOutForNonExistingElementDefaultTimeout = false;
        try {
            slickWebDriverExamplePage.waitDefaultTimeForElementThatDoesNotExist();
        } catch (NoSuchElementException e) {
            errorOutForNonExistingElementDefaultTimeout = true;
        }
        softAssert.assertTrue(errorOutForNonExistingElementDefaultTimeout, "Did not get a timeout waiting the default timeout for an element that does not exist");

        boolean errorOutForNonExistingElementPassedInTimeout = false;
        try {
            slickWebDriverExamplePage.waitPassedInTimeForElementThatDoesNotExists(5);
        } catch (NoSuchElementException e) {
            errorOutForNonExistingElementPassedInTimeout = true;
        }
        softAssert.assertTrue(errorOutForNonExistingElementPassedInTimeout, "Did not get a timeout waiting the passed in timeout for an element that does not exist");

        softAssert.assertAll();
    }

    @AfterSuite
    public void cleanup() {
        browserWrapper.getDriver().quit();
    }
}