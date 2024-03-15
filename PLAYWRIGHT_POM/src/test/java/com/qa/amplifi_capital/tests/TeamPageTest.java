package com.qa.amplifi_capital.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.amplifi_capital.base.BaseTest;

public class TeamPageTest extends BaseTest{
	@Test (priority = 1)
    public void teamPageNavigationTest(){
        teamPage=homePage.navigateToTeamPage();
        String actTeamTitle=teamPage.getTeamPageTitle();
        System.out.println(actTeamTitle);
        Assert.assertEquals(actTeamTitle, "Team | Amplifi Capital UK");
    }


    @Test (priority = 7)
    public void teamPageTitleTest() {
        String actualTitle = teamPage.getTeamPageTitle();
        Assert.assertEquals(actualTitle, "Team | Amplifi Capital UK");
    }

    @Test (priority = 3)
    public void teamPageURLTest() {
        String actualURL = teamPage.getTeamPageURL();
        Assert.assertEquals(actualURL, "https://uat.amplificapital.com/team");
    }

    @Test (priority = 5)
    public void teamPageButtonsTest() {
        String[] expectedButtonTextList = { "Learn more →"};

        List<String> actualButtonTextList = teamPage.ValidateTeamButtons();
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
    public void teamPageContentsTest() {
        teamPage.ValidateTeamPageContents();
    }

    @Test (priority = 6)
    public void teamPageH1TagTest() {
        teamPage.ValidateOneH1Tag();
    }

    @Test (priority = 2)
    public void validateCardsContents() {
        teamPage.TeamcardsContent();
    }

}
