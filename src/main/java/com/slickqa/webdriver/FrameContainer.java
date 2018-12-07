package com.slickqa.webdriver;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jcorbett
 */
public class FrameContainer implements WebContainer
{

	private String frameId = null;
	private PageElement framePageElement;
	private boolean noFrameId = false;
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger("test." + FrameContainer.class.getName());

	public FrameContainer(String frameId)
	{
		this.frameId = frameId;
	}
	// leeard 2/15/11 - adding ability to accept a PageElement for dynamic frame ID support

	public FrameContainer(PageElement framePageElement)
	{
		this.framePageElement = framePageElement;
	}

	@Override
	public WebElement findElement(WebDriver browser, PageElement item) throws NoSuchElementException
	{
		//browser.switchTo().frame(frameId);
		//browser.switchTo().defaultContent();
		noFrameId = false;
		WebElement frameWebElement = null;
		WebElement element = null;

		// checking for the case of a PageElement being passed in
		if(frameId == null)
		{
            frameWebElement = framePageElement.getElement(browser, 30);
            noFrameId = true;
            frameId = null;
		}
		try
		{
			if(frameWebElement == null)
			{
				String[] frames = frameId.split("\\.");
				for(String frame : frames)
				{
					browser.switchTo().frame(frame);
				}
			} else
			{
				if(InFrameWebElement.class.isAssignableFrom(frameWebElement.getClass()))
				{
					((InFrameWebElement)frameWebElement).beforeOperation();
					browser.switchTo().frame(((InFrameWebElement)frameWebElement).real);
				} else
				{
					browser.switchTo().frame(frameWebElement);
				}
			}

			element = browser.findElement(item.getFinder());
		} finally
		{
			browser.switchTo().defaultContent();
		}

		WebElement retval = null;
		if(frameWebElement == null)
		{
			retval = new InFrameWebElement(item.getFinder(), browser, frameId);
		} else
		{
			retval = new InFrameWebElement(item.getFinder(), browser, frameWebElement);
		}

		return retval;
	}

	@Override
	public List<WebElement> findElements(WebDriver browser, PageElement item) throws NoSuchElementException
	{
		//return parent.getElement(browser, 0).findElements(item.getFinder());

		noFrameId = false;
		WebElement frameWebElement = null;
		List<WebElement> elements = null;

		// checking for the case of a PageElement being passed in
		if(frameId == null)
		{
			frameWebElement = framePageElement.getElement(browser, 30);
			noFrameId = true;
			frameId = null;
		}
		try
		{
			if(frameWebElement == null)
			{
				String[] frames = frameId.split("\\.");
				for(String frame : frames)
				{
					browser.switchTo().frame(frame);
				}
			} else
			{
				if(InFrameWebElement.class.isAssignableFrom(frameWebElement.getClass()))
				{
					((InFrameWebElement)frameWebElement).beforeOperation();
					browser.switchTo().frame(((InFrameWebElement)frameWebElement).real);
				} else
				{
					browser.switchTo().frame(frameWebElement);
				}
			}

			elements = browser.findElements(item.getFinder());
		} finally
		{
			browser.switchTo().defaultContent();
		}

		List<WebElement> retval = new ArrayList<WebElement>();

		int indexCounter = 0;
		for (WebElement e : elements) {
			if (frameWebElement == null) {
				retval.add(new InFrameWebElement(item.getFinder(), browser, frameId, indexCounter));
			} else {
				retval.add(new InFrameWebElement(item.getFinder(), browser, frameWebElement, indexCounter));
			}

			indexCounter += 1;
		}

		return retval;
	}

	@Override
	public String getFindByDescription()
	{
		if(noFrameId)
		{
			return "In Frame 'frame without an id'";
		} else
		{
			return "In Frame '" + frameId + "'";
		}
	}
}
