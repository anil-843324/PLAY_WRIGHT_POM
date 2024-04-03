package com.qa.gojoko.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;

import org.apache.poi.EncryptedDocumentException;
// data loaind import file
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
        String allSecondTitlePath = "h2";
        int count_Second_title = page.locator(allSecondTitlePath).count();

        Locator titleLocator = page.locator(allSecondTitlePath);

        // Object[] cssObjects = getCssStyles(firstTitlePath,"JosefinSans");

        for (int i = 0; i < count_Second_title; i++) {
            if (i != 5) {
                Object[] cssObjects2 = getCssStyles(titleLocator.nth(i), "JosefinSans");

            }

        }
    }

    public void getTitleCss() {
        String allSecondTitlePath = "h2";
        Locator titleLocator = page.locator(allSecondTitlePath);

        Object[] cssObjects2 = getCssStyles(titleLocator.nth(0), "JosefinSans");

    }

    public void getTitleContent() {

    }

    public void getPragrpahCss() {

    }

    public void getPragrpahContent() {

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

    public Boolean extractFirstFont(String fontString, String actlFontFamily) {
        // Split the font string by commas and trim whitespace from each font name
        String[] fontNames = fontString.split(",");
        char[] charActl = actlFontFamily.toCharArray();

        if (fontNames.length > 0) {
            // Remove underscores from the first font name only
            String firstFont = fontNames[0].trim().replace("_", "");
            char[] charFont = firstFont.toCharArray();
            for (int i = 0; i < charActl.length; i++) {
                if (charActl[i] != charFont[i]) {
                    return false;
                }
            }
            // return firstFont;
            return true;
        } else {
            return false; // Return null if the input string is empty
        }
    }

    public Object[] getCssStyles(Locator titleLocator, String actlFontFamily) {

        titleLocator.scrollIntoViewIfNeeded();
        String fontSize = (String) titleLocator.evaluate("element => window.getComputedStyle(element).fontSize");
        // String colors = (String) titleLocator.evaluate("element =>
        // window.getComputedStyle(element).color");
        String fontFamilys = (String) titleLocator.evaluate("element => window.getComputedStyle(element).fontFamily");
        String fontWeight = (String) titleLocator.evaluate("element => window.getComputedStyle(element).fontWeight");
        // String color=convertRGBtoHex(colors);

        Boolean fontFamily = extractFirstFont(fontFamilys, actlFontFamily);

        // System.out.println("Font size: " + fontSize);
        // System.out.println("Color: " + colors);
        // System.out.println("Color: " + convertRGBtoHex(color));
        // System.out.println("Font family: " + extractFirstFont(fontFamilys,"Kanit"));
        // System.out.println("Font weight: " + fontWeight);
        return new Object[] { fontSize, fontWeight, fontFamily };
    }

    // validing loans

    private String cookieButton = "//button[contains(text(),'Accept')]";
    private String loansButton = "//button[contains(text(),'Loans')]";
    private String getMyPersonalisedButton = "//button[contains(text(),'Get my personalised quote')]";
    private String editButton = "//div[contains(text(),'Edit')]";
    private String monthsSelect = "(//div[contains(@class,\"mx-1\")])[2]//select";
    private String carPurchageLabel = "//label[contains(text(),'Car Purchase')]";
    private String homeImprovementsLabel = "//label[contains(text(),'Home Improvements')]";
    private String DetConsolidationLabel = "//label[contains(text(),'Debt Consolidation')]";
    private String LivingExpensesLabel = "//label[contains(text(),' Living Expenses ')]";
    private String holidayLabel = "//label[contains(text(),'Holiday')]";
    private String mrLabel = "(//label[contains(text(),'Mr')])[1]";
    private String mrsLabel = "//label[contains(text(),'Mrs')]";
    private String msLabel = "//label[contains(text(),'Ms')]";
    private String missLabel = "//label[contains(text(),'Miss')]";
    private String firstNameInput = "input#firstName";
    private String secondNameInput = "input#lastName";
    private String dayInput = "//input[@placeholder='DD']";
    private String monthInput = "//input[@placeholder='MM']";
    private String yearInput = "//input[@placeholder='YYYY']";
    private String emailInput = "input#email";
    private String mobileNumberInput = "input#phone";
    private String postcodeInput = "input#postcode";
    private String findMyAddressButton = "//button[contains(text(),'Find my address')]";
    private String selectAddress = "//select[@formcontrolname=\"addressFormat\"]";
    private String lessThan3YearsAgaoButton = "//label[contains(text(),'Less than 3 years ago')]";
    private String MoreThan3YearsAgoButton = "//label[contains(text(),'More than 3 years ago')]";
    private String incomeInput = "input#income";
    private String financiallyDependentOnYou0Label = "//label[contains(text(),'0')]";
    private String financiallyDependentOnYou1Label = "//label[contains(text(),'1')]";
    private String financiallyDependentOnYou2Label = "//label[contains(text(),'2')]";
    private String financiallyDependentOnYou3PlushLabel = "//label[contains(text(),'3')]";
    private String selectLivingSituation = "//div[contains(@class,'col-span-12 dropdown')]//select";
    private String mortageInput = "input#mortage";
    private String employmentStatusSelect = "//div[contains(@class,'md:col-span-9')]//select";
    private String jobTitleInput = "//div[contains(@class,'ng-input')]//input";
    private String yourEmployerNameInput = "input[aria-label=\"EmployerName\"]";
    private String quoteText = "(//lightning-layout-item[contains(@class,'bordered')])[2]";
    private String unfortunately = "//h1";
    private String checkYourEligibilityButton = "//button[contains(text(),'Check your eligibility')]";
    private String aroText = "//span[contains(text(),'We need to ask you some questions so that')]";

    // Validaing Loans Api calling or not

    public String validatingLoansAPICallingOrnot(Integer currentlyIncome, Integer mortage, String employmentStatus,
            String aro ,String vQuote, String rjQuote,String aroRjQuote) {
        
        page.locator(cookieButton).click();
        page.locator(loansButton).click();
        page.locator(getMyPersonalisedButton).click();

        if (employmentStatus.equals("em")) {
            page.locator(editButton).click();
            page.locator(monthsSelect).selectOption("12");
        }
        page.locator(carPurchageLabel).click();
        page.locator(missLabel).click();
        page.locator(firstNameInput).fill("KULJIT");
        page.locator(secondNameInput).fill("WINTERBOURNE");
        page.locator(dayInput).fill("28");
        page.locator(monthInput).fill("01");
        page.locator(yearInput).fill("1995");
        page.locator(emailInput).fill("anil9898757@yopmail.com");
        page.locator(mobileNumberInput).fill("07507665989");
        page.locator(postcodeInput).fill("NW4 3AB");
        page.locator(findMyAddressButton).click();
        page.locator(findMyAddressButton).click();
        page.locator(selectAddress).selectOption("1 Central Mansions  Watford Way  London NW4 3AB");
        page.locator(MoreThan3YearsAgoButton).click();
        page.locator(incomeInput).fill(Integer.toString(currentlyIncome));
        page.locator(financiallyDependentOnYou0Label).click();
        page.locator(selectLivingSituation).selectOption("OWNER_WITH_MORTGAGE");
        page.locator(mortageInput).fill(Integer.toString(mortage));
        page.locator(employmentStatusSelect).selectOption(employmentStatus);
        if (employmentStatus.equals("em")) {
            page.locator(jobTitleInput).fill("Account Director");
            page.keyboard().press("Enter");
            page.locator(yourEmployerNameInput).fill("Gojoko");
        }
        page.locator(getMyPersonalisedButton).click();

        try {
            String quoteTextVisible = page.locator(quoteText).textContent();
            String offerURL = "";
            if (quoteTextVisible.equals("Your loan quote")) {
                offerURL = page.url();
                System.out.println("Offer URL: " + offerURL);
                offerURL = getBaseUrl(offerURL);
            }

            if(rjQuote.equals("rjQuote")){
                  
                Page tabs = page.waitForPopup(() -> {
                    page.click("a[target='_blank']"); 
                });
                tabs.navigate("https://uat.mycommunityfinance.co.uk/");
                List<Page> pages = tabs.context().pages();
                
                pages.get(0).close();
                return offerURL;
                   
            }else if(aroRjQuote.equals("aroRjQuote")){
                   
                Page tabs = page.waitForPopup(() -> {
                    page.click("a[target='_blank']"); 
                });
                tabs.navigate("https://uat.mycommunityfinance.co.uk/");
                List<Page> pages = tabs.context().pages();
                pages.get(0).close();
                return offerURL;
            }

            return offerURL;

        } catch (Exception e) {

            if (aro.equals("aro")) {
                // redirecting ot aro website
                page.locator(checkYourEligibilityButton).click();
                page.waitForTimeout(25000);
                // String gettingAroTexts = page.locator(aroText).textContent();
                String aroUrl = "";
                // if (gettingAroTexts.equals(
                // "Your Loan")) {
                aroUrl = page.url();
                System.out.println("Aro Offer URL: " + aroUrl);
                aroUrl = getBaseUrl(aroUrl);
                // }
                return aroUrl;

            } else {
                String quoteRejectedTextVisible = page.locator(unfortunately).textContent();
                String quoteRejectedURL = "";
                if (quoteRejectedTextVisible.equals(
                        "Unfortunately, we can't offer you a loan right now.  But there may be other options for you.")) {
                    quoteRejectedURL = page.url();
                    System.out.println("Quote Rejected URL: " + quoteRejectedURL);
                    quoteRejectedURL = getBaseUrl(quoteRejectedURL);
                }

                return quoteRejectedURL;
            }

        }

    }

    public String getBaseUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return url.getProtocol() + "://" + url.getHost() + url.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Read data from Excel sheet

    
}
