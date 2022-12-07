@file:Suppress("unused")

package aoc.y22

import aoc.Puzzle

private fun Sequence<String>.shittyNameForFunction() = fold(mutableListOf(0)) { acc, item ->
    if (item.isBlank()) {
        acc.add(0)
    } else {
        acc[acc.size - 1] += item.toInt()
    }

    acc
}

class D1P1 : Puzzle {
    override fun solve(input: String): Any {
        return input.lineSequence()
            .shittyNameForFunction()
            .max()
    }
}

class D1P2 : Puzzle {
    override fun solve(input: String): Any {
        return input.lineSequence()
            .shittyNameForFunction()
            .sortedDescending()
            .take(3)
            .sum()
    }
}