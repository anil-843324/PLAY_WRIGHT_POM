package com.qa.amplifi_capital.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
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
    // Came from java not playwright
    private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
    private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public Object[] initBrowser(Properties prop) {

        String browserName = prop.getProperty("browser").trim();

        System.out.println("Browser name is : " + browserName);
        Boolean headBoolean = Boolean.parseBoolean(prop.getProperty("headless"));

        // playwright = Playwright.create();
        tlPlaywright.set(Playwright.create());

        switch (browserName.toLowerCase()) {
            case "chromium":
                // browser = playwright.chromium().launch(new
                // BrowserType.LaunchOptions().setHeadless(headBoolean).setArgs(List.of("--start-maximized")));
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headBoolean)
                        .setArgs(List.of("--start-maximized"))));
                break;
            case "chrome":
                // browser = playwright.chromium().launch(new
                // LaunchOptions().setChannel("chrome").setHeadless(headBoolean).setArgs(List.of("--start-maximized")));
                tlBrowser.set(getPlaywright().chromium().launch(new LaunchOptions().setChannel("chrome")
                        .setHeadless(headBoolean).setArgs(List.of("--start-maximized"))));
                break;
            case "firefox":
                // browser = playwright.firefox().launch(new
                // BrowserType.LaunchOptions().setHeadless(headBoolean).setArgs(List.of("--start-maximized")));
                tlBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headBoolean)
                        .setArgs(List.of("--start-maximized"))));
                break;
            case "edge":
                // browser = playwright.chromium().launch(new
                // LaunchOptions().setChannel("msedge").setHeadless(headBoolean).setArgs(List.of("--start-maximized")));
                tlBrowser.set(getPlaywright().chromium().launch(new LaunchOptions().setChannel("msedge")
                        .setHeadless(headBoolean).setArgs(List.of("--start-maximized"))));
                break;
            case "webkit":
                // browser = playwright.webkit().launch(new
                // BrowserType.LaunchOptions().setHeadless(headBoolean).setArgs(List.of("--start-maximized")));
                tlBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headBoolean)
                        .setArgs(List.of("--start-maximized"))));
                break;
            default:
                System.out.println("Please pass the right browser name....");
                break;
        }

        tlBrowserContext.set(getBrowser().newContext());
        tlPage.set(getBrowserContext().newPage());

        // browserContext = browser.newContext();
        // page = browserContext.newPage();
        getPage().navigate(prop.getProperty("url").trim());
        getPage().waitForTimeout(4000);

        return new Object[] { getPage(), getBrowserContext() };

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

    public static String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
        getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        return path;

    }

}
