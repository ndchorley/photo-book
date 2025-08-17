package com.xyphias.photobook

import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.webdriver.Http4kWebDriver
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.time.LocalDateTime
import java.time.Month.JULY

class PhotoBookTests {
    private val app = createAppFrom(TestDependencies())
    private val browser = Http4kWebDriver(app)
    
    @Test
    fun `a photo can be added and viewed`() {
        val photo =
            NewPhoto(
                url = "http://photos.com/an-image.jpg",
                title = "Sunset from Rouen",
                notes = """|The trees were in the way and it was quite a dark shot.
                           | Couldn't get a good angle.
                           |""".trimMargin(),
                takenOn =
                    LocalDateTime
                        .of(2025, JULY, 15, 12, 35)
            )

        browser
            .navigateToHomePage()
            .addPhoto(photo)
            .landsOnPhotoPage()
            .canSeePhotoAt(photo.url)
            .withTitle(photo.title)
            .andNotes(photo.notes)
            .takenOn("15 July 2025 at 12:35")
    }
    
    @Test
    fun `a photo that doesn't exist can't be viewed`() {
        browser
            .navigateToPhotoPageFor(id = "does-not-exist")
            .seesPhotoNotFoundPage()
    }
}

private fun Http4kWebDriver.navigateToHomePage(): HomePage {
    navigate().to("/")

    return HomePage(this)
}

private fun Http4kWebDriver.navigateToPhotoPageFor(id: String): Http4kWebDriver {
    navigate().to("/photo/$id")

    return this
}

private fun Http4kWebDriver.seesPhotoNotFoundPage() {
    expectThat(status!!).isEqualTo(NOT_FOUND)
}
