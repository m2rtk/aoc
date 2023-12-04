@file:Suppress("unused")

package aoc.y23.d1

import aoc.Puzzle

class D1P1 : Puzzle {
    override fun solve(input: String): Any {
        return input.lineSequence()
            .map { it.first { i -> i.isDigit() }.toString() + it.last { i -> i.isDigit() } }
            .map { it.toInt() }
            .sum()
    }
}

/*
This is admittedly an invalid solution BUT it gave me the correct answer
 */
class D1P2 : Puzzle {

    private val map = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9",
    )

    private fun String.normalize(): String {
        return replace(Regex(map.keys.joinToString("|"))) { map[it.value]!! }
    }

    override fun solve(input: String): Any {
        return input.lineSequence()
            .map { it.normalize() }
            .map { it.first { i -> i.isDigit() }.toString() + it.last { i -> i.isDigit() } }
            .map { it.toInt() }
            .sum()
    }
}