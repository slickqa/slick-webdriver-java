/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slickqa.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;

import java.util.List;

/**
 *
 * @author jcorbett
 */
public class ProxyWebElement implements WebElement, Locatable
{
	protected WebElement real;
	protected WebDriver driver;

	public ProxyWebElement(WebElement real, WebDriver driver)
	{
		this.real = real;
		this.driver = driver;
	}

	protected void beforeOperation()
	{
	}

	protected void afterOperation()
	{
	}

	@Override
	public void click()
	{
		try
		{
			beforeOperation();
			real.click();
		} finally
		{
			afterOperation();
		}
	}

	@Override
	public void submit()
	{
		try
		{
			beforeOperation();
			real.submit();
		} finally
		{
			afterOperation();
		}
	}

	@Override
	public void sendKeys(CharSequence... keysToSend)
	{
		try
		{
			beforeOperation();
			real.sendKeys(keysToSend);
		} finally
		{
			afterOperation();
		}
	}

	@Override
	public void clear()
	{
		try
		{
			beforeOperation();
			real.clear();
		} finally
		{
			afterOperation();
		}
	}

	@Override
	public String getTagName()
	{
		String retval = null;
		try
		{
			beforeOperation();
			retval = real.getTagName();
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public String getAttribute(String name)
	{
		String retval = null;
		try
		{
			beforeOperation();
			retval = real.getAttribute(name);
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public boolean isSelected()
	{
		boolean retval = false;
		try
		{
			beforeOperation();
			retval = real.isSelected();
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public boolean isEnabled()
	{
		boolean retval = false;
		try
		{
			beforeOperation();
			retval = real.isEnabled();
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public String getText()
	{
		String retval = null;
		try
		{
			beforeOperation();
			retval = real.getText();
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public List<WebElement> findElements(By by)
	{
		List<WebElement> retval = null;
		try
		{
			beforeOperation();
			retval = real.findElements(by);
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public WebElement findElement(By by)
	{
		WebElement retval = null;
		try
		{
			beforeOperation();
			retval = real.findElement(by);
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public boolean isDisplayed()
	{
		boolean retval = false;
		try
		{
			beforeOperation();
			retval = real.isDisplayed();
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public Point getLocation()
	{
		Point retval = null;
		try
		{
			beforeOperation();
			retval = real.getLocation();
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public Dimension getSize()
	{
		Dimension retval = null;
		try
		{
			beforeOperation();
			retval = real.getSize();
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public Rectangle getRect() {
		Rectangle retval = null;
		try
		{
			beforeOperation();
			retval = real.getRect();
		} finally {
			afterOperation();
		}
		return retval;
	}

	@Override
	public String getCssValue(String string)
	{
		String retval = null;
		try
		{
			beforeOperation();
			retval = real.getCssValue(string);
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	public Point getLocationOnScreenOnceScrolledIntoView()
	{
		Point retval = null;
		try
		{
			beforeOperation();
			retval = ((Locatable)real).getCoordinates().onScreen();
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public Coordinates getCoordinates()
	{
		Coordinates retval = null;
		try
		{
			beforeOperation();
			retval = ((Locatable)real).getCoordinates();
		} finally
		{
			afterOperation();
		}
		return retval;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
		X retval = null;
		try {
			beforeOperation();
			retval = real.getScreenshotAs(outputType);
		} finally {
			afterOperation();
		}
		return retval;
	}
}
