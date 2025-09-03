package com.xyphias.photobook

import com.xyphias.photobook.adding.NewPhoto
import com.xyphias.photobook.pageobjects.ListingPage
import org.http4k.core.Status.Companion.OK
import org.http4k.webdriver.Http4kWebDriver
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDateTime
import java.time.Month


class ListingPhotosTests {
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
        val title = "Sunrise in London"
        val id =
            aPhoto
                .withTitle(title)
                .andTakenOn(fourteenthAugustAt549)
                .wasAdded()

        browser
            .navigateToListingPage()
            .cannotSeeTheNoPhotosMessage()
            .butCanSeeARowForAPhoto()
            .withDateAndTimeTaken("14-08-2025 05:49")
            .andTitle(title)
            .andALinkToViewIt(id)
    }

    private fun NewPhoto.wasAdded(): Id = dependencies.repository.add(this)

    private val aPhoto =
        NewPhoto(
            "https://example.com",
            "A photo",
            "It was good",
            LocalDateTime.MIN
        )

    private val fourteenthAugustAt549: LocalDateTime =
        LocalDateTime.of(2025, Month.AUGUST, 14, 5, 49)

}

private fun NewPhoto.andTakenOn(takenOn: LocalDateTime): NewPhoto =
    copy(takenOn = takenOn)

private fun NewPhoto.withTitle(title: String): NewPhoto =
    copy(title = title)

private fun Http4kWebDriver.navigateToListingPage(): ListingPage {
    navigate().to("/list")

    expectThat(this.status!!).isEqualTo(OK)

    return ListingPage(this)
}
