package testng.example;

import com.slickqa.testng.SlickBaseTest;
import com.slickqa.testng.annotations.SlickMetaData;
import com.slickqa.testng.annotations.Step;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.text.MessageFormat;


public class ExampleTests extends SlickBaseTest {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ExampleTests.class);

    @Test(groups = { "testinggroups" })
    @SlickMetaData(title = "First Test",
            component = "First Component",
            feature = "First Feature",
            steps = {
                    @Step(step = "first step that isn't different", expectation = "first step worked"),
                    @Step(step = "second step", expectation = "second step worked"),
                    @Step(step = "weird, a third step", expectation = "that it is all over")
            })
    public void firstTest() throws Exception {
        System.out.println("ExampleTests.firstTest");
        logger.debug("****this is from the log appender!!");
        logStuff("ExampleTests.firstTest");
    }

    @Test
    @SlickMetaData(title = "Example Assertion Failure - Failed Test",
            component = "First Component",
            feature = "First Feature",
            steps = {
                    @Step(step = "first step that isn't different", expectation = "first step worked"),
                    @Step(step = "second step", expectation = "second step worked"),
                    @Step(step = "weird, a third step", expectation = "that it is all over")
            })
    public void assertionFailureTest() throws Exception {
        System.out.println("ExampleTests.assertionFailureTest");
        logStuff("ExampleTests.assertionFailureTest");
        Assert.assertTrue(false, "This is failing because of an assertion so it will be marked as Failed");
    }

    @Test
    @SlickMetaData(title = "Example Exception Thrown - Broken Test",
            component = "First Component",
            feature = "First Feature",
            steps = {
                    @Step(step = "first step that isn't different", expectation = "first step worked"),
                    @Step(step = "second step", expectation = "second step worked"),
                    @Step(step = "weird, a third step", expectation = "that it is all over")
            })
    public void exceptionFailureTest() throws Exception {
        System.out.println("ExampleTests.exceptionFailureTest");
        logStuff("ExampleTests.exceptionFailureTest");
        throw new Exception("This test threw an exception so it will be marked as Broken");
    }

    @Test
    @SlickMetaData(title = "Example Logging Test",
            component = "Another very fine component",
            feature = "A feature",
            steps = {
                    @Step(step = "first step that isn't different", expectation = "first step worked"),
                    @Step(step = "second step", expectation = "second step worked"),
                    @Step(step = "weird, a third step", expectation = "that it is all over")
            })
    public void exampleLoggingTest() throws Exception {
        System.out.println("ExampleTests.exampleLoggingTest");
        logger.debug("Hello");
        logger.info("This is from the slicklog appender");
        for(int i = 0; i < 10; i++) {
            logger.warn(MessageFormat.format("exampleLoggingTest This message is {0} of {1} {2}.", i + 1, 10, "messages"));
        }
        logStuff("ExampleTests.exampleLoggingTest");
    }

    @Test
    @SlickMetaData(title = "Example File Attach Test",
            component = "Another very fine component",
            feature = "A feature",
            steps = {
                    @Step(step = "first step", expectation = "first step worked"),
                    @Step(step = "second")
            })
    public void exampleFileAttachTest() throws Exception {
        System.out.println("ExampleTests.exampleFileAttachTest");
        File file = copyFileFromJarIfNeeded("screenshot.png");
        Path path = file.toPath();
        slickFileAttach().addFile(path);
        logStuff("ExampleTests.exampleFileAttachTest");
    }

    public static File copyFileFromJarIfNeeded(String fileName) throws IOException {
        File tempFile = new File(fileName);
        if (tempFile.exists()) {
            tempFile.delete();
        }
        InputStream link = (ExampleTests.class.getResourceAsStream(fileName));
        if (link != null) {
            Files.copy(link, tempFile.getAbsoluteFile().toPath());
        }
        else {
            tempFile = new File(fileName);
        }

        return tempFile;
    }

    public void logStuff(String test) {
        for (int i = 0; i < 10; i++) {
            if (i < 3) {
                logger.debug(test + " debug message: " + i);
            } else if (i >= 3 && i < 6) {
                logger.info(test + " info message: " + i);
            } else {
                logger.error(test + " error message: " + i);
            }
        }
    }

}
