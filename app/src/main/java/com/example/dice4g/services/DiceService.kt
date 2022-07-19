package com.example.dice4g.services

import com.example.dice4g.utils.DiceType
import com.example.dice4g.infra.dao.ResultDao
import com.example.dice4g.infra.models.Profile
import com.example.dice4g.infra.models.Result

class DiceService(val resultDao: ResultDao) {
    fun roll(profile: Profile, dice: DiceType = DiceType.D6): Number {
        val diceResult = (0..dice.sizes).random()

        val result = Result(
            Value = diceResult,
            ProfileId = profile.id,
            DiceType = dice.sizes.toString()
        )

        resultDao.insertAll(result)
        return diceResult
    }
}