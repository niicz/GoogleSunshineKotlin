package com.niicz.sunshinekotlin.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(
    entities = [(WeatherEntry::class)],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}