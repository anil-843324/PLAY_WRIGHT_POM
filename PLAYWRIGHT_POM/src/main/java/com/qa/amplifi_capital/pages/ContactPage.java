package com.qa.amplifi_capital.pages;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ContactPage {
	
	private Page page;
    private BrowserContext context;

    // 1.String locator - OR
    private String buttons = "//button";
    private String contentSecn = "(//div[contains(@class,\"container\")])";
    private String h1Tag = "h1";
    private String ProdContactPageUrl = "https://www.amplificapital.com/contact";
    // private String aboutLink = "(//a[contains(text(),'About')])[1]";

    // 2. page constructor
    public ContactPage(Page page, BrowserContext context) {
        this.page = page;
        this.context = context;
        // page.locator(aboutLink).click();

    }

    // 3. page actions/Methods
    public String getContactPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    // 4. get home page url
    public String getContactPageURL() {
        String url = page.url();
        System.out.println("page url: " + url);
        return url;
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

    public void ValidateContactPageContents() {

        // Open a new page in the same browser context

        page.bringToFront();
        Locator home_page_contenLocator = page.locator(contentSecn);
        int count_home_section = home_page_contenLocator.count();
        page.waitForTimeout(1000);
        Page tabs = page.waitForPopup(() -> {
            page.click("a[target='_blank']");
        });

        tabs.navigate(ProdContactPageUrl);
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

    public String validateSubmitForm(String name, String email, String subject, String message) {
        page.getByPlaceholder("Name").fill(name);
        page.waitForTimeout(1000);
        page.getByPlaceholder("Email").fill(email);
        page.waitForTimeout(1000);
        page.getByPlaceholder("Subject").fill(subject);
        page.waitForTimeout(1000);
        page.getByPlaceholder("Type your message here...").fill(message);
        page.waitForTimeout(1000);
        page.locator("//button").click();
        page.waitForTimeout(1000);
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Match the email against the pattern
        Matcher matcher = pattern.matcher(email);

        // Return true if the email matches the pattern, false otherwise
        boolean isValidEmail = matcher.matches();

        if (name != null && !name.isEmpty() && isValidEmail && !email.isEmpty() && subject != null && !subject.isEmpty()
                && message != null && !message.isEmpty()) {
            page.waitForTimeout(3000);
            String urlString = page.url();
            page.navigate("https://uat.amplificapital.com/contact");
            return urlString;
        } else {
            page.waitForTimeout(2000);
            String urlString = page.url();
            page.reload();
            return urlString;
        }

    }

}
