package com.qa.amplifi_capital.tests;

import com.qa.amplifi_capital.base.BaseTest;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class InvestorsPageTest  extends BaseTest{
	

	@Test (priority = 1)
    public void investorsPageNavigationTest(){
        investorsPage=homePage.navigateToInvestorsPage();
        String investorsTitle=investorsPage.getInvestorsPageTitle();
        System.out.println(investorsTitle);
        Assert.assertEquals(investorsTitle, "Investors | Amplifi Capital UK");
    }

    @Test (priority = 2)
    public void aboutPageTitleTest() {
        String actualTitle = investorsPage.getInvestorsPageTitle();
        Assert.assertEquals(actualTitle, "Investors | Amplifi Capital UK");
    }

    @Test (priority = 3)
    public void aboutPageURLTest() {
        String actualURL = investorsPage.getInvestorsPageURL();
        Assert.assertEquals(actualURL, "https://uat.amplificapital.com/our-services-for-investors");
    }

    @Test (priority = 5)
    public void aboutPageButtonsTest() {
        String[] expectedButtonTextList = { "Contact us →", "Read more about us →"};

        List<String> actualButtonTextList = investorsPage.ValidateInvestorsButtons();
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
        investorsPage.ValidateInvestorsButtons();
    }

    @Test (priority = 6)
    public void aboutPageH1TagTest() {
        investorsPage.ValidateOneH1Tag();
    }

}
