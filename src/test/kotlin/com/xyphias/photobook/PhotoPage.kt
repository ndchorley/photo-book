package com.xyphias.photobook

import org.http4k.webdriver.Http4kWebDriver
import org.openqa.selenium.By
import strikt.api.expect
import strikt.assertions.isEqualTo

class PhotoPage(private val browser: Http4kWebDriver) {
    fun canSeePhoto(photo: NewPhoto) {
        val imgElement = browser.findElement(By.tagName("img"))
        val h2Element = browser.findElement(By.tagName("h2"))
        val notesElement = browser.findElement(By.id("notes"))

        expect {
            that(imgElement.getAttribute("src")).isEqualTo(photo.url)
            that(h2Element.text).isEqualTo(photo.title)
            that(notesElement.text).isEqualTo(photo.notes.withoutNewLines())
        }
    }

    private fun String.withoutNewLines(): String =
        replace(System.lineSeparator(), "")
}
