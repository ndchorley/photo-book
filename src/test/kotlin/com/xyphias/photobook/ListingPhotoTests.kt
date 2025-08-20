package com.xyphias.photobook

import com.xyphias.photobook.pageobjects.ListingPage
import org.http4k.core.Status.Companion.OK
import org.http4k.webdriver.Http4kWebDriver
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ListingPhotoTests {
    private val app = createAppFrom(TestDependencies())
    private val browser = Http4kWebDriver(app)

    @Test
    fun `a message is shown when there are no photos`() {
        browser
            .navigateToListingPage()
            .canSeeTheMessage("You have not added any photos")
    }
}

private fun Http4kWebDriver.navigateToListingPage(): ListingPage {
    navigate().to("/list")

    expectThat(this.status!!).isEqualTo(OK)
    
    return ListingPage(this)
}
