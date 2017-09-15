package com.slickqa.webdriver;

import org.openqa.selenium.By;

/**
 *
 * @author slambson
 */
public class Relative
{
	public static RelativeElement Decendent(PageElement decendent)
	{
		return new Descendant(decendent);
	}

	public static RelativeElement Decendent(By finder) {
		return new Descendant(finder);
	}

	public static RelativeElement PrecededBySibling(PageElement precededBySibling) {
		return new PrecededBySibling(precededBySibling);
	}

	public static RelativeElement PrecededBySibling(By finder) {
		return new PrecededBySibling(finder);
	}

	public static RelativeElement FollowedBySibling(PageElement followedBySibling) {
		return new FollowedBySibling(followedBySibling);
	}

	public static RelativeElement FollowedBySibling(By finder) {
		return new FollowedBySibling(finder);
	}
}
