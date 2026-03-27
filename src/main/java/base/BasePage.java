package base;

import org.openqa.selenium.WebDriver;
import utils.ElementActions;

public class BasePage {

    protected WebDriver driver;
    protected ElementActions elementActions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.elementActions = new ElementActions(driver);
    }
}