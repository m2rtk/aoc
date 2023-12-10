@file:Suppress("unused")

package aoc.y23.d9

import aoc.Puzzle

internal data class History(val initial: List<Int>) {
    val prediction: Int by lazy {
        val sequences = mutableListOf(initial.toMutableList())

        while (!sequences.last.all { it == 0 }) {
            val new = mutableListOf<Int>()

            for ((a, b) in sequences.last.windowed(2, step = 1)) {
                new.add(b - a)
            }

            sequences.add(new)
        }

        for ((lower, higher) in sequences.reversed().windowed(2, step = 1)) {
            higher.add(higher.last + lower.last)
        }

        sequences[0].last
    }

    val predictionStart: Int by lazy {
        val sequences = mutableListOf(initial.toMutableList())

        while (!sequences.last.all { it == 0 }) {
            val new = mutableListOf<Int>()

            for ((a, b) in sequences.last.windowed(2, step = 1)) {
                new.add(b - a)
            }

            sequences.add(new)
        }

        for ((lower, higher) in sequences.reversed().windowed(2, step = 1)) {
            higher.add(0, higher.first - lower.first)
        }

        sequences[0].first
    }
}

private fun String.parse(): List<History> = lines()
    .map { it.split(Regex("\\s+")).map { i -> i.toInt() } }
    .map { History(it.toList()) }

class D9P1 : Puzzle {
    override fun solve(input: String): Any {
        return input.parse().sumOf { it.prediction }
    }
}

class D9P2 : Puzzle {
    override fun solve(input: String): Any {
        return input.parse().sumOf { it.predictionStart }
    }
}