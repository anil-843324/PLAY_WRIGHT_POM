package com.qa.gojoko.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;

public class PlaywrightFactory {

    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Properties prop;

    public Object[] initBrowser(Properties prop) {

        String browserName = prop.getProperty("browser").trim();

        System.out.println("Browser name is : " + browserName);
        Boolean headBoolean = Boolean.parseBoolean(prop.getProperty("headless"));

        playwright = Playwright.create();

        switch (browserName.toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(
                        new BrowserType.LaunchOptions().setHeadless(headBoolean).setArgs(List.of("--start-maximized")));
                break;
            case "chrome":
                browser = playwright.chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(headBoolean)
                        .setArgs(List.of("--start-maximized")));
                break;
            case "firefox":
                browser = playwright.firefox().launch(
                        new BrowserType.LaunchOptions().setHeadless(headBoolean).setArgs(List.of("--start-maximized")));
                break;
            case "edge":
            browser = playwright.chromium().launch(new LaunchOptions().setChannel("msedge").setHeadless(headBoolean)
            .setArgs(List.of("--start-maximized")));
                break;
            case "webkit":
                browser = playwright.webkit().launch(
                        new BrowserType.LaunchOptions().setHeadless(headBoolean).setArgs(List.of("--start-maximized")));
                break;
            default:
                System.out.println("Please pass the right browser name....");
                break;
        }
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));

        page = browserContext.newPage();
        page.navigate(prop.getProperty("mcf_url_uat").trim());
        page.waitForTimeout(4000);

        return new Object[] { page, browserContext };

    }
    /*
     * This method is used to initalized the properties from config file
     */

    public Properties init_prop() {
        try {

            FileInputStream ip = new FileInputStream("./srcs/test/resources/config/config.properties");
            prop = new Properties();
            prop.load(ip);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return prop;

    }

}

