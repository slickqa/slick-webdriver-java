package com.slickqa.webdriver.finders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author slambson
 */
public class FindBySrcContains extends AbstractFindByParentBy
{
	private String srcContainsText;

	public FindBySrcContains(String srcContainsText)
	{
		this.srcContainsText = srcContainsText;
	}

	@Override
	public boolean matches(WebElement e)
	{
		String attrValue = e.getAttribute("src");
		return attrValue != null && attrValue.contains(srcContainsText);
	}

	@Override
	public ArrayList<By> getParentBy()
	{
		return new ArrayList<By>(Arrays.asList(tagName("img")));
	}

	@Override
	public String toString()
	{
		return String.format("By src attribute containing '%s'.", srcContainsText);
	}

}