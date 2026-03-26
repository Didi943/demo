package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Authentification {

    WebDriver driver;

    @Test
    public void Authentification() {
        driver = new ChromeDriver();
        // ****************************************Register**********************************************/
        driver.get("https://demowebshop.tricentis.com");
        driver.findElement(By.className("ico-register")).click();
        // Vérifier la redirection vers la page register
        assertEquals("https://demowebshop.tricentis.com/register", driver.getCurrentUrl());
        // Saisir less champs
        driver.findElement(By.id("gender-female")).click();
        String firstname = "Didi8";
        driver.findElement(By.id("FirstName")).sendKeys(firstname);
        String lastname = "Dido8";
        driver.findElement(By.id("LastName")).sendKeys(lastname);
        String domain = "@gmail.com";
        String email = firstname + '.' + lastname + domain;
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys("didi123");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("didi123");
        driver.findElement(By.id("register-button")).click();
        assertEquals("https://demowebshop.tricentis.com/registerresult/1", driver.getCurrentUrl());
        // Vérification par rapport au message
        String message = driver.findElement(By.className("result")).getText();
        assertEquals("Your registration completed", message);
        driver.findElement(By.cssSelector(".button-1.register-continue-button")).click();
        // verifier la visibilite du bouton logout
        WebElement boutonlogout = driver.findElement(By.cssSelector(".ico-logout"));
        assertTrue(boutonlogout.isDisplayed());
        // logout
        boutonlogout.click();
        // ***************************login*******************************************************
        driver.findElement(By.cssSelector(".ico-login")).click();
        assertEquals("https://demowebshop.tricentis.com/login", driver.getCurrentUrl());
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys("didi123");
        driver.findElement(By.cssSelector(".button-1.login-button")).click();
        WebElement Email = driver.findElement(By.cssSelector(".account"));
        assertTrue(Email.isDisplayed());
    }

}
