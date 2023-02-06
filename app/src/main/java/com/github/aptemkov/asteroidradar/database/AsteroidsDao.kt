package com.github.aptemkov.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.aptemkov.asteroidradar.Asteroid
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidsDao {

    @Query("SELECT * FROM asteroids_database ORDER BY closeApproachDate ASC")
    fun getAllAsteroids(): Flow<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(vararg asteroids: Asteroid)

    @Query("SELECT * FROM asteroids_database WHERE id = :id")
    fun getById(id: Long): Flow<Asteroid>

}