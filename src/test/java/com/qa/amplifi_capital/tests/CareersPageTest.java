package com.qa.amplifi_capital.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.amplifi_capital.base.BaseTest;

public class CareersPageTest extends BaseTest{
	
	
	 @Test (priority = 1)
	    public void carriesPageNavigationTest(){
	        careersPage=homePage.navigateToCaeersPage();
	        String actlCareersTitle=careersPage.getCareersPageTitle();
	        System.out.println(actlCareersTitle);
	        Assert.assertEquals(actlCareersTitle, "Careers | Amplifi Capital UK");
	    }

	    @Test (priority = 2)
	    public void careersPageTitleTest() {
	        String actualTitle = careersPage.getCareersPageTitle();
	        Assert.assertEquals(actualTitle, "Careers | Amplifi Capital UK");
	    }

	    @Test (priority = 3)
	    public void careersPageURLTest() {
	        String actualURL = careersPage.getCareersPageURL();
	        Assert.assertEquals(actualURL, "https://uat.amplificapital.com/careers");
	    }

	    @Test (priority = 5)
	    public void careersPageButtonsTest() {
	        String[] expectedButtonTextList = { "View our vacancies", "Apply now →"};

	        List<String> actualButtonTextList = careersPage.ValidateButtons();
	        // Ensure the sizes of both lists are the same
	        Assert.assertEquals(actualButtonTextList.size(), expectedButtonTextList.length);

	        // Iterate over both lists simultaneously
	        for (int i = 0; i < actualButtonTextList.size(); i++) {
	            String actualButtonText = actualButtonTextList.get(i).toLowerCase().replaceAll("\\s", "");
	            String expectedButtonText = expectedButtonTextList[i].toLowerCase().replaceAll("\\s", "");
	            // Compare the actual and expected values
	            Assert.assertEquals(actualButtonText, expectedButtonText);
	        }

	    }

	    @Test (priority = 4)
	    public void careersPageContentsTest() {
	        careersPage.ValidateCareersPageContents();
	    }

	    @Test (priority = 6)
	    public void careersPageH1TagTest() {
	        careersPage.ValidateOneH1Tag();
	    }


}
