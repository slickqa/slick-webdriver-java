package com.slickqa.webdriver;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import java.io.File;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Created by slambson on 8/29/17.
 */
public class PageElementExampleTests {

    protected SoftAssert softAssert;
    protected DefaultWebDriverWrapper browserWrapper;

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

    private String getExamplePagePath() {
        String inputFilePath = System.getProperty("user.dir") + "/resources/examplePage.html";
        String url = new File(inputFilePath).getAbsolutePath();
        return "file:///"+url;
    }

    @Test
    public void loadTestPage() {
        browserWrapper.goTo(getExamplePagePath());
        softAssert.assertEquals(browserWrapper.getPageTitle(), "SlickQA: WebDriver Wrapper Test Page");

        softAssert.assertAll();
    }

    /**
     * The list of input page elements returned should be 8
     */
    @Test
    public void findListOfPageElementsTest() {
        browserWrapper.goTo(getExamplePagePath());
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        int numberOfElementsFound = slickWebDriverExamplePage.listInputElements();
        softAssert.assertEquals(numberOfElementsFound, 16, "Incorrect number of input elements found on page");
        softAssert.assertAll();
    }

    @Test
    public void getElementByAltValue() {
        browserWrapper.goTo(getExamplePagePath());
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        String imageSrc = slickWebDriverExamplePage.getElementSrcByAlt();
        softAssert.assertTrue(imageSrc.endsWith("slickFancy.gif"));
        softAssert.assertAll();
    }

    @Test
    public void getElementByAttributeValue() {
        browserWrapper.goTo(getExamplePagePath());
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
        browserWrapper.goTo(getExamplePagePath());
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
        browserWrapper.goTo(getExamplePagePath());
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
        browserWrapper.goTo(getExamplePagePath());
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
        browserWrapper.goTo(getExamplePagePath());
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
        browserWrapper.goTo(getExamplePagePath());
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
        browserWrapper.goTo(getExamplePagePath());
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
        browserWrapper.goTo(getExamplePagePath());
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
        browserWrapper.goTo(getExamplePagePath());
        SlickWebDriverExamplePage slickWebDriverExamplePage = new SlickWebDriverExamplePage(browserWrapper);
        PageElement relativeChildElement = new PageElement("Relative Child Element", FindBy.id("descendant-label-element"));

        PageElement ascendantTable = new PageElement("parent element div tag", Relative.Descendant(relativeChildElement), "table");
        String verify2 = browserWrapper.getAttribute(ascendantTable, "data-qa");
        softAssert.assertEquals(verify2, "great-3-grand-parent", "Did not get element relative to child element");

        PageElement ascendantTbody = new PageElement(Relative.Descendant(relativeChildElement), "tbody");
        String verify3 = browserWrapper.getAttribute(ascendantTbody, "data-qa");
        softAssert.assertEquals(verify3, "great-2-parent", "Did not get element relative to child element");

        PageElement ascendantTr = new PageElement("parent element div tag", Relative.Descendant(relativeChildElement), "tr");
        String verify4 = browserWrapper.getAttribute(ascendantTr, "data-qa");
        softAssert.assertEquals(verify4, "great-grand-parent", "Did not get element relative to child element");

        PageElement ascendantTd = new PageElement("parent element div tag", Relative.Descendant(relativeChildElement), "td");
        String verify5 = browserWrapper.getAttribute(ascendantTd, "data-qa");
        softAssert.assertEquals(verify5, "grand-parent", "Did not get element relative to child element");

        PageElement ascendantA = new PageElement("parent element a tag", Relative.Descendant(relativeChildElement), "a");
        String verify6 = browserWrapper.getAttribute(ascendantA, "data-qa");
        softAssert.assertEquals(verify6, "parent", "Did not get element relative to child element");

        softAssert.assertAll();
    }

