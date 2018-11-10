package com.rahul.base;

import com.aventstack.extentreports.Status;
import com.paulhammant.ngwebdriver.NgWebDriver;
import com.rahul.browserSetup.TLDriver;
import com.rahul.inputs.Constants;
import com.rahul.utilities.ExtentUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class PageBase {

    private int invalidImageCount;
    private int invalidLinksCount;


    public PageBase() {
        PageFactory.initElements(TLDriver.getDriver(), this);
    }

    protected boolean isElementClickable(WebElement element) {
        try {
            new WebDriverWait(TLDriver.getDriver(), 1).until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    protected boolean isElementPresent(List<WebElement> elements) {
        return elements.size() != 0;
    }


    protected WebElement scrollToElement(WebElement element) {
        WebElement ele = new WebDriverWait(TLDriver.getDriver(), 10).until(ExpectedConditions.visibilityOf(element));
        try {
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("window.scrollTo(0," + ele.getLocation().y + ")");
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("arguments[0].style.border='1.5px dotted black'", ele);
        } catch (Exception e) {
            System.out.println("Element method in PageBase - " + e);
        }
        return ele;
    }


    protected void waitForSync(int timeInSecs) {
        try {
            Thread.sleep(timeInSecs * 1000);
        } catch (InterruptedException e) {
            //
        }
    }


    protected void waitForAngularLoad() {
        try {
            new NgWebDriver((JavascriptExecutor) TLDriver.getDriver()).waitForAngularRequestsToFinish();
        } catch (Exception e) {
            //
        }
    }


    protected String currentUrl() {
        return TLDriver.getDriver().getCurrentUrl();
    }


    protected void pageRefresh() {
        TLDriver.getDriver().navigate().refresh();
    }


    protected void goBack() {
        TLDriver.getDriver().navigate().back();
    }


    protected void navigateToUrl(String url) {
        TLDriver.getDriver().navigate().to(url);
    }


    protected void type(WebElement element, String text) {
        scrollToElement(element);
        element.clear();
        element.sendKeys(text);
    }


    protected void click(WebElement element) {
        scrollToElement(element);
        element.click();
    }


    protected String getText(WebElement element) {
        return scrollToElement(element).getText().trim();
    }


    protected String pageTitle() {
        return TLDriver.getDriver().getTitle().trim();
    }


    protected void clickOverrideLinkInIE() {
        if ("Certificate Error: Navigation Blocked".equals(pageTitle())) {
            TLDriver.getDriver().navigate()
                    .to("javascript:document.getElementById('overridelink').click()");
        }
    }


    protected void clickByJavaScript(WebElement element) {
        scrollToElement(element);
        try {
            if (element.isEnabled() && element.isDisplayed()) {
                ((JavascriptExecutor) TLDriver.getDriver()).executeScript("arguments[0].click();", element);
            }
        } catch (Exception e) {
            System.out.println("Element is not attached to the page document " + e);
        }
    }


    // pass FILE NAME from samplefiles folder with extension
    protected void uploadingFileWithSendKeys(String filename) {
        waitForAngularLoad();
        WebElement element = TLDriver.getDriver().findElement(By.cssSelector("#fileuploadarea"));

        File filePath = new File(Constants.FILES_FOR_UPLOAD_LOC + filename);
        try {
            element.sendKeys(filePath.getAbsolutePath());
        } catch (Exception e) {
            System.out.println(filename.toUpperCase() + " File Upload failed - " + e);
        }
    }


    protected void waitForVisibility(WebElement element, int timeInSecs) {
        (new WebDriverWait(TLDriver.getDriver(), timeInSecs)).until(
                ExpectedConditions.visibilityOf(element));
    }


    protected void switchTabsUsingPartOfUrl(String platform) {
        String currentHandle = null;
        try {
            final Set<String> handles = TLDriver.getDriver().getWindowHandles();
            if (handles.size() > 1) {
                currentHandle = TLDriver.getDriver().getWindowHandle();
            }
            if (currentHandle != null) {
                for (final String handle : handles) {
                    TLDriver.getDriver().switchTo().window(handle);
                    if (currentUrl().contains(platform) && !currentHandle.equals(handle)) {
                        break;
                    }
                }
            } else {
                for (final String handle : handles) {
                    TLDriver.getDriver().switchTo().window(handle);
                    if (currentUrl().contains(platform)) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Switching tabs failed - " + e);
        }
    }


    /*To Close all Tabs/Windows except the First Tab */
    protected void closeAllTabsExceptFirst() {
        ArrayList<String> allTabs = new ArrayList<String>(TLDriver.getDriver().getWindowHandles());
        for (int i = 1; i < allTabs.size(); i++) {
            TLDriver.getDriver().switchTo().window(allTabs.get(i));
            TLDriver.getDriver().close();
        }
        TLDriver.getDriver().switchTo().window(allTabs.get(0));
    }


    protected void openNewTab(String url) {
        try {
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("window.open('" + url + "','_blank');");
        } catch (Exception e) {
            System.out.println("openNewTab failed - " + e);
        }
    }


    protected void switchToTab1() {
        try {
            ArrayList<String> tabs = new ArrayList<String>(TLDriver.getDriver().getWindowHandles());
            TLDriver.getDriver().switchTo().window(tabs.get(0));
        } catch (Exception e) {
            System.out.println("switchToTab1 failed - " + e);
        }
    }


    protected void switchToTab2() {
        try {
            ArrayList<String> tabs = new ArrayList<String>(TLDriver.getDriver().getWindowHandles());
            TLDriver.getDriver().switchTo().window(tabs.get(1));
        } catch (Exception e) {
            System.out.println("switchToTab2 failed - " + e);
        }
    }


    protected void clickExactText(List<WebElement> elements, String valueToBeSelected) throws Exception {
        WebElement element = getExactMatchingTextElementFromList(elements, valueToBeSelected);
        ((JavascriptExecutor) TLDriver.getDriver()).executeScript("arguments[0].style.border='3px dotted black'", element);
        element.click();
    }

    private WebElement getExactMatchingTextElementFromList(List<WebElement> elements, String contentText)
            throws Exception {

        WebElement elementToBeReturned = null;
        boolean found = false;

        if (elements.size() > 0) {
            for (WebElement element : elements) {
                if (element.getText().trim().replaceAll("\\s+", " ").equalsIgnoreCase(contentText)) {
                    elementToBeReturned = element;
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new Exception("Didn't find the correct text(" + contentText + ")..! from list");
            }
        } else {
            throw new Exception("Unable to find list element...!");
        }
        return elementToBeReturned;
    }


    protected void selectByVisibleText(WebElement element, String text) {
        Select se = new Select(scrollToElement(element));
        se.selectByVisibleText(text);
    }


    protected void selectByIndex(WebElement element, int index) {
        Select se = new Select(scrollToElement(element));
        se.selectByIndex(index);
    }


    protected void selectByValue(WebElement element, String value) {
        Select se = new Select(scrollToElement(element));
        se.selectByValue(value);
    }


    protected void validateInvalidImages() {
        try {
            int invalidImageCount = 0;
            List<WebElement> imagesList = TLDriver.getDriver().findElements(By.tagName("img"));
            ExtentUtil.fetchTest().log(Status.INFO, "Total no. of images are " + imagesList.size());
            for (WebElement imgElement : imagesList) {
                if (imgElement != null) {
                    verifyImageActive(imgElement);
                }
            }
            ExtentUtil.fetchTest().log(Status.INFO, "Total no. of broken images are " + invalidImageCount);
        } catch (Exception e) {
            System.out.println("validateInvalidImages - " + e);
        }
    }


    private void verifyImageActive(WebElement imgElement) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(imgElement.getAttribute("src"));
            HttpResponse response = client.execute(request);
            // verifying response code the HttpStatus should be 200 if not,
            // increment as invalid images count
            if (response.getStatusLine().getStatusCode() != 200)
                invalidImageCount++;
        } catch (Exception e) {
            System.out.println("verifyImageActive - " + e);
        }
    }


    protected void verifyLinks() {
        invalidLinksCount = 0;
        List<WebElement> links = TLDriver.getDriver().findElements(By.tagName("a"));
        ExtentUtil.fetchTest().log(Status.INFO, "Total links are: " + links.size());

        for (WebElement link : links) {
            if (link != null) {
                String url = link.getAttribute("href");
                if (url != null && !url.contains("javascript")) {
                    verifyLinkActive(url);
                } else {
                    invalidLinksCount++;
                }
            }
        }
        ExtentUtil.fetchTest().log(Status.INFO, "Total no. of invalid links are " + invalidLinksCount);
    }


    private void verifyLinkActive(String linkUrl) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(linkUrl);
        try {
            HttpResponse response = client.execute(request);
            // verifying response code and The HttpStatus should be 200 if not,
            // increment invalid link count
            if (response.getStatusLine().getStatusCode() != 200) {
                invalidLinksCount++;
            }
        } catch (Exception e) {
            System.out.println("verifyLinkActive - " + e);
        }
    }


    protected void fullPageScreenshot(String OS_Browser, String testname) {
        String timeStamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());
        String imageName = testname + "-" + OS_Browser + "-" + timeStamp + ".png";
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000))
                .takeScreenshot(TLDriver.getDriver());
        try {
            ImageIO.write(screenshot.getImage(), "PNG", new File("./FullPage_Screenshots/" + imageName));
        } catch (Exception e) {
            System.out.println("Capturing FullPage Screenshot failed - " + e);
        }
    }


    protected void clickEscape() {
        try {
            WebElement body = TLDriver.getDriver().findElement(By.cssSelector("body"));
            body.sendKeys(Keys.ESCAPE);
        } catch (Exception e) {
            //
        }
    }


    protected void verifyEquals(String expected, String actual, String message) {
        try {
            Assert.assertEquals(expected, actual);
        } catch (Throwable t) {
            ExtentUtil.fetchTest().log(Status.WARNING, message);
        }
    }


    protected void verifyContains(String expected, String actual, String message) {
        try {
            Assert.assertTrue(expected.contains(actual));
        } catch (Throwable t) {
            ExtentUtil.fetchTest().log(Status.WARNING, message);
        }
    }


    // To Get Tooltip Text
    protected String getTooltipText(WebElement element) {
        return element.getAttribute("title").trim();
    }


    protected void clearLocalStorage() {
        try {
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("window.localStorage.clear();");
        } catch (Exception e) {
            //
        }
    }


    protected void setAttributeValue(WebElement element, String value) {
        String scriptSetAttrValue = "arguments[0].setAttribute(arguments[1],arguments[2])";
        try {
            ((JavascriptExecutor) TLDriver.getDriver())
                    .executeScript(scriptSetAttrValue, element, "value", value);
        } catch (Exception e) {
            System.out.println("setAttributeValue failed - " + e);
        }
    }


    // user defined check color Method
    protected void checkElementColor(WebElement element, String expectedColorInHex) {
        try {
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("arguments[0].style.border='3px dotted black'", element);
            String color = element.getCssValue("color");
            String colorInHex = Color.fromString(color).asHex();
            assertEquals(colorInHex, expectedColorInHex);
        } catch (Exception e) {
            System.out.println("checkElementColor failed - " + e);
        }
    }


    protected void checkElementBackgroundColor(WebElement element, String expectedColorInHex) {
        try {
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("arguments[0].style.border='3px dotted black'", element);
            String color = element.getCssValue("background-color");
            String colorInHex = Color.fromString(color).asHex();
            assertEquals(colorInHex, expectedColorInHex);
        } catch (Exception e) {
            System.out.println("checkElementBackgroundColor failed - " + e);
        }
    }


    protected void mouseOver(WebElement element) {
        try {
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("arguments[0].style.border='3px dotted black'", element);
            final Actions builder = new Actions(TLDriver.getDriver());
            final Action mouseover = builder.moveToElement(element).build();
            mouseover.perform();
        } catch (Exception e) {
            System.out.println("mouseOver failed - " + e);
        }
    }


    protected void mouseHoverByJavaScript(WebElement element) {
        try {
            String javaScript = "var evObj = document.createEvent('MouseEvents');"
                    + "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
                    + "arguments[0].dispatchEvent(evObj);";
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript(javaScript, element);
        } catch (Exception e) {
            System.out.println("mouseHoverByJavaScript failed - " + e);
        }
    }


    protected boolean isAlertPresent() {
        try {
            final Alert alert = TLDriver.getDriver().switchTo().alert();
            alert.getText();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    protected String getAlertText() {
        String text;
        try {
            final Alert alert = TLDriver.getDriver().switchTo().alert();
            text = alert.getText();
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    protected void acceptAlert() {
        try {
            final Alert alert = TLDriver.getDriver().switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            System.out.println("acceptAlert failed - " + e);
        }
    }


    protected void dismissAlert() {
        try {
            final Alert alert = TLDriver.getDriver().switchTo().alert();
            alert.dismiss();
        } catch (Exception e) {
            System.out.println("dismissAlert failed - " + e);
        }
    }


    protected void dragAndDrop(WebElement sourceElement, WebElement destinationElement) {
        if (sourceElement.isDisplayed() && destinationElement.isDisplayed()) {
            Actions action = new Actions(TLDriver.getDriver());
            action.dragAndDrop(sourceElement, destinationElement).build().perform();
        }
    }


    protected void scrollDownBy500PixelCount() {
        try {
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("window.scrollBy(0,500)");
        } catch (Exception e) {
            System.out.println("scrollDownBy500PixelCount failed - " + e);
        }
    }


    // To ScrollUp using JavaScript Executor
    protected void scrollUpBy500PixelCount() throws Exception {
        try {
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("scroll(0, -500);");
        } catch (Exception e) {
            System.out.println("scrollUpBy500PixelCount failed - " + e);
        }
    }


    protected void scrollToBottomOfPage() {
        try {
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        } catch (Exception e) {
            System.out.println("scrollToBottomOfPage failed - " + e);
        }
    }


    protected void scrollToGetElementInView(WebElement element) {
        try {
            ((JavascriptExecutor) TLDriver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            System.out.println("scrollToGetElementInView failed - " + e);
        }
    }


    protected void rightClick(WebElement element) {
        Actions action = new Actions(TLDriver.getDriver());
        action.contextClick(element).build().perform();
    }


    protected void doubleClick(WebElement element) {
        Actions action = new Actions(TLDriver.getDriver());
        action.doubleClick(element).build().perform();
    }


    protected void clickContainsText(List<WebElement> elements, String valueToBeSelected) throws Exception {
        WebElement element = getContainsMatchingTextElementFromList(elements, valueToBeSelected);
        ((JavascriptExecutor) TLDriver.getDriver()).executeScript("arguments[0].style.border='3px dotted black'", element);
        element.click();
    }

    private WebElement getContainsMatchingTextElementFromList(List<WebElement> elements, String contentText)
            throws Exception {
        WebElement elementToBeReturned = null;
        boolean found = false;

        if (elements.size() > 0) {
            for (WebElement element : elements) {
                if (element.getText().trim().replaceAll("\\s+", " ").contains(contentText)) {
                    elementToBeReturned = element;
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new Exception("Didn't find the correct text(" + contentText + ")..! from list");
            }
        } else {
            throw new Exception("Unable to find list element...!");
        }
        return elementToBeReturned;
    }


    // To Close Current Tab
    protected void closeCurrentTab() {
        TLDriver.getDriver().close();
    }


    // To Switch To Frame By Index
    protected void switchToFrameByIndex(int index) throws Exception {
        TLDriver.getDriver().switchTo().frame(index);
    }


    // To Switch To Frame By Frame Name
    protected void switchToFrameByFrameName(String frameName) throws Exception {
        TLDriver.getDriver().switchTo().frame(frameName);
    }


    // To Switch To Frame By Web Element
    protected void switchToFrameByWebElement(WebElement element) throws Exception {
        TLDriver.getDriver().switchTo().frame(element);
    }


    // To Switch out of a Frame
    protected void switchOutOfFrame() throws Exception {
        TLDriver.getDriver().switchTo().defaultContent();
    }


    // in case of more than 2 frames using frame name
    protected void switchToFrame(String ParentFrameName, String ChildFrameName) {
        TLDriver.getDriver().switchTo().frame(ParentFrameName).switchTo().frame(ChildFrameName);
    }


    // in case of more than 2 frames using frame id
    protected void switchToFrame(int ParentFrameId, int ChildFrameId) {
        TLDriver.getDriver().switchTo().frame(ParentFrameId).switchTo().frame(ChildFrameId);
    }


    // switching to default frame
    protected void switchToDefaultFrame() {
        TLDriver.getDriver().switchTo().defaultContent();
    }


}

