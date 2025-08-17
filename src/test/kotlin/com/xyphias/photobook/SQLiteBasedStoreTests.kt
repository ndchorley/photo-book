package com.xyphias.photobook

import kotlin.io.path.createTempFile

class SQLiteBasedStoreTests : RepositoryContract() {
    private val dbFile = createTempFile("photo-book", ".db")
    private val jdbcUrl = "jdbc:sqlite:$dbFile"

    override val repository: Repository = SQLiteBasedStore.createFor(jdbcUrl)
}
