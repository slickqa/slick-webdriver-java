package com.slickqa.webdriver;

import org.openqa.selenium.WebDriver;
import java.util.Set;

/**
 * This is a wrapper api for performing tasks on web driver browsers.  Included in this interface are the methods that
 * are needed for performing actions on web pages.
 *
 * @author jcorbett
 */
public interface WebDriverWrapper
{

	/**
	 * Set the default timeout for the wrapper.  This affects all calls, as by default this api waits for an element
	 * to exist before trying to perform an action on it.
	 *
	 * @param timeout The amount of time to wait for elements in seconds.
	 */
	void setDefaultTimeout(int timeout);

	/**
	 * Clear the text on web page element, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to clear the text on.
	 */
	void clear(PageElement locator);

	/**
	 * Clear the text web page element, waiting a maximum of the amount of time passed in for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to clear the text on.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 */
	void clear(PageElement locator, int timeout);

	/**
	 * Click on a web page element, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to click.
	 */
	void click(PageElement locator);

	/**
	 * Click on a web page element, waiting a maximum of the amount of time passed in for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to click.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 */
	void click(PageElement locator, int timeout);


	/**
	 * Click on a hidden web page element, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to click.
	 */
	void clickHiddenElement(PageElement locator);

	/**
	 * Click on a hidden web page element, waiting a maximum of the amount of time passed in for the element to exist.
	 *
	 * @param locator The hiddent page element instance that describes how to find the element to click.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 */
	void clickHiddenElement(PageElement locator, int timeout);

	/**
	 * Double click on a web page element, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to click.
	 */
	void doubleClick(PageElement locator);

	/**
	 * Double click on a web page element, waiting a maximum of the amount of time passed in for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to click.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 */
	void doubleClick(PageElement locator, int timeout);

	/**
	 * Submit a web page element, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to submit.
	 */
	void submit(PageElement locator);

	/**
	 * Submit a web page element, waiting a maximum of the amount of time passed in for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to submit.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 */
	void submit(PageElement locator, int timeout);

	/**
	 * Type text on an element in a page, waiting a maximum of the amount of time passed in for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to type in.
	 * @param text The text to type.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
         * @param should_log Whether we should log what we are typing or not
	 */
	void type(PageElement locator, String text, int timeout, boolean should_log);

    /**
	 * Type text on an element in a page, waiting a maximum of the amount of time passed in for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to type in.
	 * @param text The text to type.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 */
    void type(PageElement locator, String text, int timeout);

    /**
	 * Type text on an element in a page, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to type in.
	 * @param text The text to type.
     * @param should_log Whether we should log what we are typing or not
	 */
	void type(PageElement locator, String text, boolean should_log);

	/**
	 * Type text on an element in a page, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to type in.
	 * @param text The text to type.
	 */
	void type(PageElement locator, String text);

	/**
	 * Get the text of an element, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to get the text from.
	 * @return The text contained in the element.
	 */
	String getText(PageElement locator);

	/**
	 * Get the text of an element, waiting a maximum of the amount of time passed in for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to get the text from.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 * @return The text contained in the element.
	 */
	String getText(PageElement locator, int timeout);

	/**
	 * Get the value of an attribute, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to get the attribute value from.
	 */
    void setSelected(PageElement locator);

	/**
	 * Select an input of type checkbox, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to select.
     * @param timeout The max amount of time to wait for locator to exist.
	 */
    void setSelected(PageElement locator, int timeout);

	/**
	 * Select an input of type checkbox, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to select.
     * @return true if the checkbox element is selected, false otherwise
	 */
    boolean isSelected(PageElement locator);

	/**
	 * Check if an input of type checkbox is selected, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to check.
     * @param timeout The max amount of time to wait for locator to exist.
     * @return true if the checkbox element is selected, false otherwise
	 */
    boolean isSelected(PageElement locator, int timeout);

	/**
	 * Check if an element is enabled
	 *
	 * @param locator The page element instance that describes how to find the element to check
	 * @return boolean
	 */
	boolean isEnabled(PageElement locator);

