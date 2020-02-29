import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class SignUpTest {
    private WebDriver driver;
    private SignUpPage signUpPage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.spotify.com/uk/signup/");
        signUpPage = PageFactory.initElements(driver, SignUpPage.class);
    }

    @Test
    public void typeInvalidYear() {
        signUpPage.typeDay("20")
                .setMonth("November")
                .typeYear("65")
                .setShare(true);
        Assert.assertTrue(signUpPage.isErrorVisible("Please enter a valid year."));
        Assert.assertFalse(signUpPage.isErrorVisible("When were are you born?"));
    }

    @Test
    public void typeValidMonth() {
        signUpPage.setMonth("November");
        Assert.assertFalse(signUpPage.isErrorVisible("Please enter your birth month."));
    }

    @Test
    public void typeInvalidEmail() {
        signUpPage.typeEmail("test@gmail.test");
        signUpPage.typeConfirmEmail("dfgdg@gmail.test");
        signUpPage.typeName("Testname");
        signUpPage.submitRegistration();
        Assert.assertTrue(signUpPage.isErrorVisible("Email address doesn't match."));
    }

    @Test
    public void signUpWithEmptyPassword() {
        signUpPage.typeEmail("test@gmail.com")
                .typeConfirmEmail("test@gmail.com")
                .typeName("Testname")
                .submitRegistration();
        Assert.assertTrue(signUpPage.isErrorVisible("Enter a password to continue."));
    }

    @Test
    public void typeInvalidValues() {
        signUpPage.typeEmail("testmail")
                .typeConfirmEmail("test@gmail.com")
                .typePassword("12345698")
                .typeName("testname")
                .typeDay("12")
                .setGender("Male")
                .setShare(false)
                .submitRegistration();
        Assert.assertEquals(5, signUpPage.getErrors().size());
        Assert.assertEquals("Please enter a valid year.", signUpPage.getErrorByNumber(4));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}


