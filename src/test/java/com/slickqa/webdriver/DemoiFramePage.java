package com.slickqa.webdriver;

import org.openqa.selenium.NoSuchElementException;

import java.util.List;

/**
 * Created by slambson on 8/30/17.
 */
public class DemoiFramePage {

    private PageElement demoIframe = new PageElement("Demo iFrame", FindBy.name("myDemoFrame"));
    private PageElement forecastLink = new PageElement("Forecast Link", In.Frame(demoIframe), FindBy.linkText("FORECAST"));
    private PageElement forecastLinkNoFrameSpecified = new PageElement("Forecast Link", FindBy.linkText("FORECAST"));
    private PageElement forecastLinkAtIndex = new PageElement("Forecast Link", In.Frame(demoIframe), FindBy.linkText("FORECAST"), 0);
    private PageElement iframeLinks = new PageElement("iFrame links", In.Frame(demoIframe), FindBy.cssSelector(".topMenuNavList a"));
    private PageElement parentElement = new PageElement("Parent Element", FindBy.id("capSubnav"));
    private PageElement childElements = new PageElement("Child Elements", In.ParentElement(parentElement), FindBy.tagName("a"));

    private WebDriverWrapper browserWrapper;


    public DemoiFramePage(WebDriverWrapper browserWrapper) {
        this.browserWrapper = browserWrapper;
    }

    public void clickForecastLink() {
        browserWrapper.click(forecastLink);
    }

    public boolean clickForecastLinkWithoutSpecifyingFrame() {
        try {
            browserWrapper.click(forecastLinkNoFrameSpecified, 10);
            return false;
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public void clickForecastLinkAtIndex() {
        browserWrapper.click(forecastLinkAtIndex);
    }

    public List<PageElement> getForecastElementsInFrame() {
        return browserWrapper.getPageElements(iframeLinks);
    }

    public void listForecastLinkElementsInIFrameElement() {
        List<PageElement> inputElementList = browserWrapper.getPageElements(iframeLinks);
        for (PageElement element : inputElementList ) {
            System.out.println("Input element: has the href attribute value: " + browserWrapper.getAttribute(element, "href"));
        }
    }


    public int listInputElementsInParentElement() {
        List<PageElement> childElementList = browserWrapper.getPageElements(childElements);
        for (int x=0; x<childElementList.size(); x++) {
            PageElement element = childElementList.get(x);
            System.out.println("Input element: " + x + " has the href attribute value: " + browserWrapper.getAttribute(element, "href"));
        }
        return childElementList.size();
    }
}