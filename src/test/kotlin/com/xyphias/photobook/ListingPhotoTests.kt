package com.xyphias.photobook

import org.http4k.core.Status.Companion.OK
import org.http4k.webdriver.Http4kWebDriver
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ListingPhotoTests {
    private val app = createAppFrom(TestDependencies())
    private val browser = Http4kWebDriver(app)

    @Test
    fun `the listing page is visible`() {
        browser.canNavigateToListingPage()
    }
}

private fun Http4kWebDriver.canNavigateToListingPage() {
    navigate().to("/list")

    expectThat(this.status!!).isEqualTo(OK)
}
