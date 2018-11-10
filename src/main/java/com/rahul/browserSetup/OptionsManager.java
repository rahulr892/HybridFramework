package com.rahul.browserSetup;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariOptions;

/**
 * Created by Rahul Rana on 10-Mar-18.
 */
class OptionsManager {

    //Get Windows Chrome Options
    ChromeOptions getWinChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("allow-running-insecure-content");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-notifications");
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability(ChromeOptions.CAPABILITY, options);
        return options;
    }


    //Get Mac Chrome Options
    ChromeOptions getMacChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("allow-running-insecure-content");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-notifications");
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.MAC);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability(ChromeOptions.CAPABILITY, options);
        return options;
    }


    //Get IE Options
    InternetExplorerOptions getIEOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        return options;
    }


    //Get Safari Options
    SafariOptions getSafariOptions() {
        SafariOptions options = new SafariOptions();
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.MAC);
        options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        return options;
    }


    //Get Firefox Options
    FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("moz:firefoxOptions", options);
        options.setCapability("dom.file.createInChild", options);
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.WIN10);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability(FirefoxOptions.FIREFOX_OPTIONS, true);
        return options;
    }


}
