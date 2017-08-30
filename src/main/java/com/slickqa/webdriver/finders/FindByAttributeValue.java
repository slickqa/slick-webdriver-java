
package com.slickqa.webdriver.finders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;

/**
 */
public class FindByAttributeValue extends AbstractFindByParentBy
{

	String attribute;
	String value;

	public FindByAttributeValue(String attribute, String value)
	{
		this.attribute = attribute;
		this.value = value;
	}

	@Override
	public String toString()
	{
		return String.format("By attribute '%s' with value '%s.", attribute, value);
	}

	@Override
	public boolean matches(WebElement e)
	{
		String attrValue = e.getAttribute(attribute);
		return attrValue != null && attrValue.equals(value);
	}

	@Override
	public ArrayList<By> getParentBy()
	{
		return new ArrayList<By>(Arrays.asList(tagName("input")));
	}
}
