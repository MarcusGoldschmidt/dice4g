package com.example.dice4g.utils

enum class DiceType(val sizes: Int) {
    D4(4),
    D6(6),
    D8(8),
    D10(10),
    D12(12),
    D20(20), ;

    override fun toString(): String {
        return "D" + this.sizes.toString()
    }
}