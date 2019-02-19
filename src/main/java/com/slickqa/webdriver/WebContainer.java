package com.slickqa.webdriver;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *
 * @author jcorbett
 */
public interface WebContainer
{
	public WebElement findElement(WebDriver browser, PageElement item) throws NoSuchElementException;

	public List<WebElement> findElements(WebDriver browser, PageElement item) throws NoSuchElementException;

	public String getFindByDescription();
}