	/**
	 * Check if an element is enabled
	 *
	 * @param locator The page element instance that describes how to find the element to check
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds
	 * @return boolean
	 */
	boolean isEnabled(PageElement locator, int timeout);

	/**
	 * Set the state of an input of type checkbox
	 *
	 * @param locator The page element instance that describes how to find the element to set
	 * @param checked True if you want it checked, false otherwise
	 */
	void setCheckboxState(PageElement locator, boolean checked);

	/**
	 * Set the state of an input of type checkbox
	 *  
	 * @param locator The page element instance that describes how to find the element to set
	 * @param checked True if you want it checked, false otherwise
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds
	 */
	void setCheckboxState(PageElement locator, boolean checked, int timeout);

	/**
	 * Check if an input of type checkbox is selected, waiting a maximum of the default timeout for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to check.
	 * @param attribute The name of the attribute to retrieve the value for.
     * @return the value of the attribute
	 */
	String getAttribute(PageElement locator, String attribute);

	/**
	 * Get the value of an attribute, waiting a maximum of the amount of time passed in for the element to exist.
	 *
	 * @param locator The page element instance that describes how to find the element to get the attribute value from.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 * @param attribute The name of the attribute to retrieve the value for.
	 * @return The value of the attribute.
	 */
	String getAttribute(PageElement locator, int timeout, String attribute);

	/**
	 * Select an option of a drop down list by providing the text displayed in the list.  Wait a maximum of the default
	 * timeout for the select list to exist.
	 *
	 * @param selectList The page element instance that describes how to find the select list.
	 * @param option The text of the option displayed in the list that you want to select.
	 */
	void selectByOptionText(PageElement selectList, String option);

	/**
	 * Select an option of a drop down list by providing the text displayed in the list.  Wait a maximum of the amount
	 * of time passed in for the select list to exist.
	 *
	 * @param selectList The page element instance that describes how to find the select list.
	 * @param option The text of the option displayed in the list that you want to select.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 */
	void selectByOptionText(PageElement selectList, String option, int timeout);

	/**
	 * Select an option of a drop down list by providing the text of the value attribute.  Wait a maximum of the default
	 * timeout for the select list to exist.
	 *
	 * @param selectList The page element instance that describes how to find the select list.
	 * @param optionValue The text of the value attribute of the option you want to select.
	 */
	void selectByOptionValue(PageElement selectList, String optionValue);

	/**
	 * Select an option of a drop down list by providing the text of the value attribute.  Wait a maximum of the amount
	 * of time passed in for the select list to exist.
	 *
	 * @param selectList The page element instance that describes how to find the select list.
	 * @param optionValue The text of the value attribute of the option you want to select.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 */
	void selectByOptionValue(PageElement selectList, String optionValue, int timeout);

	/**
	 * Get the title of the current page in the browser.
	 *
	 * @return The title of the current page displayed in the browser.
	 */
	String getPageTitle();

    /**
	 * Get the title of the current page in the browser.
	 *
     * @param should_log Whether we should log what we are doing.
	 * @return The title of the current page displayed in the browser.
	 */
	String getPageTitle(boolean should_log);

	/**
	 * Get the html source of the current page in the browser.
	 *
	 * @return The html source of the current page displayed in the browser.
	 */
	String getPageSource();

    /**
	 * Get the html source of the current page in the browser.
	 *
     * @param should_log if false the method won't log messages.
	 * @return The html source of the current page displayed in the browser.
	 */
	String getPageSource(boolean should_log);

	/**
	 * Get the url of the current page in the browser.
	 *
	 * @return The url of the current page displayed in the browser.
	 */
	String getPageUrl();

    /**
	 * Get the url of the current page in the browser.
	 *
     * @param  should_log Whether we should log what we are doing.
	 * @return The url of the current page displayed in the browser.
	 */
	String getPageUrl(boolean should_log);

	/**
	 * Tell the browser to go to a specific URL.
	 *
	 * @param url The url that you want the browser to go to.
	 */
	void goTo(String url);

