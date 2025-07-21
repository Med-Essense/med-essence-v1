package com.example.test.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.test.model.DreamEntry
import java.util.Date
import java.util.Calendar
import java.util.Locale

class DreamDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dream_database.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_DREAMS = "dreams"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_DATE = "date"
        const val COLUMN_IS_IMPORTANT = "is_important"



    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_DREAMS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_CONTENT TEXT NOT NULL,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_DATE INTEGER NOT NULL,
                $COLUMN_IS_IMPORTANT INTEGER DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DREAMS")
        onCreate(db)
    }

    // Add a new dream entry
    fun addDreamEntry(dream: DreamEntry): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, dream.title)
            put(COLUMN_CONTENT, dream.content)
            put(COLUMN_CATEGORY, dream.category)
            put(COLUMN_DATE, dream.date.time)
            put(COLUMN_IS_IMPORTANT, if (dream.isImportant) 1 else 0)
        }
        return db.insert(TABLE_DREAMS, null, values)
    }

    // Get all dreams sorted by date (newest first)
    fun getAllDreams(): List<DreamEntry> {
        val dreams = mutableListOf<DreamEntry>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_DREAMS,
            null, null, null, null, null,
            "$COLUMN_DATE DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                dreams.add(createDreamFromCursor(it))
            }
        }
        return dreams
    }

    // Get dreams by specific date (whole day)
    fun getDreamsByDate(date: Date): List<DreamEntry> {
        val db = readableDatabase
        val calendar = Calendar.getInstance().apply { time = date }
        val startOfDay = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis

        val endOfDay = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
        }.timeInMillis

        val cursor = db.query(
            TABLE_DREAMS,
            null,
            "$COLUMN_DATE BETWEEN ? AND ?",
            arrayOf(startOfDay.toString(), endOfDay.toString()),
            null, null,
            "$COLUMN_DATE DESC"
        )

        return cursor.use {
            generateSequence { if (it.moveToNext()) it else null }
                .map {
                    DreamEntry(
                        id = it.getLong(it.getColumnIndexOrThrow(COLUMN_ID)),
                        title = it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE)),
                        content = it.getString(it.getColumnIndexOrThrow(COLUMN_CONTENT)),
                        category = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                        date = Date(it.getLong(it.getColumnIndexOrThrow(COLUMN_DATE))),
                        isImportant = it.getInt(it.getColumnIndexOrThrow(COLUMN_IS_IMPORTANT)) == 1
                    )
                }
                .toList()
        }
    }

    // Get dreams by category
    fun getDreamsByCategory(category: String): List<DreamEntry> {
        val dreams = mutableListOf<DreamEntry>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_DREAMS,
            null,
            "$COLUMN_CATEGORY = ?",
            arrayOf(category),
            null,
            null,
            "$COLUMN_DATE DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                dreams.add(createDreamFromCursor(it))
            }
        }
        return dreams
    }

    // Get a single dream by ID
    fun getDreamById(id: Long): DreamEntry? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_DREAMS,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        return cursor.use {
            if (it.moveToFirst()) {
                createDreamFromCursor(it)
            } else {
                null
            }
        }
    }
    // Add this to your DreamDatabaseHelper class


    // Update an existing dream entry
    fun updateDreamEntry(dream: DreamEntry): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, dream.title)
            put(COLUMN_CONTENT, dream.content)
            put(COLUMN_CATEGORY, dream.category)
            put(COLUMN_DATE, dream.date.time)
            put(COLUMN_IS_IMPORTANT, if (dream.isImportant) 1 else 0)
        }

        return db.update(
            TABLE_DREAMS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(dream.id.toString())
        )
    }

    // Delete a dream entry
    fun deleteDreamEntry(id: Long): Int {
        val db = writableDatabase
        return db.delete(
            TABLE_DREAMS,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    // Helper method to create DreamEntry from cursor
    private fun createDreamFromCursor(cursor: android.database.Cursor): DreamEntry {
        return DreamEntry(
            id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
            content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)),
            category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)),
            date = Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE))),
            isImportant = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_IMPORTANT)) == 1
        )
    }

    // Get all unique categories
    fun getAllCategories(): List<String> {
        val categories = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.query(
            true,  // distinct
            TABLE_DREAMS,
            arrayOf(COLUMN_CATEGORY),
            null, null, null, null,
            COLUMN_CATEGORY,
            null
        )

        cursor.use {
            while (it.moveToNext()) {
                it.getString(0)?.let { category ->
                    if (category.isNotEmpty()) {
                        categories.add(category)
                    }
                }
            }
        }
        return categories
    }
}