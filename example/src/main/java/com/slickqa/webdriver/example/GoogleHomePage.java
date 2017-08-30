package com.slickqa.webdriver.example;

import com.slickqa.webdriver.*;

/**
 * Google's Home Page
 * Created by jcorbett on 6/24/17.
 */
public class GoogleHomePage implements InFlow {
    public static String URL = "https://www.google.com";

    // NOTE: by prefixing the elements with their type, it can help find the elements in code completion
    protected PageElements inputElements = new PageElements(FindBy.tagName("input"));

    private WebDriverWrapper browserWrapper;

    public void listInputElements() {
        ArrayList<PageElement> inputElementList = browserWrapper.getPageElements(inputElements);
        for (int x=0; x<inputElementList.size(); x++) {
            PageElement element = inputElementList.get(x);
            System.out.println("Input element: " + x + " has the id attribute value: " + browserWrapper.getAttribute(element, "id"));
        }
    }
}
