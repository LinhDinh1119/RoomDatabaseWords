package com.example.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabase.databaseModel.Word
import kotlinx.coroutines.CoroutineScope

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE : WordRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, WordRoomDatabase::class.java, "word_database").build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}