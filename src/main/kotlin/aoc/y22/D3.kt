package aoc.y22

import aoc.lines

private class D3 {
    private fun <T> findDup(a: Set<T>, b: Set<T>): Set<T> {
        return a.intersect(b)
    }

    private fun priorityOf(it: Char): Int {
         return if (it.isLowerCase()) { it.code - 96 } else { it.code - 38 }
    }

    fun t1(sequence: Sequence<String>) {
        sequence
            .map { Pair(it.substring(0, it.length / 2), it.substring(it.length / 2)) }
            .map { findDup(it.first.toSet(), it.second.toSet()) }
            .map { it.first() }
            .map { priorityOf(it) }
            .sum()
            .also { println(it) }
    }

    private fun findDup2(lists: List<String>): Char {
        val sets = lists.map { it.toSet() }

        return findDup(findDup(sets[0], sets[1]), sets[2]).first()
    }

    fun t2(sequence: Sequence<String>) {
        val groups = mutableListOf<List<String>>()
        var group = mutableListOf<String>()
        sequence.forEachIndexed { index, item ->
            group.add(item)
            if ((index + 1) % 3 == 0) {
                groups.add(group)
                group = mutableListOf()
            }
        }

        groups
            .map { findDup2(it) }
            .sumOf { priorityOf(it) }
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