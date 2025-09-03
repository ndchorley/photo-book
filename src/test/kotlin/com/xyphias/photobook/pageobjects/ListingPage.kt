package com.xyphias.photobook.pageobjects

import com.xyphias.photobook.Id
import org.http4k.webdriver.Http4kWebDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class ListingPage(private val browser: Http4kWebDriver) {
    fun canSeeTheMessage(message: String) {
        val h2Element = browser.findElement(By.tagName("h2"))
        
        expectThat(h2Element.text).isEqualTo(message)
    }

    fun cannotSeeTheNoPhotosMessage(): ListingPage {
        val h2Element =
            browser
                .findElements(By.tagName("h2"))
                .firstOrNull()

        expectThat(h2Element).isNull()

        return this
    }

    fun butCanSeeARowForAPhoto(): Row {
        val rowElement =
            browser
                .findElement(By.tagName("table"))
                .findElements(By.tagName("tr"))
                .firstOrNull()

        expectThat(rowElement).isNotNull()

        return Row(rowElement!!)
    }
}

class Row(rowElement: WebElement) {
    private val tdElements = rowElement.findElements(By.tagName("td"))

    fun andTitle(title: String): Row {
        expectThat(tdElements[1].text).isEqualTo(title)
        
        return this
    }

    fun withDateAndTimeTaken(takenOn: String): Row {
        expectThat(tdElements[0].text).isEqualTo(takenOn)
        
        return this
    }

    fun andALinkToViewIt(id: Id) {
        val linkElement = tdElements[2].findElement(By.tagName("a"))
        
        expect { 
            that(linkElement.getAttribute("href"))
                .isNotNull()
                .and { isEqualTo("/photo/${id.value}") }
            
            that(linkElement.text).isEqualTo("View")
        }
    }
}