	/**
	 * Tell the browser to go back.
	 */
	void goBack();

	/**
	 * Tell the browser to go forward.
	 */
	void goForward();

	/**
	 * Get the underlying webdriver object.
	 *
	 * @return The webdriver object used by this instance of WebDriverWrapper.
	 */
	WebDriver getDriver();

	/**
	 * Wait the default timeout for page to exist.  Page provided must implement the SelfAwarePage's isCurrentPage
	 * method.
	 *
	 * @param page The class object of the SelfAwarePage to wait for.
	 */
	void waitFor(Class<? extends SelfAwarePage> page);

	/**
	 * Wait a max of the provided timeout for page to exist.  Page provided must implement the SelfAwarePage's
	 * isCurrentPage method.
	 *
	 * @param page The class object of the SelfAwarePage to wait for.
	 * @param timeout The amount of time (in seconds) to wait for the page to exist.
	 */
	void waitFor(Class<? extends SelfAwarePage> page, int timeout);

	/**
	 * Wait the default timeout for page to exist.  Page provided must implement the SelfAwarePage's isCurrentPage
	 * method.
	 *
	 * @param page an instance of the page SelfAwarePage
	 */
	void waitFor(SelfAwarePage page);

	/**
	 * Wait a max of the provided timeout for page to exist.  Page provided must implement the SelfAwarePage's
	 * isCurrentPage method.
	 *
	 * @param page an instance of the page SelfAwarePage
	 * @param timeout The amount of time (in seconds) to wait for the page to exist.
	 */
	void waitFor(SelfAwarePage page, int timeout);

	/**
	 * Wait for an element to exist a max of the default timeout.
	 *
	 * @param element The element to wait for.
	 */
	void waitFor(PageElement element);

	/**
	 * Wait for an element to exist a max of the provided timeout.
	 *
	 * @param element The element to wait for.
	 * @param timeout The amount of time (in seconds) to wait for the element to exist.
	 */
	void waitFor(PageElement element, int timeout);

	/**
	 * Wait not only for an element to exist, but for an element to be visible.  Use the default timeout.
	 *
	 * @param element The element to wait for.
	 */
	void waitForVisible(PageElement element);

	/**
	 * Wait not only for an element to exist, but for an element to be visible.  Use the timeout provided.
	 *
	 * @param element The element to wait for.
	 * @param timeout The maximum amount of time to wait.
	 */
	void waitForVisible(PageElement element, int timeout);

	/**
	 * Check for the existence of a web page element.  This is a quick check, no waiting is performed.
     * We log what what page element we are checking for.
	 *
	 * @param element The PageElement that describes where to find the element.
	 * @return true if the element exists and is accessible, false otherwise
	 */
	boolean exists(PageElement element);

    /**
	 * Check for the existence of a web page element.  This is a quick check, no waiting is performed.
	 *
	 * @param element The PageElement that describes where to find the element.
     * @param should_log Whether we should log what page element we are checking for.
	 * @return true if the element exists and is accessible, false otherwise
	 */
	boolean exists(PageElement element, boolean should_log);

	/**
	 * Check for the existence of a SelfAwarePage.  This is a non-waiting check, unless the page provided waits for
	 * elements on the page.  This is the same as getting an instance of the page, then calling isCurrentPage.
	 *
	 * @param page The class object of the SelfAwarePage to wait for.
	 * @return the return value of the page's isCurrentPage.
	 */
	boolean exists(Class<? extends SelfAwarePage> page);

	/**
	 * "Handle" the page, or call the page's handlePage method, passing in the context object provided.  This usually
	 * means filling out a form on the page, or clicking a particular link based on information in the context object.
	 *
	 * @param page The page to "handle".
	 * @param context The context object (type determined by the page class)
     * @param <T> The type is determined by the page's handlePage method parameter
     * @throws Exception if the page's handlePage throws an exception.
	 */
	<T> void handlePage(Class<? extends SelfAwarePage<T>> page, T context) throws Exception;

