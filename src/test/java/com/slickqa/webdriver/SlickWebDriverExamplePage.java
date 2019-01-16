package com.slickqa.webdriver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Created by slambson on 9/4/17.
 */
public class SlickWebDriverExamplePage {
    private PageElement imageAttributeValue = new PageElement("Image element by attribute value", FindBy.attributeValue("data-qa", "slick-logo"));
    private PageElement elementByAttributeValue = new PageElement("Element by attribute value", FindBy.attributeValue("alt", "slick logo"));
    private PageElement imgSlickLogo = new PageElement("Slick Logo", FindBy.alt("slick logo"));
    private PageElement parentElement1 = new PageElement("Parent Element", FindBy.className("sidenav-inner"));
    private PageElement childElements1 = new PageElement("Child Elements", In.ParentElement(parentElement1), FindBy.tagName("a"));
    private PageElement parentElement2 = new PageElement("Parent Element", FindBy.attributeValue("data-qa", "parent-div"));
    private PageElement childElements2 = new PageElement("Child Elements", In.ParentElement(parentElement2), FindBy.id("features-link"));
    private PageElement elements2ByXpath = new PageElement("Child Elements", FindBy.xpath("//*[@id=\"features-link\"]"));
    private PageElement childElements2ByXpath = new PageElement("Child Elements", In.ParentElement(parentElement2), FindBy.xpath("*[@id=\"features-link\"]"));
    private PageElement inputElements = new PageElement("input elements", FindBy.tagName("input"));
    private PageElement childElementWithFindByParent = new PageElement("Child element with findby parent", In.ParentElement(FindBy.attributeValue("data-qa", "parent-div")), FindBy.id("features-link"));
    private PageElement childElements3 = new PageElement("Child Elements in Parent specified with FindBy", In.ParentElement(FindBy.className("sidenav-inner")), FindBy.tagName("a"));
    private PageElement childElementByIndex = new PageElement("Child Element by index", In.ParentElement(parentElement1), FindBy.tagName("a"), 6);
    private PageElement sliderRates = new PageElement("Rates slider", FindBy.id("product-slider"));
    private PageElement invalidElement = new PageElement("Invalid element", FindBy.id("product-slidera"));
    private PageElement hiddenElement = new PageElement("Hidden element", FindBy.id("hiddenElement"));

    private WebDriverWrapper browserWrapper;

    public SlickWebDriverExamplePage(WebDriverWrapper browserWrapper) { this.browserWrapper = browserWrapper; }

    public String getElementSrcByAttributeValue() {
        String src = browserWrapper.getAttribute(elementByAttributeValue, "src");
        String alt = browserWrapper.getAttribute(elementByAttributeValue, "alt");
        String dataQa = browserWrapper.getAttribute(elementByAttributeValue, "data-qa");
        //System.out.println("src: " + src);
        System.out.println("alt: " + alt);
        System.out.println("data-qa: " + dataQa);
        return src;
    }

    public String getElementSrcByAlt() {
        String src = browserWrapper.getAttribute(imgSlickLogo, "src");
        String alt = browserWrapper.getAttribute(imgSlickLogo, "alt");
        String dataQa = browserWrapper.getAttribute(imgSlickLogo, "data-qa");
        System.out.println("src: " + src);
        System.out.println("alt: " + alt);
        System.out.println("data-qa: " + dataQa);
        return src;
    }

    public int listInputElementsInParentElement() {
        List<PageElement> childElementList = browserWrapper.getPageElements(childElements1);
        for (int x=0; x<childElementList.size(); x++) {
            PageElement element = childElementList.get(x);
            System.out.println("Input element: " + x + " has the href attribute value: " + browserWrapper.getAttribute(element, "href"));
        }
        return childElementList.size();
    }

    public int listInputElementsInParentElementFindBy() {
        List<PageElement> childElementList = browserWrapper.getPageElements(childElements3);
        for (int x=0; x<childElementList.size(); x++) {
            PageElement element = childElementList.get(x);
            System.out.println("Input element: " + x + " has the href attribute value: " + browserWrapper.getAttribute(element, "href"));
        }
        return childElementList.size();
    }

    public String getElementByIndexFromParentElement() {
        return browserWrapper.getText(childElementByIndex);
    }

    public void clickOnElementInParentElement() {
        browserWrapper.click(childElements2);
    }

    public void clickOnElementInParentElementChildByXpath() {
        browserWrapper.click(childElements2ByXpath);
    }

    public void clickOnElementByXpath() {
        browserWrapper.click(elements2ByXpath);
    }

    public void clickOnElementInParentElementWithFindBy() {
        browserWrapper.click(childElementWithFindByParent);
    }

    public int listInputElements() {
        List<PageElement> inputElementList = browserWrapper.getPageElements(inputElements);
        for (int x=0; x<inputElementList.size(); x++) {
            PageElement element = inputElementList.get(x);
            if (browserWrapper.isVisible(element)) {
                System.out.println("Input element: " + x + " has the id attribute value: " + browserWrapper.getAttribute(element, "id"));
                if (browserWrapper.isVisible(element) & browserWrapper.isEnabled(element)) {
                    System.out.println("Clicking on input element: " + x);
                    try {
                        browserWrapper.click(element);
                    } catch(Exception e) {

                    }
                }
            } else {
                System.out.println("Element: " + x + "is invisible");
            }
        }
        return inputElementList.size();
    }

    public int getRangeInputValue() {
        return Integer.valueOf(browserWrapper.getAttribute(sliderRates, "value"));
    }

    public void slideRangeInputElement(int xOffset) throws Exception {
        browserWrapper.slideRangeInput(sliderRates, xOffset);
    }

    public void clickRangeInputElement(int xOffset, int yOffset) {
        browserWrapper.clickAtOffset(sliderRates, xOffset, yOffset);
    }

    public void waitDefaultTimeForElementThatExists() {
        browserWrapper.waitFor(sliderRates);
    }

    public void waitPassedInTimeForElementThatExists(int timeout) {
        browserWrapper.waitFor(sliderRates, timeout);
    }

    public void waitDefaultTimeForElementThatDoesNotExist() {
        browserWrapper.waitFor(invalidElement);
    }

    public void waitPassedInTimeForElementThatDoesNotExists(int timeout) {
        browserWrapper.waitFor(invalidElement, timeout);
    }

    public void waitPassedInTimeForElementToNotBeVisibleElementExists(int timeout) {
        browserWrapper.waitForNotVisible(sliderRates, timeout);
    }

    public void waitPassedInTimeForElementToNotBeVisibleElementDoesNotExists(int timeout) {
        browserWrapper.waitForNotVisible(invalidElement, timeout);
    }

    public void waitPassedInTimeForElementToNotBeVisibleElementIsHidden(int timeout) {
        browserWrapper.waitForNotVisible(hiddenElement, timeout);
    }
}
