@file:Suppress("unused")

package aoc.y22

import aoc.Puzzle

private fun parse(line: String): Pair<IntRange, IntRange> {
    val (first, second) = line.split(",", limit = 2)

    fun p(s: String): IntRange {
        val (a, b) = s.split("-", limit = 2)
        return IntRange(a.toInt(), b.toInt())
    }

    return Pair(p(first), p(second))
}

private fun IntRange.overlapCompletely(other: IntRange): Boolean {
    return first in other && last in other || other.first in this && other.last in this
}

private fun IntRange.overlapABit(other: IntRange): Boolean {
    return first in other || last in other || other.first in this || other.last in this
}

class D4P1 : Puzzle {
    override fun solve(input: String): Any {
        return input.lineSequence()
            .map { parse(it) }
            .filter { (a, b) -> a.overlapCompletely(b) }
            .count()
    }
}

class D4P2 : Puzzle {
    override fun solve(input: String): Any {
        return input.lineSequence()
            .map { parse(it) }
            .filter { (a, b) -> a.overlapABit(b) }
            .count()
    }
}
