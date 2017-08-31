package testng.example;

import com.slickqa.webdriver.FindBy;
import com.slickqa.webdriver.In;
import com.slickqa.webdriver.PageElement;
import com.slickqa.webdriver.WebDriverWrapper;
import com.sofi.ui.dataobjects.FlowDataSet;
import com.sofi.ui.lib.PageInFlow;

import java.util.List;

/**
 * Created by slambson on 8/29/17.
 */
public class ExamplePage extends PageInFlow {

    protected PageElement buttonGoogleSearch = new PageElement("Google Search button", FindBy.value("Google Search"));
    protected PageElement inputElements = new PageElement("input elements", FindBy.tagName("input"));
    protected PageElement elementByAttributeValue = new PageElement("Element by Attribute Value", FindBy.attributeValue("aria-label", "I'm Feeling Lucky"));

    public ExamplePage(WebDriverWrapper browserWrapper) {
        this.browserWrapper = browserWrapper;
    }

    public boolean isCurrentPage(WebDriverWrapper browser) {

        return browserWrapper.exists(buttonGoogleSearch);
    }

    public void handlePage(WebDriverWrapper browser, FlowDataSet context) throws Exception {
    }

    public void completePage() {
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
}