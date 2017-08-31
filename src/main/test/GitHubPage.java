package testng.example;

        import com.slickqa.webdriver.*;
        import com.sofi.ui.dataobjects.FlowDataSet;
        import com.sofi.ui.lib.PageInFlow;

/**
 * Created by slambson on 8/30/17.
 */
public class GitHubPage extends PageInFlow {

    protected PageElement filesTable = new PageElement("Files table", FindBy.className("files"));
    protected PageElement inputElements = new PageElement("Examples Link", In.ParentElement(filesTable), FindBy.tagName("a"));
    protected PageElement authorLinkByAttributeValue = new PageElement("Author link by attribute value", FindBy.attributeValue("rel", "author"));


    public GitHubPage(WebDriverWrapper browserWrapper) {
        this.browserWrapper = browserWrapper;
    }

    public boolean isCurrentPage(WebDriverWrapper browser) {

        return browserWrapper.exists(filesTable);
    }

    public void handlePage(WebDriverWrapper browser, FlowDataSet context) throws Exception {
    }

    public void completePage() {
    }

    public void clickExamplesDirectoryLink() {
        browserWrapper.click(inputElements);
    }

    public void clickElementByAttributeValue() {
        browserWrapper.click(authorLinkByAttributeValue);
    }
}