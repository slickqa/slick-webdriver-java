
package com.slickqa.webdriver.finders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jcorbett
 */
public class FindByAlt extends AbstractFindByParentBy
{

	String altText;

	public FindByAlt(String altText)
	{
		this.altText = altText;
	}

	@Override
	public String toString()
	{
		return String.format("By alt text '%s'.", altText);
	}

	@Override
	public boolean matches(WebElement e)
	{
		String attrValue = e.getAttribute("alt");
		return attrValue != null && attrValue.equals(altText);
	}

	@Override
	public ArrayList<By> getParentBy()
	{
		return new ArrayList<By>(Arrays.asList(tagName("img"), tagName("input")));
	}
}
