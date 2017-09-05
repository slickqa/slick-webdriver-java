package com.slickqa.webdriver;

import org.openqa.selenium.By;

/**
 *
 * @author jcorbett
 */
public class In
{
	public static WebContainer Frame(String frameId)
	{
		return new FrameContainer(frameId);
	}

	public static WebContainer Frame(PageElement element)
	{
		return new FrameContainer(element);
	}

	public static WebContainer ParentElement(PageElement parent)
	{
		return new ParentElementContainer(parent);
	}

	public static WebContainer ParentElement(By finder) {
		return new ParentElementContainer(finder);
	}
}
