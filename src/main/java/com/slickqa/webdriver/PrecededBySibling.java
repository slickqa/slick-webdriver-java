package com.slickqa.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author slambson
 */
public class PrecededBySibling implements RelativeElement
{
	private PageElement precedingSibling;

	public PrecededBySibling(PageElement precedingSibling)
	{
		this.precedingSibling = precedingSibling;
	}

	public PrecededBySibling(By finder) {
		this.precedingSibling = new PageElement("Preceded By Sibling FindBy: " + finder.toString(), finder);
	}

	@Override
	public WebElement findElement(WebDriver browser, PageElement item, String tagName) throws NoSuchElementException
	{
		// fail fast, the child element will handle the timing
		WebElement relativeElement = precedingSibling.getElement(browser, 0);
		return relativeElement.findElement(By.xpath("following-sibling::" + tagName));
	}

	@Override
	public String getFindByDescription()
	{
		return "Relative child element found " + precedingSibling.getFindByDescription();
	}
}
