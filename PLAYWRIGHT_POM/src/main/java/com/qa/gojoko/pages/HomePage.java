package com.qa.gojoko.pages;

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
    private String h1Tag = "h1";
    private String linkspath = "//a[contains(@class,'underline')]";
    private String amplifiname = "(//p[contains(text(),'Amplifi Capital (U.K.) Limited')])[3]";
    private String contactUSButton = "//button[contains(text(),'Contact Us →')]";
    private String UatHomePageUrl = "https://uat.amplificapital.com";
    private String ProdHomePageUrl = "https://www.amplificapital.com";
    private String aboutLink = "(//a[contains(text(),'About')])[1]";

    private String contactLink = "(//a[contains(text(),'Contact')])[1]";
    private String careersLink = "(//a[contains(text(),'Careers')])[1]";
    private String legalsLink = "//ul[contains(@class,\"md-regular\")]//li";
    private String legalsContentsPath = "(//div[contains(@class,\"container\")])[1]";
    private String newsTitlePath = "//h3[contains(text(),'In the news')]";

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

    // Validating CSS with Figma
    public void getTextCss() {
        String allSecondTitlePath="h2";
        int count_Second_title = page.locator(allSecondTitlePath).count();

        Locator titleLocator = page.locator(allSecondTitlePath);

        // Object[] cssObjects = getCssStyles(firstTitlePath,"JosefinSans");
        
        for(int i=0;i<count_Second_title;i++){
            if(i!=5){
                Object[] cssObjects2 = getCssStyles(titleLocator.nth(i),"JosefinSans");
               
                
            }
            
        }
    }
    public void getTitleCss(){
        String allSecondTitlePath="h2";
        Locator titleLocator = page.locator(allSecondTitlePath);
       
        Object[] cssObjects2 = getCssStyles(titleLocator.nth(0),"JosefinSans");
         
       
    }


    public void getTitleContent(){

    }

    public void getPragrpahCss(){

    }


    public void getPragrpahContent(){

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
    public void ValidateContentsOfLegalsPage() {
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

    // public AboutPage navigateToAboutPage(){
    // page.waitForTimeout(3000);
    // page.locator(aboutLink).click();
    // page.waitForTimeout(2000);
    // return new AboutPage(page,context);
    // }

    // public InvestorsPage navigateToInvestorsPage(){
    // page.waitForTimeout(3000);
    // page.locator(investorsLink).click();
    // page.waitForTimeout(2000);
    // return new InvestorsPage(page,context);
    // }

    // public ContactPage navigateToContactPage(){
    // page.waitForTimeout(3000);
    // page.locator(contactLink).click();
    // page.waitForTimeout(2000);
    // return new ContactPage(page,context);
    // }

    // public CareersPage navigateToCaeersPage(){
    // page.waitForTimeout(3000);
    // page.locator(careersLink).click();
    // page.waitForTimeout(2000);
    // return new CareersPage(page,context);
    // }

    public static String convertRGBtoHex(String rgbColor) {
        String[] values = rgbColor.substring(4, rgbColor.length() - 1).split(",");
        int r = Integer.parseInt(values[0].trim());
        int g = Integer.parseInt(values[1].trim());
        int b = Integer.parseInt(values[2].trim());

        String hexR = Integer.toHexString(r);
        String hexG = Integer.toHexString(g);
        String hexB = Integer.toHexString(b);

        // Pad single-digit hex values with a leading zero
        hexR = hexR.length() == 1 ? "0" + hexR : hexR;
        hexG = hexG.length() == 1 ? "0" + hexG : hexG;
        hexB = hexB.length() == 1 ? "0" + hexB : hexB;

        return "#" + hexR.toUpperCase() + hexG.toUpperCase() + hexB.toUpperCase();
    }

    public  Boolean extractFirstFont(String fontString,String actlFontFamily) {
        // Split the font string by commas and trim whitespace from each font name
        String[] fontNames = fontString.split(",");
        char[] charActl = actlFontFamily.toCharArray();
       
        if (fontNames.length > 0) {
            // Remove underscores from the first font name only
            String firstFont = fontNames[0].trim().replace("_", "");
            char[] charFont = firstFont.toCharArray();
            for(int i=0;i<charActl.length;i++){
              if(charActl[i]!=charFont[i]){
                  return false;
              }
            }
            // return firstFont;
            return true;
        } else {
            return false; // Return null if the input string is empty
        }
    }
    
    public Object[] getCssStyles(Locator titleLocator,String actlFontFamily) {
        
        titleLocator.scrollIntoViewIfNeeded();
        String fontSize = (String) titleLocator.evaluate("element => window.getComputedStyle(element).fontSize");
        // String colors = (String) titleLocator.evaluate("element => window.getComputedStyle(element).color");
        String fontFamilys = (String) titleLocator.evaluate("element => window.getComputedStyle(element).fontFamily");
        String fontWeight = (String) titleLocator.evaluate("element => window.getComputedStyle(element).fontWeight");
        // String color=convertRGBtoHex(colors);

        Boolean fontFamily=extractFirstFont(fontFamilys,actlFontFamily);

        // System.out.println("Font size: " + fontSize);
        // System.out.println("Color: " + colors);
        // System.out.println("Color: " + convertRGBtoHex(color));
        // System.out.println("Font family: " + extractFirstFont(fontFamilys,"Kanit"));
        // System.out.println("Font weight: " + fontWeight);
        return new Object[] { fontSize,fontWeight,fontFamily};
    }


}
