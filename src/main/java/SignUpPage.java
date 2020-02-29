import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SignUpPage {
    WebDriver driver;
    WebDriverWait wait;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 5);
    }

    @FindBy(id = "register-email")
    private WebElement emailField;
    @FindBy(id = "register-confirm-email")
    private WebElement confirmEmailField;
    @FindBy(id = "register-password")
    private WebElement passwordlField;
    @FindBy(id = "register-displayname")
    private WebElement nameField;
    @FindBy(id = "register-dob-day")
    private WebElement dayOfBirthField;
    @FindBy(id = "register-dob-month")
    private WebElement monthOfBirthDropDown;
    @FindBy(id = "register-dob-year")
    private WebElement yearOfBirthField;
    @FindBy(id="register-thirdparty")
    private WebElement shareMyRegistrationCheckBox;
    @FindBy(id="register-button-email-submit")
    private WebElement registerButton;
    @FindBy(xpath = "//label[@class='has-error' and string-length(text())>0]")
    private List<WebElement> errorsLabel;

    // xpath locator for choosing a particular month from dropdown by text
    private String monthOfBirthDropDownOption = "//select[@id='register-dob-month']/option[text()=\"%s\"]";

    // xpath locator for choosing a gender from radio buttons
    private String genderRadioButton = "//li[@id='li-gender']/label[normalize-space()=\"%s\"]/input";

    // xpath locator for finding error element by text
    private String errorByText = "//label[@class='has-error' and text()=\"%s\"]";


    public SignUpPage typeEmail(String email) {
        emailField.sendKeys(email);
        return this;
    }
    public SignUpPage typeConfirmEmail(String email) {
        confirmEmailField.sendKeys(email);
        return this;
    }
    public SignUpPage typePassword(String password) {
        passwordlField.sendKeys(password);
        return this;
    }
    public SignUpPage typeName(String name) {
        nameField.sendKeys(name);
        return this;
    }
    public SignUpPage typeDay(String day) {
        dayOfBirthField.sendKeys(day);
        return this;
    }
    public SignUpPage setMonth(String month) {
        monthOfBirthDropDown.click();
        // after click we have to see the particular option in dropdown
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format(monthOfBirthDropDownOption, month))));
        driver.findElement(By.xpath(String.format(monthOfBirthDropDownOption, month))).click();
        return this;
    }
    public SignUpPage typeYear(String year) {
        yearOfBirthField.sendKeys(year);
        return this;
    }
    public SignUpPage setGender(String value) {
        driver.findElement(By.xpath(String.format(genderRadioButton, value))).click();
        return this;
    }
    public SignUpPage setShare(boolean value) {
        // if we pass true and checkBox = false then we make checked, and on the contrary
        // if we pass false and checkBox = true then we make unchecked
        if (!shareMyRegistrationCheckBox.isSelected() == value)
            shareMyRegistrationCheckBox.click();
        return this;
    }

    public SignUpPage submitRegistration() {
        registerButton.click();
        return this;
    }

    // get all errors from screen
    public List<WebElement> getErrors() {
        return errorsLabel;
    }

    // get all error from by number of all visible errors
    public String getErrorByNumber(int number) {
        return getErrors().get(number - 1).getText();
    }

    // check visibility of error by text
    public boolean isErrorVisible(String message) {
        return driver.findElements(By.xpath(String.format(errorByText, message))).size() > 0 &&
                driver.findElements(By.xpath(String.format(errorByText, message))).get(0).isDisplayed();
    }

}



















