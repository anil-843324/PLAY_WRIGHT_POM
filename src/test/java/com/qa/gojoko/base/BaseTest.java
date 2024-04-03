package com.qa.gojoko.base;

import java.util.Properties;


import org.testng.annotations.AfterTest;

import org.testng.annotations.BeforeTest;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.qa.gojoko.factory.PlaywrightFactory;
import com.qa.gojoko.pages.HomePage;




public class BaseTest {

    PlaywrightFactory pf;
    Page page;
	protected HomePage homePage;
    
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

