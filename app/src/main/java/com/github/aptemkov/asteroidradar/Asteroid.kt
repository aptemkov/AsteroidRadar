package com.github.aptemkov.asteroidradar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroids_database")
data class Asteroid constructor (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean,
)