package com.xyphias.photobook

import org.flywaydb.core.Flyway
import kotlin.io.path.createTempFile

class SQLiteBasedStoreTests : RepositoryContract() {
    private val dbFile = createTempFile("photo-book", ".db")
    private val jdbcUrl = "jdbc:sqlite:$dbFile"

    init {
        Flyway
            .configure()
            .dataSource(jdbcUrl, "", "")
            .load()
            .migrate()
    }

    override val repository: Repository = SQLiteBasedStore(jdbcUrl)
}
