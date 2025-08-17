package com.xyphias.photobook

fun createAppFrom(dependencies: Dependencies): PhotoBookApp {
    return PhotoBookApp(
        dependencies.renderTemplate,
        dependencies.repository
    )
}
