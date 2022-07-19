package com.example.dice4g.app

import android.content.Context
import androidx.room.Room
import com.example.dice4g.infra.models.Profile
import com.example.dice4g.services.DiceService


class InstanceManager(context: Context) {
    val db: AppDatabase
    private var currentProfile: Profile? = null

    init {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "dice4g"
        )
        .allowMainThreadQueries()
        .build()
    }

    fun getCurrentProfile(): Profile {
        if (currentProfile == null) {
            currentProfile = db.profileDao().findGuest()

            if (currentProfile == null) {
                currentProfile = Profile(Name = "Guest")
                db.profileDao().insertAll(currentProfile!!)
            }
        }

        return currentProfile!!
    }

    fun createDiceService(): DiceService {
        return DiceService(db.resultDao())
    }

    companion object {
        private var instance: InstanceManager? = null
        fun getInstance(context: Context): InstanceManager {
            synchronized(InstanceManager::class.java) {
                if (instance == null) {
                    instance = InstanceManager(context)
                }
            }
            return instance!!
        }

        fun createInstance(context: Context) {
            synchronized(InstanceManager::class.java) {
                if (instance == null) {
                    instance = InstanceManager(context)
                }
            }
        }
    }
}