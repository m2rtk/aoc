package aoc.y22

import aoc.lines

private class D4 {
    private fun parse(line: String): Pair<IntRange, IntRange> {
        val (first, second) = line.split(",", limit = 2)

        fun p(s: String): IntRange {
            val (a, b) = s.split("-", limit = 2)
            return IntRange(a.toInt(), b.toInt())
        }

        return Pair(p(first), p(second))
    }

    private fun IntRange.overlapsCompletely(other: IntRange): Boolean {
        return this.first <= other.first && this.last >= other.last
    }

    private fun IntRange.overlapsABit(other: IntRange): Boolean {
        return this.any { other.contains(it) }
    }

    fun t1(sequence: Sequence<String>) {
        sequence
            .map { parse(it) }
            .filter { it.first.overlapsCompletely(it.second) || it.second.overlapsCompletely(it.first) }
            .count()
            .also { println(it) }
    }

    fun t2(sequence: Sequence<String>) {
        sequence
            .map { parse(it) }
            .filter { it.first.overlapsABit(it.second) }
            .count()
            .also { println(it) }
    }
}

fun main() {
    fun sample() = lines("/aoc/y22/D4_sample.txt")
    fun input() = lines("/aoc/y22/D4_input.txt")

    val d = D4()

    d.t1(sample())
    d.t1(input())
    d.t2(sample())
    d.t2(input())
}