package com.slickqa.webdriver;

import com.slickqa.webdriver.finders.*;
import org.openqa.selenium.By;

/**
 * FindBy just extends the selenium By class adding some additional finders for locating PageElements
 *
 * @author slambson
 */
public abstract class FindBy extends By
{
	/**
	 * Find an image by it's source.  This must match exactly.
	 *
	 * @param srcValue The exact value of the src attribute.
	 * @return a By instance that finds web elements in web driver.
	 */
	public static By src(String srcValue)
	{
	    return new FindBySrc(srcValue);
	}

	/**
	 * Find an image by the "alt" attribute value.  Lot's of images have an alt attribute providing
	 * alternate text to display.  Use this method to find an image by this text.
	 *
	 * @param alt The text of the alt attribute to search for.
	 *
	 * @return a By instance that finds web elements in web driver.
	 */
	public static By alt(String alt)
	{
	    return new FindByAlt(alt);
	}

	/**
	 * Find an image by part of it's source text.  This is does not have to match exactly.
	 *
	 * @param srcContainsValue Part of the text in the src attribute.
	 * @return a By instance that finds web elements in web driver.
	 */
	public static By srcContains(String srcContainsValue)
	{
	    return new FindBySrcContains(srcContainsValue);
	}

	/**
	 * Find a link by it's href.  This must match exactly.
	 *
	 * @param hrefValue The exact value of the href attribute.
	 * @return a By instance that finds web elements in web driver.
	 */
	public static By href(String hrefValue)
	{
	    return new FindByHref(hrefValue);
	}

	/**
	 * Find a link by part of it's href text.  This is does not have to match exactly.
	 *
	 * @param hrefContainsValue Part of the text in the href attribute.
	 * @return a By instance that finds web elements in web driver.
	 */
	public static By hrefContains(String hrefContainsValue)
	{
	    return new FindByHrefContains(hrefContainsValue);
	}

	/**
	 * Find an input by it's value.  This must match exactly.
	 *
	 * @param valueText The exact value of the value attribute.
	 * @return a By instance that finds web elements in web driver.
	 */
	public static By value(String valueText)
	{
	    return new FindByValue(valueText);
	}

	/**
	 * Find an element by a specified attributes values.  This must match exactly.
	 *
	 * @param attribute The attribute of the page element to look for
	 * @param value The value of the attribute to locate the page element
	 * @return a By instance that finds web elements in web driver.
	 */
	public static By attributeValue(String attribute, String value)
	{
		return new FindByAttributeValue(attribute, value);
	}

	/**
	 * Find an element by the specified text.  This must match exactly.
	 *
	 * @param text The text inside the page element you want to locate
	 * @return a By instance that finds web elements in web driver.
	 */
	public static By text(String text)
	{
		return new FindByText(text);
	}
}

