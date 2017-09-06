package com.slickqa.webdriver;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * PageElement is essentially an equivalent to the selenium WebElement
 *
 * @author jcorbett
 */
public class PageElement 
{
	/**
	 * The amount of time (in milliseconds) which a cached WebElement (found by this class' getElement)
	 * is valid for, set to 3 seconds by default.
	 */
	public static int CACHE_VALID_FOR = 3000;

    private String name;
    private By finder;
    private WebContainer container;
    private WebElement cache;
    private Date lastCacheSave;
    private int elementIndex = -1;

    /**
     * Create a PageElement with a FindBy that is contained in a WebContainer (another PageElement).
     *
     * @param container A WebContainer is basically a PageElement that contains another PageElement.  It is a way to identify a parent/child relationship among PageElements
     * @param finder The By used to locate the PageElement, i.e. FindBy.id or FindBy.cssSelector
     * @return PageElement instance
     */
    public PageElement(WebContainer container, By finder) {
        this.name = name;
        this.container = container;
        this.finder = finder;
        this.cache = null;
        this.lastCacheSave = null;
    }

    /**
     * Create a PageElement with a FindBy that is contained in a WebContainer (another PageElement).   The name param is used primarily for logging.
     *
     * @param name This is a common name for the PageElement, used mostly in logging
     * @param container A WebContainer is basically a PageElement that contains another PageElement.  It is a way to identify a parent/child relationship among PageElements
     * @param finder The By used to locate the PageElement, i.e. FindBy.id or FindBy.cssSelector
     * @return PageElement instance
     */
    public PageElement(String name, WebContainer container, By finder) {
        this.name = name;
        this.container = container;
        this.finder = finder;
	    this.cache = null;
	    this.lastCacheSave = null;
    }

    /**
     * Create a PageElement with a FindBy that is contained in a WebContainer (another PageElement) and an elementIndex.   The name param is used primarily for logging.
     *
     * @param container A WebContainer is basically a PageElement that contains another PageElement.  It is a way to identify a parent/child relationship among PageElements
     * @param elementIndex The index of the PageElement from the list of PageElements located by the specified finder
     * @param finder The By used to locate the PageElement, i.e. FindBy.id or FindBy.cssSelector
     * @return PageElement instance
     */
    public PageElement(WebContainer container, By finder, int elementIndex) {
        this.name = name;
        this.container = container;
        this.finder = finder;
        this.cache = null;
        this.lastCacheSave = null;
        this.elementIndex = elementIndex;
    }

    /**
     * Create a PageElement with a FindBy that is contained in a WebContainer (another PageElement) and an elementIndex.   The name param is used primarily for logging.
     *
     * @param name This is a common name for the PageElement, used mostly in logging
     * @param container A WebContainer is basically a PageElement that contains another PageElement.  It is a way to identify a parent/child relationship among PageElements
     * @param elementIndex The index of the PageElement from the list of PageElements located by the specified finder
     * @param finder The By used to locate the PageElement, i.e. FindBy.id or FindBy.cssSelector
     * @return PageElement instance
     */
    public PageElement(String name, WebContainer container, By finder, int elementIndex) {
        this.name = name;
        this.container = container;
        this.finder = finder;
        this.cache = null;
        this.lastCacheSave = null;
        this.elementIndex = elementIndex;
    }

    /**
     * Create a PageElement with a FindBy.  The name param is used primarily for logging.
     *
     * @param name This is a common name for the PageElement, used mostly in logging
     * @param finder The FindBy used to locate the PageElement, i.e. FindBy.id or FindBy.cssSelector
     * @return PageElement instance
     */
    public PageElement(String name, By finder) {
        this.name = name;
        this.finder = finder;
        this.container = null;
	    this.cache = null;
	    this.lastCacheSave = null;
    }

    /**
     * Create a PageElement with a FindBy and an elementIndex.  The expectation is that the FindBy will locate a list of PageElements and we want a specific index in that list.
     * The name param is used primarily for logging.
     *
     * @param name This is a common name for the PageElement, used mostly in logging
     * @param finder The By used to locate the PageElement, i.e. FindBy.id or FindBy.cssSelector
     * @param elementIndex The index of the PageElement from the list of PageElements located by the specified finder
     * @return PageElement instance
     */
    public PageElement(String name, By finder, int elementIndex) {
        this.name = name;
        this.finder = finder;
        this.container = null;
        this.cache = null;
        this.lastCacheSave = null;
        this.elementIndex = elementIndex;
    }

    /**
     * Create a PageElement with a FindBy and an elementIndex.
     *
     * @param finder The By used to locate the PageElement, i.e. FindBy.id or FindBy.cssSelector
     * @return PageElement
     */
    public PageElement(By finder) {
        name = finder.toString();
        this.finder = finder;
        this.container = null;
	    this.cache = null;
	    this.lastCacheSave = null;
    }

    /**
     * Create a PageElement with a FindBy and an elementIndex.  The expectation is that the FindBy will locate a list of PageElements and we want a specific index in that list.
     *
     * @param finder The By used to locate the PageElement, i.e. FindBy.id or FindBy.cssSelector
     * @param elementIndex The index of the PageElement from the list of PageElements located by the specified finder
     * @return PageElement instance
     */
    public PageElement(By finder, int elementIndex) {
        name = finder.toString();
        this.finder = finder;
        this.container = null;
        this.cache = null;
        this.lastCacheSave = null;
        this.elementIndex = elementIndex;
    }

	/**
	 * Create a PageElement from an existing WebElement.  Normally you shouldn't do this, and by doing this exists()
	 * will always return true.  This is primarily here so we can return a list of child PageElements.
	 * 
	 * @param element The web element to create a page element from.
	 */
	public PageElement(WebElement element)
	{
			name = "Pre Existing Webdriver WebElement";
			this.finder = null;
			this.container = null;
			this.cache = element;
			this.lastCacheSave = null;
            this.elementIndex = -1;

    }

