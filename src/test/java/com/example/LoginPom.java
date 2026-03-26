package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.chrome.ChromeDriver;

import com.example.Page.Login;

public class LoginPom {
    WebDriver driver;
    Login log;

    @Before
    public void Setup() {
        driver = new ChromeDriver();
        log = new Login(driver);
        driver.get("https://demowebshop.tricentis.com");

    }

    @After
    public void TearDown() {
        driver.manage().deleteAllCookies();
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    
    @Test

    public void TestLogin() {

        log.ClickLink();
        assertEquals("https://demowebshop.tricentis.com/login", driver.getCurrentUrl());
        log.loginDyhia("Didi12@gmail.com", "Didi123");
        assertTrue(log.isAccountDisplayed());

    }
}