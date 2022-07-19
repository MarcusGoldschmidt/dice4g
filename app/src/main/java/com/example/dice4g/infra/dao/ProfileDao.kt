package com.example.dice4g.infra.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dice4g.infra.models.Profile
import java.util.UUID

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    fun getAll(): List<Profile>

    @Query("SELECT * FROM profile WHERE name = 'Guest'")
    fun findGuest(): Profile?

    @Query("SELECT * FROM profile WHERE id IN (:profileIds)")
    fun loadAllByIds(profileIds: List<String>): List<Profile>

    @Insert
    fun insertAll(vararg users: Profile)

    @Delete
    fun delete(user: Profile)
}