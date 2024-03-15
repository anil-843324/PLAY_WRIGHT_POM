package com.qa.amplifi_capital.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;



public class HomePage {

	private Page page;
    private BrowserContext context;

    // 1.String locator - OR
    private String buttons = "//button";
    private String contentSecn = "(//div[contains(@class,\"container\")])";
    private String arroButton = "svg";
    private String h1Tag = "h1";
    private String linkspath = "//a[contains(@class,'underline')]";
    private String newsarticlesLinks = "(//div[contains(@class,'overflow')])//article";
    private String amplifiname = "(//p[contains(text(),'Amplifi Capital (U.K.) Limited')])[3]";
    private String contactUSButton = "//button[contains(text(),'Contact Us →')]";
    private String UatHomePageUrl = "https://uat.amplificapital.com";
    private String ProdHomePageUrl = "https://www.amplificapital.com";
    private String aboutLink = "(//a[contains(text(),'About')])[1]";
    private String teamLink = "(//a[contains(text(),'Team')])[1]";
    private String creditUnionsLink = "(//a[contains(text(),'Credit Unions')])[1]";
    private String investorsLink = "(//a[contains(text(),'Investors')])[1]";
    private String contactLink = "(//a[contains(text(),'Contact')])[1]";
    private String careersLink = "(//a[contains(text(),'Careers')])[1]";
    private String legalsLink="//ul[contains(@class,\"md-regular\")]//li";
    private String legalsContentsPath="(//div[contains(@class,\"container\")])[1]";
    private String newsTitlePath="//h3[contains(text(),'In the news')]";
    // 2. page constructor
    public HomePage(Page page, BrowserContext context) {
        this.page = page;
        this.context = context;

    }

    // 3. page actions/Methods
    public String getHomePageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    // 4. get home page url
    public String getHomePageURL() {
        String url = page.url();
        System.out.println("page url: " + url);
        return url;
    }

    // Validate buttons
    public List<String> ValidateButtons() {
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
            page.navigate("https://uat.amplificapital.com");
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

    public void ValidateHomePageNewsCarousel() {
        page.locator(arroButton).nth(1).scrollIntoViewIfNeeded();
        page.waitForTimeout(1000);
        Locator carsol_buttons = page.locator(newsarticlesLinks);
        int count_carsol_buttons = carsol_buttons.count();
        boolean flag = true;
        for (int j = 0; j < count_carsol_buttons; j++) {
            page.waitForTimeout(1000);

            try {
                if (page.locator(arroButton).nth(1).isVisible() && flag) {
                    carsol_buttons.nth(j).click();

                    Page newPage = context.waitForPage(() -> {
                    });
                    page.waitForTimeout(2000);
                    newPage.close();

                    // Close the newly opened tab
                    page.waitForTimeout(1000);
                    newPage.close();
                    page.bringToFront();
                    page.waitForTimeout(1000);
                    page.locator(arroButton).nth(1).click();

                } else {
                    page.locator(arroButton).nth(0).click();
                    flag = false;
                    j = 0;
                }
            } catch (Exception e) {
                if (flag) {
                    System.out.println("yet not verfied");
                }
                System.out.println("carosal verfied with disible and enable button");
                break;
            }

        }
    }

    public void ValidateFooterLinks() {
        page.locator(amplifiname).scrollIntoViewIfNeeded();
        page.waitForTimeout(1000);
        Locator links = page.locator(linkspath);
        int count_links = links.count();
        for (int i = 0; i < count_links; i++) {
            links.nth(i).scrollIntoViewIfNeeded();
            page.waitForTimeout(1000);
            links.nth(i).click();

            if (links.nth(i).textContent().equals("here")
                    || links.nth(i).textContent().equals("Gojoko Marketing Ltd.")) {
                Page newPage = context.waitForPage(() -> {
                });
                page.waitForTimeout(2000);
                newPage.close();
            }
            // redirecting to the home page
            page.waitForTimeout(1000);
            page.navigate(UatHomePageUrl);
            page.waitForTimeout(1000);
            page.locator(contactUSButton).scrollIntoViewIfNeeded();
        }


    }

    public void ValidateHompePageContents() {
        page.bringToFront();
        Locator home_page_contenLocator = page.locator(contentSecn);
        int count_home_section = home_page_contenLocator.count();
        page.waitForTimeout(1000);
        Page tabs = page.waitForPopup(() -> {
            page.click("a[target='_blank']"); 
        });

        tabs.navigate(ProdHomePageUrl);
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

    // leagal pages
    public void ValidateContentsOfLegalsPage(){
        page.bringToFront();
        Locator linksLocator = page.locator(legalsLink);
        int count_links_section = linksLocator.count();
        page.waitForTimeout(1000);
        Page tabs = page.waitForPopup(() -> {
            page.click("a[target='_blank']"); 
        });

        tabs.navigate(ProdHomePageUrl);
        List<Page> pages = tabs.context().pages();

        for (int i = 3; i < count_links_section; i++) {
        page.bringToFront();
        page.waitForTimeout(1000);
        page.locator(newsTitlePath).scrollIntoViewIfNeeded();
        linksLocator.nth(i).scrollIntoViewIfNeeded();
        linksLocator.nth(i).click();
        page.waitForTimeout(1000);
        String uat_content = page.locator(legalsContentsPath).textContent();
        uat_content = uat_content.trim().toLowerCase().replaceAll("\\s", "");
        page.navigate(UatHomePageUrl);
        page.waitForTimeout(1000);
        pages.get(1).bringToFront();
        pages.get(1).waitForTimeout(1000);
        pages.get(1).locator(newsTitlePath).scrollIntoViewIfNeeded();
        pages.get(1).locator(legalsLink).nth(i).scrollIntoViewIfNeeded(); 
        pages.get(1).locator(legalsLink).nth(i).click();
        String prod_content = pages.get(1).locator(legalsContentsPath).textContent();
        prod_content = prod_content.trim().toLowerCase().replaceAll("\\s", "");
        if (prod_content.equals(uat_content)) {
        System.out.println(URLExtractor(page.url()) + " Contents are equal");
        } else {
        System.out.println("Contents are not equal");
        }
        pages.get(1).waitForTimeout(1000);
        pages.get(1).navigate(UatHomePageUrl);
        pages.get(1).waitForTimeout(1000);
        page.bringToFront();
        }
        pages.get(1).close();
    }
    
    public AboutPage navigateToAboutPage(){
        page.waitForTimeout(3000);
        page.locator(aboutLink).click();
        page.waitForTimeout(2000);
        return new AboutPage(page,context);
    }

    public TeamPage navigateToTeamPage(){
        page.waitForTimeout(3000);
        page.locator(teamLink).click();
        page.waitForTimeout(2000);
        return new TeamPage(page,context);
    }

    public CreditUnionsPage navigateToCreditUnionsPage(){
        page.waitForTimeout(3000);
        page.locator(creditUnionsLink).click();
        page.waitForTimeout(2000);
        return new CreditUnionsPage(page,context);
    }

    public InvestorsPage navigateToInvestorsPage(){
        page.waitForTimeout(3000);
        page.locator(investorsLink).click();
        page.waitForTimeout(2000);
        return new InvestorsPage(page,context);
    }

    public ContactPage navigateToContactPage(){
        page.waitForTimeout(3000);
        page.locator(contactLink).click();
        page.waitForTimeout(2000);
        return new ContactPage(page,context);
    }

    public CareersPage navigateToCaeersPage(){
        page.waitForTimeout(3000);
        page.locator(careersLink).click();
        page.waitForTimeout(2000);
        return new CareersPage(page,context);
    }
    
	
}
