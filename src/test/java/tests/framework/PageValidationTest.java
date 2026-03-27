package tests.framework;

import base.BasePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SignUpPage;
import utils.ElementActions;
import utils.WaitUtils;

public class PageValidationTest {

    @Test
    public void shouldRejectNullDriverForFrameworkClasses() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new BasePage(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> new ElementActions(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> new WaitUtils(null));
    }

    @Test
    public void shouldRejectUnsupportedProfileType() {
        SignUpPage signUpPage = new SignUpPage(TestDriverStub.newDriver());

        Assert.assertThrows(IllegalArgumentException.class, () -> signUpPage.selectProfile("relative"));
    }

    @Test
    public void shouldRejectUnsupportedGenderType() {
        SignUpPage signUpPage = new SignUpPage(TestDriverStub.newDriver());

        Assert.assertThrows(IllegalArgumentException.class, () -> signUpPage.selectGender("other"));
    }
}
