@file:Suppress("unused")

package aoc.y15

import aoc.Puzzle


class D1P1 : Puzzle {
    override fun solve(input: String): String {
        var floor = 0

        for (char in input) {
            when (char) {
                '(' -> floor++
                ')' -> floor--
            }
        }

        return floor.toString()
    }
}

class D1P2 : Puzzle {
    override fun solve(input: String): String {
        var floor = 0

        for (i in input.indices) {
            when (input[i]) {
                '(' -> floor++
                ')' -> floor--
            }

            if (floor < 0) {
                return (i+1).toString()
            }
        }

        throw RuntimeException()
    }
}
