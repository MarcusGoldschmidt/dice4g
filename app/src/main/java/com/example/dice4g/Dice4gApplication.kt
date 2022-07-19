package com.example.dice4g

import android.app.Application
import android.content.res.Configuration
import com.example.dice4g.app.InstanceManager

class Dice4gApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        InstanceManager.createInstance(this.baseContext)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}