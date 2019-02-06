package com.slickqa.webdriver;

import java.util.List;

/**
 * Created by slambson on 8/29/17.
 */
public class ExamplePage {

    private PageElement buttonGoogleSearch = new PageElement("Google Search button", FindBy.value("Google Search"));
    private PageElement inputElements = new PageElement("input elements", FindBy.tagName("input"));
    private PageElement elementByAttributeValue = new PageElement("Element by Attribute Value", FindBy.attributeValue("aria-label", "I'm Feeling Lucky"));
    private PageElement selectList = new PageElement("Select List", FindBy.name("select_list"));

    private WebDriverWrapper browserWrapper;

    public ExamplePage(WebDriverWrapper browserWrapper) {
        this.browserWrapper = browserWrapper;
    }

    public int listInputElements() {
        List<PageElement> inputElementList = browserWrapper.getPageElements(inputElements);
        for (int x=0; x<inputElementList.size(); x++) {
            PageElement element = inputElementList.get(x);
            System.out.println("Input element: " + x + " has the id attribute value: " + browserWrapper.getAttribute(element, "id"));
            if (browserWrapper.isVisible(element) & browserWrapper.isEnabled(element)) {
                System.out.println("Clicking on input element: " + x);
                browserWrapper.click(element);
            }
        }
        return inputElementList.size();
    }

    public void selectByValue(String value) {
        browserWrapper.selectByOptionValue(selectList, value);
    }

    public void selectByText(String value) {
        browserWrapper.selectByOptionText(selectList, value);
    }

    public void selectByIndex(int value) {
        browserWrapper.selectByIndex(selectList, value);
    }

    public String getSelectedOption() {
        return browserWrapper.getFirstSelectedOptionText(selectList);
    }
}