package com.slickqa.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author slambson
 */
public class FollowedBySibling implements RelativeElement
{
	private PageElement followingSibling;
	private String tagName;

	public FollowedBySibling(PageElement followingSibling)
	{
		this.followingSibling = followingSibling;
	}

	public FollowedBySibling(By finder) {
		this.followingSibling = new PageElement("Followed By Sibling FindBy: " + finder.toString(), finder);
	}

	@Override
	public WebElement findElement(WebDriver browser, PageElement item, String tagName) throws NoSuchElementException
	{
		// fail fast, the child element will handle the timing
		WebElement relativeElement = followingSibling.getElement(browser, 0);
		return relativeElement.findElement(By.xpath("preceding-sibling::" + tagName + "[1]"));
	}

	@Override
	public String getFindByDescription()
	{
		return "Relative child element found " + followingSibling.getFindByDescription();
	}
}
