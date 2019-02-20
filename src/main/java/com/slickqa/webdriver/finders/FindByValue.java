package com.slickqa.webdriver.finders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jcorbett
 */
public class FindByValue extends AbstractFindByParentBy
{
	private String valueText;

	public FindByValue(String valueText)
	{
		this.valueText = valueText;
	}

	@Override
	public boolean matches(WebElement e)
	{
		String attrValue = e.getAttribute("value");
		return attrValue != null && attrValue.equals(valueText);
	}

	@Override
	public ArrayList<By> getParentBy()
	{
		return new ArrayList<By>(Arrays.asList(tagName("input")));
	}

	@Override
	public String toString()
	{
		return String.format("By value attribute matching '%s'.", valueText);
	}

}
