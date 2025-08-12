package com.xyphias.photobook

import org.http4k.webdriver.Http4kWebDriver
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import strikt.api.expectThat
import strikt.assertions.contains

class PhotoBookTests {
    private val app = PhotoBookApp()

    @Test
    fun `a photo can be added and viewed`() {
        val photoUrl = "http://photos.com/an-image.jpg"
        
        navigateToHomePage()
        addPhoto(photoUrl)
        
        landOnPhotoPage()
    }

    private val webDriver = Http4kWebDriver(app)
    
    private fun navigateToHomePage() {
        webDriver.navigate().to("/")
    }

    private fun addPhoto(url: String) {
        val urlInput = webDriver.findElement(By.id("url"))
        urlInput.sendKeys(url)
        
        val submitButton = webDriver.findElement(By.name("submit"))
        submitButton.submit()
    }

    private fun landOnPhotoPage() {
        expectThat(webDriver.currentUrl!!).contains(Regex("/photo/.*"))
    }
}
