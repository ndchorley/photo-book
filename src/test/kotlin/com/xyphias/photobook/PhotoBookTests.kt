package com.xyphias.photobook

import org.http4k.webdriver.Http4kWebDriver
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo

class PhotoBookTests {
    private val app = PhotoBookApp()
    private val browser = Http4kWebDriver(app)

    @Test
    fun `a photo can be added and viewed`() {
        val url = "http://photos.com/an-image.jpg"
        
        navigateToHomePage()
        addPhoto(url)
        
        landOnPhotoPage()
        canSeePhotoFrom(url)
    }
    
    private fun navigateToHomePage() {
        browser.navigate().to("/")
    }

    private fun addPhoto(url: String) {
        val urlInput = browser.findElement(By.id("url"))
        urlInput.sendKeys(url)
        
        val submitButton = browser.findElement(By.name("submit"))
        submitButton.submit()
    }

    private fun landOnPhotoPage() {
        expectThat(browser.currentUrl!!).contains(Regex("/photo/.*"))
    }

    private fun canSeePhotoFrom(photoUrl: String) {
        val imgElement = browser.findElement(By.tagName("img"))
        
        expectThat(imgElement.getAttribute("src")).isEqualTo(photoUrl)
    }
}
