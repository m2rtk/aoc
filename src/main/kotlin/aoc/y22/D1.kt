package aoc.y22

import aoc.lines

private class D1 {
    private fun Sequence<String>.shittyNameForFunction() = fold(mutableListOf(0)) { acc, item ->
        if (item.isBlank()) {
            acc.add(0)
        } else {
            acc[acc.size - 1] += item.toInt()
        }

        acc
    }

    fun t1(sequence: Sequence<String>) {
        sequence
            .shittyNameForFunction()
            .max()
            .also { println(it) }
    }

    fun t2(sequence: Sequence<String>) {
        sequence
            .shittyNameForFunction()
            .sortedDescending()
            .take(3)
            .sum()
            .also { println(it) }
    }
}

fun main() {
    fun sample() = lines("/aoc/y22/D1_sample.txt")
    fun input() = lines("/aoc/y22/D1_input.txt")

    val d = D1()

    d.t1(sample())
    d.t1(input())
    d.t2(sample())
    d.t2(input())
}