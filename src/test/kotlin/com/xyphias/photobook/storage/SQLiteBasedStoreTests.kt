package com.xyphias.photobook.storage

import kotlin.io.path.createTempFile

class SQLiteBasedStoreTests : RepositoryContract() {
    private val dbFile = createTempFile("photo-book", ".db")
    private val jdbcUrl = "jdbc:sqlite:$dbFile"

    override val repository: Repository = SQLiteBasedStore.Companion.createFor(jdbcUrl)
}