package com.slickqa.webdriver.finders;


import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrBy extends By {
    private List<By> finders;

    public OrBy(By first, By ...others) {
        finders = new ArrayList<By>(others.length + 1);
        finders.add(first);
        finders.addAll(Arrays.asList(others));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(By finder : finders) {
            if(first) {
                first = false;
            } else {
                sb.append(" Or ");
            }
            sb.append(finder.toString());
        }
        return sb.toString();
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
        ArrayList<WebElement> retval = new ArrayList<WebElement>();
        for(By finder : finders) {
            retval.addAll(finder.findElements(context));
        }
        return retval;
    }
}
