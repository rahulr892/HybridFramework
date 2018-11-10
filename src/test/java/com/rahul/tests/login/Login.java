package com.rahul.tests.login;

import com.rahul.base.TestBase;
import com.rahul.browserSetup.TLDriver;
import com.rahul.inputs.Constants;
import com.rahul.pages.Login_Page;
import com.rahul.pages.SignIn_Page;
import com.rahul.utilities.TestUtils;
import com.rahul.utilities.Xls_Reader;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class Login extends TestBase {

    private final static String testData = Constants.TESTDATA_LOC;
    private static Xls_Reader xls = new Xls_Reader(testData);

    /**
     * @return test data from TestData.xlsx
     */
    @DataProvider
    private Object[][] getTestData() {
        System.out.println("Executing DataProvider");
        return TestUtils.getData("Login", xls);
    }

    /*############################################################
     * ' Function Name: login_Test
	 * ' Purpose: Simple login test
	 */
    @Test(dataProvider = "getTestData", priority = 1, description = "test description text")
    public void login_Test(Hashtable<String, String> data, ITestContext context) throws Exception {
        String testname = "login_Test";
        String OS_Browser = context.getCurrentXmlTest().getParameter("OS_Browser");
        String url = context.getCurrentXmlTest().getParameter("URL");

        TLDriver.getDriver().get(url);

        Login_Page LoginPage = new Login_Page();
        SignIn_Page SignInPage = new SignIn_Page();

        LoginPage.wait_For_Login_PageLoad()
                .click_SignIn();

        SignInPage.wait_For_SignIn_PageLoad()
                .type_Email(TestUtils.getTestData(data, "emailAdd"))
                .click_Next();
    }

}

