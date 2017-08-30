package com.slickqa.webdriver;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by slambson on 8/29/17.
 */
public class PageElements {

    private By finder;
    private ArrayList<PageElement> cache;
    private Date lastCacheSave;

    /**
     * Create a PageElements with a FindBy
     *
     * @param finder The By used to locate the PageElements, i.e. FindBy.id or FindBy.cssSelector
     * @return PageElement instance
     */
    public PageElements(By finder) {
        this.finder = finder;
        this.cache = null;
        this.lastCacheSave = null;
    }

    public boolean cacheIsValid()
    {
        if (lastCacheSave == null)
            return true;
        return false;
    }

    /**
     * Return the FindBy description of the PageElement
     *
     * @return String a description of the FindBy for the PageElement
     */
    public String getFindByDescription() {
        return finder.toString();
    }

    /**
     * Return a list of PageElements based on the PageElements FindBy (By)
     *
     * @return ArrayList<PageElement> the list of PageElements located
     */
    public ArrayList<PageElement> getElements(WebDriver browser, int timeout) throws NoSuchElementException
    {
        ArrayList<PageElement> pageElements = new ArrayList<PageElement>();

        // use the cache
        if (cache != null && cacheIsValid())
            return cache;

        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.SECOND, timeout);
        do {
            try {
                List<WebElement> webElements = browser.findElements(finder);
                for (WebElement e : webElements) {
                    pageElements.add(new PageElement(e));
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
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                }
            }
        } while (Calendar.getInstance().before(endTime) && pageElements.size() == 0);

        if (pageElements.size() == 0) {
            throw new NoSuchElementException("Was unable to find list of elements, to be found by " + getFindByDescription());
        }
        cache = pageElements;
        lastCacheSave = new Date();
        return pageElements;
    }
}
