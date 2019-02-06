package com.slickqa.webdriver.example;

import com.slickqa.webdriver.OutputFileSupport;

/**
 * This output file support just throws everything in the current working directory
 */
public class DumbOutfileSupport implements OutputFileSupport {
    @Override
    public File getOutputFile(String filename) {
        return new java.io.File( "." + File.separator + filename );
    }

    @Override
    public OutputStream getOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(filename);
    }

    @Override
    public File getSessionOutputFile(String filename) {
        return getOutputFile(filename);
    }

    @Override
    public OutputStream getSessionOutputStream(String filename) throws FileNotFoundException {
        return getOutputStream(filename);
    }
}
