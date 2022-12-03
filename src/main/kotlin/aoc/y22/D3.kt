package aoc.y22

import aoc.lines

private class D3 {
    private fun <T> findDup(sets: List<Set<T>>): Set<T> = sets.reduce { acc, ts -> acc.intersect(ts) }

    private fun priorityOf(it: Char): Int {
         return if (it.isLowerCase()) { it.code - 96 } else { it.code - 38 }
    }

    private fun String.splitInto2Pieces(): List<String> {
        return listOf(substring(0, length / 2), substring(length / 2))
    }

    fun t1(sequence: Sequence<String>) {
        sequence
            .map { it.splitInto2Pieces() }
            .map { it.map { i -> i.toSet() } }
            .map { findDup(it) }
            .sumOf { priorityOf(it.first()) }
            .also { println(it) }
    }

    fun t2(sequence: Sequence<String>) {
        sequence
            .chunked(3)
            .map { it.map { i -> i.toSet() } }
            .map { findDup(it) }
            .sumOf { priorityOf(it.first()) }
            .also { println(it) }
    }
}

fun main() {
    fun sample() = lines("/aoc/y22/D3_sample.txt")
    fun input() = lines("/aoc/y22/D3_input.txt")

    val d = D3()

    d.t1(sample())
    d.t1(input())
    d.t2(sample())
    d.t2(input())
}