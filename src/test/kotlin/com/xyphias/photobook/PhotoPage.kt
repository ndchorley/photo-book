package com.xyphias.photobook

import org.http4k.webdriver.Http4kWebDriver
import org.openqa.selenium.By
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo

class PhotoPage(private val browser: Http4kWebDriver) {
    fun canSeePhoto(photo: NewPhoto): PhotoPage {
        expectThat(browser.currentUrl!!).contains(Regex("/photo/.*"))

        val imgElement = browser.findElement(By.tagName("img"))
        val notesElement = browser.findElement(By.id("notes"))

        expect {
            that(imgElement.getAttribute("src")).isEqualTo(photo.url)
            that(notesElement.text).isEqualTo(photo.notes.withoutNewLines())
        }

        return this
    }

    fun withTitle(title: String) {
        val h2Element = browser.findElement(By.tagName("h2"))

        expectThat(h2Element.text).isEqualTo(title)
    }

    private fun String.withoutNewLines(): String =
        replace(System.lineSeparator(), "")
}
