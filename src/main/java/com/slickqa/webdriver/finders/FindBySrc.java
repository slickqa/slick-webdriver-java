package com.slickqa.webdriver.finders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jcorbett
 */
public class FindBySrc extends AbstractFindByParentBy
{
	private String srcText;

	public FindBySrc(String srcText)
	{
		this.srcText = srcText;
	}

	@Override
	public boolean matches(WebElement e)
	{
		String attrValue = e.getAttribute("src");
		return attrValue != null && attrValue.equals(srcText);
	}

	@Override
	public ArrayList<By> getParentBy()
	{
		return new ArrayList<By>(Arrays.asList(tagName("img")));
	}

	@Override
	public String toString()
	{
		return String.format("By src attribute matching '%s'.", srcText);
	}

}
