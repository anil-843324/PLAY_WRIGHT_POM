package com.qa.amplifi_capital.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CreditUnionsPage {
	private Page page;
    private BrowserContext context;

    // 1.String locator - OR
    private String buttons = "//button";
    private String contentSecn = "(//div[contains(@class,\"container\")])";
    private String h1Tag = "h1";
    private String UatCreditUnionsPageUrl = "https://uat.amplificapital.com/credit-unions";
    private String ProdCreditUnionsPageUrl = "https://www.amplificapital.com/credit-unions";

    
    // 2. page constructor
    public CreditUnionsPage(Page page, BrowserContext context) {
        this.page = page;
        this.context = context;
    }

    // 3. page actions/Methods
    public String getCreditUnionsTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    // 4. get home page url
    public String getCreditUnionsURL() {
        String url = page.url();
        System.out.println("page url: " + url);
        return url;
    }

    // Validate buttons
    public List<String> ValidateCreditUnionButtons() {
        List<String> buttonTextList = new ArrayList<>();
        page.waitForTimeout(1000);
        Locator home_button = page.locator(buttons);
        int count_home_buttons = home_button.count();
        for (int j = 0; j < count_home_buttons; ++j) {
            home_button.nth(j).scrollIntoViewIfNeeded();
            String buttonText = home_button.nth(j).textContent();
            System.out.println("page url: " + buttonText);
            buttonTextList.add(buttonText);
            page.waitForTimeout(2000);
            home_button.nth(j).click();
            page.waitForTimeout(2000);
            page.navigate(UatCreditUnionsPageUrl);
            page.waitForTimeout(2000);

        }
        return buttonTextList;
    }

    public void ValidateOneH1Tag() {

        page.waitForTimeout(1000);
        // Find all h1 elements
        List<ElementHandle> h1Elements = page.querySelectorAll(h1Tag);
        System.out.println(h1Elements);
        // Validate the count
        if (h1Elements.size() == 1) {
            System.out.println("There is only one h1 tag on the page.");
        } else {
            System.out.println("there are multiple h1 tags on the page.");
        }
    }

    
        



    public void ValidateCreditUnionsPageContents() {

        // Open a new page in the same browser context
    	page.waitForTimeout(2000);
        page.locator(h1Tag).scrollIntoViewIfNeeded();
        page.bringToFront();
        Locator home_page_contenLocator = page.locator(contentSecn);
        int count_home_section = home_page_contenLocator.count();
        page.waitForTimeout(1000);
        Page tabs = page.waitForPopup(() -> {
            page.click("a[target='_blank']"); 
        });
//        page.waitForTimeout(2000);
        tabs.navigate(ProdCreditUnionsPageUrl);
        List<Page> pages = tabs.context().pages();


        for (int i = 0; i < count_home_section; i++) {
        page.bringToFront();
        page.waitForTimeout(1000);
        home_page_contenLocator.nth(i).scrollIntoViewIfNeeded();
        String uat_content = home_page_contenLocator.nth(i).textContent();
        uat_content = uat_content.trim().toLowerCase().replaceAll("\\s", "");

        pages.get(1).bringToFront();
        pages.get(1).waitForTimeout(1000);
        pages.get(1).locator(contentSecn).nth(i).scrollIntoViewIfNeeded();
        String prod_content = pages.get(1).locator(contentSecn).nth(i).textContent();
        prod_content = prod_content.trim().toLowerCase().replaceAll("\\s", "");
        if (prod_content.equals(uat_content)) {
        System.out.println(URLExtractor(page.url()) + " Contents are equal");
        } else {
        System.out.println("Contents are not equal");
        }
        page.bringToFront();
        }
        pages.get(1).close();

    }

    public String URLExtractor(String urlString) {
        try {
            String result = new URL(urlString).getHost().replaceFirst("^uat\\.", "") + new URL(urlString).getPath();
            return result;
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL: " + urlString);
            return ""; // Return an empty string in case of an exception
        }
    }
}
