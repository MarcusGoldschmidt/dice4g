package com.example.dice4g.infra.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Result(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val ProfileId: String,
    val Value: Int,
    val DiceType: String,
    val CreatedAt: Long = System.currentTimeMillis(),
)