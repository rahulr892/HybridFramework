package com.rahul.tests.login;

import com.rahul.browserSetup.TLDriver;
import com.rahul.pages.Login_Page;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class ValidateTitle {

    /*############################################################
     * ' Function Name: validate_PageTitle
	 * ' Purpose: Simple login test
	 */
    @Test(priority = 1, description = "test description text")
    public void validate_PageTitle(ITestContext context) throws Exception {
        String testname = "validate_PageTitle";
        String OS_Browser = context.getCurrentXmlTest().getParameter("OS_Browser");
        String url = context.getCurrentXmlTest().getParameter("URL");

        TLDriver.getDriver().get(url);

        Login_Page LoginPage = new Login_Page();

        LoginPage.wait_For_Login_PageLoad()
                .validate_Page_Title();
    }

}
