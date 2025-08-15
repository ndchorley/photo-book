package com.xyphias.photobook

import com.xyphias.photobook.Environment.DEVELOPMENT
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.webdriver.Http4kWebDriver
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo

class PhotoBookTests {
    private val app = createAppFor(DEVELOPMENT)
    private val browser = Http4kWebDriver(app)
    
    @Test
    fun `a photo can be added and viewed`() {
        val photo =
            NewPhoto(
                url = "http://photos.com/an-image.jpg",
                title = "Sunset from Rouen",
                notes = """|The trees were in the way and it was quite a dark shot.
                           | Couldn't get a good angle.
                           |""".trimMargin()
            )
        
        navigateToHomePage()
        addPhoto(photo)
        
        landOnPhotoPage().canSeePhoto(photo)
    }
    
    @Test
    fun `a photo that doesn't exist can't be viewed`() {
        navigateToPhotoPageFor(id = "does-not-exist")
        
        seesPhotoNotFoundPage()
    }

    private fun navigateToHomePage() {
        browser.navigate().to("/")
    }

    private fun addPhoto(photo: NewPhoto) {
        val urlInput = browser.findElement(By.id("url"))
        urlInput.sendKeys(photo.url)
        
        val titleInput = browser.findElement(By.id("title"))
        titleInput.sendKeys(photo.title)
        
        val notesInput = browser.findElement(By.id("notes"))
        notesInput.sendKeys(photo.notes)
        
        val submitButton = browser.findElement(By.name("submit"))
        submitButton.submit()
    }

    private fun landOnPhotoPage(): PhotoPage {
        expectThat(browser.currentUrl!!).contains(Regex("/photo/.*"))
        
        return PhotoPage(browser)
    }

    private fun navigateToPhotoPageFor(id: String) {
        browser.navigate().to("/photo/$id")
    }

    private fun seesPhotoNotFoundPage() {
        expectThat(browser.status!!).isEqualTo(NOT_FOUND)
    }
}
