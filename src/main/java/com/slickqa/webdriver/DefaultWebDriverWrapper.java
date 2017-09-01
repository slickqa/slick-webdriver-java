package com.slickqa.webdriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the WebDriverWrapper interface
 *
 * @author jcorbett
 */
public class DefaultWebDriverWrapper implements WebDriverWrapper {

    private WebDriver driver;
    private Capabilities driver_capabilities;
    private int timeout;
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("test." + DefaultWebDriverWrapper.class.getName());
    private int screenshot_counter;
    private int htmlsource_counter;
    private static String original_browser_window_handle = "";
    private OutputFileSupport debugSupport;

    /**
     * Get the underlying webdriver object.
     *
     * @param caps The Capabilites for the webdriver instance
     * @return The webdriver object used by this instance of WebDriverWrapper.
     */
    public static WebDriver getDriverFromCapabilities(Capabilities caps) {
        if (caps.getCapability(RemoteDriverWithScreenshots.REMOTE_URL) == null) {
            if (caps.getBrowserName().equals(DesiredCapabilities.htmlUnit().getBrowserName())) {
                HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_52);
                driver.setJavascriptEnabled(true);
                return driver;
            } else if (caps.getBrowserName().equals(DesiredCapabilities.firefox().getBrowserName())) {
                if (caps.getPlatform() == Platform.WINDOWS) {
                    DesiredCapabilities createCaps = DesiredCapabilities.firefox();
                    createCaps.setAcceptInsecureCerts(true);
                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setPreference("app.update.auto", false);
                    createCaps.setCapability(FirefoxDriver.PROFILE, profile);
                    return new FirefoxDriver(createCaps);
                } else {
                    DesiredCapabilities createCaps = DesiredCapabilities.firefox();
                    createCaps.setAcceptInsecureCerts(true);
                    FirefoxProfile profile = new FirefoxProfile();
                    profile.setPreference("app.update.auto", false);
                    profile.setAssumeUntrustedCertificateIssuer(false);
                    profile.setPreference("app.update.auto", false);
                    return new FirefoxDriver(createCaps);
                }
            } else if (caps.getBrowserName().equals(DesiredCapabilities.internetExplorer().getBrowserName())) {
                return new InternetExplorerDriver();
            } else if (caps.getBrowserName().equals(DesiredCapabilities.chrome().getBrowserName())) {
                return new ChromeDriver();
            } else if (caps.getBrowserName().equals(DesiredCapabilities.phantomjs().getBrowserName())) {
                DesiredCapabilities phantomcaps = new DesiredCapabilities();
                phantomcaps.merge(caps);
                phantomcaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--web-security=no", "--ignore-ssl-errors=yes"});
                PhantomJSDriver driver = new PhantomJSDriver(phantomcaps);
                driver.manage().window().setSize(new Dimension(1920, 1080));
                return driver;
            } else if (caps.getBrowserName().equals(DesiredCapabilities.safari().getBrowserName())) {
                return new SafariDriver();
            } else {
                return new FirefoxDriver();
            }
        } else {
            try {
                return new RemoteDriverWithScreenshots(caps);
            } catch (MalformedURLException ex) {
                logger.error("Invalid URL for remote webdriver '" + caps.getCapability(RemoteDriverWithScreenshots.REMOTE_URL) + "': ", ex);
                return null;
            }
        }
    }

    /**
     * Return a DefaultWebDriverWrapper instance from an existing WebDriver instance
     *
     * @param driver The existing WebDriver instance
     * @return debugSupport and OutputFileSupport object to support saving screenshots and other files from the webdriver wrapper
     */
    public DefaultWebDriverWrapper(WebDriver driver, OutputFileSupport debugSupport) {
        this.driver = driver;
        screenshot_counter = 0;
        htmlsource_counter = 0;
        this.debugSupport = debugSupport;
    }

    /**
     * Return a DefaultWebDriverWrapper instance from an existing WebDriver instance
     *
     * @param caps The Capabilites for the webdriver instance
     * @return debugSupport and OutputFileSupport object to support saving screenshots and other files from the webdriver wrapper
     */
    public DefaultWebDriverWrapper(Capabilities caps, OutputFileSupport debugSupport) {
        this(getDriverFromCapabilities(caps), debugSupport);
        driver_capabilities = caps;
        if (!driver_capabilities.getBrowserName().equals(DesiredCapabilities.chrome().getBrowserName())) {
            original_browser_window_handle = driver.getWindowHandle();
        }
        setDefaultTimeout(30); // default timeout is 30 seconds
    }

    @Override
    public void setDefaultTimeout(int p_timeout) {
        this.timeout = p_timeout;
    }

    /**
     * Return a WebElement based off the PageElement
     *
     * @param locator The PageElement to use to locate the WebElement
     * @return p_timeout the max time to wait for the WebElement to exist
     */
    public WebElement getElement(PageElement locator, int p_timeout) {
        WebElement element = null;
        try {
            element = locator.getElement(driver, p_timeout);
        } catch (NoSuchElementException ex) {
            logger.error("Element with name {} and found {} was not found after {} seconds.", locator.getName(), locator.getFindByDescription(), p_timeout);
            logger.error("Current page URL: {}", driver.getCurrentUrl());
            logger.error("Current page title: {}", driver.getTitle());
            saveHTMLSource("no-such-element");
            takeScreenShot("no-such-element");
            throw ex;
        }
        return element;
    }

    /**
     * Return a list of WebElements based off the PageElements
     *
     * @param locator The PageElement to use to locate the WebElement
     * @return p_timeout the max time to wait for the WebElement to exist
     */
    public List<PageElement> getElements(PageElement locator, int p_timeout) {
        List<PageElement> elements;
        try {
            elements = locator.getElements(driver, p_timeout);
        } catch (NoSuchElementException ex) {
            logger.error("Elements with found {} were not found after {} seconds.", locator.getFindByDescription(), p_timeout);
            logger.error("Current page URL: {}", driver.getCurrentUrl());
            logger.error("Current page title: {}", driver.getTitle());
            saveHTMLSource("no-such-element");
            takeScreenShot("no-such-element");
            throw ex;
        }
        return elements;
    }

    @Override
    public boolean isEnabled(PageElement locator) {
        return isEnabled(locator, timeout);
    }

    @Override
    public boolean isEnabled(PageElement locator, int p_timeout) {
        logger.debug("Checking whether element is enabled with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
        return getElement(locator, p_timeout).isEnabled();
    }

    @Override
    public void setCheckboxState(PageElement locator, boolean checked) {
        setCheckboxState(locator, checked, timeout);
    }

    @Override
    public void setCheckboxState(PageElement locator, boolean checked, int p_timeout) {
        WebElement element = getElement(locator, p_timeout);
        if (checked) {
            logger.info("setting checkbox element state to true with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            logger.info("setting checkbox element state to false with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    @Override
    public void click(PageElement locator) {
        click(locator, timeout);
    }

    @Override
    public void click(PageElement locator, int p_timeout) {
        logger.info("Clicking on element with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
        //waitForVisible(locator);

        for(int tries = 0; tries < 3; tries++) {
            try {
                getElement(locator, p_timeout).click();
                break;
            } catch (StaleElementReferenceException e) {
                logger.warn("Got a stale element exception trying to click, retrying.", e);
            }
        }
    }

    @Override
    public void clickHiddenElement(PageElement locator) {
        clickHiddenElement(locator, timeout);
    }

    @Override
    public void clickHiddenElement(PageElement locator, int p_timeout) {
        logger.info("Clicking on hidden element with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
        WebDriver webDriver = getDriver();
        By findByMethod = locator.getFinder();
        WebElement element = webDriver.findElement(findByMethod);
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click()", element);
    }

    @Override
    public void doubleClick(PageElement locator) {
        doubleClick(locator, timeout);
    }

    @Override
    public void doubleClick(PageElement locator, int p_timeout) {
        WebElement element = getElement(locator, p_timeout);
        logger.info("Double clicking element '{}' located by '{}'.", locator.getName(), locator.getFindByDescription());
        /*
         Actions builder = new Actions(driver);
         Action dblclick = builder.doubleClick(element).build();
         dblclick.perform();
         *
         */
        try {
            WebElement realElement = element;
            if (InFrameWebElement.class.isAssignableFrom(element.getClass())) // if we're in a frame
            {
                ((InFrameWebElement) element).beforeOperation();
                realElement = ((InFrameWebElement) element).real;
            }
            Actions builder = new Actions(driver);
            Action dblclick = builder.doubleClick(realElement).build();
            dblclick.perform();
            //hoping we're firefox, because this won't work on IE
            // no reliable way to tell the difference if running remote
/*
             ((JavascriptExecutor) getDriver()).executeScript("var evt = document.createEvent('MouseEvents');"
             + "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
             + "arguments[0].dispatchEvent(evt);", realElement);
             * 
             */
        } finally {
            if (InFrameWebElement.class.isAssignableFrom(element.getClass())) // if we're in a frame
            {
                ((InFrameWebElement) element).afterOperation();
            }
        }
    }

    @Override
    public void clear(PageElement locator) {
        clear(locator, timeout);
    }

    @Override
    public void clear(PageElement locator, int p_timeout) {
        logger.info("Clearing the text from element with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
        getElement(locator, p_timeout).clear();
    }

    @Override
    public void submit(PageElement locator) {
        submit(locator, timeout);
    }

    @Override
    public void submit(PageElement locator, int p_timeout) {
        logger.info("Submitting an element with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
        getElement(locator, p_timeout).submit();
    }

    @Override
    public void type(PageElement locator, String text, int p_timeout) {
        clear(locator, p_timeout);
        logger.info("Typing text '{}' in element with name '{}' and found '{}'.", new Object[]{
                        text, locator.getName(), locator.getFindByDescription()
                    });
        getElement(locator, p_timeout).sendKeys(text);
    }

    @Override
    public void type(PageElement locator, String text) {
        type(locator, text, timeout);
    }

    @Override
    public String getText(PageElement locator) {
        logger.info("Getting text from element with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
        return getText(locator, timeout);
    }

    @Override
    public String getText(PageElement locator, int p_timeout) {
        return getElement(locator, p_timeout).getText();
    }

    @Override
    public void setSelected(PageElement locator, int p_timeout) {
        logger.info("Setting selected element with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
        WebElement element = getElement(locator, p_timeout);
        if (!element.isSelected()) {
            element.click();
        }
    }

    @Override
    public void setSelected(PageElement locator) {
        setSelected(locator, timeout);
    }

    @Override
    public boolean isSelected(PageElement locator, int p_timeout) {
        logger.info("Checking if is selected element with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
        return getElement(locator, p_timeout).isSelected();
    }

    @Override
    public boolean isSelected(PageElement locator) {
        return isSelected(locator, timeout);
    }

    @Override
    public String getAttribute(PageElement locator, String attribute) {
        return getAttribute(locator, timeout, attribute);
    }

    @Override
    public String getAttribute(PageElement locator, int p_timeout, String attribute) {
        logger.info("Getting attribute '" + attribute + "' from element with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
        return getElement(locator, p_timeout).getAttribute(attribute);
    }

    @Override
    public String getPageTitle() {
        logger.debug("Getting current page title.");
        return driver.getTitle();
    }

    @Override
    public String getPageSource() {
        logger.debug("Getting current page html source.");
        return driver.getPageSource();
    }

    @Override
    public String getPageUrl() {
        logger.debug("Getting current page url.");
        return driver.getCurrentUrl();
    }

    @Override
    public void goTo(String url) {
        logger.info("Going to page '{}'.", url);
        driver.get(url);
    }

    @Override
    public void goBack() {
        logger.info("Going back in the browser.");
        driver.navigate().back();
    }

    @Override
    public void goForward() {
        logger.info("Going forward in the browser.");
        driver.navigate().forward();
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public void waitFor(Class<? extends SelfAwarePage> page) {
        waitFor(page, timeout);
    }

    @Override
    public void waitFor(Class<? extends SelfAwarePage> page, int p_timeout) {
        logger.info("Waiting for page '{}' a max of {} seconds.", page.getName(), p_timeout);
        try {
            SelfAwarePage page_instance = page.newInstance();
            waitFor(page_instance, p_timeout);
        } catch (InstantiationException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        } catch (IllegalAccessException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        }
    }

    @Override
    public void waitFor(SelfAwarePage page) { waitFor(page, timeout); }

    @Override
    public void waitFor(SelfAwarePage page, int p_timeout) {
        Date start_time = new Date();
        Calendar end_time = Calendar.getInstance();
        end_time.add(Calendar.SECOND, p_timeout);
        while (Calendar.getInstance().before(end_time) && !page.isCurrentPage(this)) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
            }
        }
        if (!page.isCurrentPage(this)) {
            logger.error("Waited for page '{}' for {} seconds, but still is not here.", page.getClass().getName(), p_timeout);
            logger.error("Current page URL: {}", driver.getCurrentUrl());
            logger.error("Current page title: {}", driver.getTitle());
            saveHTMLSource("page-not-found");
            takeScreenShot("page-not-found");
            throw new NoSuchElementException("Couldn't find page '" + page.getClass().getName() + "' after " + p_timeout + " seconds.");
        }
        logger.debug("Found page '{}' after {} seconds.", page.getClass().getName(), ((new Date()).getTime() - start_time.getTime()) / 1000);
    }

    @Override
    public void waitForPageInFlow(InFlow page) { waitForPageInFlow(page, timeout); }

    @Override
    public void waitForPageInFlow(InFlow page, int p_timeout) {
        Date start_time = new Date();
        Calendar end_time = Calendar.getInstance();
        end_time.add(Calendar.SECOND, p_timeout);
        while (Calendar.getInstance().before(end_time) && !page.isCurrentPage()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
            }
        }
        if (!page.isCurrentPage()) {
            logger.error("Waited for page '{}' for {} seconds, but still is not here.", page.getClass().getName(), p_timeout);
            logger.error("Current page URL: {}", driver.getCurrentUrl());
            logger.error("Current page title: {}", driver.getTitle());
            saveHTMLSource("page-not-found");
            takeScreenShot("page-not-found");
            throw new NoSuchElementException("Couldn't find page '" + page.getClass().getName() + "' after " + p_timeout + " seconds.");
        }
        logger.debug("Found page '{}' after {} seconds.", page.getClass().getName(), ((new Date()).getTime() - start_time.getTime()) / 1000);
    }


    @Override
    public <T> void handlePage(Class<? extends SelfAwarePage<T>> page, T context) throws Exception {
        try {
            SelfAwarePage<T> page_instance = page.newInstance();
            page_instance.handlePage(this, context);
        } catch (InstantiationException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        } catch (IllegalAccessException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        }
    }

    @Override
    public <T> void handlePageInFlow(Class<? extends InFlow<T>> page, T context) throws Exception {
        try {
            InFlow<T> page_instance = page.newInstance();
            page_instance.handlePage(context);
        } catch (InstantiationException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        } catch (IllegalAccessException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        }
    }

    @Override
    public boolean isCurrentPage(Class<? extends SelfAwarePage> page) {
        try {
            SelfAwarePage page_instance = page.newInstance();
            return page_instance.isCurrentPage(this);
        } catch (InstantiationException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        } catch (IllegalAccessException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        }
    }

    @Override
    public boolean isCurrentPageInFlow(Class<? extends InFlow> page) {
        try {
            InFlow page_instance = page.newInstance();
            return page_instance.isCurrentPage();
        } catch (InstantiationException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        } catch (IllegalAccessException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        }
    }

    @Override
    public void selectByOptionText(PageElement selectList, String option) {
        selectByOptionText(selectList, option, timeout);
    }

    @Override
    public void selectByOptionText(PageElement selectList, String option, int p_timeout) {
        logger.debug("Selecting option with display text '{}' of select list '{}' found by '{}' waiting a max timeout of {} seconds.", new Object[]{option, selectList.getName(), selectList.getFinder(), p_timeout});
        Select selectInput = new Select(getElement(selectList, p_timeout));
        selectInput.selectByVisibleText(option);
    }

    @Override
    public void selectByOptionValue(PageElement selectList, String optionValue) {
        selectByOptionValue(selectList, optionValue, timeout);
    }

    @Override
    public void selectByOptionValue(PageElement selectList, String optionValue, int p_timeout) {
        logger.info("Selecting option with value '{}' of select list '{}' found by '{}' waiting a max timeout of {} seconds.", new Object[]{optionValue, selectList.getName(), selectList.getFinder(), p_timeout});
        Select selectInput = new Select(getElement(selectList, p_timeout));
        selectInput.selectByValue(optionValue);
    }

    @Override
    public void waitFor(PageElement element) {
        waitFor(element, timeout);
    }

    @Override
    public void waitFor(PageElement element, int p_timeout) {
        logger.info("Waiting for element '{}' a max of {} seconds.", element.getName(), p_timeout);
    }

    @Override
    public void waitForVisible(PageElement element) {
        waitForVisible(element, timeout);
    }

    @Override
    public void waitForVisible(PageElement element, int p_timeout) {
        logger.info("Waiting a max of {} seconds for element '{}' found by {} to become visible.", new Object[]{p_timeout, element.getName(), element.getFindByDescription()});
        Calendar end_time = Calendar.getInstance();
        Date start_time = end_time.getTime();
        end_time.add(Calendar.SECOND, p_timeout);
        WebElement wdelement = getElement(element, p_timeout);
        logger.info("Found element '{}' after {} seconds, waiting for it to become visible.", element.getName(), ((new Date()).getTime() - start_time.getTime()) / 1000);
        while (!wdelement.isDisplayed() && (Calendar.getInstance().before(end_time))) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                logger.warn("Caught interrupted exception, while waiting for element, but it shouldn't cause too much trouble: {}", e.getMessage());
            }
        }
        if (!wdelement.isDisplayed()) {
            throw new ElementNotVisibleException("Waited " + p_timeout + " seconds for element " + element.getName() + " found by " + element.getFindByDescription() + " to become visible, and it never happened.");
        }
        logger.debug("Element '{}' was found visisble after {} seconds.", element.getName(), ((new Date()).getTime() - start_time.getTime()) / 1000);
    }

    @Override
    public boolean exists(PageElement element) {
        logger.debug("Checking for existence of element '{}'.", element.getName());
        return element.exists(driver, 0);
    }

    @Override
    public boolean exists(Class<? extends SelfAwarePage> page) {
        logger.info("Checking for existence of page '{}'.", page.getName());

        try {
            SelfAwarePage page_instance = page.newInstance();
            if (!page_instance.isCurrentPage(this)) {
                logger.info("Checked for page '{}' , but it does not exist.", page.getName());
                return false;
            }
            logger.debug("Found page '{}'.", page.getName());
            return true;
        } catch (InstantiationException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        } catch (IllegalAccessException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        }
    }

    @Override
    public String getWindowHandle() {
        logger.info("Getting current browser window handle");
        return driver.getWindowHandle();
    }

    @Override
    public Set<String> getWindowHandles() {
        logger.info("Getting all browser window handles");
        return driver.getWindowHandles();
    }

    @Override
    public void switchToWindowByHandle(String windowHandle) {
        logger.info("Switching to the window with handle '{}'.", windowHandle);
        driver.switchTo().window(windowHandle);
    }

    @Override
    public void switchToWindowByURLContains(String partialWindowURL) {
        switchToWindowByURLContains(partialWindowURL, timeout);
    }

    @Override
    public void switchToWindowByURLContains(String partialWindowURL, int p_timeout) {
        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.SECOND, p_timeout);
        String switchToHandle = "";
        logger.info("Looking for the window with the URL containing '{}'.", partialWindowURL);

        while (true) {
            if (Calendar.getInstance().after(endTime)) {
                logger.error("Unable to find window with URL containing '{}'.", partialWindowURL);
                if (original_browser_window_handle.equals("") == false) {
                    logger.error("Switching back to the original window: " + original_browser_window_handle);
                    switchToWindowByHandle(original_browser_window_handle);
                } else {
                    logger.error("original_browser_window_handle was not set, cannot switch back to the original browser window.  Reopening browser instead.");
                    reopen();
                }
                throw new NoSuchWindowException("Timed out waiting for a known page");
            }
            for (String possibleHandle : getWindowHandles()) {
                switchToWindowByHandle(possibleHandle);
                logger.info("Current browser URL: " + getPageUrl().toLowerCase());

                if (getPageUrl().toLowerCase().contains(partialWindowURL.toLowerCase()) == true) {
                    switchToHandle = possibleHandle;
                } else {
                    try {
                        java.lang.Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
            if (switchToHandle.isEmpty() == false) {
                logger.debug("Found the window with the URL containing '{}', switching to it now.", partialWindowURL);
                switchToWindowByHandle(switchToHandle);
                break;
            }
        }
    }

    @Override
    public void switchToWindowByURL(String windowURL) {
        switchToWindowByURL(windowURL, timeout);
    }

    @Override
    public void switchToWindowByURL(String windowURL, int p_timeout) {
        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.SECOND, p_timeout);
        String switchToHandle = "";
        logger.info("Looking for the window with the URL matching '{}'.", windowURL);

        while (true) {
            if (Calendar.getInstance().after(endTime)) {
                logger.error("Unable to find window with URL matching '{}'.", windowURL);
                if (original_browser_window_handle.equals("") == false) {
                    logger.error("Switching back to the original window: " + original_browser_window_handle);
                    switchToWindowByHandle(original_browser_window_handle);
                } else {
                    logger.error("original_browser_window_handle was not set, cannot switch back to the original browser window.  Reopening browser instead.");
                    reopen();
                }
                throw new NoSuchWindowException("Timed out waiting for a known page");
            }
            for (String possibleHandle : getWindowHandles()) {
                switchToWindowByHandle(possibleHandle);
                logger.info("Current browser URL: " + getPageUrl().toLowerCase());

                if (getPageUrl().toLowerCase().equals(windowURL.toLowerCase()) == true) {
                    switchToHandle = possibleHandle;
                } else {
                    try {
                        java.lang.Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
            if (switchToHandle.isEmpty() == false) {
                logger.debug("Found the window with the URL matching '{}', switching to it now.", windowURL);
                switchToWindowByHandle(switchToHandle);
                break;
            }
        }
    }

    @Override
    public void closeWindow() {
        logger.info("Closing current browser window");
        driver.close();
    }

    @Override
    public void closeWindow(String windowHandle) {
        logger.info("Closing the the window with handle '{}'.", windowHandle);
        switchToWindowByHandle(windowHandle);
        closeWindow();
    }

    @Override
    public boolean isVisible(PageElement locator) {
        boolean elementVisible = true;

        logger.debug("Checking visibility on element with name '{}' and found '{}'.", locator.getName(), locator.getFindByDescription());
        if (exists(locator) == true) {
            WebElement wdelement = getElement(locator, timeout);
            elementVisible = wdelement.isDisplayed();

            if (elementVisible) {
                logger.info("Found visible element with name '{}' and found '{}'", locator.getName(), locator.getFindByDescription());
            } else {
                logger.info("Element was NOT VISIBLE with name '{}' and found '{}'", locator.getName(), locator.getFindByDescription());
            }
        } else {
            elementVisible = false;
        }
        return elementVisible;
    }

    @Override
    public void takeScreenShot() {
        takeScreenShot("screenshot");
    }

    @Override
    public void takeScreenShot(String name) {
        if (TakesScreenshot.class.isAssignableFrom(driver.getClass())) {
            if (name == null) {
                name = "screenshot";
            }
            if (!name.toLowerCase().endsWith(".png")) {
                name = name + ".png";
            }
            name = ++screenshot_counter + "-" + name;
            File ss_file = debugSupport.getOutputFile(name);
            logger.debug("Taking screenshot, output file will be {}", ss_file.getAbsolutePath());
            File ss_tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            try {
                FileUtils.copyFile(ss_tmp, ss_file);
            } catch (IOException ex) {
                logger.error("Unable to copy screenshot from '" + ss_tmp.getAbsolutePath() + "' to '" + ss_file.getAbsolutePath() + "': ", ex);
            }
        } else {
            logger.warn("Requested screenshot by name '{}', but browser doesn't support taking screenshots.", name);
        }

    }

    @Override
    public void saveHTMLSource() {
        saveHTMLSource("pagesource");
    }

    @Override
    public void saveHTMLSource(String name) {
        if (name == null) {
            name = "pagesource";
        }
        if (!name.toLowerCase().endsWith(".html")) {
            name = name + ".html";
        }
        name = ++htmlsource_counter + "-" + name;
        File src_file = debugSupport.getOutputFile(name);
        logger.debug("Saving current page HTML source, output file will be {}", src_file.getAbsolutePath());

        try {
            FileUtils.writeStringToFile(src_file, getPageSource(), Charset.defaultCharset());
        } catch (IOException ex) {
            logger.error("Unable to save the current page HTML source to the file '" + src_file.getAbsolutePath() + "': ", ex);
        }

    }

    @Override
    public void reopen() {
        try {
            driver.quit();
        } catch (WebDriverException wde) {
            logger.error("Caught an Exception when trying to quit the driver in reopen()", wde);
        }
        driver = getDriverFromCapabilities(driver_capabilities);

        if (driver_capabilities.getBrowserName().equals(DesiredCapabilities.chrome().getBrowserName()) == false) {
            original_browser_window_handle = driver.getWindowHandle();
        }
    }

    @Override
    public void closeAllWindowsExceptOriginal() {
        if (original_browser_window_handle.equals("") == false) {
            logger.info("Closing all browser windows except: " + original_browser_window_handle);

            for (String windowHandle : getWindowHandles()) {
                if (windowHandle.equals(original_browser_window_handle) == false) {
                    closeWindow(windowHandle);
                }
            }
            switchToWindowByHandle(original_browser_window_handle);
        } else {
            logger.info("original_browser_window_handle is not set, no other windows to close");
        }
    }

    @Override
    public void logToSessionFile(String filename, String logString) {
        String sessionOutputFileName = debugSupport.getSessionOutputFile(filename).getAbsolutePath();
        logger.info("Writing to session file, session file will be " + sessionOutputFileName);

        try {
            OutputStream os = debugSupport.getSessionOutputStream(filename);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(logString);
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            logger.error("Unable to write to the session file '" + sessionOutputFileName + "': ", ex);
        } catch (NotFoundException ex) {
            logger.error("Unable to write to the session file '" + sessionOutputFileName + "': ", ex);
        }
    }

    @Override
    public String getFirstSelectedOptionText(PageElement selectList) {
        return getFirstSelectOptionText(selectList, timeout);
    }

    @Override
    public String getFirstSelectOptionText(PageElement selectList, int p_timeout) {
        logger.info("Getting first selected option as text from of select list '{}' found by '{}' waiting a max timeout of {} seconds.", new Object[]{selectList.getName(), selectList.getFinder(), p_timeout});
        Select selectInput = new Select(getElement(selectList, p_timeout));
        return selectInput.getFirstSelectedOption().getText();
    }

    @Override
    public void hover(PageElement locator) {
        hover(locator, timeout);
    }

    @Override
    public void hover(PageElement locator, int p_timeout) {
        WebElement element = getElement(locator, p_timeout);
        logger.info("Hovering mouse over element '{}' located by '{}'.", locator.getName(), locator.getFindByDescription());
        Actions builder = new Actions(driver);
        Action hover = builder.moveToElement(element, 2, 2).build();
        hover.perform();
    }

    @Override
    public void waitForNotVisible(PageElement element) {
        waitForNotVisible(element, timeout);
    }

    @Override
    public void waitForNotVisible(PageElement element, int p_timeout) {
        logger.info("Waiting a max of {} seconds for element '{}' found by {} to become invisible.", new Object[]{p_timeout, element.getName(), element.getFindByDescription()});

        Calendar end_time = Calendar.getInstance();
        Date start_time = end_time.getTime();
        end_time.add(Calendar.SECOND, p_timeout);
        WebElement wdelement = getElement(element, p_timeout);
        logger.debug("Found element '{}' after {} seconds, waiting for it to become invisible.", element.getName(), ((new Date()).getTime() - start_time.getTime()) / 1000);

        while (Calendar.getInstance().before(end_time)) {
            try {
                if (!wdelement.isDisplayed()) {
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    logger.error("Caught interrupted exception, while waiting for element, but it shouldn't cause too much trouble: {}", e.getMessage());
                }
            } catch (StaleElementReferenceException e) {
            }
        }
        if (wdelement.isDisplayed()) {
            throw new ElementNotVisibleException("Waited " + p_timeout + " seconds for element " + element.getName() + " found by " +  element.getName() + "to become invisible, and it never happened.");
        }

        logger.info("Element '{}' was not found visible after {} seconds.", element.getName(), ((new Date()).getTime() - start_time.getTime()) / 1000);
    }

    @Override
    public void waitForDoesNotExist(PageElement element) {
        waitForDoesNotExist(element, timeout);
    }

    @Override
    public void waitForDoesNotExist(PageElement element, int p_timeout) {
        logger.info("Waiting a max of {} seconds for element '{}' found by {} to no longer exist.", new Object[]{p_timeout, element.getName(), element.getFindByDescription()});

        Calendar end_time = Calendar.getInstance();
        Date start_time = end_time.getTime();
        end_time.add(Calendar.SECOND, p_timeout);
        logger.info("Waiting for element '{}' to no longer exist.", element.getName());

        while (element.exists(driver, 0) && (Calendar.getInstance().before(end_time))) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                logger.error("Caught interrupted exception, while waiting for element, but it shouldn't cause too much trouble: {}", e.getMessage());
            }
        }
        if (element.exists(driver, 1)) {
            throw new NoSuchElementException("Waited " + p_timeout + " seconds for element " + element.getName() + " found by " + element.getFindByDescription() + " to no longer exist, and it never happened.");
        }

        logger.debug("Element '{}' no longer exists after {} seconds.", element.getName(), ((new Date()).getTime() - start_time.getTime()) / 1000);
    }

    @Override
    public void waitForTextNotEmpty(PageElement element) {
        waitForTextNotEmpty(element, this.timeout);
    }

    @Override
    public void waitForTextNotEmpty(PageElement element, int p_timeout) {
        logger.info("Waiting a maximum of {} seconds for element '{}' found by {} to exist and for it's text to be not empty.", new Object[]{p_timeout, element.getName(), element.getFindByDescription()});
        Date beginning = new Date();
        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.SECOND, p_timeout);
        do {
            String elementText = getText(element, p_timeout);
            if (elementText != null && !elementText.isEmpty()) {
                logger.debug("Found element '{}' with text '{}' after {} seconds.", new Object[]{element.getName(), elementText, (((new Date()).getTime() - beginning.getTime()) / 1000)});
                return;
            }
        } while (Calendar.getInstance().before(endTime));
        logger.error("Waited {} seconds for the text of element '{}' found by {} to not be empty.", new Object[]{(((new Date()).getTime() - beginning.getTime()) / 1000), element.getName(), element.getFindByDescription()});
        throw new TimeoutError("Timeout of " + p_timeout + " seconds exceeded while waiting for element '" + element.getName() + "' to provide non empty text.");
    }

    @Override
    public <T extends PageWithActions> T on(Class<T> page) {
        logger.info("Creating instance of page '{}'.", page.getName());

        try {
            PageWithActions page_instance = page.newInstance();
            logger.info("Calling initializePage for page '{}'.", page.getName());
            page_instance.initializePage(this);
            logger.info("Returning instance of page '{}'.", page.getName());
            return (T) page_instance;
        } catch (InstantiationException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        } catch (IllegalAccessException ex) {
            logger.error("Unable to create instance of page class " + page.getName() + ".", ex);
            throw new IllegalStateException("Unable to create instance of page class " + page.getName() + ".", ex);
        }
    }

    @Override
    public <T extends PageWithActions> T on (T page) {
        logger.info("Calling initializePage for page '{}'.", page.getClass().getName());
        page.initializePage(this);
        return (T) page;
    }

    @Override
    public List<PageElement> getPageElements(PageElement locator) {
        return getPageElements(locator, timeout);
    }

    @Override
    public List<PageElement> getPageElements(PageElement locator, int p_timeout) {
        List<PageElement> elements = new ArrayList<PageElement>();
        logger.info("Finding elements found '{}'.", locator.getFindByDescription());

        for(int tries = 0; tries < 3; tries++) {
            try {
                elements = getElements(locator, p_timeout);
                break;
            } catch (StaleElementReferenceException e) {
                logger.warn("Got a stale element exception trying to click, retrying.", e);
            }
        }
        return elements;
    }

    @Override
    public void scrollIntoView(PageElement locator){
        scrollIntoView(locator, 0);
    }

    @Override
    public void scrollIntoView(PageElement locator, int timeout){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].scrollIntoView();", locator.getElement(driver, timeout));
    }
}
