
package com.slickqa.webdriver.finders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jcorbett
 */
public class FindByAttributeValue extends AbstractFindByParentBy
{
    String attribute;
	String attributeValu;

	public FindByAttributeValue(String attribute, String attributeValu)
	{
		this.attribute = attribute;
		this.attributeValu = attributeValu;
	}

	@Override
	public String toString()
	{
		return String.format("By attribute '%s' with value '%s'.", attribute, attributeValu);
	}

	@Override
	public boolean matches(WebElement e)
	{
		String attrValue = e.getAttribute(attribute);
		return attrValue != null && attrValue.equals(attributeValu);
	}

	@Override
	public ArrayList<By> getParentBy()
	{
		return new ArrayList<By>(Arrays.asList(tagName("img"), tagName("input"), tagName("table"), tagName("tr"), tagName("td"), tagName("div")));
	}
}
