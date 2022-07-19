package com.example.dice4g.infra.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Profile(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val Name: String,
)