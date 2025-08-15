package com.xyphias.photobook

import com.xyphias.photobook.Environment.DEVELOPMENT
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.webdriver.Http4kWebDriver
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class PhotoBookTests {
    private val app = createAppFor(DEVELOPMENT)
    private val browser = Http4kWebDriver(app)
    
    @Test
    fun `a photo can be added and viewed`() {
        val photo =
            NewPhoto(
                url = "http://photos.com/an-image.jpg",
                title = "Sunset from Rouen",
                notes = """|The trees were in the way and it was quite a dark shot.
                           | Couldn't get a good angle.
                           |""".trimMargin()
            )

        browser
            .navigateToHomePage()
            .addPhoto(photo)
            .canSeePhoto(photo)
            .withTitle(photo.title)
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
