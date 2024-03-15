package com.qa.amplifi_capital.tests;

import java.util.List;

import org.testng.Assert;

import org.testng.annotations.Test;

import com.qa.amplifi_capital.base.BaseTest;

public class CreditUnionsPageTest extends BaseTest{
	@Test (priority = 1)
    public void creditUnionsPageNavigationTest(){
        creditUnionsPage=homePage.navigateToCreditUnionsPage();
        String actCreditUnionsTitle=creditUnionsPage.getCreditUnionsTitle();
        System.out.println(actCreditUnionsTitle);
        Assert.assertEquals(actCreditUnionsTitle, "Credit Unions | Amplifi Capital UK");
    }

    @Test (priority = 2)
    public void creditUnionsPageTitleTest() {
        String actualTitle = creditUnionsPage.getCreditUnionsTitle();
        Assert.assertEquals(actualTitle, "Credit Unions | Amplifi Capital UK");
    }

    @Test (priority = 3)
    public void creditUnionsPageURLTest() {
        String actualURL = creditUnionsPage.getCreditUnionsURL();
        Assert.assertEquals(actualURL, "https://uat.amplificapital.com/credit-unions");
    }

    @Test (priority = 5)
    public void creditUnionsPageButtonsTest() {
        String[] expectedButtonTextList = { "Contact us →", "Read more about us →"};

        List<String> actualButtonTextList = creditUnionsPage.ValidateCreditUnionButtons();
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
    public void creditUnionsPageContentsTest() {
        creditUnionsPage.ValidateCreditUnionsPageContents();
    }

    @Test (priority = 6)
    public void creditUnionsPageH1TagTest() {
        creditUnionsPage.ValidateOneH1Tag();
    }
}
