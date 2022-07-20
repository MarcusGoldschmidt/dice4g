package com.example.dice4g.ui.profiles

import android.app.Application
import androidx.lifecycle.*
import com.example.dice4g.app.InstanceManager
import com.example.dice4g.infra.models.Profile
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val manager = InstanceManager.getInstance(app.applicationContext)
    private val profileDao = manager.db.profileDao()
    private val resultDao = manager.db.resultDao()

    private val _profiles = MutableLiveData<List<Profile>>(listOf())
    val profiles: LiveData<List<Profile>> = _profiles

    private val _profileName = MutableLiveData<String>("Player")
    val profileName: LiveData<String> = _profileName

    fun setName(name: String) {
        _profileName.value = name
    }

    fun loadProfiles() = viewModelScope.launch {
        _profiles.value = manager.db.profileDao().getAll()
    }

    fun addProfile() = viewModelScope.launch {
        val profile = Profile(
            Name = _profileName.value!!
        )
        profileDao.insertAll(profile)
        _profiles.value = listOf(profile) + _profiles.value!!
    }

    fun removeProfile(id: String) = viewModelScope.launch {
        if (profileDao.countProfiles() <= 1L) {
            return@launch
        }

        if (id == profileDao.findGuest()?.id) {
            return@launch
        }

        val profile = profileDao.findById(id) ?: return@launch

        profileDao.delete(profile)
        resultDao.removeResultByProfileId(id)

        _profiles.value = _profiles.value?.filter {
            it.id != id
        } ?: listOf()

        if (profile == manager.getCurrentProfile()) {
            manager.setCurrentProfile(profileDao.findGuest() ?: profileDao.getAll().first())
        }
    }

    fun useProfile(id: String) = viewModelScope.launch {
        val profile = profileDao.findById(id) ?: return@launch
        manager.setCurrentProfile(profile)
    }
}