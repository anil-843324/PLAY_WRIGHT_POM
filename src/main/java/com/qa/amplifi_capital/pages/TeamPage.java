package com.qa.amplifi_capital.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TeamPage {

	 private Page page;
	    private BrowserContext context;

	    // 1.String locator - OR
	    private String buttons = "//button";
	    private String contentSecn = "(//div[contains(@class,\"container\")])";
	    private String h1Tag = "h1";
	    private String UatTeamPageUrl = "https://uat.amplificapital.com/team";
	    private String ProdTeamPageUrl = "https://www.amplificapital.com/team";

	    private String amplifi_team_uat_all_cards = "(//div[contains(@class,'grid grid-cols-12 gap-y-5')])/div";

	    private String cardCloseButton = "document.querySelectorAll('button.absolute')[1].click()";

	    private String allCardsPath = "(//div[contains(@class,'place-self-center')])";
	    private String cardContentsPath = "(//div[contains(@class,'py-1')])[1]";

	    // 2. page constructor
	    public TeamPage(Page page, BrowserContext context) {
	        this.page = page;
	        this.context = context;
	    }

	    // 3. page actions/Methods
	    public String getTeamPageTitle() {
	        String title = page.title();
	        System.out.println("page title: " + title);
	        return title;
	    }

	    // 4. get home page url
	    public String getTeamPageURL() {
	        String url = page.url();
	        System.out.println("page url: " + url);
	        return url;
	    }

	    // Validate buttons
	    public List<String> ValidateTeamButtons() {
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
	            page.navigate(UatTeamPageUrl);
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

	    public void ValidateTeamPageContents() {

	        // Open a new page in the same browser context

	        page.bringToFront();
	        Locator home_page_contenLocator = page.locator(contentSecn);
	        int count_home_section = home_page_contenLocator.count();
	        page.waitForTimeout(3000);
	        Page tabs = page.waitForPopup(() -> {
	            page.click("a[target='_blank']");
	        });
	        page.waitForTimeout(1000);
	        tabs.navigate(ProdTeamPageUrl);
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

	    // Validateing Team of products
	    public void TeamcardsContent() {

	        int cardCount = page.locator(amplifi_team_uat_all_cards).count();
	        page.waitForTimeout(3000);
	        Page tabs = page.waitForPopup(() -> {
	            page.click("a[target='_blank']");
	        });
	        tabs.navigate(ProdTeamPageUrl);
	        page.waitForTimeout(1000);
	        List<Page> pages = tabs.context().pages();
	        page.bringToFront();
	        for (int i = 0; i < cardCount; i++) {
	            page.locator(amplifi_team_uat_all_cards).nth(i).scrollIntoViewIfNeeded();
	            // String card_name = page.locator(cardName).textContent();
	            String xpath = String.format("(//div[contains(@class,'place-self-center')])[%d]//p[1]", i + 1);
	            String card_name = page.locator(xpath).textContent();
	            if (!card_name.equals("Cheng Choo") && !card_name.equals("Matthew Lawson")) {
	                page.locator(allCardsPath).nth(i).click();
	                page.waitForTimeout(1000);
	                String uatCardContents = page.locator(cardContentsPath).textContent().trim().toLowerCase()
	                        .replaceAll("\\s", "");
	                page.evaluate(cardCloseButton);
	                pages.get(1).bringToFront();
	                pages.get(1).waitForTimeout(1000);
	                pages.get(1).locator(amplifi_team_uat_all_cards).nth(i).scrollIntoViewIfNeeded();
	                pages.get(1).locator(allCardsPath).nth(i).click();
	                pages.get(1).waitForTimeout(1000);
	                String prodCardContents = pages.get(1).locator(cardContentsPath).textContent().trim().toLowerCase()
	                        .replaceAll("\\s", "");

	                pages.get(1).evaluate(cardCloseButton);
	                pages.get(1).waitForTimeout(1000);

	                if (uatCardContents.equals(prodCardContents)) {
	                    System.out.println(" Contents are equal");
	                } else {
	                    System.out.println("Contents are not equal");
	                }
	                page.bringToFront();
	                page.waitForTimeout(1000);

	            }

	        }
	        pages.get(1).close();

	    }

}
