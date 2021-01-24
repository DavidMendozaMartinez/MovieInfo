package com.davidmendozamartinez.movieinfo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.davidmendozamartinez.movieinfo.data.local.model.MovieLocal

@Database(
    entities = [MovieLocal::class],
    version = 1,
    exportSchema = false
)
abstract class MovieInfoDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: MovieInfoDatabase? = null

        fun getInstance(context: Context): MovieInfoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieInfoDatabase::class.java, "MovieInfo.db"
            ).build()
    }
}
