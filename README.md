# slick-webdriver-java
Slick webdriver java wrapper library

The code in the examples directory show how to utilize this project best.  There are 2 interfaces provided that your Page classes can implement.

INTERFACES
> -SelfAwarePage
> - isCurrentPage() - this method needs to be implemented to define how webdriver can identify that this page is the current page being displayed (i.e. certain element(s) exist)
> - handlePage() - this method needs to be implemented to define a default action for interacting with this page
>
> -PageWithActions
> - initializePage() - this method needs to be implemented to initilize the page with the browser wrapper instance
> - this interface allows your test method to do something like this: browser.on(GoogleHomePage.class).searchFor("slickqa");
>
>
CLASSES
> -PageElement
> - Provides a nice way to encapsulate webdriver functionality for locating elements on a Page.  Allows your Page class to have a line like this:
> - public static PageElement textResultStats = new PageElement("Results Statistics", FindBy.id("resultStats"));
>
> -FindBy
> - extends the org.openqa.selenium.By Class to include some additional ways to locate elements on a Page
>
> -WebContainer
> - Provides a way to define elements that contain other elements
>
> -FrameContainer
> - Provides some encapsulation for interacting with elements that are inside a frame on a Page
