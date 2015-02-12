import data.ContactFormMapper;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import model.ContactForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

/**
 * Created by ndealbera on 2/11/2015.
 */

/**
 * First of all it is necessary to use the JUnit annotation RunWith to declare the JUnitParamsRunner,
 * the runner will take care of the execution.
 */
@RunWith(JUnitParamsRunner.class)
public class JunitParamsTest {

    private final String TEST_DATA = "src/main/resources/data.csv";
    private final String TEST_ENVIRONMENT = "http://www.twinmeerkats.com/p/lab.html";

    /**
     * The test method will use the FileParameters annotation to bind the test with Mapper class, notice that it needs
     * 2 parameters:
     * value    the path to the CSV data file
     * mapper   the class that describes how to prepare the test data
     * @param contactForm   the method receives a parameter, that is an object
     *                      contained inside the return value of map method in ContactFormMapper class
     */
    @Test
    @FileParameters(value = TEST_DATA, mapper = ContactFormMapper.class)
    public void testForm(ContactForm contactForm) {
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get(TEST_ENVIRONMENT);

        WebElement nameField = driver.findElement(By.id("name"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement messageField = driver.findElement(By.id("message"));
        Select subjectSelect = new Select(driver.findElement(By.name("selection")));
        WebElement submitButton = driver.findElement(By.className("button"));

        nameField.sendKeys(contactForm.getName());
        emailField.sendKeys(contactForm.getEmail());
        messageField.sendKeys(contactForm.getMessage());
        subjectSelect.selectByValue(contactForm.getSubject());
        submitButton.click();

        //Perform assertions, as the form doesn't work, we don't have assertions to perform

        driver.quit();

    }
}
