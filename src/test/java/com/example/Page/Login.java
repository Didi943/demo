package com.example.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login {
    WebDriver driver;
    // Les locators
    By LinkLogin = By.cssSelector(".ico-login");
    By Email = By.id("Email");
    By Password = By.id("Password");
    By BtnLogin = By.cssSelector(".button-1.login-button");
    By Account = By.cssSelector(".account");

    // Les actions
    public Login(WebDriver driver) {
        this.driver = driver;
    }

    public void ClickLink() {
        driver.findElement(LinkLogin).click();

    }

    public void loginDyhia(String email, String password) {
        driver.findElement(Email).sendKeys(email);
        driver.findElement(Password).sendKeys(password);
        driver.findElement(BtnLogin).click();
    }

    public boolean isAccountDisplayed() {
        return driver.findElement(Account).isDisplayed();
    }

}
