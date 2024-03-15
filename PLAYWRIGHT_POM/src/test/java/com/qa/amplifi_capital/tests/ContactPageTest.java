package com.qa.amplifi_capital.tests;

import org.testng.Assert;

import org.testng.annotations.Test;

import com.qa.amplifi_capital.base.BaseTest;



public class ContactPageTest extends BaseTest{
	
	@Test (priority = 1)
    public void contactPageNavigationTest(){
        contactPage=homePage.navigateToContactPage();
        String actlContactTitle=contactPage.getContactPageTitle();
        System.out.println(actlContactTitle);
        Assert.assertEquals(actlContactTitle, "Contact | Amplifi Capital UK");
    }

    @Test (priority = 2)
    public void contactPageTitleTest() {
        String actualTitle = contactPage.getContactPageTitle();
        Assert.assertEquals(actualTitle, "Contact | Amplifi Capital UK");
    }

    @Test (priority = 3)
    public void contactPageURLTest() {
        String actualURL = contactPage.getContactPageURL();
        Assert.assertEquals(actualURL, "https://uat.amplificapital.com/contact");
    }


    @Test (priority = 4)
    public void contactPageContentsTest() {
        contactPage.ValidateContactPageContents();
    }

    @Test (priority = 5)
    public void ValidatingWithIncorrectEmailType() {
        String actlurl=contactPage.validateSubmitForm("QA_Testing", "invalidemail", "Testing", "Hello i am testing with playwright");
        Assert.assertEquals(actlurl, "https://uat.amplificapital.com/contact");
    }

    @Test (priority = 6)
    public void ValidatingWithEmptyName() {
        String actlurl=contactPage.validateSubmitForm( "", "testing@yopmail.com", "Testing", "Hello i am testing with playwright");

        Assert.assertEquals(actlurl, "https://uat.amplificapital.com/contact");
    }

    @Test (priority = 7)
    public void ValidatingWithEmptyEmail() {
        String actlurl=contactPage.validateSubmitForm("QA_Testing", "", "Testing", "Hello i am testing with playwright");
        Assert.assertEquals(actlurl, "https://uat.amplificapital.com/contact");
    }
    @Test (priority = 9)
    public void  ValidatingWithAllCorrectFields() {
        String actlurl=contactPage.validateSubmitForm("QA_Testing", "testing@yopmail.com", "Testing", "Hello i am testing with playwright");
        Assert.assertEquals(actlurl, "https://uat.amplificapital.com/thank-you");
    }
    @Test (priority = 8)
    public void ValidatingWithAllEmptyFields() {
        String actlurl=contactPage.validateSubmitForm("", "", "", "");
        Assert.assertEquals(actlurl, "https://uat.amplificapital.com/contact");
    }


}
