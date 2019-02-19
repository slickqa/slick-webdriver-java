package com.slickqa.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author slambson
 */
public class Descendant implements RelativeElement
{
	private PageElement descendant;

	public Descendant(PageElement descendant)
	{
		this.descendant = descendant;
	}

	public Descendant(By finder) {
		this.descendant = new PageElement("Descendant FindBy: " + finder.toString(), finder);
	}

	@Override
	public WebElement findElement(WebDriver browser, PageElement item, String tagName) throws NoSuchElementException
	{
		// fail fast, the descendant element will handle the timing
		WebElement relativeElement = descendant.getElement(browser, 0);
		  // the position forces it to the nearest ancestor
		return relativeElement.findElement(By.xpath("ancestor::" + tagName + "[position()=1]"));
	}

	@Override
	public String getFindByDescription()
	{
		return "Relative descendant element found " + descendant.getFindByDescription();
	}
}
