
package com.slickqa.webdriver.finders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author slambson
 */
public class FindByText extends AbstractFindByParentBy
{
	String text;

	public FindByText(String text)
	{
		this.text = text;
	}

	@Override
	public String toString()
	{
		return String.format("By text '%s'.", text);
	}

	@Override
	public boolean matches(WebElement e)
	{
		String textValue = e.getText();
		return textValue != null && textValue.equals(text);
	}

	@Override
	public ArrayList<By> getParentBy()
	{
		return new ArrayList<By>(Arrays.asList(tagName("label"), tagName("div"), tagName("h1"), tagName("h2"), tagName("h3")));
	}
}
