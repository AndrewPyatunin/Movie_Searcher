package com.andreich.moviesearcher.data.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    @TypeConverter
    fun fromString(value: String?): List<String> {
        if (value == null) return emptyList()

        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromListString(list: List<String?>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListEpisode(value: String?): List<EpisodeEntity> {
        if (value == null) return emptyList()

        return Gson().fromJson(value, object : TypeToken<List<EpisodeEntity>>() {}.type)
    }

    @TypeConverter
    fun fromListEpisodeToString(list: List<EpisodeEntity?>?): String {
        return  Gson().toJson(list)
    }

    @TypeConverter
    fun fromListPersonEntityToString(list: List<PersonEntity?>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListPersonEntity(value: String?): List<PersonEntity> {
        if (value == null) return emptyList()

        return Gson().fromJson(value, object : TypeToken<List<PersonEntity>>() {}.type)
    }

    @TypeConverter
    fun fromListMovieToString(list: List<MovieEntity?>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListMovieEntity(value: String?): List<MovieEntity> {
        return Gson().fromJson(value, object : TypeToken<List<MovieEntity>>() {}.type)
    }
}