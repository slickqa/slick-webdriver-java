package com.slickqa.webdriver;

/**
 * The PageInfFlowInterface interface enforces 3 methods: if the browser's current page is itself, how
 * to handle any forms on the page, how to continue past the page.  To help provide information to accomplish this
 * when you implement this interface you will need to define the type of context object that is used to handle the page.
 *
 * @author slambson
 */
public interface PageInFlowInterface<T> {

    /**
     * Determine if the browser's current page is the one represented by this class.  It is VERY important that this
     * be a quick check, not one that waits for 30 seconds before returning false.  You should pass in a 0 timeout to
     * whatever elements you look for in the browser.
     *
     * @return true if the current page is the one this class represents, false otherwise.
     */
    boolean isCurrentPage();

    /**
     * Handle any elements on the page, in accordance to information provided by the context object.
     *
     * @param context The context in which to handle the elements on the page.
     * @throws Exception In case the implementing method needs to throw an exception during the handling.
     */
    void handlePage(T context) throws Exception;

    /**
     * Continue past page, in accordance to information provided by the context object.
     *
     * @param context The context in which to handle the elements on the page.
     * @throws Exception In case the implementing method needs to throw an exception during the handling.
     */
    void completePage(T context) throws Exception;

    void setBrowserWrapper(WebDriverWrapper browserWrapper);
}
