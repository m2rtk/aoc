@file:Suppress("unused")

package aoc.y22.d3

import aoc.Puzzle

private fun <T> findDup(sets: List<Set<T>>): Set<T> = sets.reduce { acc, ts -> acc.intersect(ts) }

private fun priorityOf(it: Char): Int {
    return if (it.isLowerCase()) {
        it.code - 96
    } else {
        it.code - 38
    }
}

private fun String.splitInto2Pieces(): List<String> {
    return listOf(substring(0, length / 2), substring(length / 2))
}

class D3P1 : Puzzle {
    override fun solve(input: String): Any {
        return input.lineSequence()
            .map { it.splitInto2Pieces() }
            .map { it.map { i -> i.toSet() } }
            .map { findDup(it) }
            .sumOf { priorityOf(it.first()) }
    }
}

class D3P2 : Puzzle {
    override fun solve(input: String): Any {
        return input.lineSequence()
            .chunked(3)
            .map { it.map { i -> i.toSet() } }
            .map { findDup(it) }
            .sumOf { priorityOf(it.first()) }
    }
}