    /**
     * Return the By for locating the PageElement
     *
     * @return By the By for the PageElement
     */
    public By getFinder() {
        return finder;
    }


    /**
     * Return the Name (used primarily in logging) of the PageElement
     *
     * @return String the name of the PageElement
     */
    public String getName() {
        return name;
    }

    public boolean cacheIsValid()
    {
		if (lastCacheSave == null)
			return true; // special case: if we have a cached element, and no save date we always want to use that element
		return false;
    }

    /**
     * Return a selenium WebElement based on the PageElement FindBy (By)
     *
     * @param browser The webdriver browser instance
     * @param timeout The max amount of time to wait for the element
     * @return WebElement the selenium WebElement based on the PageElement FindBy (By)
     */
    public WebElement getElement(WebDriver browser, int timeout) throws NoSuchElementException 
	{
		// use the cache
		if (cache != null && cacheIsValid())
			return cache;

        WebElement element = null;
        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.SECOND, timeout);
        // Fixing Issue #17 - Need to be able to pass in a timeout of 0, or have no timeout to the PageElement.exists function
        // We need to ensure this executes at least once such as in
        // the case of the timeout being zero
        do {
            try {
                if (container == null) {
                    if (elementIndex == -1) {
                        element = browser.findElement(finder);
                    }
                    else {
                        List<WebElement> elements = browser.findElements(finder);
                        if (elements.size() < (elementIndex + 1)) {
                            element = null;
                        }
                        else {
                            element = elements.get(elementIndex);
                        }
                    }
                } else {
                    if (elementIndex == -1) {
                        element = container.findElement(browser, this);
                    }
                    else {
                        List<WebElement> elements = container.findElements(browser, this);
                        if (elements.size() < (elementIndex + 1)) {
                            element = null;
                        }
                        else {
                            element = elements.get(elementIndex);
                        }
                    }
                }
                if (element != null) {
                    element.isEnabled(); // cause a NoSuchElementException if it can't find it
                }
            } catch (NoSuchElementException ex) {
                element = null;
            } catch (StaleElementReferenceException ex) {
                element = null;
            } catch (NoSuchFrameException ex) {
                element = null;
            } catch (WebDriverException ex) {
				element = null;
				if (ex.getMessage().contains("this.getWindow() is null"))
					browser.switchTo().defaultContent(); // hack for issue http://code.google.com/p/selenium/issues/detail?id=1438
			}

            // This is the same check as the while loop, but we don't want to sleep if we're already over
            // time (in the case that timeout == 0).
            if (element == null && Calendar.getInstance().before(endTime)) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                }
            }
        } while (Calendar.getInstance().before(endTime) && element == null);

        if (element == null) {
            if (elementIndex == -1) {
                throw new NoSuchElementException("Was unable to find element " + getName() + ", to be found by " + getFindByDescription());
            }
            else {
                throw new NoSuchElementException("Was unable to find element " + getName() + ", to be found by " + getFindByDescription() + " at index: " + elementIndex);
            }
        }
		cache = element;
		lastCacheSave = new Date();
        return element;
    }

    /**
     * Return a list of PageElements based on the PageElements FindBy (By)
     *
     * @return List<PageElement> the list of PageElements located
     */
    public List<PageElement> getElements(WebDriver browser, int timeout) throws NoSuchElementException {
        List<PageElement> pageElements = new ArrayList<PageElement>();

        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.SECOND, timeout);
        do {
            try {
                if (container == null) {
                    List<WebElement> webElements = browser.findElements(finder);
                    for (WebElement e : webElements) {
                        pageElements.add(new PageElement(e));
                    }
                } else {
                    List<WebElement> webElements = container.findElements(browser, this);
                    for (WebElement e : webElements) {
                        pageElements.add(new PageElement(e));
                    }
                }
            } catch (NoSuchElementException ex) {
                pageElements = new ArrayList<PageElement>();
            } catch (StaleElementReferenceException ex) {
                pageElements = new ArrayList<PageElement>();
            } catch (NoSuchFrameException ex) {
                pageElements = new ArrayList<PageElement>();
            } catch (WebDriverException ex) {
                pageElements = new ArrayList<PageElement>();
            }
            // This is the same check as the while loop, but we don't want to sleep if we're already over
            // time (in the case that timeout == 0).
            if (pageElements.size() == 0 && Calendar.getInstance().before(endTime)) {
                try {Thread.sleep(500);} catch (InterruptedException ie) {}
            }
        } while (Calendar.getInstance().before(endTime) && pageElements.size() == 0);

        if (pageElements.size() == 0) {
            throw new NoSuchElementException("Was unable to find list of elements, to be found by " + getFindByDescription());
        }
        lastCacheSave = new Date();
        return pageElements;
    }

    /**
     * Return the FindBy description of the PageElement
     *
     * @return String a description of the FindBy for the PageElement
     */
    public String getFindByDescription() {
		if (cache != null && lastCacheSave == null)
		{
			// We created the page element from an existing WebElement
			return "Page Element created from an existing webdriver WebElement.";
		}
        if (container != null) {
            return container.getFindByDescription() + " " + finder.toString();
        } else {
            return finder.toString();
        }
    }

    /**
     * Return whether the PageElement exists or not
     *
     * @return boolean whether the PageElement exists or not
     */
    public boolean exists(WebDriver browser, int timeout) {
        try {
            getElement(browser, timeout);
        } catch (NoSuchElementException ex) {
            return false;
        }

        return true;
    }
}
