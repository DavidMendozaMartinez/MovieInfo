package com.davidmendozamartinez.movieinfo.data.local

import androidx.paging.DataSource
import androidx.room.*
import com.davidmendozamartinez.movieinfo.data.local.model.MovieLocal

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(movie: MovieLocal)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): DataSource.Factory<Int, MovieLocal>

    @Delete
    suspend fun deleteFavorite(movie: MovieLocal)

    @Query("SELECT EXISTS (SELECT 1 FROM favorites WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean

}