package com.xyphias.photobook.pageobjects

import org.http4k.webdriver.Http4kWebDriver
import org.openqa.selenium.By
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class ListingPage(private val browser: Http4kWebDriver) {
    fun canSeeTheMessage(message: String) {
        val h2Element = browser.findElement(By.tagName("h2"))
        
        expectThat(h2Element.text).isEqualTo(message)
    }

    fun cannotSeeTheNoPhotosMessage() {
        val h2Element =
            browser
                .findElements(By.tagName("h2"))
                .firstOrNull()

        expectThat(h2Element).isNull()
    }
}