    /**
     * Test get element relative to child FindBy
     */
    @Test
    public void getElementRelativeToChildFindBy() {
        browserWrapper.goTo(getExamplePagePath());

        PageElement ascendantTable = new PageElement("parent element div tag", Relative.Descendant(FindBy.id("descendant-label-element")), "table");
        String verify2 = browserWrapper.getAttribute(ascendantTable, "data-qa");
        softAssert.assertEquals(verify2, "great-3-grand-parent", "Did not get element relative to child element");

        PageElement ascendantTbody = new PageElement(Relative.Descendant(FindBy.id("descendant-label-element")), "tbody");
        String verify3 = browserWrapper.getAttribute(ascendantTbody, "data-qa");
        softAssert.assertEquals(verify3, "great-2-parent", "Did not get element relative to child element");

        PageElement ascendantTr = new PageElement("parent element div tag", Relative.Descendant(FindBy.id("descendant-label-element")), "tr");
        String verify4 = browserWrapper.getAttribute(ascendantTr, "data-qa");
        softAssert.assertEquals(verify4, "great-grand-parent", "Did not get element relative to child element");

        PageElement ascendantTd = new PageElement("parent element div tag", Relative.Descendant(FindBy.id("descendant-label-element")), "td");
        String verify5 = browserWrapper.getAttribute(ascendantTd, "data-qa");
        softAssert.assertEquals(verify5, "grand-parent", "Did not get element relative to child element");

        PageElement ascendantA = new PageElement("parent element a tag", Relative.Descendant(FindBy.id("descendant-label-element")), "a");
        String verify6 = browserWrapper.getAttribute(ascendantA, "data-qa");
        softAssert.assertEquals(verify6, "parent", "Did not get element relative to child element");

        softAssert.assertAll();
    }

