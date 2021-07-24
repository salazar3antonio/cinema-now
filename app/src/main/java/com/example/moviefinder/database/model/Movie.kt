package com.example.moviefinder.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "all_movies_table")
data class Movie(
    @PrimaryKey
    val id: Int,
    val poster: String?,
    val backdrop: String?,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val longReleaseDate: String,
    var isWantToWatch: Boolean = false
) : Parcelable