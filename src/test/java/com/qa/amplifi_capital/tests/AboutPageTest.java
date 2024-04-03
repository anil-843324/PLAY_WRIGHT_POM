package com.qa.amplifi_capital.tests;
import java.util.List;
import org.testng.Assert;

import org.testng.annotations.Test;

import com.qa.amplifi_capital.base.BaseTest;


public class AboutPageTest extends BaseTest{
	
	@Test (priority = 1)
    public void aboutPageNavigationTest(){
        aboutPage=homePage.navigateToAboutPage();
        String actAboutTitle=aboutPage.getAboutPageTitle();
        System.out.println(actAboutTitle);
         Assert.assertEquals(actAboutTitle, "About | Amplifi Capital UK");
    }

    @Test (priority = 2)
    public void aboutPageTitleTest() {
        String actualTitle = aboutPage.getAboutPageTitle();
        Assert.assertEquals(actualTitle, "About | Amplifi Capital UK");
    }

    @Test (priority = 3)
    public void aboutPageURLTest() {
        String actualURL = aboutPage.getAboutPageURL();
        Assert.assertEquals(actualURL, "https://uat.amplificapital.com/about");
    }

    @Test (priority = 5)
    public void aboutPageButtonsTest() {
        String[] expectedButtonTextList = { "For credit unions →", "For investors →"};

        List<String> actualButtonTextList = aboutPage.ValidateButtons();
        // Ensure the sizes of both lists are the same
        Assert.assertEquals(actualButtonTextList.size(), expectedButtonTextList.length);

        // Iterate over both lists simultaneously
        for (int i = 0; i < actualButtonTextList.size(); i++) {
            String actualButtonText = actualButtonTextList.get(i).toLowerCase();
            String expectedButtonText = expectedButtonTextList[i].toLowerCase();
            // Compare the actual and expected values
            Assert.assertEquals(actualButtonText, expectedButtonText);
        }

    }

    @Test (priority = 4)
    public void aboutPageContentsTest() {
        aboutPage.ValidateAboutPageContents();
    }

    @Test (priority = 6)
    public void aboutPageH1TagTest() {
        aboutPage.ValidateOneH1Tag();
    }


}
