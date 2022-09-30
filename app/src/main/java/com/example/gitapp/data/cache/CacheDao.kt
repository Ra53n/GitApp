package com.example.gitapp.data.cache

import androidx.room.*
import io.reactivex.rxjava3.core.Observable

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: CacheUserEntity)

    @Delete
    fun delete(entity: CacheUserEntity)

    @Update
    fun update(entity: CacheUserEntity)

    @Query("SELECT * FROM cache_user_entity")
    fun getAllUsers(): Observable<List<CacheUserEntity>>

    @Insert
    fun setUsers(list: List<CacheUserEntity>)

    @Query("DELETE FROM cache_user_entity")
    fun deleteAllUsers()
}