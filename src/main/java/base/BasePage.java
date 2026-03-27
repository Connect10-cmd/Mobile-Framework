package base;

import org.openqa.selenium.WebDriver;
import utils.ElementActions;

public class BasePage {

    protected final WebDriver driver;
    protected final ElementActions elementActions;

    public BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null while creating page objects");
        }
        this.driver = driver;
        this.elementActions = new ElementActions(driver);
    }
}
