package com.qa.amplifi_capital.base;


import java.util.Properties;


import org.testng.annotations.AfterTest;

import org.testng.annotations.BeforeTest;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.qa.amplifi_capital.factory.PlaywrightFactory;
import com.qa.amplifi_capital.pages.AboutPage;
import com.qa.amplifi_capital.pages.CareersPage;
import com.qa.amplifi_capital.pages.ContactPage;
import com.qa.amplifi_capital.pages.CreditUnionsPage;
import com.qa.amplifi_capital.pages.HomePage;
import com.qa.amplifi_capital.pages.InvestorsPage;
import com.qa.amplifi_capital.pages.TeamPage;



public class BaseTest {

    PlaywrightFactory pf;
    Page page;
	protected HomePage homePage;
    protected AboutPage aboutPage;
    protected CreditUnionsPage creditUnionsPage;
    protected InvestorsPage investorsPage;
    protected ContactPage contactPage;
    protected CareersPage careersPage;
    protected TeamPage teamPage;
	BrowserContext	browserContext;
	Properties prop;

    @BeforeTest
    public void setup() {
        pf = new PlaywrightFactory();
        prop=pf.init_prop();
        Object[] browserObjects = pf.initBrowser(prop);
        page = (Page) browserObjects[0];
        browserContext = (BrowserContext) browserObjects[1];
        homePage = new HomePage(page, browserContext);

    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }
}
