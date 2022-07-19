package com.example.dice4g.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dice4g.app.InstanceManager
import com.example.dice4g.utils.DiceType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    private val _text = MutableLiveData<String>("-")
    val text: LiveData<String> = _text

    private val _dice = MutableLiveData<DiceType>(DiceType.D6)
    val dice: LiveData<DiceType> = _dice

    fun changeDiceType(diceType: DiceType) {
        _dice.value = diceType
    }

    fun roll(manager: InstanceManager) {
        CoroutineScope(Dispatchers.IO).launch {
            val diceService = manager.createDiceService()

            val currentProfile = manager.getCurrentProfile()
            val result = diceService.roll(currentProfile, _dice.value!!)

            _text.postValue(result.toString())
        }
    }
}