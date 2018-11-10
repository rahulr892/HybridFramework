package com.rahul.pages;

import com.aventstack.extentreports.Status;
import com.rahul.base.PageBase;
import com.rahul.browserSetup.TLDriver;
import com.rahul.inputs.Constants;
import com.rahul.utilities.ExtentUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignIn_Page extends PageBase {

    @FindBy(css = "#identifierId")
    private WebElement email_Input;
    @FindBy(linkText = "Forgot email?")
    private WebElement forgotEmail_Link;
    @FindBy(linkText = "Create account")
    private WebElement createAccout_Link;
    @FindBy(css = "#identifierNext > content > span")
    private WebElement next_Button;


    public SignIn_Page wait_For_SignIn_PageLoad() {
        ExtentUtil.fetchTest().log(Status.INFO, "Wait for signin page to load");
        waitForAngularLoad();
        (new WebDriverWait(TLDriver.getDriver(), 20)).until(ExpectedConditions.and(
                ExpectedConditions.visibilityOf(email_Input),
                ExpectedConditions.visibilityOf(forgotEmail_Link),
                ExpectedConditions.visibilityOf(next_Button)));
        return this;
    }


    public SignIn_Page type_Email(String text) {
        ExtentUtil.fetchTest().log(Status.INFO, "Typing email: " + text);
        type(email_Input, text);
        return this;
    }

    public SignIn_Page click_Next() {
        ExtentUtil.fetchTest().log(Status.INFO, "Clicking next button");
        click(next_Button);
        waitForSync(3);
        return this;
    }

    public SignIn_Page validate_Links_And_Images(String OS_Browser) {
        ExtentUtil.fetchTest().log(Status.INFO, "Validate links and images on page");
        if (OS_Browser.equalsIgnoreCase(Constants.Win7_Chrome)) {
            verifyLinks();
        }
        validateInvalidImages();
        return this;
    }


    public SignIn_Page take_Screenshot(String OS_Browser, String testname) {
        fullPageScreenshot(OS_Browser, testname);
        return this;
    }


}

