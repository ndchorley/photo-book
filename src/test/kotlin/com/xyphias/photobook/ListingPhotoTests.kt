package com.xyphias.photobook

import com.xyphias.photobook.pageobjects.ListingPage
import org.http4k.core.Status.Companion.OK
import org.http4k.webdriver.Http4kWebDriver
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDateTime


class ListingPhotoTests {
    private val dependencies = TestDependencies()
    private val app = createAppFrom(dependencies)
    private val browser = Http4kWebDriver(app)

    @Test
    fun `a message is shown when there are no photos`() {
        browser
            .navigateToListingPage()
            .canSeeTheMessage("You have not added any photos")
    }

    @Test
    fun `a row is shown for a photo`() {
        aPhoto.wasAdded()

        browser
            .navigateToListingPage()
            .cannotSeeTheNoPhotosMessage()
    }

    private fun NewPhoto.wasAdded() {
        dependencies.repository.add(this)
    }

    private val aPhoto =
        NewPhoto(
            "https://example.com",
            "A photo",
            "It was good",
            LocalDateTime.MIN
        )
}

private fun Http4kWebDriver.navigateToListingPage(): ListingPage {
    navigate().to("/list")

    expectThat(this.status!!).isEqualTo(OK)
    
    return ListingPage(this)
}
