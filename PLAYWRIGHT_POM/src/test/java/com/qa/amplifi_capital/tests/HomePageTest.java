package com.qa.amplifi_capital.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.amplifi_capital.base.BaseTest;


public class HomePageTest extends BaseTest{
	

	@Test
    public void homePageTitleTest() {
        String actualTitle = homePage.getHomePageTitle();
        Assert.assertEquals(actualTitle, "Amplifi Capital UK - Building a Financial Community");
        // Assert.assertEquals(actualTitle, AppContant.LEARN_ABOUT_OUR_HISTORY);
    }

    @Test
    public void homePageURLTest() {
        String actualURL = homePage.getHomePageURL();
        Assert.assertEquals(actualURL, "https://uat.amplificapital.com");
    }

    @Test
    public void homePageButtonsTest() {
        String[] expectedButtonTextList = { "Learn about our history →", "For credit unions →", "For investors →",
                "Meet the team →", "Contact us →" };

        List<String> actualButtonTextList = homePage.ValidateButtons();
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

    @Test
    public void homePageContentsTest() {
        homePage.ValidateHompePageContents();
    }

    @Test
    public void homePageH1TagTest() {
        homePage.ValidateOneH1Tag();
    }

    @Test
    public void homePageNewsCarouselTest() {
        homePage.ValidateHomePageNewsCarousel();
    }

    @Test
    public void homePageFooterLinksTest() {
        homePage.ValidateFooterLinks();
    }

    @Test
    public void homePageLegalsTest(){
     
    	homePage.ValidateContentsOfLegalsPage();
    }
    

    
    
    
    
}
