package com.example.gitapp.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CacheUserEntity::class], version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase(){
    abstract fun cacheDao(): CacheDao
}