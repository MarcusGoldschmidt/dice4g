package com.example.dice4g.infra.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dice4g.infra.models.Profile
import com.example.dice4g.infra.models.Result
import java.util.*

@Dao
interface ResultDao {
    @Query("SELECT * FROM result WHERE ProfileId = :profileId")
    fun getByProfileId(profileId: String): List<Result>

    @Query("SELECT * FROM result WHERE ProfileId = :profileId ORDER BY CreatedAt DESC LIMIT :limit OFFSET :offset")
    fun getPagedList(profileId: String, limit: Int, offset: Int): List<Result>

    @Query("DELETE FROM result WHERE ProfileId = :profileId")
    fun removeResultByProfileId(profileId: String)

    @Insert
    fun insertAll(vararg users: Result)
}