@file:Suppress("unused")

package aoc.y15

import aoc.Puzzle

private data class Point(val x: Int, val y: Int)

class D3P1 : Puzzle {
    override fun solve(input: String): String {
        val seen = mutableSetOf<Point>()
        var x = 0
        var y = 0

        seen.add(Point(x, y))

        for (c in input) {
            when (c) {
                '>' -> x++
                '<' -> x--
                'v' -> y++
                '^' -> y--
            }
            seen.add(Point(x, y))
        }

        println(seen)
        return seen.size.toString()
    }
}

class D3P2 : Puzzle {
    override fun solve(input: String): String {
        return input
    }
}