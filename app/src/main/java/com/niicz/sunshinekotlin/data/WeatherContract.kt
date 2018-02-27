package com.niicz.sunshinekotlin.data

import android.arch.persistence.room.*
import android.content.ContentValues
import android.provider.BaseColumns
import android.text.format.Time


class WeatherContract {

    fun normalizeDate(startDate: Long): Long {
        // normalize the start date to the beginning of the (UTC) day
        val time = Time()
        time.set(startDate)
        val julianDay = Time.getJulianDay(startDate, time.gmtoff)
        return time.setJulianDay(julianDay)
    }

    @Entity(tableName = LocationEntry.TABLE_NAME)
    class LocationEntry {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(index = true, name = COLUMN_lID)
        var lID: Long = 0

        @ColumnInfo(name = COLUMN_LOCATION_SETTING)
        var locationSetting: String = ""

        @ColumnInfo(name = COLUMN_CITY_NAME)
        var city: String = ""

        @ColumnInfo(name = COLUMN_COORD_LAT)
        var coordLat: String = ""

        @ColumnInfo(name = COLUMN_COORD_LONG)
        var coordLong: String = ""

        fun fromContentValues(values: ContentValues): LocationEntry {
            val locationEntry = LocationEntry()
            if (values.containsKey(LocationEntry.COLUMN_lID)) {
                locationEntry.lID = values.getAsLong(COLUMN_lID)
            }
            if (values.containsKey(LocationEntry.COLUMN_LOCATION_SETTING)) {
                locationEntry.locationSetting = values.getAsString(LocationEntry.COLUMN_LOCATION_SETTING)!!
            }
            if (values.containsKey(LocationEntry.COLUMN_CITY_NAME)) {
                locationEntry.city = values.getAsString(LocationEntry.COLUMN_CITY_NAME)
            }
            if (values.containsKey(LocationEntry.COLUMN_COORD_LAT)) {
                locationEntry.coordLat = values.getAsString(LocationEntry.COLUMN_COORD_LAT)
            }
            if (values.containsKey(LocationEntry.COLUMN_COORD_LONG)) {
                locationEntry.coordLong = values.getAsString(LocationEntry.COLUMN_COORD_LONG)
            }
            return locationEntry
        }

        companion object {
            const val TABLE_NAME = "location"
            const val COLUMN_lID = "lID"
            const val COLUMN_LOCATION_SETTING = "locationSetting"
            const val COLUMN_CITY_NAME = "city"
            const val COLUMN_COORD_LAT = "coordLat"
            const val COLUMN_COORD_LONG = "coordLong"
        }
    }


    @Entity(
        tableName = WeatherEntry.TABLE_NAME,
        foreignKeys = [(ForeignKey(
            entity = LocationEntry::class,
            parentColumns = [("lID")],
            childColumns = ["locationKey"],
            onDelete = ForeignKey.CASCADE
        ))]
    )
    class WeatherEntry{

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(index = true, name = COLUMN_wID)
        var wID: Long = 0
        @ColumnInfo(name = COLUMN_LOC_KEY)
        var locationKey: Long = 0
        @ColumnInfo(name = COLUMN_DATE)
        var date: String = ""
        @ColumnInfo(name = COLUMN_WEATHER_ID)
        var weatherID: String = ""
        @ColumnInfo(name = COLUMN_SHORT_DESC)
        var description: String = ""
        @ColumnInfo(name = COLUMN_MIN_TEMP)
        var min: Double = 0.0
        @ColumnInfo(name = COLUMN_MAX_TEMP)
        var max: Double = 0.0
        @ColumnInfo(name = COLUMN_HUMIDITY)
        var humidity: Double = 0.0
        @ColumnInfo(name = COLUMN_PRESSURE)
        var pressure: Double = 0.0
        @ColumnInfo(name = COLUMN_WIND_SPEED)
        var wind: Double = 0.0
        @ColumnInfo(name = COLUMN_DEGREES)
        var degrees: String = ""

        fun fromContentValues(values: ContentValues): WeatherEntry {
            val weatherEntry = WeatherEntry()
            if (values.containsKey(WeatherEntry.COLUMN_wID)) {
                weatherEntry.wID = values.getAsLong(WeatherEntry.COLUMN_wID)!!
            }
            if (values.containsKey(WeatherEntry.COLUMN_LOC_KEY)) {
                weatherEntry.locationKey = values.getAsLong(WeatherEntry.COLUMN_LOC_KEY)
            }
            if (values.containsKey(WeatherEntry.COLUMN_DATE)) {
                weatherEntry.date = values.getAsString(WeatherEntry.COLUMN_DATE)
            }
            if (values.containsKey(WeatherEntry.COLUMN_WEATHER_ID)) {
                weatherEntry.weatherID = values.getAsString(WeatherEntry.COLUMN_WEATHER_ID)
            }
            if (values.containsKey(WeatherEntry.COLUMN_SHORT_DESC)) {
                weatherEntry.description = values.getAsString(WeatherEntry.COLUMN_SHORT_DESC)
            }
            if (values.containsKey(WeatherEntry.COLUMN_MIN_TEMP)) {
                weatherEntry.min = values.getAsDouble(WeatherEntry.COLUMN_MIN_TEMP)
            }
            if (values.containsKey(WeatherEntry.COLUMN_MAX_TEMP)) {
                weatherEntry.max = values.getAsDouble(WeatherEntry.COLUMN_MAX_TEMP)
            }
            if (values.containsKey(WeatherEntry.COLUMN_HUMIDITY)) {
                weatherEntry.humidity = values.getAsDouble(WeatherEntry.COLUMN_HUMIDITY)
            }
            if (values.containsKey(WeatherEntry.COLUMN_PRESSURE)) {
                weatherEntry.pressure = values.getAsDouble(WeatherEntry.COLUMN_PRESSURE)
            }
            if (values.containsKey(WeatherEntry.COLUMN_WIND_SPEED)) {
                weatherEntry.wind = values.getAsDouble(WeatherEntry.COLUMN_WIND_SPEED)
            }
            if (values.containsKey(WeatherEntry.COLUMN_DEGREES)) {
                weatherEntry.degrees = values.getAsString(WeatherEntry.COLUMN_DEGREES)
            }
            return weatherEntry
        }

        companion object {

            const val TABLE_NAME = "weather"
            const val COLUMN_wID = BaseColumns._ID
            const val COLUMN_LOC_KEY = "locationKey"
            const val COLUMN_DATE = "date"
            const val COLUMN_WEATHER_ID = "weatherID"
            const val COLUMN_SHORT_DESC = "description"
            const val COLUMN_MIN_TEMP = "min"
            const val COLUMN_MAX_TEMP = "max"
            const val COLUMN_HUMIDITY = "humidity"
            const val COLUMN_PRESSURE = "pressure"
            const val COLUMN_WIND_SPEED = "wind"
            const val COLUMN_DEGREES = "degrees"

        }

    }
}