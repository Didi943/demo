package com.example.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Register {
    WebDriver driver;

    // Les locators

    By LinkRegister = By.cssSelector(".ico-register");
    By Gender = By.id("gender-female");
    By FisrtName = By.id("FirstName");
    By LastName = By.id("LastName");
    By Email = By.id("Email");
    By Password = By.id("Password");
    By ComfirmPassword = By.id("ConfirmPassword");
    By BtnRegister = By.id("register-button");

    // Les actions

    public Register(WebDriver driver) {
        this.driver = driver;
    }

    public void ClickLink() {
        driver.findElement(LinkRegister).click();

    }

    public void registerDyhia(
            String firstname, String lastname,
            String email,
            String password,
            String comfirmPassword) {

        driver.findElement(Gender).click();
        driver.findElement(FisrtName).sendKeys(firstname);
        driver.findElement(LastName).sendKeys(lastname);
        driver.findElement(Email).sendKeys(email);
        driver.findElement(Password).sendKeys(password);
        driver.findElement(ComfirmPassword).sendKeys(comfirmPassword);
        driver.findElement(BtnRegister).click();
    }

}
