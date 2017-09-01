package com.slickqa.webdriver;

import com.slickqa.webdriver.*;

/**
 * Created by slambson on 8/30/17.
 */
public class GitHubPage {

    private PageElement filesTable = new PageElement("Files table", FindBy.className("files"));
    private PageElement inputElements = new PageElement("Examples Link", In.ParentElement(filesTable), FindBy.tagName("a"));
    private PageElement authorLinkByAttributeValue = new PageElement("Author link by attribute value", FindBy.attributeValue("rel", "author"));

    private WebDriverWrapper browserWrapper;


    public GitHubPage(WebDriverWrapper browserWrapper) {
        this.browserWrapper = browserWrapper;
    }

    public void clickExamplesDirectoryLink() {
        browserWrapper.click(inputElements);
    }

    public void clickElementByAttributeValue() {
        browserWrapper.click(authorLinkByAttributeValue);
    }
}