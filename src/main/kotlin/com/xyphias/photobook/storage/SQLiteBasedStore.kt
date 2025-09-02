package com.xyphias.photobook.storage

import com.xyphias.photobook.Id
import com.xyphias.photobook.adding.NewPhoto
import com.xyphias.photobook.Photo
import com.xyphias.photobook.listing.PhotoSummary
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
                    "SELECT rowid, url, title, notes, takenOn FROM Photos WHERE rowid = ?;"
                )
                statement.setString(1, id.value)
                val results = statement.executeQuery()

                if (!results.next()) return null

                return Photo(
                    Id(results.getString("rowid")),
                    results.getString("url"),
                    results.getString("title"),
                    results.getString("notes"),
                    LocalDateTime.parse(results.getString("takenOn"))
                )
        }
    }

    override fun all(): List<PhotoSummary> {
        dataSource.connection.use {
            connection ->

            val results = 
                connection
                    .prepareStatement("SELECT rowid, title, takenOn FROM Photos")
                    .executeQuery()
            
                val photos = mutableListOf<PhotoSummary>()
            
                while (results.next()) {
                    photos.add(
                        PhotoSummary(
                            Id(results.getString("rowid")),
                            results.getString("title"),
                            LocalDateTime.parse(results.getString("takenOn"))
                        )
                    )
                }
                
                return photos.toList()
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
