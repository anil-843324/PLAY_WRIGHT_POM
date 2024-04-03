package com.qa.gojoko.tests;



import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.gojoko.base.BaseTest;

public class HomePageTest extends BaseTest {

   // @Test
   // public void homePageTitleTest() {
   //    String actualTitle = homePage.getHomePageTitle();
   //    Assert.assertEquals(actualTitle, "Gojoko Technologies");
   //    // Assert.assertEquals(actualTitle, AppContant.LEARN_ABOUT_OUR_HISTORY);
   // }

   // @Test
   // public void homePageURLTest() {
   //    String actualURL = homePage.getHomePageURL();
   //    Assert.assertEquals(actualURL, "https://d3l1mrajc2xxq2.cloudfront.net/");
   // }

   // @Test
   // public void validatMoreThanJustAFintechSectionTest() {
   //    homePage.getTitleCss();
   //    homePage.getTitleContent();
   //    homePage.getPragrpahCss();
   //    homePage.getPragrpahContent();
   // }

   // @Test
   // public void validatNewEraOfCreditUnionsSectionTest() {
   //    homePage.getTextCss();

   // }

   // @Test
   // public void validatMeetOurTeamSectionTest() {
   //    homePage.getTextCss();

   // }

   // @Test
   // public void validatOurPartnersSectionTest() {
   //    homePage.getTextCss();

   // }

   // @Test
   // public void validatOurNetworkSectionTest() {
   //    homePage.getTextCss();

   // }

   // @Test
   // public void validatContactUsSectionTest() {
   //    homePage.getTextCss();

   // }

   @Test (priority = 1)
   public void ValidateLoansOfferUrlGeneratingOrNotTest() {
      String actualOfferURL = homePage.validatingLoansAPICallingOrnot(60000, 200, "em",null,"vQuote",  "rjQuote","aroRjQuote");
      System.out.println("TestPage " + actualOfferURL);
      Assert.assertEquals(actualOfferURL, "https://uat.customer.mycommunityfinance.co.uk/s/loan-offer");
   }

   @Test (priority = 2)
   public void ValidateQuoteGetsRejectedOrNotWithInvalidData() {
      String actualQuoteRejectedURL = homePage.validatingLoansAPICallingOrnot(10000, 1000, "un",null,"vQuote",  "rjQuote","aroRjQuote");
      System.out.println("TestPage " + actualQuoteRejectedURL);
      Assert.assertEquals(actualQuoteRejectedURL, "https://uat.mycommunityfinance.co.uk/loans/quote-rejected");
   }

   @Test (priority = 3)
   public void ValidateIfaQuoteGetsRejectedThenRedirectsToTheAROpageOrNotTest() {
      System.out.println(
            "Please activate the UAT VPN so that we can validate this test case; otherwise, the test case will fail:");
      String aroActualQuoteRejectedURL = homePage.validatingLoansAPICallingOrnot(10000, 1000, "un","aro","vQuote",  "rjQuote","aroRjQuote");
      System.out.println("TestPage " + aroActualQuoteRejectedURL);
      Assert.assertEquals(aroActualQuoteRejectedURL, "https://marketplace.aro-sandbox.co.uk/resume-application-partner/");
   }



   // @Test
   // public void validateCssDescription(){
   // homePage.getTextCss();
   // }

   // @Test
   // public void homePageButtonsTest() {
   // String[] expectedButtonTextList = { "Learn about our history →", "For credit
   // unions →", "For investors →",
   // "Meet the team →", "Contact us →" };

   // List<String> actualButtonTextList = homePage.ValidateButtons();
   // // Ensure the sizes of both lists are the same
   // Assert.assertEquals(actualButtonTextList.size(),
   // expectedButtonTextList.length);

   // // Iterate over both lists simultaneously
   // for (int i = 0; i < actualButtonTextList.size(); i++) {
   // String actualButtonText = actualButtonTextList.get(i).toLowerCase();
   // String expectedButtonText = expectedButtonTextList[i].toLowerCase();
   // // Compare the actual and expected values
   // Assert.assertEquals(actualButtonText, expectedButtonText);
   // }

   // }

   // @Test
   // public void homePageFooterLinksTest() {
   // homePage.ValidateFooterLinks();
   // }

   // @Test
   // public void homePageLegalsTest(){

   // homePage.ValidateContentsOfLegalsPage();
   // }

}
