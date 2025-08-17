package com.xyphias.photobook

import org.flywaydb.core.Flyway
import org.sqlite.SQLiteDataSource
import java.time.LocalDateTime

class SQLiteBasedStore(jdbcUrl: String) : Repository {
    private val dataSource = SQLiteDataSource().also { it.url = jdbcUrl }

    override fun add(photo: NewPhoto): Id {
        dataSource.connection.use {
            connection ->
                val statement = connection.prepareStatement(
                    "INSERT INTO Photos (url, title, notes, takenOn) VALUES (?, ?, ?, ?);"
                )

                statement.setString(1, photo.url)
                statement.setString(2, photo.title)
                statement.setString(3, photo.notes)
                statement.setString(4, photo.takenOn.toString())

                statement.execute()
                val result = statement.generatedKeys
                result.next()

                return Id(result.getString(1))
        }
    }

    override fun find(id: Id): Photo? {
        dataSource.connection.use {
            connection ->
                val statement = connection.prepareStatement(
                    "SELECT url, title, notes, takenOn FROM Photos WHERE rowid = ?;"
                )
                statement.setString(1, id.value)
                val results = statement.executeQuery()

                if (!results.next()) return null

                return Photo(
                    results.getString("url"),
                    results.getString("title"),
                    results.getString("notes"),
                    LocalDateTime.parse(results.getString("takenOn"))
                )
        }
    }

    companion object {
        fun createFor(jdbcUrl: String): SQLiteBasedStore {
            Flyway
                .configure()
                .dataSource(jdbcUrl, "", "")
                .load()
                .migrate()

            return SQLiteBasedStore(jdbcUrl)
        }
    }
}