	/**
	 * Check to see if the page that bet browser is on is described by the following self aware page class.
	 *
	 * @param page The page class that is to be checked against the current page the browser is on.
	 * @return true if the page class' isCurrentPage method returns true.
	 */
	boolean isCurrentPage(Class<? extends SelfAwarePage> page);

	/**
	 * Get the browser window handle of the current window.  This is a non-waiting function.
	 *
	 * @return The window handle of the current window.
	 */
	String getWindowHandle();

	/**
	 * Get a set containing all the browser window handles.  This is a non-waiting function.
	 *
	 * @return A set containing all the browser window handles.
	 */
	Set<String> getWindowHandles();

	/**
	 * Switch to the browser window using the specified window handle.  The handle can be obtained from getWindowHandle or getWindowHandles
	 *
	 * @param windowHandle The handle of the browser window you want to switch to.
	 */
	void switchToWindowByHandle(String windowHandle);

    /**
	 * Switch to the browser window that contains the partial URL string.  Wait a maximum of the default
	 * timeout for the select list to exist.
	 *
	 * @param partialWindowURL Part of the url of the browser window you want to switch to.
	 */
	void switchToWindowByURLContains(String partialWindowURL);

	/**
	 * Switch to the browser window that contains the partial URL string.  Wait the specified timeout
	 * for the switch to window to be successful
	 * @param windowURL Part of the url of the browser window you want to switch to.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 */
	void switchToWindowByURLContains(String windowURL, int timeout);

	/**
	 * Switch to the browser window that contains the specified URL or partial URL.  Wait a maximum of the default
	 * timeout for the select list to exist.
	 * @param windowURL The url of the browser window you want to switch to.
	 */
	void switchToWindowByURL(String windowURL);

	/**
	 * Switch to the browser window that contains the specified URL or partial URL.  Wait the specified timeout
	 * for the switch to window to be successful
	 * @param windowURL The url of the browser window you want to switch to.
	 * @param timeout The maximum amount of time to wait for the element to exist in seconds.
	 */
	void switchToWindowByURL(String windowURL, int timeout);

	/**
	 * Closes the current browser window.  This is a non-waiting function.
	 */
	void closeWindow();

	/**
	 * Closes the the specified browser window.  This is a non-waiting function, the window must currently exist.
	 * @param windowHandle The windowHandle to switch to and close.  You can use the getWindowHandle function to get this handle.
	 */
	void closeWindow(String windowHandle);

	/**
	 * Checks whether the specified page element is visible.  This is a non-waiting function, the page element must currently exist.
	 * We log what element we are checking the visibility for.
     *
     * @param locator The page element to check visibility on
     * @return true if the element is visible, false otherwise
	 */
	boolean isVisible(PageElement locator);

    /**
	 * Checks whether the specified page element is visible.  This is a non-waiting function, the page element must currently exist.
     *
     * @param should_log Whether we should log what page element we are checking the visibility for.
     * @param locator The page element to check visibility on
     * @return true if the element is visible, false otherwise
	 */
	boolean isVisible(PageElement locator, boolean should_log);

	/**
	 * Take a screenshot, naming it automatically.  This will be placed in the testcase's log directory.
	 */
	void takeScreenShot();

	/**
	 * Take a screenshot, naming it with the provided name.  If ".png" is not at the end of the filename it will be
	 * added.  Also a number may be attached to help keep order.
	 *
	 * @param name The name to give to the screenshot file
	 */
	void takeScreenShot(String name);
        
    /**
	 * Saves the current page HTML source to a file, naming the file automatically.  This will be placed in the testcase's log directory.
	 */
	void saveHTMLSource();

	/**
	 * Saves the current page HTML source to a file, naming file with the provided name.  If ".html" is not at the end of the filename it will be
	 * added.  Also a number may be attached to help keep order.
	 *
	 * @param name Save the HTML source to a file with the provided name
	 */
	void saveHTMLSource(String name);

