package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Asteroid(
    @PrimaryKey val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable {

    /**
     * Called for talkback support
     * */
    fun isAsteroidPotentiallyHazardous(): String {
        return "This asteroid is ${if (isPotentiallyHazardous) "potentially hazardous" else "not potentially hazardous"}"
    }
}