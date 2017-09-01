package com.slickqa.webdriver;

import java.util.List;

/**
 * Created by slambson on 8/30/17.
 */
public class DemoiFramePage {

    private PageElement demoIframe = new PageElement("Demo iFrame", FindBy.name("myDemoFrame"));
    private PageElement forecastLink = new PageElement("Forecast Link", In.Frame(demoIframe), FindBy.linkText("FORECAST"));
    private PageElement iFrameLinks = new PageElement("iFrame Links", In.Frame(demoIframe), FindBy.tagName("a"));
    private PageElement parentElement = new PageElement("Parent Element", FindBy.id("capSubnav"));
    private PageElement childElements = new PageElement("Child Elements", In.ParentElement(parentElement), FindBy.tagName("a"));

    private WebDriverWrapper browserWrapper;


    public DemoiFramePage(WebDriverWrapper browserWrapper) {
        this.browserWrapper = browserWrapper;
    }

    public void clickForecastLink() {
        browserWrapper.click(forecastLink);
    }

    public void listInputElementsInIFrameElement() {
        List<PageElement> inputElementList = browserWrapper.getPageElements(iFrameLinks);
        for (int x=0; x<inputElementList.size(); x++) {
            PageElement element = inputElementList.get(x);
            System.out.println("Input element: " + x + " has the id attribute value: " + browserWrapper.getAttribute(element, "id"));
            if (browserWrapper.isVisible(element) & browserWrapper.isEnabled(element)) {
                System.out.println("Clicking on input element: " + x);
                browserWrapper.click(element);
            }
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