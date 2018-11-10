package com.rahul.pages;

import com.aventstack.extentreports.Status;
import com.rahul.base.PageBase;
import com.rahul.browserSetup.TLDriver;
import com.rahul.inputs.Constants;
import com.rahul.utilities.ExtentUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Login_Page extends PageBase {

    @FindBy(css = ".gmail-nav__nav-link.gmail-nav__nav-link__for-work")
    private WebElement forWork_Link;
    @FindBy(css = ".gmail-nav__nav-link.gmail-nav__nav-link__sign-in")
    private WebElement signIn_Link;
    @FindBy(css = ".gmail-nav__nav-link.gmail-nav__nav-link__create-account")
    private WebElement createAccout_Button;


    public Login_Page wait_For_Login_PageLoad() {
        ExtentUtil.fetchTest().log(Status.INFO, "Wait for login page to load");
        waitForAngularLoad();
        (new WebDriverWait(TLDriver.getDriver(), 20)).until(ExpectedConditions.and(
                ExpectedConditions.visibilityOf(forWork_Link),
                ExpectedConditions.visibilityOf(signIn_Link),
                ExpectedConditions.visibilityOf(createAccout_Button)));
        return this;
    }


    public Login_Page click_SignIn() {
        ExtentUtil.fetchTest().log(Status.INFO, "Clicking sign in link");
        click(signIn_Link);
        return this;
    }


    public Login_Page validate_Page_Title() {
        ExtentUtil.fetchTest().log(Status.INFO, "Validating page title");
        verifyEquals(pageTitle(), "Gmail - Free Storage and Email from Google", "Page title validation failed");
        return this;
    }


    public Login_Page verify_Color_On_Links() {
        ExtentUtil.fetchTest().log(Status.INFO, "Verify color on all links");
        List<WebElement> allLinks = TLDriver.getDriver().findElements(By.cssSelector("body a[href='#']"));
        for (WebElement ele : allLinks) {
            String color = ele.getCssValue("color");
            String colorInHex = Color.fromString(color).asHex();
            verifyEquals(colorInHex, "#026091", ele.getText() + " link color fail");
        }
        return this;
    }


    public Login_Page validate_Links_And_Images(String OS_Browser) {
        ExtentUtil.fetchTest().log(Status.INFO, "Validate links and images on page");
        if (OS_Browser.equalsIgnoreCase(Constants.Win7_Chrome)) {
            verifyLinks();
        }
        validateInvalidImages();
        return this;
    }


    public Login_Page take_Screenshot(String OS_Browser, String testname) {
        fullPageScreenshot(OS_Browser, testname);
        return this;
    }


}

