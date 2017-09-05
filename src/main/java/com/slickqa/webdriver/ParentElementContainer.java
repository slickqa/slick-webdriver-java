package com.slickqa.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 *
 * @author jcorbett
 */
public class ParentElementContainer implements WebContainer
{
	private PageElement parent;

	public ParentElementContainer(PageElement parent)
	{
		this.parent = parent;
	}

	public ParentElementContainer(By finder) {
		this.parent = new PageElement("Parent FindBy: " + finder.toString(), finder);
	}

	@Override
	public WebElement findElement(WebDriver browser, PageElement item) throws NoSuchElementException
	{
		// fail fast, the child element will handle the timing
		return parent.getElement(browser, 0).findElement(item.getFinder());
	}

	@Override
	public List<WebElement> findElements(WebDriver browser, PageElement item) throws NoSuchElementException
	{
		WebElement parentElement = parent.getElement(browser, 0);
		List<WebElement> childElements = parentElement.findElements(item.getFinder());
		return childElements;
	}

	@Override
	public String getFindByDescription()
	{
		return "Inside parent element found " + parent.getFindByDescription();
	}
}
