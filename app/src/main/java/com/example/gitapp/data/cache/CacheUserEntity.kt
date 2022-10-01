package com.example.gitapp.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache_user_entity")
data class CacheUserEntity(
    val login: String,
    @PrimaryKey
    val id: String,
    val avatarUrl: String
)