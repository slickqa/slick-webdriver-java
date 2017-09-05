package com.slickqa.webdriver;

import com.slickqa.webdriver.*;

/**
 * Created by slambson on 8/30/17.
 */
public class GitHubPage {

    private PageElement filesTable = new PageElement("Files table", FindBy.className("files"));
    private PageElement inputElements = new PageElement("Examples Link", In.ParentElement(filesTable), FindBy.tagName("a"));

    private WebDriverWrapper browserWrapper;


    public GitHubPage(WebDriverWrapper browserWrapper) {
        this.browserWrapper = browserWrapper;
    }

    public void clickExamplesDirectoryLink() {
        browserWrapper.click(inputElements);
    }
}