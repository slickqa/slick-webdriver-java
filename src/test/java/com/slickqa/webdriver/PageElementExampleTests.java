package com.slickqa.webdriver;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;


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
            //FirefoxDriverManager.getInstance().setup();
            //capability = DesiredCapabilities.firefox();
            ChromeDriverManager.getInstance().setup();
            capability = DesiredCapabilities.chrome();
        }
        browserWrapper = new DefaultWebDriverWrapper(DefaultWebDriverWrapper.getDriverFromCapabilities(capability), new TestOutputFileSupport());
    }

    @BeforeMethod
    public void setupMethod() {
        softAssert = new SoftAssert();
    }

    /**
     * The list of input page elements returned should be 8
     */
    @Test
    public void findListOfPageElementsTest() {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        int numberOfElementsFound = slickWebDriverExamplePage.listInputElements();
        softAssert.assertEquals(numberOfElementsFound, 8, "Incorrect number of input elements found on page");
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

    /**
     * Test get element relative to child element
     */
    @Test
    public void getElementRelativeToChildElement() {
        browserWrapper.goTo(testPage);
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        PageElement relativeChildElement = new PageElement("Relative Child Element", FindBy.id("descendent-label-element"));

        PageElement ascendantTable = new PageElement("parent element div tag", Relative.Decendent(relativeChildElement), "table");
        String verify2 = browserWrapper.getAttribute(ascendantTable, "data-qa");
        softAssert.assertEquals(verify2, "great-3-grand-parent", "Did not get element relative to child element");

        PageElement ascendantTbody = new PageElement(Relative.Decendent(relativeChildElement), "tbody");
        String verify3 = browserWrapper.getAttribute(ascendantTbody, "data-qa");
        softAssert.assertEquals(verify3, "great-2-parent", "Did not get element relative to child element");

        PageElement ascendantTr = new PageElement("parent element div tag", Relative.Decendent(relativeChildElement), "tr");
        String verify4 = browserWrapper.getAttribute(ascendantTr, "data-qa");
        softAssert.assertEquals(verify4, "great-grand-parent", "Did not get element relative to child element");

        PageElement ascendantTd = new PageElement("parent element div tag", Relative.Decendent(relativeChildElement), "td");
        String verify5 = browserWrapper.getAttribute(ascendantTd, "data-qa");
        softAssert.assertEquals(verify5, "grand-parent", "Did not get element relative to child element");

        PageElement ascendantA = new PageElement("parent element a tag", Relative.Decendent(relativeChildElement), "a");
        String verify6 = browserWrapper.getAttribute(ascendantA, "data-qa");
        softAssert.assertEquals(verify6, "parent", "Did not get element relative to child element");

        softAssert.assertAll();
    }

    /**
     * Test get element relative to child FindBy
     */
    @Test
    public void getElementRelativeToChildFindBy() {
        browserWrapper.goTo(testPage);

        PageElement ascendantTable = new PageElement("parent element div tag", Relative.Decendent(FindBy.id("descendent-label-element")), "table");
        String verify2 = browserWrapper.getAttribute(ascendantTable, "data-qa");
        softAssert.assertEquals(verify2, "great-3-grand-parent", "Did not get element relative to child element");

        PageElement ascendantTbody = new PageElement(Relative.Decendent(FindBy.id("descendent-label-element")), "tbody");
        String verify3 = browserWrapper.getAttribute(ascendantTbody, "data-qa");
        softAssert.assertEquals(verify3, "great-2-parent", "Did not get element relative to child element");

        PageElement ascendantTr = new PageElement("parent element div tag", Relative.Decendent(FindBy.id("descendent-label-element")), "tr");
        String verify4 = browserWrapper.getAttribute(ascendantTr, "data-qa");
        softAssert.assertEquals(verify4, "great-grand-parent", "Did not get element relative to child element");

        PageElement ascendantTd = new PageElement("parent element div tag", Relative.Decendent(FindBy.id("descendent-label-element")), "td");
        String verify5 = browserWrapper.getAttribute(ascendantTd, "data-qa");
        softAssert.assertEquals(verify5, "grand-parent", "Did not get element relative to child element");

        PageElement ascendantA = new PageElement("parent element a tag", Relative.Decendent(FindBy.id("descendent-label-element")), "a");
        String verify6 = browserWrapper.getAttribute(ascendantA, "data-qa");
        softAssert.assertEquals(verify6, "parent", "Did not get element relative to child element");

        softAssert.assertAll();
    }

    /**
     * Test get element relative to preceding sibling element
     */
    @Test
    public void getElementRelativeToPrecedBySiblingElement() {
        browserWrapper.goTo(testPage);
        PageElement precedingSiblingElement = new PageElement("Preceding Sibling Element", FindBy.text("This Is A Label"));

        PageElement precededByA = new PageElement("preceded by sibling a tag", Relative.PrecededBySibling(precedingSiblingElement), "a");
        String verify1 = browserWrapper.getAttribute(precededByA, "data-qa");
        softAssert.assertEquals(verify1, "following-sibling-element-a", "Did not get element relative to preceding sibling element");

        PageElement precededByInput = new PageElement(Relative.PrecededBySibling(precedingSiblingElement), "input");
        String verify2 = browserWrapper.getAttribute(precededByInput, "data-qa");
        softAssert.assertEquals(verify2, "following-sibling-element-input", "Did not get element relative to preceding sibling element");

        PageElement precededByLabel = new PageElement("preceded by sibling a tag", Relative.PrecededBySibling(precedingSiblingElement), "label");
        String verify3 = browserWrapper.getAttribute(precededByLabel, "data-qa");
        softAssert.assertEquals(verify3, "following-sibling-element-label", "Did not get element relative to preceding sibling element");

        softAssert.assertAll();
    }

    /**
     * Test get element relative to preceding sibling FindBy
     */
    @Test
    public void getElementRelativeToPrecededBySiblingFindBy() {
        browserWrapper.goTo(testPage);

        PageElement precededByA = new PageElement(Relative.PrecededBySibling(FindBy.text("This Is A Label")), "a");
        String verify1 = browserWrapper.getAttribute(precededByA, "data-qa");
        softAssert.assertEquals(verify1, "following-sibling-element-a", "Did not get element relative to preceding sibling element");

        PageElement precededByInput = new PageElement("preceded by sibling a tag", Relative.PrecededBySibling(FindBy.text("This Is A Label")), "input");
        String verify2 = browserWrapper.getAttribute(precededByInput, "data-qa");
        softAssert.assertEquals(verify2, "following-sibling-element-input", "Did not get element relative to preceding sibling element");

        PageElement precededByLabel = new PageElement("preceded by sibling a tag", Relative.PrecededBySibling(FindBy.text("This Is A Label")), "label");
        String verify3 = browserWrapper.getAttribute(precededByLabel, "data-qa");
        softAssert.assertEquals(verify3, "following-sibling-element-label", "Did not get element relative to preceding sibling element");

        softAssert.assertAll();
    }

    /**
     * Test get element relative to following sibling element
     */
    @Test
    public void getElementRelativeToFollowedBySiblingElement() {
        browserWrapper.goTo(testPage);
        PageElement followingSiblingElement = new PageElement("Following Sibling Element", FindBy.text("This Is A Label"));

        PageElement followingElementInput = new PageElement("following sibling input tag", Relative.FollowedBySibling(followingSiblingElement), "input");
        String verify1 = browserWrapper.getAttribute(followingElementInput, "data-qa");
        softAssert.assertEquals(verify1, "preceding-sibling-element-input", "Did not get element relative to preceding sibling element");

        PageElement followingElementLabel = new PageElement("following sibling label label", Relative.FollowedBySibling(followingSiblingElement), "label");
        String verify2 = browserWrapper.getAttribute(followingElementLabel, "data-qa");
        softAssert.assertEquals(verify2, "preceding-sibling-element-label", "Did not get element relative to preceding sibling element");

        PageElement followingElementA = new PageElement(Relative.FollowedBySibling(followingSiblingElement), "a");
        String verify3 = browserWrapper.getAttribute(followingElementA, "data-qa");
        softAssert.assertEquals(verify3, "preceding-sibling-element-a", "Did not get element relative to preceding sibling element");

        softAssert.assertAll();
    }

    /**
     * Test get element relative to following sibling element
     */
    @Test
    public void getElementRelativeToFollowedBySiblingFindBy() {
        browserWrapper.goTo(testPage);

        PageElement followingElementInput = new PageElement("following sibling input tag", Relative.FollowedBySibling(FindBy.text("This Is A Label")), "input");
        String verify1 = browserWrapper.getAttribute(followingElementInput, "data-qa");
        softAssert.assertEquals(verify1, "preceding-sibling-element-input", "Did not get element relative to preceding sibling element");

        PageElement followingElementLabel = new PageElement(Relative.FollowedBySibling(FindBy.text("This Is A Label")), "label");
        String verify2 = browserWrapper.getAttribute(followingElementLabel, "data-qa");
        softAssert.assertEquals(verify2, "preceding-sibling-element-label", "Did not get element relative to preceding sibling element");

        PageElement followingElementA = new PageElement("following sibling label a", Relative.FollowedBySibling(FindBy.text("This Is A Label")), "a");
        String verify3 = browserWrapper.getAttribute(followingElementA, "data-qa");
        softAssert.assertEquals(verify3, "preceding-sibling-element-a", "Did not get element relative to preceding sibling element");

        softAssert.assertAll();
    }

    /**
     * Test get element by text
     */
    @Test
    public void getElementByText() {
        browserWrapper.goTo(testPage);

        PageElement labelByText = new PageElement("label element by text", FindBy.text("Get Label Element By Text"));
        String verify1 = browserWrapper.getAttribute(labelByText, "data-qa");
        softAssert.assertEquals(verify1, "label-element-by-text", "Did not get element relative to preceding sibling element");

        PageElement divByText = new PageElement("div element by text", FindBy.text("Get Div Element By Text"));
        String verify2 = browserWrapper.getAttribute(divByText, "data-qa");
        softAssert.assertEquals(verify2, "div-element-by-text", "Did not get element relative to preceding sibling element");

        PageElement h3ByText = new PageElement("h3 element by text", FindBy.text("Get H3 Element By Text"));
        String verify3 = browserWrapper.getAttribute(h3ByText, "data-qa");
        softAssert.assertEquals(verify3, "h3-element-by-text", "Did not get element relative to preceding sibling element");

        softAssert.assertAll();
    }

    @Test
    public void getElementClasses() {
        browserWrapper.goTo(testPage);

        PageElement label = new PageElement(FindBy.id("label-with-class"));
        String text = browserWrapper.getText(label);
        String elementClasses = browserWrapper.getAttribute(label, "class");

        softAssert.assertEquals(text, "5-Year");
        softAssert.assertEquals(elementClasses, "selected");

        softAssert.assertAll();
    }


    @AfterSuite
    public void cleanup() {
        browserWrapper.getDriver().quit();
    }
}