package com.xyphias.photobook

import org.http4k.webdriver.Http4kWebDriver
import org.openqa.selenium.By
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo

class PhotoPage(private val browser: Http4kWebDriver) {
    fun landsOnPhotoPage(): PhotoPage {
        expectThat(browser.currentUrl!!).contains(Regex("/photo/.*"))

        return this
    }

    fun canSeePhotoAt(url: String): PhotoPage {
        val imgElement = browser.findElement(By.tagName("img"))

        expectThat(imgElement.getAttribute("src")).isEqualTo(url)

        return this
    }

    fun withTitle(title: String): PhotoPage {
        val h2Element = browser.findElement(By.tagName("h2"))

        expectThat(h2Element.text).isEqualTo(title)

        return this
    }

    fun andNotes(notes: String): PhotoPage {
        val notesElement = browser.findElement(By.id("notes"))

        expectThat(notesElement.text).isEqualTo(notes.withoutNewLines())

        return this
    }

    fun takenOn(dateAndTime: String) {
        val takenOnElement = browser.findElement(By.id("taken-on"))

        expectThat(takenOnElement.text).isEqualTo(dateAndTime)
    }

    private fun String.withoutNewLines(): String =
        replace(System.lineSeparator(), "")
}
