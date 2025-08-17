package com.xyphias.photobook.pageobjects

import com.xyphias.photobook.NewPhoto
import org.http4k.webdriver.Http4kWebDriver
import org.openqa.selenium.By

class HomePage(private val browser: Http4kWebDriver) {
    fun addPhoto(photo: NewPhoto): PhotoPage {
        val urlInput = browser.findElement(By.id("url"))
        urlInput.sendKeys(photo.url)

        val titleInput = browser.findElement(By.id("title"))
        titleInput.sendKeys(photo.title)

        val notesInput = browser.findElement(By.id("notes"))
        notesInput.sendKeys(photo.notes)

        val takenOnInput = browser.findElement(By.id("taken-on"))
        takenOnInput.sendKeys(photo.takenOn.toString())

        val submitButton = browser.findElement(By.name("submit"))
        submitButton.submit()

        return PhotoPage(browser)
    }
}
