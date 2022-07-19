package com.example.dice4g.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dice4g.infra.dao.ProfileDao
import com.example.dice4g.infra.dao.ResultDao
import com.example.dice4g.infra.models.Profile
import com.example.dice4g.infra.models.Result

@Database(entities = [Profile::class, Result::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun resultDao(): ResultDao
}