    /**
     * Test get element relative to preceding sibling element
     */
    @Test
    public void getElementRelativeToPrecedBySiblingElement() {
        browserWrapper.goTo(getExamplePagePath());
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
        browserWrapper.goTo(getExamplePagePath());

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
        browserWrapper.goTo(getExamplePagePath());
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
        browserWrapper.goTo(getExamplePagePath());

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
     * Test get element relative to following sibling element
     */
    @Test
    public void getElementRelativeToFollowedBySiblingFindByInParent() {
        browserWrapper.goTo(getExamplePagePath());

        PageElement fixedRateContainer = new PageElement(FindBy.id("product-fixed-container"));

        PageElement labelFixedFiveYear = new PageElement(In.ParentElement(fixedRateContainer), FindBy.text("5-Year"));
        PageElement checkboxFixedFiveYear = new PageElement("Fixed 5 Year Checkbox", Relative.FollowedBySibling(labelFixedFiveYear), "input");
        String verify5yearFixedLabel = browserWrapper.getAttribute(labelFixedFiveYear, "data-qa");
        softAssert.assertEquals(verify5yearFixedLabel, "label-5-year-fixed", "Did not get correct element");
        String verify5yearFixedCheckbox = browserWrapper.getAttribute(checkboxFixedFiveYear, "data-qa");
        softAssert.assertEquals(verify5yearFixedCheckbox, "checkbox-5-year-fixed", "Did not get correct element");

        PageElement labelFixedSevenYear = new PageElement(In.ParentElement(fixedRateContainer), FindBy.text("7-Year"));
        PageElement checkboxFixedSevenYear = new PageElement("Fixed 7 Year Checkbox", Relative.FollowedBySibling(labelFixedSevenYear), "input");
        String verify7yearFixedLabel = browserWrapper.getAttribute(labelFixedSevenYear, "data-qa");
        softAssert.assertEquals(verify7yearFixedLabel, "label-7-year-fixed", "Did not get correct element");
        String verify7yearFixedCheckbox = browserWrapper.getAttribute(checkboxFixedSevenYear, "data-qa");
        softAssert.assertEquals(verify7yearFixedCheckbox, "checkbox-7-year-fixed", "Did not get correct element");

        PageElement variableRateContainer = new PageElement(FindBy.id("product-variable-container"));

        PageElement labelVariableFiveYear = new PageElement(In.ParentElement(variableRateContainer), FindBy.text("5-Year"));
        PageElement checkboxVariableFiveYear = new PageElement("Variable 5 Year Checkbox", Relative.FollowedBySibling(labelVariableFiveYear), "input");
        String verify5yearVariableLabel = browserWrapper.getAttribute(labelVariableFiveYear, "data-qa");
        softAssert.assertEquals(verify5yearVariableLabel, "label-5-year-variable", "Did not get correct element");
        String verify5yearVariableCheckbox = browserWrapper.getAttribute(checkboxVariableFiveYear, "data-qa");
        softAssert.assertEquals(verify5yearVariableCheckbox, "checkbox-5-year-variable", "Did not get correct element");

        PageElement labelVariableSevenYear = new PageElement(In.ParentElement(variableRateContainer), FindBy.text("7-Year"));
        PageElement checkboxVariableSevenYear = new PageElement("Fixed 7 Year Checkbox", Relative.FollowedBySibling(labelVariableSevenYear), "input");
        String verify7yearVariableLabel = browserWrapper.getAttribute(labelVariableSevenYear, "data-qa");
        softAssert.assertEquals(verify7yearVariableLabel, "label-7-year-variable", "Did not get correct element");
        String verify7yearVariableCheckbox = browserWrapper.getAttribute(checkboxVariableSevenYear, "data-qa");
        softAssert.assertEquals(verify7yearVariableCheckbox, "checkbox-7-year-variable", "Did not get correct element");

        softAssert.assertAll();
    }

//    /**
//     * Test get element relative to following sibling element
//     */
//    @Test
//    public void getElementRelativeToFollowedBySiblingFindByInParentByXpath() {
//        browserWrapper.goTo(testPage);
//
//        PageElement fixedRateContainer = new PageElement(FindBy.id("product-fixed-container"));
//
//        PageElement labelFixedFiveYear = new PageElement(In.ParentElement(fixedRateContainer), FindBy.xpath(".label[text()='5-Year']"));
//        PageElement checkboxFixedFiveYear = new PageElement("Fixed 5 Year Checkbox", Relative.FollowedBySibling(labelFixedFiveYear), "input");
//        String verify5yearFixedLabel = browserWrapper.getAttribute(labelFixedFiveYear, "data-qa");
//        softAssert.assertEquals(verify5yearFixedLabel, "label-5-year-fixed", "Did not get correct element");
//        String verify5yearFixedCheckbox = browserWrapper.getAttribute(checkboxFixedFiveYear, "data-qa");
//        softAssert.assertEquals(verify5yearFixedCheckbox, "checkbox-5-year-fixed", "Did not get correct element");
//
//        PageElement labelFixedSevenYear = new PageElement(In.ParentElement(fixedRateContainer), FindBy.xpath(".label[text()='7-Year']"));
//        PageElement checkboxFixedSevenYear = new PageElement("Fixed 7 Year Checkbox", Relative.FollowedBySibling(labelFixedSevenYear), "input");
//        String verify7yearFixedLabel = browserWrapper.getAttribute(labelFixedSevenYear, "data-qa");
//        softAssert.assertEquals(verify7yearFixedLabel, "label-7-year-fixed", "Did not get correct element");
//        String verify7yearFixedCheckbox = browserWrapper.getAttribute(checkboxFixedSevenYear, "data-qa");
//        softAssert.assertEquals(verify7yearFixedCheckbox, "checkbox-7-year-fixed", "Did not get correct element");
//
//        PageElement variableRateContainer = new PageElement(FindBy.id("product-variable-container"));
//
//        PageElement labelVariableFiveYear = new PageElement(In.ParentElement(variableRateContainer), FindBy.xpath(".label[text()='5-Year']"));
//        PageElement checkboxVariableFiveYear = new PageElement("Variable 5 Year Checkbox", Relative.FollowedBySibling(labelVariableFiveYear), "input");
//        String verify5yearVariableLabel = browserWrapper.getAttribute(labelVariableFiveYear, "data-qa");
//        softAssert.assertEquals(verify5yearVariableLabel, "label-5-year-variable", "Did not get correct element");
//        String verify5yearVariableCheckbox = browserWrapper.getAttribute(checkboxVariableFiveYear, "data-qa");
//        softAssert.assertEquals(verify5yearVariableCheckbox, "checkbox-5-year-variable", "Did not get correct element");
//
//        PageElement labelVariableSevenYear = new PageElement(In.ParentElement(variableRateContainer), FindBy.xpath(".label[text()='7-Year']"));
//        PageElement checkboxVariableSevenYear = new PageElement("Fixed 7 Year Checkbox", Relative.FollowedBySibling(labelVariableSevenYear), "input");
//        String verify7yearVariableLabel = browserWrapper.getAttribute(labelVariableSevenYear, "data-qa");
//        softAssert.assertEquals(verify7yearVariableLabel, "label-7-year-variable", "Did not get correct element");
//        String verify7yearVariableCheckbox = browserWrapper.getAttribute(checkboxVariableSevenYear, "data-qa");
//        softAssert.assertEquals(verify7yearVariableCheckbox, "checkbox-7-year-variable", "Did not get correct element");
//
//        softAssert.assertAll();
//    }

        /**
         * Test get element by text
         */
    @Test
    public void getElementByText() {
        browserWrapper.goTo(getExamplePagePath());

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
        browserWrapper.goTo(getExamplePagePath());

        PageElement label = new PageElement(FindBy.id("label-with-class"));
        String text = browserWrapper.getText(label);
        String elementClasses = browserWrapper.getAttribute(label, "class");

        softAssert.assertEquals(text, "5-Year");
        softAssert.assertEquals(elementClasses, "selected");

        softAssert.assertAll();
    }

    @Test
    public void waitForValue() {
        boolean timedOutAsExpectedWaitingForValueEmpty;
        boolean timedOutAsExpectedWaitingForValueNotEmpty;
        boolean timedOutWaitingForValueEmpty;
        boolean timedOutWaitingForValueNotEmpty;

        browserWrapper.goTo(getExamplePagePath());
        PageElement inputBox = new PageElement("input box", FindBy.id("yo"));
        try {
            browserWrapper.waitForValueNotEmpty(inputBox, 2);
            timedOutAsExpectedWaitingForValueNotEmpty = false;
        } catch (TimeoutError e) {
            timedOutAsExpectedWaitingForValueNotEmpty = true;
        }
        softAssert.assertTrue(timedOutAsExpectedWaitingForValueNotEmpty, "Did not get a timeout waiting for the value attribute to not be empty");

        try {
            browserWrapper.waitForValueEmpty(inputBox, 2);
            timedOutWaitingForValueEmpty = false;
        } catch (TimeoutError e) {
            timedOutWaitingForValueEmpty = true;
        }
        softAssert.assertFalse(timedOutWaitingForValueEmpty, "Timed out waiting for value attribute to be empty");

        browserWrapper.type(inputBox, "hey there, how is it going?");

        try {
            browserWrapper.waitForValueEmpty(inputBox, 2);
            timedOutAsExpectedWaitingForValueEmpty = false;
        } catch (TimeoutError e) {
            timedOutAsExpectedWaitingForValueEmpty = true;
        }
        softAssert.assertTrue(timedOutAsExpectedWaitingForValueEmpty, "Did not get a timeout waiting for the value attribute to be empty");

        try {
            browserWrapper.waitForValueNotEmpty(inputBox, 2);
            timedOutWaitingForValueNotEmpty = false;
        } catch (TimeoutError e) {
            timedOutWaitingForValueNotEmpty = true;
        }
        softAssert.assertFalse(timedOutWaitingForValueNotEmpty, "Timed out waiting for value attribute to not be empty");

        softAssert.assertAll();
    }

    @Test
    public void waitForText() {
        boolean timedOutAsExpectedWaitingForTextEmpty;
        boolean timedOutWaitingForTextNotEmpty;
        boolean timedOutAsExpectedWaitingForTextNotEmpty;
        boolean timedOutWaitingForTextEmpty;

        browserWrapper.goTo(getExamplePagePath());

        PageElement whatIsSlick = new PageElement("What is Slick", FindBy.id("what-is-slick"));

        try {
            browserWrapper.waitForTextEmpty(whatIsSlick, 2);
            timedOutAsExpectedWaitingForTextEmpty = false;
        } catch (TimeoutError e) {
            timedOutAsExpectedWaitingForTextEmpty = true;
        }
        softAssert.assertTrue(timedOutAsExpectedWaitingForTextEmpty, "Did not timeout waiting for the text to be empty");

        try {
            browserWrapper.waitForTextNotEmpty(whatIsSlick, 2);
            timedOutWaitingForTextNotEmpty = false;
        } catch (TimeoutError e) {
            timedOutWaitingForTextNotEmpty = true;
        }
        softAssert.assertFalse(timedOutWaitingForTextNotEmpty, "Timed out waiting for text to not be empty");

        PageElement inputBox = new PageElement("input box", FindBy.id("yo"));

        try {
            browserWrapper.waitForTextNotEmpty(inputBox, 2);
            timedOutAsExpectedWaitingForTextNotEmpty = false;
        } catch (TimeoutError e) {
            timedOutAsExpectedWaitingForTextNotEmpty = true;
        }
        softAssert.assertTrue(timedOutAsExpectedWaitingForTextNotEmpty, "Did not timeout wiating for the text to not be empty");

        try {
            browserWrapper.waitForTextEmpty(inputBox, 2);
            timedOutWaitingForTextEmpty = false;
        } catch (TimeoutError e) {
            timedOutWaitingForTextEmpty = true;
        }
        softAssert.assertFalse(timedOutWaitingForTextEmpty, "Timed out waiting for the text to be empty");

        softAssert.assertAll();
    }

    @Test
    public void testAcceptAlert() {
        browserWrapper.goTo(getExamplePagePath());

        PageElement alertButton = new PageElement("alert box", FindBy.id("alert-button"));

        boolean gotExceptionAcceptingAlertWhenDoesNotExist;
        try {
            browserWrapper.acceptAlert();
            gotExceptionAcceptingAlertWhenDoesNotExist = false;
        } catch (Exception e) {
            gotExceptionAcceptingAlertWhenDoesNotExist = true;
        }
        softAssert.assertTrue(gotExceptionAcceptingAlertWhenDoesNotExist, "Did not get an exception accepting an alert that does not exist");

        browserWrapper.click(alertButton);
        boolean alertExists = browserWrapper.alertPresent();
        softAssert.assertTrue(alertExists, "Alert did not exist");

        browserWrapper.acceptAlert();
        alertExists = browserWrapper.alertPresent();
        softAssert.assertFalse(alertExists, "Alert still existed");

        browserWrapper.click(alertButton);
        boolean alertExistsWithTimeout = browserWrapper.alertPresent(2);
        softAssert.assertTrue(alertExistsWithTimeout, "Alert did not exist");

        browserWrapper.acceptAlert();
        alertExistsWithTimeout = browserWrapper.alertPresent(2);
        softAssert.assertFalse(alertExistsWithTimeout, "Alert still existed");

        softAssert.assertAll();
    }

    @Test
    public void testWaitForAlert() {
        browserWrapper.goTo(getExamplePagePath());

        boolean timeoutAsExpectedWaitingForAlertPresent;
        boolean timeoutWaitingForAlertPresent;
        boolean timeoutAsExpectedWaitingForNoAlertPresent;
        boolean timeoutWaitingForNoAlertPresent;

        PageElement alertButton = new PageElement("alert box", FindBy.id("alert-button"));

        try {
            browserWrapper.waitForAlertPresent(2);
            timeoutAsExpectedWaitingForAlertPresent = false;
        } catch(TimeoutError e) {
            timeoutAsExpectedWaitingForAlertPresent = true;
        }
        softAssert.assertTrue(timeoutAsExpectedWaitingForAlertPresent, "Did not timeout as expected waiting for an alert to be present");

        browserWrapper.click(alertButton);

        try {
            browserWrapper.waitForAlertPresent(2);
            timeoutWaitingForAlertPresent = false;
        } catch (Exception e){
            timeoutWaitingForAlertPresent = true;
        }
        softAssert.assertFalse(timeoutWaitingForAlertPresent, "Timed out waiting for an alert to be present");

        try {
            browserWrapper.waitForNoAlertPresent(2);
            timeoutAsExpectedWaitingForNoAlertPresent = false;
        } catch (TimeoutError e) {
            timeoutAsExpectedWaitingForNoAlertPresent = true;
        }
        softAssert.assertTrue(timeoutAsExpectedWaitingForNoAlertPresent, "Did not timeout as expected waiting for no alert to be present");

        browserWrapper.acceptAlert();

        try {
            browserWrapper.waitForNoAlertPresent(2);
            timeoutAsExpectedWaitingForNoAlertPresent = false;
        } catch (Exception e) {
            timeoutAsExpectedWaitingForNoAlertPresent = true;
        }
        softAssert.assertFalse(timeoutAsExpectedWaitingForNoAlertPresent, "Timed out waiting for no alert to be present");

        softAssert.assertAll();
    }

    @Test
    public void testCookies() {
        browserWrapper.goTo("http://www.google.com");
        Set<Cookie> cookies = browserWrapper.getAllCookies();
        Assert.assertTrue(cookies.size() > 0, "Did not start out with any cookies, cannot continue test");
        browserWrapper.deleteAllCookies();
        cookies = browserWrapper.getAllCookies();
        softAssert.assertTrue(cookies.size() == 0, "Did not successfully delete all cookies");

        String cookieName = "C";
        String cookieValue = "Is for cookie";
        Cookie c = new Cookie(cookieName, cookieValue);
        browserWrapper.addCookie(c);
        cookies = browserWrapper.getAllCookies();
        Assert.assertTrue(cookies.size() == 1, "Did not successfully add a cookie, cannot continue test");

        String valueOfCookie = browserWrapper.getValueOfCookieNamed(cookieName);
        softAssert.assertEquals(valueOfCookie, cookieValue, "Cookie value returned was not correct");

        Cookie returnedCooked = browserWrapper.getCookieNamed(cookieName);
        softAssert.assertEquals(returnedCooked.getName(), cookieName, "Returned cookie did not have correct name");
        softAssert.assertEquals(returnedCooked.getValue(), cookieValue,"Returned cookie did not have correct value");

        String cookie2Name = "C2";
        String cookie2Value = "C2 is not for cookie";
        Cookie c2 = new Cookie(cookie2Name, cookie2Value);
        browserWrapper.addCookie(c2);
        cookies = browserWrapper.getAllCookies();
        Assert.assertTrue(cookies.size() == 2, "Did not successfully add a 2nd cookie, cannot continue test");

        browserWrapper.deleteCookieNamed(cookieName);
        cookies = browserWrapper.getAllCookies();
        softAssert.assertTrue(cookies.size() == 1, "Did not successfully delete a cookie by name");
        softAssert.assertEquals(cookies.iterator().next().getName(), cookie2Name, "We deleted the wrong cookie");

        softAssert.assertAll();
    }

    @Test
    public void testSelectByValue() {
        browserWrapper.goTo(getExamplePagePath());
        ExamplePage examplePage = new ExamplePage(browserWrapper);
        examplePage.selectByValue("mercedes");
        String option = examplePage.getSelectedOption();
        softAssert.assertEquals(option, "Mercedes");

        softAssert.assertAll();
    }

    @Test
    public void testSelectByIndex() {
        browserWrapper.goTo(getExamplePagePath());
        ExamplePage examplePage = new ExamplePage(browserWrapper);
        examplePage.selectByIndex(1);
        String option = examplePage.getSelectedOption();
        softAssert.assertEquals(option, "Saab");

        softAssert.assertAll();
    }

    @Test
    public void testSelectByText() {
        browserWrapper.goTo(getExamplePagePath());
        ExamplePage examplePage = new ExamplePage(browserWrapper);
        examplePage.selectByText("Audi");
        String option = examplePage.getSelectedOption();
        softAssert.assertEquals(option, "Audi");

        softAssert.assertAll();
    }

    @Test
    public void typeKey() {
        browserWrapper.goTo(getExamplePagePath());
        PageElement inputBox = new PageElement("input box", FindBy.id("yo"));

        browserWrapper.type(inputBox, "Test Backspace Key");
        String fieldText = browserWrapper.getAttribute(inputBox, "value");
        Assert.assertEquals(fieldText, "Test Backspace Key", "Text not equal to start, can't continue test");

        browserWrapper.type(inputBox, Keys.BACK_SPACE);
        fieldText = browserWrapper.getAttribute(inputBox, "value");
        Assert.assertEquals(fieldText, "Test Backspace Ke", "Text not correct after typing Backspace, it must not have worked correctly!");
    }

    @Test
    public void typeListOfKey() {
        browserWrapper.goTo(getExamplePagePath());
        PageElement inputBox = new PageElement("input box", FindBy.id("yo"));

        browserWrapper.type(inputBox, "Test Backspace Keys");
        String fieldText = browserWrapper.getAttribute(inputBox, "value");
        Assert.assertEquals(fieldText, "Test Backspace Keys", "Text not equal to start, can't continue test");

        List<Keys> keys = new ArrayList<Keys>();
        keys.add(Keys.BACK_SPACE);
        keys.add(Keys.BACK_SPACE);
        keys.add(Keys.BACK_SPACE);
        browserWrapper.type(inputBox, keys);
        fieldText = browserWrapper.getAttribute(inputBox, "value");
        Assert.assertEquals(fieldText, "Test Backspace K", "Text not correct after typing Backspace, it must not have worked correctly!");
    }

    @Test
    public void OrWith2Finders() {
        browserWrapper.goTo(getExamplePagePath());
        // reset the button
        browserWrapper.click(new PageElement("Reset Or finder button", FindBy.id("or-find-reset")));
        PageElement button = new PageElement("Or finder button, but found by ID", FindBy.id("or-find-button"));
        PageElement orFinderElement = new PageElement("Button with a changing class", FindBy.Or(FindBy.className("one"), FindBy.className("two")));
        Assert.assertTrue(browserWrapper.exists(orFinderElement), "Element should be found by class one");
        browserWrapper.click(button);
        Assert.assertTrue(browserWrapper.exists(orFinderElement), "Element should be found by class two");
        Assert.assertEquals(browserWrapper.getAttribute(orFinderElement, "class"), "two");
    }

    @Test
    public void OrWith3Finders() {
        browserWrapper.goTo(getExamplePagePath());
        browserWrapper.click(new PageElement("Reset Or finder button", FindBy.id("or-find-reset")));

        PageElement button = new PageElement("Or finder button, but found by ID", FindBy.id("or-find-button"));
        PageElement orFinderElement = new PageElement("Button with a changing class", FindBy.Or(FindBy.className("one"), FindBy.className("two"), FindBy.className("three")));
        Assert.assertTrue(browserWrapper.exists(orFinderElement), "Element should be found by class one");
        browserWrapper.click(button);
        Assert.assertTrue(browserWrapper.exists(orFinderElement), "Element should be found by class two");
        Assert.assertEquals(browserWrapper.getAttribute(orFinderElement, "class"), "two");
        browserWrapper.click(button);
        Assert.assertTrue(browserWrapper.exists(orFinderElement), "Element should be found by class three");
        Assert.assertEquals(browserWrapper.getAttribute(orFinderElement, "class"), "three");
    }


    @AfterSuite
    public void cleanup() {
        browserWrapper.getDriver().quit();
    }
}