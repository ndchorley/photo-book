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
        
        landOnPhotoPage()
        canSeePhoto(photo)
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

    private fun landOnPhotoPage() {
        expectThat(browser.currentUrl!!).contains(Regex("/photo/.*"))
    }

    private fun canSeePhoto(photo: NewPhoto) {
        val imgElement = browser.findElement(By.tagName("img"))
        val h2Element = browser.findElement(By.tagName("h2"))
        val notesElement = browser.findElement(By.id("notes"))
        
        expect {
            that(imgElement.getAttribute("src")).isEqualTo(photo.url)
            that(h2Element.text).isEqualTo(photo.title)
            that(notesElement.text).isEqualTo(photo.notes.withoutNewLines())
        }
    }

    private fun navigateToPhotoPageFor(id: String) {
        browser.navigate().to("/photo/$id")
    }

    private fun seesPhotoNotFoundPage() {
        expectThat(browser.status!!).isEqualTo(NOT_FOUND)
    }
}

private fun String.withoutNewLines(): String =
    replace(System.lineSeparator(), "")
