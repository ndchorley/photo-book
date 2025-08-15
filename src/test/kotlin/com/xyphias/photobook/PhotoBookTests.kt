package com.xyphias.photobook

import com.xyphias.photobook.Environment.DEVELOPMENT
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.webdriver.Http4kWebDriver
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo

class PhotoBookTests {
    private val app = createAppFor(DEVELOPMENT)
    private val browser = Http4kWebDriver(app)

    @Test
    fun `a photo can be added and viewed`() {
        val url = "http://photos.com/an-image.jpg"
        val title = "Sunset from Rouen"
        
        navigateToHomePage()
        addPhoto(url, title)
        
        landOnPhotoPage()
        canSeePhotoWithDetails(url, title)
    }
    
    @Test
    fun `a photo that doesn't exist can't be viewed`() {
        navigateToPhotoPageFor(id = "does-not-exist")
        
        seesPhotoNotFoundPage()
    }

    private fun navigateToHomePage() {
        browser.navigate().to("/")
    }

    private fun addPhoto(url: String, title: String) {
        val urlInput = browser.findElement(By.id("url"))
        urlInput.sendKeys(url)
        
        val titleInput = browser.findElement(By.id("title"))
        titleInput.sendKeys(title)
        
        val submitButton = browser.findElement(By.name("submit"))
        submitButton.submit()
    }

    private fun landOnPhotoPage() {
        expectThat(browser.currentUrl!!).contains(Regex("/photo/.*"))
    }

    private fun canSeePhotoWithDetails(url: String, title: String) {
        val imgElement = browser.findElement(By.tagName("img"))
        val h2Element = browser.findElement(By.tagName("h2"))
        
        expect {
            that(imgElement.getAttribute("src")).isEqualTo(url)
            that(h2Element.text).isEqualTo(title)
        }
    }

    private fun navigateToPhotoPageFor(id: String) {
        browser.navigate().to("/photo/$id")
    }

    private fun seesPhotoNotFoundPage() {
        expectThat(browser.status!!).isEqualTo(NOT_FOUND)
    }
}
