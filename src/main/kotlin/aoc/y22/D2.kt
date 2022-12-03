package aoc.y22

import aoc.lines

private class D2 {
    enum class Hand(val value: Int) {
        ROCK(1), PAPER(2), SCISSORS(3);

        fun winsAgainst() = when (this) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }

        fun losesAgainst() = when (this) {
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }

        companion object {
            fun parse(s: String) = when (s) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> throw RuntimeException()
            }
        }
    }

    enum class Result(val value: Int) {
        WIN(6), DRAW(3), LOSE(0);

        companion object {
            fun parse(s: String) = when (s) {
                "X" -> LOSE
                "Y" -> DRAW
                "Z" -> WIN
                else -> throw RuntimeException()
            }
        }
    }

    fun handForResult(h1: Hand, expected: Result): Hand {
        return when (expected) {
            Result.DRAW -> h1
            Result.LOSE -> h1.winsAgainst()
            else -> h1.losesAgainst()
        }
    }

    fun play(me: Hand, other: Hand): Result {
        return when {
            me == other -> Result.DRAW
            me.winsAgainst() == other -> Result.WIN
            me.losesAgainst() == other -> Result.LOSE
            else -> throw RuntimeException()
        }
    }

    fun calc(h1: Hand, h2: Hand): Int {
        return play(h2, h1).value + h2.value
    }

    fun t1(input: Sequence<String>): Int {
        return input
            .map { it.split(" ") }
            .map { it.map { h -> Hand.parse(h) } }
            .map { calc(it[0], it[1]) }
            .sum()
            .also { println(it) }
    }

    fun t2(input: Sequence<String>): Int {
        return input
            .map { it.split(" ") }
            .map { Pair(Hand.parse(it[0]), Result.parse(it[1])) }
            .map { calc(it.first, handForResult(it.first, it.second)) }
            .sum()
            .also { println(it) }
    }
}

fun main() {
    fun sample() = lines("/aoc/y22/D2_sample.txt")
    fun input() = lines("/aoc/y22/D2_input.txt")

    val d2 = D2()

    d2.t1(sample())
    d2.t1(input())
    d2.t2(sample())
    d2.t2(input())
}

