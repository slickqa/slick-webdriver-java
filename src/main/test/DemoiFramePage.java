package testng.example;

import com.slickqa.webdriver.FindBy;
import com.slickqa.webdriver.In;
import com.slickqa.webdriver.PageElement;
import com.slickqa.webdriver.WebDriverWrapper;
import com.sofi.ui.dataobjects.FlowDataSet;
import com.sofi.ui.lib.PageInFlow;

import java.util.List;

/**
 * Created by slambson on 8/30/17.
 */
public class DemoiFramePage extends PageInFlow {

    protected PageElement demoIframe = new PageElement("Demo iFrame", FindBy.name("myDemoFrame"));
    protected PageElement forecastLink = new PageElement("Forecast Link", In.Frame(demoIframe), FindBy.linkText("FORECAST"));
    protected PageElement iFrameLinks = new PageElement("iFrame Links", In.Frame(demoIframe), FindBy.tagName("a"));
    protected PageElement parentElement = new PageElement("Parent Element", FindBy.id("capSubnav"));
    protected PageElement childElements = new PageElement("Child Elements", In.ParentElement(parentElement), FindBy.tagName("a"));


    public DemoiFramePage(WebDriverWrapper browserWrapper) {
        this.browserWrapper = browserWrapper;
    }

    public boolean isCurrentPage(WebDriverWrapper browserWrapper) {

        return browserWrapper.exists(demoIframe);
    }

    public void handlePage(WebDriverWrapper browserWrapper, FlowDataSet context) throws Exception {
    }

    public void completePage() {
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