	/**
	 * Reopen a new browser with the same configuration as the current one.
	 */
	void reopen();

    /**
	 * Close all of the browser windows except the original one.
	 */
    void closeAllWindowsExceptOriginal();

    /**
	 * Log a message to a separate file that can be a "session" file.
     *
     * @param name The name of the file to append to
     * @param logString The message to log
	 */
    void logToSessionFile(String name, String logString);

    /**
	 * Gets the first selected option and returns the text from that option.
	 *
	 * @param selectList that you want to get the first selected option from
     * @return The text of the first selected option
	 */
    String getFirstSelectedOptionText(PageElement selectList);

    /**
	 * Gets the first selected option and returns the text from that option.
	 *
	 * @param selectList that you want to get the first selected option from
     * @param timeout The maximum amount of time to wait for the element to exist in seconds.
     * @return The text of the first selected option
	 */
    String getFirstSelectOptionText(PageElement selectList, int timeout);

    /**
     * Hover the mouse over an element, firing a mouse over event.  This is particularly useful for pages
     * that use javascript events to display something when the mouse hovers over the element.
     *
     * The default timeout for searching for the element applies.
     *
     * @param element The element to mouse over.
     */
    void hover(PageElement element);

    /**
     * Hover the mouse over an element, firing a mouse over event.  This is particularly useful for pages
     * that use javascript events to display something when the mouse hovers over the element.
     *
     * The timeout is for how long to search for the element to exist.
     *
     * @param element The element to mouse over.
     * @param timeout The maximum amount of time to wait for the element to exist in seconds.
     */
    void hover(PageElement element, int timeout);

    /**
	 * Wait for an element to be not visible.  Use the default timeout.
	 *
	 * @param element The element to wait for.
	 */
    void waitForNotVisible(PageElement element);

    /**
     * Wait for an element to be not visible.  Use the default timeout
     *
     * @param element The element to wait for.
     * @param timeout The maximum amount of time to wait.
     */
    void waitForNotVisible(PageElement element, int timeout);
        
    /**
	 * Wait for an element to not exist.  Use the default timeout.
	 *
	 * @param element The element to wait for.
	 */
    void waitForDoesNotExist(PageElement element);

    /**
     * Wait for an element to not exist.  Use the default timeout
     *
     * @param element The element to wait for.
     * @param timeout The maximum amount of time to wait.
     */
    void waitForDoesNotExist(PageElement element, int timeout);

    /**
     * Wait for an element's getText() to not be empty or null.  Use the default timeout.
     * This method will also wait for the element to exist.
     *
     * An example usage for this method would be to wait for an ajax error message.
     *
     * @param element The element to wait for the text to be not empty.
     */
    void waitForTextNotEmpty(PageElement element);

    /**
     * Wait for an element's getText() to not be empty or null.  Use the specified timeout.
     * This method will also wait for the element to exist.
     *
     * An example usage for this method would be to wait for an ajax error message.
     *
     * @param element The element to wait for the text to be not empty.
     * @param timeout The maximum wait time in seconds.
     */
    void waitForTextNotEmpty(PageElement element, int timeout);

    /**
     * Use actions on a PageWithActions.  Basically any class (page) that implements the interface
     * PageWithActions, and has a default no-arg constructor can be instantiated.  Example would look like
     * this:
     * <code>
     *     browser.on(MyExamplePage.class).doSomething("interesting");
     * </code>
     *
     * @param <T> The java type that get's created.
     * @param page The page class.
     * @return an instance of the page initialized with the browser object.
     */
    <T extends PageWithActions> T on(Class<T> page);

	/**
	 * Use actions on a PageWithActions.  Basically any instance of a class (page) that implements the interface
	 * PageWithActions.  We will call the initializePage method on the page instance. Example would look like
	 * this:
	 * <code>
	 *     browser.on(MyExamplePage).doSomething("interesting");
	 * </code>
	 *
	 * @param page an instance of the page.
	 * @return Class the class of the instantiated page
	 */
	<T extends PageWithActions> T on (T page);
}
