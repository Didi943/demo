package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.example.Page.Register;

public class RegisterPom {
    WebDriver driver;
    Register Re;

    @Before
    public void Setup() {
        driver = new ChromeDriver();
        Re = new Register(driver);
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
    public void TestRegister() {

        Re.ClickLink();
        Re.registerDyhia("Didi12", "Didi12", "Didi12@gmail.com", "Didi123", "Didi123");

    }

}
