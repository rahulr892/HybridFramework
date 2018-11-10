package com.rahul.browserSetup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    private static OptionsManager options = new OptionsManager();

    /**
     * Start browser based on input in testng.xml and used in WebDriverListener
     *
     * @param OS_Browser fetched from xml
     * @return driver instance
     */
    public static WebDriver createInstance(String OS_Browser) {
        WebDriver driver = null;
        String hub = "http://localhost:4444/wd/hub";

        WebDriverManager.chromedriver().setup();
        WebDriverManager.iedriver().arch32().version("3.11").setup();
        WebDriverManager.firefoxdriver().version("0.20.1").setup();

        if (("Win7_Chrome").equalsIgnoreCase(OS_Browser)) {
            try {
                driver = new ChromeDriver(options.getWinChromeOptions());
            } catch (Throwable e) {
                System.out.println("Win7_Chrome - " + e);
            }

        } else if (("Win7_IE").equalsIgnoreCase(OS_Browser)) {
            try {
                driver = new InternetExplorerDriver(options.getIEOptions());
            } catch (Throwable e) {
                System.out.println("Win7_IE - " + e);
            }

        } else if (("Mac_Chrome").equalsIgnoreCase(OS_Browser)) {
            try {
                driver = new RemoteWebDriver(new URL(hub), options.getMacChromeOptions());
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } catch (Throwable e) {
                System.out.println("Mac_Chrome - " + e);
            }

        } else if (("Mac_Safari").equalsIgnoreCase(OS_Browser)) {
            try {
                driver = new RemoteWebDriver(new URL(hub), options.getSafariOptions());
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } catch (Throwable e) {
                System.out.println("Mac_Safari - " + e);
            }

        } else if (("Win10_FF").equalsIgnoreCase(OS_Browser)) {
            try {
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
                driver = new RemoteWebDriver(new URL(hub), options.getFirefoxOptions());
                ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
            } catch (Throwable e) {
                System.out.println("Win10_FF - " + e);
            }

        } else if (("Win7_FF").equalsIgnoreCase(OS_Browser)) {
            try {
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
                FirefoxOptions options = new FirefoxOptions();
                options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                driver = new FirefoxDriver(options);
            } catch (Throwable e) {
                System.out.println("Win7_FF - " + e);
            }
        } else {
            try {
                driver = new ChromeDriver(options.getWinChromeOptions());
            } catch (Throwable e) {
                System.out.println("Win7_Chrome - " + e);
            }
        }

        if (driver != null) {
            try {
                driver.manage().window().maximize();
                driver.manage().timeouts().setScriptTimeout(1500, TimeUnit.SECONDS);
            } catch (Exception e) {
                System.out.println("Please check browser value");
                e.printStackTrace();
            }
        }
        return driver;
    }

}

