package com.xyphias.photobook.storage

import com.xyphias.photobook.Repository
import com.xyphias.photobook.SQLiteBasedStore
import kotlin.io.path.createTempFile

class SQLiteBasedStoreTests : RepositoryContract() {
    private val dbFile = createTempFile("photo-book", ".db")
    private val jdbcUrl = "jdbc:sqlite:$dbFile"

    override val repository: Repository = SQLiteBasedStore.Companion.createFor(jdbcUrl)
}